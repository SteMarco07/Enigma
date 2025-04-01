package stellino.marco.enigma;
import java.util.ArrayList;

/**
 * questa classe rappresenta un rotore della macchina enigma
 */

public class Rotore {
    private ArrayList<Integer> alphabet;
    private int rotazione;
    private char letteraRotazione;

    /**
     * costruisce un nuovo rotore
     * @param stringa stringa di 26 lettere che rappresenta la configurazione del rotore
     * @param letteraRotazione posizione iniziale del rotore
     */
    public Rotore(String stringa, String letteraRotazione) {
        stringa = stringa.toLowerCase();
        this.alphabet = new ArrayList<>();
        for (char c : stringa.toCharArray()) {
            this.alphabet.add(c - 'a');
        }
        this.letteraRotazione = (char)(letteraRotazione.toLowerCase().charAt(0) - 'a');
        this.rotazione = 0;

    }

    /**
     * modifica la configurazione del rotore cambiando la stringa di cnfigurazione del rotore e la lettera di partenza
     * @param stringa stringa di 26 lettere che rappresenta la configurazione del rotore
     * @param letteraRotazione posizione iniziale del rotore
     */
    public void modificaCombinazione(String stringa, String letteraRotazione) {
        stringa = stringa.toLowerCase();
        this.alphabet.clear();
        for (char c : stringa.toCharArray()) {
            this.alphabet.add(c - 'a');
        }
        this.letteraRotazione = (char)(letteraRotazione.toLowerCase().charAt(0) - 'a');;
        this.rotazione = 0;

    }

    /**
     * Calcola l'uscita dato in input in una fase precedente al riflessore
     * @param lettera Lettera in input da cifrare
     * @return Lettera output cifrata
     */
    public char criptaAvanti(char lettera){
        lettera = Character.toLowerCase(lettera);
        int i = (lettera - 'a' + this.rotazione)%26;
        int uscita = ( alphabet.get(i) - this.rotazione + 26)%26;
        //System.out.println((char)('a' + uscita));
        return (char)('a' + uscita);
    }

    /**
     * Calcola l'uscita dato in input in una fase successiva al riflessore
     * @param lettera Lettera in input da cifrare
     * @return Lettera output cifrata
     */
    public char criptaIndietro(char lettera){
        lettera = Character.toLowerCase(lettera);
        int i = (lettera - 'a' + this.rotazione)%26;
        int uscita = (alphabet.indexOf(i)- this.rotazione + 26)%26;
        //System.out.println((char)('a' + uscita));
        return (char)('a' + uscita);
    }

    /**
     * ruota il rotore di una posizione
     */
    public void ruota() {
        this.rotazione++;
        this.rotazione %= 26;
    }

    /**
     * verifica se il rotore ha raggiunto la posizione della lettera di rotazione
     * @return ritorna true se ha raggiunto la lettera altrimenti false
     */
    public boolean isCambioLettera(){
        return this.rotazione == this.letteraRotazione + 1;
    }

    /**
     * restituisce la posizione corrente del rotore
     * @return la posizione del rotore
     */
    public int getRotazione(){
        return this.rotazione;
    }

    /**
     * imposta la posizione del rotore
     * @param r nuova posizione del rotore (compresa tra 0 e 25)
     */
    public void setRotazione(int r){
        if ( r >= 0 && r < 26){
            this.rotazione = r;
        } else {
            this.rotazione = 0;
        }
    }

    /**
     * imposta la rotazione del rotore aumentandola o diminuendola di 1
     * @param aumenta valore booleano (true aumenta, false diminuisce)
     */
    public void setRotazione(boolean aumenta){
        if (aumenta){
            this.rotazione++;
            this.rotazione %= 26;
        } else {
            this.rotazione--;
            if (this.rotazione == -1){
                this.rotazione = 25;
            }
        }
    }



}
