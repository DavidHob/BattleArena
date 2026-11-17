// Placerar klassen i paketet org.example.
package org.example;

// Importerar Swing för dialogrutan som visas om något går fel.
import javax.swing.*;
// Importerar undantaget som kan uppstå vid anslutning eller skrivning.
import java.io.IOException;
// Importerar klassen som ansluter klienten till servern.
import java.net.Socket;
// Importerar klassen som används för att skicka matchresultatet som text.
import java.io.DataOutputStream;

// Klienten skickar färdiga matchresultat till MatchServer.
public class MatchClient {

    // Tar emot matchresultatet som Main har satt ihop.
    public void sendResult(String matchResult) {
        // Ansluter till port 6767 på samma dator. Java stänger anslutningen när blocket avslutas.
        try (Socket socket = new Socket("localhost", 6767)) {
            // Kopplar output till socketens utgående ström.
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            // Skickar texten i det format som serverns readUTF() kan läsa.
            output.writeUTF(matchResult);
        } catch (IOException e) { // Körs om det uppstår ett I/O-fel under försöket.
            // Visar ett felmeddelande för spelaren i en dialogruta.
            JOptionPane.showMessageDialog(null, "Kunde inte ansluta till matchservern.");
        } // Slut på felhanteringen.
    } // Slut på sendResult().
} // Slut på klassen MatchClient.