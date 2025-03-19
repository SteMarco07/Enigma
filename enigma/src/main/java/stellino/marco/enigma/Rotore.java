package stellino.marco.enigma;
import java.util.ArrayList;


public class Rotore {
    private final ArrayList<Integer> alphabet;
    private int rotazione;
    private String letteraRotazione;

    public Rotore(String stringa, String letteraRotazione) {
        stringa = stringa.toLowerCase();
        this.alphabet = new ArrayList<>();
        for (char c : stringa.toCharArray()) {
            this.alphabet.add(c - 'a');
        }
        this.letteraRotazione = letteraRotazione.toLowerCase();
        this.rotazione = 0;
    }

    public void modificaCombinazione(String stringa, String letteraRotazione) {
        stringa = stringa.toLowerCase();
        for (char c : stringa.toCharArray()) {
            this.alphabet.add(c - 'a');
        }
        this.letteraRotazione = letteraRotazione.toLowerCase();
    }

    /**
     * Calcola l'uscita dato in input in una fase precedente al riflessore
     * @param lettera Lettera in input (String)
     * @return Lettera output (String)
     */
    public String get_uscita_dritto( String lettera){
        lettera = lettera.toLowerCase();
        int i = (lettera.charAt(0) - 'a' + this.rotazione)%26;
        int uscita = ( alphabet.get(i) - this.rotazione + 26)%26;
        return String.valueOf((char)('a' + uscita));
    }

    /**
     * Calcola l'uscita dato in input in una fase successiva al riflessore
     * @param lettera Lettera in input (String)
     * @return Lettera output (String)
     */
    public String get_uscita_inverso(String lettera){
        lettera = lettera.toLowerCase();
        int i = (lettera.charAt(0) - 'a' + this.rotazione)%26;
        int uscita = (alphabet.indexOf(i)- this.rotazione + 26)%26;
        return String.valueOf((char)('a' + uscita));
    }

    /**
     * Funzione che si occupa dell'effettiva rotazione del dizionario valori
     */
    public void ruota() {
        this.rotazione++;
        this.rotazione %= 26;
    }

    public boolean isCambioLettera(){
        return this.rotazione == this.letteraRotazione.charAt(0) - 'a';
    }

    public int get_rotazione(){
        return this.rotazione;
    }

    public void set_rotazione(int v){
        if ( v >= 0 && v < 26){
            this.rotazione = v;
        } else {
            this.rotazione = 0;
        }
    }


}
