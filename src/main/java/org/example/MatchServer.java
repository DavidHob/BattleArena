// Placerar serverklassen i paketet org.example.
package org.example;

// Representerar fel som kan uppstå vid nätverks- eller filhantering.
import java.io.IOException;
// Används för att lyssna efter klientanslutningar.
import java.net.ServerSocket;
// Representerar anslutningen till en enskild klient.
import java.net.Socket;
// Läser matchresultatet i samma format som klienten skickar det.
import java.io.DataInputStream;
// Innehåller metoden som skriver matchresultatet till filen.
import java.nio.file.Files;
// Representerar sökvägen till historikfilen.
import java.nio.file.Path;
// Anger hur historikfilen ska öppnas när vi skriver till den.
import java.nio.file.StandardOpenOption;

// Tar emot matchresultat från klienter och sparar dem i historikfilen.
public class MatchServer {

    // Startar serverns huvudtråd.
    static void main() {
        // Öppnar port 6767. Java stänger serverSocket när try-blocket avslutas.
        try (ServerSocket serverSocket = new ServerSocket(6767)) {
            // Fortsätter att ta emot klienter så länge servern körs.
            while (true) {
                // Väntar på nästa klient och sparar den klientens anslutning.
                Socket clientSocket = serverSocket.accept();
                // Skapar en tråd som ska hantera just den här klienten.
                Thread clientThread = new Thread(() -> handleClient(clientSocket));
                // Startar klienttråden så att huvudtråden kan gå vidare till nästa accept().
                clientThread.start();
            } // Slut på ett varv i serverloopen.
        } catch (IOException e) { // Hanterar fel när servern öppnar porten eller tar emot en anslutning.
            // Skriver ut vad som gick fel i serverns konsol.
            System.err.println("Fel i matchservern: " + e.getMessage());
        } // Slut på serverns felhantering.
    } // Slut på main().

    // Läser och sparar matchresultatet från den anslutning som metoden får in.
    private static void handleClient(Socket clientSocket) {
        // Java stänger klientens anslutning när blocket avslutas, även om ett fel uppstår.
        try (clientSocket) {
            // Kopplar en DataInputStream till klientens inkommande data.
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            // Väntar på resultatet från klienten och läser det som en String.
            String matchResult = input.readUTF();
            // Visar det mottagna resultatet i serverns konsol.
            System.out.println("Servern tog emot: " + matchResult);
            // Anger filnamnet relativt serverns arbetsmapp.
            Path historyPath = Path.of("match-history.txt");
            // Använder ett gemensamt lås så att en klienttråd i taget skriver till filen.
            synchronized (MatchServer.class) {
                // Skriver resultatet följt av en radbrytning.
                Files.writeString(historyPath, matchResult + System.lineSeparator(),
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND); // Skapar filen vid behov och lägger till resultatet sist.
            } // Släpper låset så att nästa klienttråd kan skriva.
        } catch (IOException e) { // Hanterar I/O-fel för den här klienten.
            // Skriver ut vad som gick fel i serverns konsol.
            System.err.println("Fel i matchservern: " + e.getMessage());
        } // Slut på klientens felhantering.
    } // Slut på handleClient().
} // Slut på klassen MatchServer.