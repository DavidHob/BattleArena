// Placerar klassen i paketet org.example.
package org.example;

// Importerar Swing för dialogrutan som visar matchhistoriken.
import javax.swing.*;
// Importerar undantaget som kan uppstå när programmet läser filen.
import java.io.IOException;
// Importerar Files för att kunna läsa innehållet i filen.
import java.nio.file.Files;
// Importerar Path för att kunna ange sökvägen till filen.
import java.nio.file.Path;
// Dubblett av importen ovanför Path. Den här raden kan tas bort.
import java.nio.file.Files;

// Läser och visar sparade matchresultat.
public class MatchHistory {

    // static gör att Main kan anropa metoden direkt via MatchHistory.
    public static void showHistory() {
        // Anger filens sökväg relativt programmets arbetsmapp.
        Path historyPath = Path.of("match-history.txt");

        // Försöker läsa och visa historiken.
        try {
            // Läser hela filen och sparar innehållet som en String.
            String history = Files.readString(historyPath);
            // Visar texten i en dialogruta. null betyder att rutan saknar ett föräldrafönster.
            JOptionPane.showMessageDialog(null, history);
        } catch (IOException e) { // Körs om filen exempelvis saknas eller inte går att läsa.
            // Visar felmeddelandet i terminalen.
            System.out.println("Kunde inte läsa matchhistoriken");
        } // Slut på felhanteringen.
    } // Slut på showHistory().
} // Slut på klassen MatchHistory.