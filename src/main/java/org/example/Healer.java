// Placerar interfacet i samma paket som Mage.
package org.example;

// Definierar vad en klass måste kunna göra för att implementera Healer.
public interface Healer {

    // Kräver en heal-metod som tar noll argument och returnerar void.
    void heal();
} // Slut på interfacet Healer.