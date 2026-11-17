// Placerar klassen i paketet org.example.
package org.example;

// Mage bygger på ArenaCharacter och uppfyller interfacet Healer genom heal().
public class Mage extends ArenaCharacter implements Healer {

    // Konstruktorn tar emot namnet på den nya karaktären.
    public Mage(String name) {
        // Skickar namnet och 90 start-HP till ArenaCharacters konstruktor.
        super(name, 90);
    } // Slut på konstruktorn.

    // Låter kompilatorn kontrollera att attack() matchar metoden i basklassen.
    @Override
    // Utför Mages vanliga attack mot motståndaren.
    public void attack(ArenaCharacter opponent) {
        // Ger motståndaren 15 skada före eventuell skadereducering.
        opponent.takeDamage(15);
    } // Slut på den vanliga attacken.

    // Låter kompilatorn kontrollera att heal() matchar metoden i Healer.
    @Override
    // Läker Mage med upp till 20 HP.
    public void heal() {
        // Basklassens restoreHealth() ser till att HP stannar vid maxgränsen.
        restoreHealth(20);
    } // Slut på heal().

    // Utför Mages specialförmåga. Parametern opponent ingår i den gemensamma metoden.
    public String useSpecialAbility(ArenaCharacter opponent) {
        // Sparar Mages HP före läkningen.
        int healthBefore = getHealth();
        // Läker Mage. Den här förmågan påverkar Mages egna HP.
        heal();
        // Räknar ut hur mycket HP Mage faktiskt fick tillbaka.
        int newHealth = getHealth() - healthBefore;
        // Returnerar texten som Battle kan skriva ut och visa i valrutan.
        return getName() + " återfick " + newHealth + " HP.";
    } // Slut på specialförmågan.
} // Slut på klassen Mage.