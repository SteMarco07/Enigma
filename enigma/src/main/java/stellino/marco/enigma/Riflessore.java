package stellino.marco.enigma;

import java.util.ArrayList;


/**
 * questa classe rappresenta il riflessore della macchina enigma
 */

public class Riflessore {
    private final ArrayList<Integer> alphabet;

    /**
     * costruisce un riflessore
     * @param combinazione stringa di 26 lettere che rappresenta la configurazione del riflessore
     */
    public Riflessore(String combinazione) {
        combinazione = combinazione.toLowerCase();
        this.alphabet = new ArrayList<>();
        for (char c : combinazione.toCharArray()) {
            this.alphabet.add(c-'a');
        }
        //System.out.println(alphabet);
    }

    /**
     * cifra una lettera passando per il riflessore
     * @param lettera lettera da cifrare
     * @return lettera cifrata
     */
    public char cripta(char lettera) {
        lettera = Character.toLowerCase(lettera);
        int uscita = alphabet.get(lettera - 'a');
        //System.out.println((char)('a' + uscita));
        return (char)('a' + uscita);
    }

    /**
     * modifica la configurazione del riflessore con una stringa di 26 lettere
     * @param combinazione stringa di 26 lettere che rappresenta la configurazione del riflessore
     */
    public void modificaCombinazione(String combinazione) {
        combinazione = combinazione.toLowerCase();
        this.alphabet.clear();
        for (char c : combinazione.toCharArray()) {
            this.alphabet.add(c-'a');
        }
        //System.out.println(alphabet);

    }

}
