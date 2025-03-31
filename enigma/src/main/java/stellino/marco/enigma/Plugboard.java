package stellino.marco.enigma;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Plugboard {
    private HashMap<Character, Character> coppie;

    public Plugboard() {
        coppie = new HashMap<>();
    }

    public void aggiungiCoppia(char lettera1, char lettera2) {
        char l1 = Character.toLowerCase(lettera1);
        char l2 = Character.toLowerCase(lettera2);

        if (l1 < 'a' || l1 > 'z' || l2 < 'a' || l2 > 'z') {
            throw new IllegalArgumentException("Le lettere devono essere tra 'a' e 'z'");
        }

        if (l1 == l2) {
            throw new IllegalArgumentException("Una lettera non può essere collegata a se stessa");
        }

        if (coppie.containsKey(l1) || coppie.containsKey(l2)) {
            throw new IllegalArgumentException("Una delle lettere è già stata aggiunta");
        }

        coppie.put(l1, l2);
        coppie.put(l2, l1);
    }

    public void generaMappatureCasuali() {
        coppie.clear();

        ArrayList<Character> lettere = new ArrayList<>();
        for (int i = 'a'; i <= 'z'; i++) {
            lettere.add((char) i);
        }

        Collections.shuffle(lettere);

        for (int i = 0; i < 6 * 2; i += 2) {
            char l1 = lettere.get(i);
            char l2 = lettere.get(i + 1);
            aggiungiCoppia(l1, l2);
        }
    }

    public char cifra(char lettera) {
        char lower = Character.toLowerCase(lettera);
        return coppie.getOrDefault(lower, lower);
    }

    public void rimuoviCoppia(char lettera) {
        char l = Character.toLowerCase(lettera);
        if (!coppie.containsKey(l)) return;

        char associato = coppie.get(l);
        coppie.remove(l);
        coppie.remove(associato);
    }

    public void modificaCoppia(char vecchiaLettera, char nuovaLettera1, char nuovaLettera2) {
        char vecchia = Character.toLowerCase(vecchiaLettera);
        char nuova1 = Character.toLowerCase(nuovaLettera1);
        char nuova2 = Character.toLowerCase(nuovaLettera2);

        if (nuova1 < 'a' || nuova1 > 'z' || nuova2 < 'a' || nuova2 > 'z') {
            throw new IllegalArgumentException("Le nuove lettere devono essere tra 'a' e 'z'");
        }

        if (nuova1 == nuova2) {
            throw new IllegalArgumentException("Le nuove lettere non possono essere uguali");
        }

        if (!coppie.containsKey(vecchia)) {
            throw new IllegalArgumentException("La lettera da modificare non esiste nelle coppie");
        }

        char vecchioAssociato = coppie.get(vecchia);
        coppie.remove(vecchia);
        coppie.remove(vecchioAssociato);

        if (coppie.containsKey(nuova1) || coppie.containsKey(nuova2)) {
            throw new IllegalArgumentException("Una delle nuove lettere è già in uso");
        }

        coppie.put(nuova1, nuova2);
        coppie.put(nuova2, nuova1);
    }
}