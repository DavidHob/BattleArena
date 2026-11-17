// Placerar klassen i paketet org.example.
package org.example;

// Importerar typer från Swing, där JOptionPane finns.
import javax.swing.*;
// Importerar Random för att slumpa datorns handling.
import java.util.Random;
// Importerar JOptionPane direkt för spelets dialogrutor.
import javax.swing.JOptionPane;

// Battle sköter turordningen, utför handlingar och avgör vem som vinner.
public class Battle {

    // Utför en handling och returnerar en text som beskriver resultatet.
    public String executeAction(ArenaCharacter actor, ArenaCharacter opponent, Action action) {
        // Deklarerar en variabel som får sitt värde i någon av grenarna nedan.
        String actionMessage;

        // Kollar om karaktären har valt en vanlig attack.
        if (action == Action.ATTACK) {
            // Sparar motståndarens HP före attacken.
            int hp = opponent.getHealth();
            // Kör den vanliga attack som hör till karaktären som agerar.
            actor.attack(opponent);
            // Räknar ut hur mycket HP motståndaren faktiskt förlorade.
            int newHP = hp - opponent.getHealth();
            // Sätter ihop en beskrivning med båda namnen och den faktiska skadan.
            actionMessage = (actor.getName() + " attackerade " + opponent.getName() + " och gjorde " + newHP + " skada");
        } else { // Körs när handlingen är SPECIAL.
            // Utför karaktärens egen specialförmåga och sparar beskrivningen den returnerar.
            actionMessage = actor.useSpecialAbility(opponent);
        } // Slut på valet mellan attack och specialförmåga.

        // Visar vad handlingen gjorde.
        System.out.println(actionMessage);
        // Visar om handlingen var ATTACK eller SPECIAL.
        System.out.println(actor.getName() + " valde:" + action);
        // Visar HP för den karaktär som agerade.
        System.out.println(actor.getName() + " :" + actor.getHealth());
        // Visar motståndarens HP efter handlingen.
        System.out.println(opponent.getName() + " :" + opponent.getHealth());
        // Skickar tillbaka samma beskrivning så att den också kan visas i nästa valruta.
        return actionMessage;
    } // Slut på executeAction().

    // Kontrollerar om matchen är slut.
    public boolean isOver(ArenaCharacter player, ArenaCharacter computer) {
        // || betyder eller. Det räcker att en av karaktärerna är besegrad.
        return player.isDefeated() || computer.isDefeated();
    } // Slut på isOver().

    // Returnerar den vinnande karaktären efter att matchen har tagit slut.
    public ArenaCharacter getWinner(ArenaCharacter player, ArenaCharacter computer) {
        // Kollar om spelaren är besegrad.
        if (player.isDefeated()) {
            // När spelaren är besegrad har datorn vunnit.
            return computer;
        } else { // När spelaren fortfarande har HP kvar har datorn blivit besegrad.
            // Returnerar spelaren som vinnare.
            return player;
        } // Slut på valet av vinnare.
    } // Slut på getWinner().

    // Slumpar ett första val för datorns tur.
    public Action chooseComputerAction() {
        // Skapar objektet som används för att slumpa valet.
        Random random = new Random();

        // Slumpar true eller false och väljer attack vid true.
        if (random.nextBoolean()) {
            // Ger tillbaka vanlig attack.
            return Action.ATTACK;
        } else { // Körs när slumpningen ger false.
            // Ger tillbaka specialförmåga. Manakontrollen görs senare i play().
            return Action.SPECIAL;
        } // Slut på det slumpade valet.
    } // Slut på chooseComputerAction().

    // Kör matchen och returnerar vinnaren, eller null om spelaren avbryter.
    public ArenaCharacter play(ArenaCharacter player, ArenaCharacter computer) {
        // Sparar datorns senaste handling. Vid matchstart är texten tom.
        String lastComputerAction = "";

        // Upprepar turerna så länge båda karaktärerna har HP kvar.
        while (!isOver(player, computer)) {
            // Visar läget i matchen och läser spelarens val som text.
            String actionChoice = JOptionPane.showInputDialog(
                    // Visar datorns senaste handling följd av spelarens aktuella HP.
                    lastComputerAction + "\nDin HP: " + player.getHealth()
                            // Visar spelarens återstående mana på en ny rad.
                            + "\nDin mana:" + player.getMana()
                            // Visar datorns aktuella HP på en ny rad.
                            + "\nDatorns HP: " + computer.getHealth()
                            // Visar datorns återstående mana på en ny rad.
                            + "\nDatorns mana:" + computer.getMana()
                            // Lägger till en tom rad och visar de två alternativen.
                            + "\n\n1. Attack\n2. SpecialAbility"
            ); // Slut på anropet som visar valrutan.

            // Avbryt eller stängning av rutan ger null.
            if (actionChoice == null) {
                // Avslutar matchen utan en vinnare.
                return null;
            } // Slut på kontrollen för Avbryt.

            // Jämför spelarens inmatning med texten "1".
            if (actionChoice.equals("1")) {
                // Utför spelarens vanliga attack utan att dra mana.
                executeAction(player, computer, Action.ATTACK);
            } else if (actionChoice.equals("2")) { // Spelaren valde specialförmågan.
                // Försöker dra 20 mana. ! gör att blocket körs om betalningen misslyckas.
                if (!player.trySpendMana()) {
                    // Förklarar i en dialog varför förmågan inte kunde användas.
                    JOptionPane.showMessageDialog(null, "Du måste ha 20 mana");
                    // Visar spelarens valruta igen före datorns tur.
                    continue;
                } // Slut på spelarens manakontroll.

                // Utför spelarens specialförmåga efter att kostnaden har betalats.
                executeAction(player, computer, Action.SPECIAL);
            } else { // Körs när spelaren skrev något annat än "1" eller "2".
                // Visar vilka val som är giltiga.
                JOptionPane.showMessageDialog(null, "Välj 1 eller 2");
                // Börjar om vid valrutan så att spelaren får välja igen.
                continue;
            } // Slut på hanteringen av spelarens val.

            // Kontrollerar att matchen fortfarande pågår efter spelarens handling.
            if (!isOver(player, computer)) {
                // Slumpar och sparar datorns första val.
                Action computerAction = chooseComputerAction();

                // Försöker betala mana endast vid SPECIAL och går in i blocket om betalningen misslyckas.
                if (computerAction == Action.SPECIAL && !computer.trySpendMana()) {
                    // Byter till vanlig attack om datorn saknar tillräckligt med mana.
                    computerAction = Action.ATTACK;
                } // Slut på datorns manakontroll.

                // Utför datorns slutliga val och sparar resultattexten till nästa valruta.
                lastComputerAction = executeAction(computer, player, computerAction);
            } // Slut på datorns tur.
        } // Slut på matchloopen. Nu har någon blivit besegrad.

        // Hämtar karaktären som vann matchen.
        ArenaCharacter winner = getWinner(player, computer);
        // Skriver ut vinnarens namn i terminalen.
        System.out.println("Vinnaren är: " + winner.getName());
        // Ger tillbaka vinnaren till Main, som använder den för att skapa matchresultatet.
        return winner;
    } // Slut på play().
} // Slut på klassen Battle.