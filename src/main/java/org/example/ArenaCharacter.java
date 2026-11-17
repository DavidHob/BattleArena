// Placerar klassen i paketet org.example.
package org.example;

// Abstrakt basklass med gemensamma egenskaper för Warrior, Mage och Archer.
public abstract class ArenaCharacter {

    // Lagrar karaktärens namn.
    private String name;
    // Lagrar hur mycket HP karaktären har just nu.
    private int health;
    // Lagrar karaktärens högsta möjliga HP. final gör att värdet tilldelas en gång.
    private final int maxHealth;
    // Varje ny karaktär börjar med 60 mana.
    private int mana = 60;

    // Konstruktorn tar emot namn och start-HP från en underklass.
    public ArenaCharacter(String name, int health) {
        // Sparar parametern name i objektets fält name.
        this.name = name;
        // Sätter aktuellt HP till startvärdet.
        this.health = health;
        // Använder startvärdet som gräns för hur mycket karaktären kan läka.
        this.maxHealth = health;
    } // Slut på konstruktorn.

    // Låter andra klasser läsa karaktärens namn.
    public String getName() {
        // Skickar tillbaka namnet från fältet.
        return name;
    } // Slut på getName().

    // Låter andra klasser läsa karaktärens aktuella HP.
    public int getHealth() {
        // Skickar tillbaka hur mycket HP som finns kvar.
        return health;
    } // Slut på getHealth().

    // Låter andra klasser läsa hur mycket mana karaktären har kvar.
    public int getMana() {
        // Skickar tillbaka det aktuella manasaldot.
        return mana;
    } // Slut på getMana().

    // Minskar karaktärens HP med den inkommande skadan.
    public void takeDamage(int damage) {
        // Drar av skadan från aktuellt HP.
        health -= damage;

        // Kollar om träffen tog karaktären under 0 HP.
        if (health < 0) {
            // Sätter HP till 0 så att värdet aldrig blir negativt efter träffen.
            health = 0;
        } // Slut på HP-kontrollen.
    } // Slut på takeDamage().

    // Kollar om karaktären är besegrad.
    public boolean isDefeated() {
        // Returnerar true vid 0 HP och false annars.
        return health == 0;
    } // Slut på isDefeated().

    // Varje underklass bestämmer hur dess vanliga attack fungerar.
    public abstract void attack(ArenaCharacter opponent);

    // Varje underklass utför sin egen specialförmåga och returnerar en beskrivning.
    public abstract String useSpecialAbility(ArenaCharacter opponent);

    // Återställer HP upp till karaktärens maxgräns.
    public void restoreHealth(int amount) {
        // Lägger till den begärda läkningen.
        health += amount;
        // Kollar om läkningen tog karaktären över maxgränsen.
        if (health > maxHealth) {
            // Begränsar aktuellt HP till maxHealth.
            health = maxHealth;
        } // Slut på kontrollen av max-HP.
    } // Slut på restoreHealth().

    // Försöker betala 20 mana och returnerar om betalningen lyckades.
    public boolean trySpendMana() {
        // Kollar om karaktären har mindre mana än specialförmågan kostar.
        if (mana < 20) {
            // Meddelar att betalningen misslyckades.
            return false;
        } else { // Körs när karaktären har minst 20 mana.
            // Drar av kostnaden från manasaldot.
            mana -= 20;
            // Meddelar att betalningen lyckades.
            return true;
        } // Slut på mana-kontrollen.
    } // Slut på trySpendMana().
} // Slut på klassen ArenaCharacter.