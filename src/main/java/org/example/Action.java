// Placerar Action i samma paket som Battle och karaktärerna.
package org.example;

// En enum anger vilka handlingar en karaktär kan välja under sin tur.
public enum Action {
    // En vanlig attack som inte kostar mana.
    ATTACK,
    // Karaktärens specialförmåga, som kostar 20 mana när den används i Battle.
    SPECIAL
} // Slut på Action.