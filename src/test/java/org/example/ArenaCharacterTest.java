// Placerar testklassen i samma paket som karaktärerna.
package org.example;

// Importerar @Test så att JUnit hittar testmetoderna.
import org.junit.jupiter.api.Test;

// Låter oss anropa assertEquals direkt för att jämföra förväntat och faktiskt värde.
import static org.junit.jupiter.api.Assertions.assertEquals;
// Låter oss anropa assertTrue direkt för att kontrollera att ett villkor är sant.
import static org.junit.jupiter.api.Assertions.assertTrue;

// Samlar åtta tester för karaktärernas HP, attacker, läkning och försvar.
public class ArenaCharacterTest {

    @Test // Markerar metoden nedan som ett JUnit-test.
    void healthDoesNotGoBelowZero () { // Kontrollerar att HP stannar vid 0.
        // Skapar en Warrior som börjar med 120 HP.
        Warrior warrior1 = new Warrior("Test1");
        // Ger 150 skada, vilket är mer än Warriorns aktuella HP.
        warrior1.takeDamage(150);
        // Förväntar sig 0 HP. Första argumentet är förväntat, andra är faktiskt.
        assertEquals(0, warrior1.getHealth());
    } // Slut på testet för lägsta HP.

    @Test // Markerar metoden nedan som ett JUnit-test.
    void healthGetsReduced () { // Kontrollerar att skada minskar HP.
        // Skapar en Warrior med 120 HP.
        Warrior warrior2 = new Warrior("Test2");
        // Drar av 30 HP.
        warrior2.takeDamage(30);
        // Förväntar sig 90 HP eftersom 120 minus 30 är 90.
        assertEquals(90, warrior2.getHealth());
    } // Slut på skadetestet.

    @Test // Markerar metoden nedan som ett JUnit-test.
    void characterDefeated() { // Kontrollerar att en karaktär med 0 HP räknas som besegrad.
        // Skapar en Warrior med 120 HP.
        Warrior warrior3 = new Warrior("Test3");
        // Ger exakt så mycket skada som Warriorn har HP.
        warrior3.takeDamage(120);
        // Förväntar sig att isDefeated() returnerar true.
        assertTrue(warrior3.isDefeated());
    } // Slut på testet för isDefeated().

    @Test // Markerar metoden nedan som ett JUnit-test.
    void attacking() { // Kontrollerar skadan från Warriorns vanliga attack.
        // Skapar karaktären som ska attackera.
        Warrior warrior4 = new Warrior("Test4");
        // Skapar motståndaren med 120 HP.
        Warrior warrior5 = new Warrior("Test5");
        // Låter den första Warriorn attackera den andra för 10 skada.
        warrior4.attack(warrior5);
        // Förväntar sig att motståndaren har 110 HP kvar.
        assertEquals(110, warrior5.getHealth());
    } // Slut på attacktestet.

    @Test // Markerar metoden nedan som ett JUnit-test.
    void polymorph() { // Kontrollerar att Java väljer Archers attack via basklasstypen.
        // Variabelns typ är ArenaCharacter och objektets typ är Archer.
        ArenaCharacter archer1 = new Archer("Test6");
        // Skapar en Warrior med 120 HP som motståndare.
        Warrior opponent = new Warrior("Test7");
        // Java kör Archers attack eftersom objektet är en Archer.
        archer1.attack(opponent);
        // Förväntar sig 108 HP eftersom Archers attack gör 12 skada.
        assertEquals(108, opponent.getHealth());
    } // Slut på polymorfismtestet.

    @Test // Markerar metoden nedan som ett JUnit-test.
    void restoringHealth() { // Kontrollerar att läkningen stannar vid max-HP.
        // Skapar en Mage med 90 HP som både startvärde och maxgräns.
        Mage mage1 = new Mage("Test8");
        // Sänker Mages HP från 90 till 80.
        mage1.takeDamage(10);
        // Försöker återställa 20 HP. Det finns plats för 10 HP upp till maxgränsen.
        mage1.restoreHealth(20);
        // Förväntar sig att Mage stannar på 90 HP.
        assertEquals(90,mage1.getHealth());
    } // Slut på testet för max-HP.

    @Test // Markerar metoden nedan som ett JUnit-test.
    void restoreingHealth2() { // Kontrollerar Mages läkning via interfacet Healer.
        // Skapar en Mage med 90 HP.
        Mage mage2 = new Mage("Test9");
        // Båda variablerna refererar till samma Mage. Healer ger tillgång till heal().
        Healer healer1 = mage2;
        // Sänker Mages HP från 90 till 60.
        mage2.takeDamage(30);
        // Anropar Mages heal() via Healer och återställer 20 HP.
        healer1.heal();
        // Läser av samma Mage via mage2 och förväntar sig 80 HP.
        assertEquals(80,mage2.getHealth());
    } // Slut på testet för heal() via Healer.

    @Test // Markerar metoden nedan som ett JUnit-test.
    void defendHalvesDmg() { // Kontrollerar att skölden halverar exakt en träff.
        // Skapar en Warrior med 120 HP.
        Warrior warrior6 = new Warrior("Test10");
        // Höjer skölden inför nästa träff.
        warrior6.defending();
        // Skölden halverar 20 skada till 10.
        warrior6.takeDamage(20);
        // Förväntar sig 110 HP efter den första träffen.
        assertEquals(110, warrior6.getHealth());
        // Skölden är förbrukad, så nästa träff gör hela 20 skada.
        warrior6.takeDamage(20);
        // Förväntar sig 90 HP eftersom 110 minus 20 är 90.
        assertEquals(90, warrior6.getHealth());
    } // Slut på försvarstestet.
} // Slut på ArenaCharacterTest.