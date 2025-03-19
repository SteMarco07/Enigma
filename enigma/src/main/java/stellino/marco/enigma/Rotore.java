package stellino.marco.enigma;
import java.util.ArrayList;


public class Rotore {
    private final ArrayList<Integer> alphabet;
    private int rotazione;
    private String letteraRotazione;

    public Rotore(String stringa, String letteraRotazione) {
        stringa = stringa.toLowerCase();
        this.alphabet = new ArrayList<>();
        for (int i = 0; i < stringa.length(); i++) {
            this.alphabet.add((int)stringa.charAt(i)-'a');
        }
        System.out.println(this.alphabet);
        this.letteraRotazione = letteraRotazione.toLowerCase();
        this.rotazione = 0;
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

    private boolean isCambioLettera(){
        return this.rotazione == this.letteraRotazione.charAt(0) - 'a';
    }








}
