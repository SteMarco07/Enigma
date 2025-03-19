package stellino.marco.enigma;

import java.util.ArrayList;

public class Riflessore {
    private final ArrayList<Integer> alphabet;

    public Riflessore(String combinazione) {
        this.alphabet = new ArrayList<>();
        for (char c : combinazione.toCharArray()) {
            this.alphabet.add(c-'a');
        }
    }

    public String get_uscita(String lettera) {
        lettera = lettera.toLowerCase();
        int uscita = alphabet.get(lettera.charAt(0) - 'a');
        return String.valueOf((char)('a' + uscita));
    }
}
