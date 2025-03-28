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

    public char cripta(char lettera) {
        lettera = Character.toLowerCase(lettera);
        int uscita = alphabet.get(lettera - 'a');
        //System.out.println((char)('a' + uscita));
        return (char)('a' + uscita);
    }

    public void modificaCombinazione(String combinazione) {
        for (char c : combinazione.toCharArray()) {
            this.alphabet.add(c-'a');
        }
    }

}
