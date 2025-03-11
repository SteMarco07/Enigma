package stellino.marco.enigma;
import java.util.TreeMap;

public class Rotore {
    private TreeMap <Character, Character> valori;
    private int rotazione;

    public Rotore(String stringa) {
        stringa = stringa.toLowerCase();
        this.valori = new TreeMap<>();
        for (int i = 0; i < stringa.length(); i++) {
            this.valori.put((char)('a'+ i), stringa.charAt(i));
        }
        this.rotazione = 0;
    }

    /**
     * Calcola l'uscita dato in input in una fase precedente al riflessore
     * @param lettera Lettera in input (char)
     * @return Lettera output (char)
     */
    public char get_uscita_dritto( char lettera){
        return this.valori.get(lettera);
    }

    /**
     * Calcola l'uscita dato in input in una fase successiva al riflessore
     * @param lettera Lettera in input (char)
     * @return Lettera output (char)
     */
    public char get_uscita_inverso(char lettera){
        for (var i : this.valori.keySet()) {
            if (lettera == this.valori.get(i)) {
                return i;
            }
        }
        return ' ';
    }

    /**
     * Funzione che si occupa dell'effettiva rotazione del dizionario valori
     */
    private void rotazione() {
        char temp = this.valori.get(this.valori.lastKey());
        for (char i = 'z'; i >= 'b'; i--) {
            char valorePrecedente = this.valori.get((char)(i - 1));
            this.valori.put(i, valorePrecedente);
        }
        this.valori.put('a', temp);
    }

    /**
     * Gestisce la rotazione dei rotori
     * @return
     */
    public boolean ruota() {
        this.rotazione++;
        this.rotazione();
        if (this.rotazione == 26) {
            this.rotazione = 0;
            return true;
        }
        return false;
    }

    /**
     * Ritorna il valore intero della rotazione
     * @return rotazione (int)
     */
    public int get_rotazione(){
        return this.rotazione;
    }







}
