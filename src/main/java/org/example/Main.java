// Placerar klassen i paketet org.example.
package org.example;

// Importerar JOptionPane för spelets dialogrutor.
import javax.swing.JOptionPane;
// Importerar AWT-typer. Main använder inga av dem, så raden kan tas bort.
import java.awt.*;
// Importerar Random för att slumpa datorns karaktär.
import java.util.Random;

// Main visar menyn, skapar karaktärerna och startar matchen.
public class Main {

    // JVM anropar main() när du startar spelet.
    static void main() {
        // Varje nytt varv börjar med att visa huvudmenyn.
        while (true) {
            // Visar huvudmenyn och sparar spelarens svar som text.
            String menuChoice = JOptionPane.showInputDialog("1. Spela Match/2. Visa Matchhistorik");

            // Avbryt eller stängning av dialogrutan ger null.
            if (menuChoice == null) {
                // Avslutar main() och därmed spelet.
                return;
            } // Slut på kontrollen för Avbryt.

            // Kontrollerar om spelaren valde matchhistorik.
            if ("2".equals(menuChoice)) {
                // Ber MatchHistory att läsa och visa sparade matcher.
                MatchHistory.showHistory();
                // Går tillbaka till huvudmenyn när historikrutan stängs.
                continue;
            } // Slut på historikvalet.

            // Här är "1" det enda giltiga valet som återstår.
            if (!"1".equals(menuChoice)) {
                // Visar vilka val som är giltiga.
                JOptionPane.showMessageDialog(null, "Välj 1 eller 2.");
                // Hoppar över matchstarten och visar huvudmenyn igen.
                continue;
            } // Slut på kontrollen för ogiltiga menyval.

            // Frågar vad spelarens karaktär ska heta.
            String playerName = JOptionPane.showInputDialog("Vad vill du heta?");

            // Frågar igen vid tomt namn eller bara blanktecken.
            while (playerName != null && playerName.isBlank()) {
                // Förklarar varför spelaren får frågan igen.
                JOptionPane.showMessageDialog(null, "Du skrev inget namn försök igen.");
                // Sparar det nya svaret i samma variabel.
                playerName = JOptionPane.showInputDialog("Vad vill du heta?");
            } // Slut på namnloopen.

            // Kontrollerar om spelaren avbröt namnfrågan.
            if (playerName == null) {
                // Avslutar spelet utan att starta en match.
                return;
            } // Slut på kontrollen av namnfrågan.

            // Visar karaktärsvalen och sparar svaret som text.
            String characterChoice = JOptionPane.showInputDialog(
                    // Varje \n börjar en ny rad i dialogrutan.
                    "Välj karaktär:\n1. Warrior\n2. Mage\n3. Archer"
            ); // Slut på anropet som visar karaktärsvalen.

            // Kontrollerar om spelaren avbröt karaktärsvalet.
            if (characterChoice == null) {
                // Avslutar spelet.
                return;
            } // Slut på kontrollen för Avbryt.

            // ArenaCharacter är den gemensamma typen för alla tre karaktärerna.
            ArenaCharacter player;

            // Kontrollerar om spelaren valde Warrior.
            if (characterChoice.equals("1")) {
                // Skapar en Warrior med namnet som spelaren skrev in.
                player = new Warrior(playerName);
            } else if (characterChoice.equals("2")) { // Spelaren valde Mage.
                // Skapar en Mage med spelarens namn.
                player = new Mage(playerName);
            } else if (characterChoice.equals("3")) { // Spelaren valde Archer.
                // Skapar en Archer med spelarens namn.
                player = new Archer(playerName);
            } else { // Spelaren skrev något annat än 1, 2 eller 3.
                // Visar vilka karaktärsval som är giltiga.
                JOptionPane.showMessageDialog(null, "Fel input - välj mellan 1-3");
                // Avslutar spelet utan att starta en match.
                return;
            } // Slut på spelarens karaktärsval.

            // Skapar objektet som slumpar datorns karaktär.
            Random random = new Random();
            // nextInt(3) ger 0, 1 eller 2.
            int computerChoice = random.nextInt(3);

            // Lagrar datorns karaktär med den gemensamma typen ArenaCharacter.
            ArenaCharacter player2;

            // Använder Warrior om det slumpade talet är 0.
            if (computerChoice == 0) {
                // Skapar en Warrior som heter Datorn.
                player2 = new Warrior("Datorn");
            } else if (computerChoice == 1) { // Talet är 1, så datorn får Mage.
                // Skapar en Mage som heter Datorn.
                player2 = new Mage("Datorn");
            } else { // Det enda återstående talet är 2.
                // Skapar en Archer som heter Datorn.
                player2 = new Archer("Datorn");
            } // Slut på datorns karaktärsval.

            // Skapar objektet som ska köra matchen.
            Battle battle1 = new Battle();
            // Kör matchen och sparar vinnaren som play() returnerar.
            ArenaCharacter winner = battle1.play(player, player2);

            // play() returnerar null om spelaren avbryter matchen.
            if (winner == null) {
                // Avslutar spelet utan att skicka något matchresultat.
                return;
            } // Slut på kontrollen av matchens resultat.

            // Sätter ihop spelarnamnet och vinnarens namn till en textrad.
            String matchResult = "Spelare: " + player.getName() + " | Vinnare: " + winner.getName();

            // Skapar klienten som kan ansluta till matchservern.
            MatchClient client = new MatchClient();
            // Försöker skicka resultatet till servern för sparning.
            client.sendResult(matchResult);
        } // Nästa varv visar huvudmenyn igen.
    } // Slut på main().
} // Slut på klassen Main.