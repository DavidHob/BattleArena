// Placerar klassen i paketet org.example.
package org.example;

// Archer ärver bland annat namn, HP och mana från ArenaCharacter.
public class Archer extends ArenaCharacter {

    // Konstruktorn tar emot namnet när en ny Archer skapas.
    public Archer(String name) {
        // Ger namnet och 100 start-HP till basklassens konstruktor.
        super(name, 100);
    } // Slut på konstruktorn.

    // Låter kompilatorn kontrollera att attack() matchar metoden i basklassen.
    @Override
    // Utför en vanlig attack mot karaktären som skickas in som opponent.
    public void attack(ArenaCharacter opponent) {
        // Ger motståndaren 12 skada. Motståndarens takeDamage hanterar hur mycket HP som försvinner.
        opponent.takeDamage(12);
    } // Slut på den vanliga attacken.

    // Utför Archers kraftskott mot motståndaren.
    public void powerShot(ArenaCharacter opponent) {
        // Ger motståndaren 24 skada före eventuell skadereducering.
        opponent.takeDamage(24);
    } // Slut på kraftskottet.

    // Utför specialförmågan och returnerar ett meddelande om resultatet.
    public String useSpecialAbility(ArenaCharacter opponent) {
        // Sparar motståndarens HP före skottet.
        int opponentHealth = opponent.getHealth();
        // Kör kraftskottet så att motståndarens HP uppdateras.
        powerShot(opponent);
        // Räknar ut hur mycket HP motståndaren faktiskt förlorade.
        int newOpponentHealth = opponentHealth - opponent.getHealth();

        // Ger tillbaka texten som Battle kan skriva ut och visa i valrutan.
        return getName() + " använde powershot och gjorde " + newOpponentHealth + " dmg.";
    } // Slut på specialförmågan.
} // Slut på klassen Archer.