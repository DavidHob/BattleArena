// Placerar klassen i paketet org.example.
package org.example;

// Warrior ärver gemensamma metoder för bland annat HP och mana från ArenaCharacter.
public class Warrior extends ArenaCharacter {

    // Anger om skölden är uppe. En boolean börjar på false som standard.
    private boolean defending;

    // Konstruktorn tar emot namnet när en ny Warrior skapas.
    public Warrior(String name) {
        // Skickar namnet och 120 start-HP till basklassens konstruktor.
        super(name, 120);
    } // Slut på konstruktorn.

    // Låter kompilatorn kontrollera att attack() matchar metoden i basklassen.
    @Override
    // Utför en vanlig attack mot karaktären som skickas in som opponent.
    public void attack(ArenaCharacter opponent) {
        // Ger motståndaren 10 skada före eventuell skadereducering.
        opponent.takeDamage(10);
    } // Slut på den vanliga attacken.

    // Aktiverar skölden inför nästa träff.
    public void defending() {
        // Sparar att skölden är uppe.
        defending = true;
    } // Slut på defending().

    // Låter kompilatorn kontrollera att takeDamage() matchar metoden i basklassen.
    @Override
    // Tar emot inkommande skada och tar hänsyn till skölden.
    public void takeDamage(int amount) {
        // Kollar om skölden är uppe när träffen kommer.
        if (defending) {
            // Halverar skadan. Vid udda skada avrundar heltalsdivisionen nedåt.
            amount = amount / 2;
            // Förbrukar skölden så att den bara skyddar mot en träff.
            defending = false;
        } // Slut på sköldkontrollen.

        // Låter basklassen dra av skadan och hålla HP på minst 0.
        super.takeDamage(amount);
    } // Slut på takeDamage().

    // Utför specialförmågan och returnerar en beskrivning av vad som hände.
    public String useSpecialAbility(ArenaCharacter opponent) {
        // Höjer Warriors egen sköld.
        defending();
        // Ger tillbaka texten som Battle kan skriva ut och visa i valrutan.
        return getName() + " höjer skölden. Nästa träff gör 50% skada.";
    } // Slut på specialförmågan.
} // Slut på klassen Warrior.