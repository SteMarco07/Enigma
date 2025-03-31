package stellino.marco.enigma;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PlugBoard {
    private HashMap<Character, Character> coppie;
    private ArrayList<String> combinazioni;

    public PlugBoard() {
        coppie = new HashMap<>();
        combinazioni = new ArrayList<>();
    }

    //Non funziona
    public ArrayList<String> getCombinazioni() {
        //System.out.println(combinazioni);
        return this.combinazioni;
    }

    public boolean aggiungiCoppia(char lettera1, char lettera2) {
        boolean eseguibile = true;
        char l1 = Character.toLowerCase(lettera1);
        char l2 = Character.toLowerCase(lettera2);

        if (l1 < 'a' || l1 > 'z' || l2 < 'a' || l2 > 'z') {
            eseguibile = false;
        }

        if (l1 == l2) {
            eseguibile = false;
        }

        if (coppie.containsKey(l1) || coppie.containsKey(l2)) {
            eseguibile = false;
        }

        if (combinazioni.size() >= 6) {
            eseguibile = false;
        }
        if (eseguibile) {
            this.combinazioni.add("" + lettera1 + lettera2);
            //System.out.println("Creata combinazione: " +  l1 + l2);
            coppie.put(l1, l2);
            coppie.put(l2, l1);
        }
        return eseguibile;
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
        //System.out.println(""+l+associato);
        if (combinazioni.remove(""+l+associato)) {
            System.out.println("rimosso");
        }
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