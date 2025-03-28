package stellino.marco.enigma;
import java.util.ArrayList;


public class Rotore {
    private ArrayList<Integer> alphabet;
    private int rotazione;
    private char letteraRotazione;

    public Rotore(String stringa, String letteraRotazione) {
        stringa = stringa.toLowerCase();
        this.alphabet = new ArrayList<>();
        for (char c : stringa.toCharArray()) {
            this.alphabet.add(c - 'a');
        }
        this.letteraRotazione = (char)(letteraRotazione.toLowerCase().charAt(0) - 'a');
        this.rotazione = 0;

    }


    public void modificaCombinazione(String stringa, String letteraRotazione) {
        stringa = stringa.toLowerCase();
        this.alphabet.clear();
        for (char c : stringa.toCharArray()) {
            this.alphabet.add(c - 'a');
        }
        this.letteraRotazione = (char)(letteraRotazione.toLowerCase().charAt(0) - 'a');
    }

    /**
     * Calcola l'uscita dato in input in una fase precedente al riflessore
     * @param lettera Lettera in input (String)
     * @return Lettera output (String)
     */
    public String criptaAvanti(String lettera){
        lettera = lettera.toLowerCase();
        int i = (lettera.charAt(0) - 'a' + this.rotazione)%26;
        int uscita = ( alphabet.get(i) - this.rotazione + 26)%26;
        //System.out.println((char)('a' + uscita));
        return String.valueOf((char)('a' + uscita));
    }

    /**
     * Calcola l'uscita dato in input in una fase successiva al riflessore
     * @param lettera Lettera in input (String)
     * @return Lettera output (String)
     */
    public String criptaIndietro(String lettera){
        lettera = lettera.toLowerCase();
        int i = (lettera.charAt(0) - 'a' + this.rotazione)%26;
        int uscita = (alphabet.indexOf(i)- this.rotazione + 26)%26;
        //System.out.println((char)('a' + uscita));
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
        return this.rotazione == this.letteraRotazione + 1;
    }

    public int getRotazione(){
        return this.rotazione;
    }

    public void setRotazione(int v){
        if ( v >= 0 && v < 26){
            this.rotazione = v;
        } else {
            this.rotazione = 0;
        }
    }


}