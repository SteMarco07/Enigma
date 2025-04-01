package stellino.marco.enigma;

import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * questa classe rappresenta la macchina enigma nel suo complesso
 * per il funzionamento si appoggia ai metodi delle classi rotore, riflessore e plugboard
 */

public class Enigma {
    private ArrayList<Rotore> rotori;
    private Riflessore riflessore;
    private final TreeMap<String, ArrayList<String>> combinazioniRotori;
    private final TreeMap<String , String> combinazioniRiflessori;
    private PlugBoard plugBoard;

    /**
     * costruisce la macchna enigma composta di rotori e rifessori
     * @param pRotori percorso file del rotore
     * @param pRiflessori percorso file del riflessore
     * @throws IOException eccezione sull'apertura dei file
     * @throws FileNotFoundException eccezione sull'esistenza dei file
     */
    public Enigma(String pRotori, String pRiflessori) throws IOException, FileNotFoundException {
        this.rotori = new ArrayList<>();
        this.combinazioniRotori = new TreeMap<>();
        this.combinazioniRiflessori = new TreeMap<>();
        this.caricaRotori(pRotori);
        this.caricaRiflessori(pRiflessori);
        this.rotori.add(new Rotore(this.combinazioniRotori.get("I").getFirst(), this.combinazioniRotori.get("I").getLast()));
        this.rotori.add(new Rotore(this.combinazioniRotori.get("II").getFirst(), this.combinazioniRotori.get("II").getLast()));
        this.rotori.add(new Rotore(this.combinazioniRotori.get("III").getFirst(), this.combinazioniRotori.get("III").getLast()));
        this.riflessore = new Riflessore(this.combinazioniRiflessori.get("B"));
        this.plugBoard = new PlugBoard();
        //System.out.println(this.combinazioniRotori);
        //System.out.println(this.combinazioniRiflessori);

    }


    /**
     * carica i rotori da file
     * @param percorso percorso file dei rotori
     * @throws IOException eccezione sull'apertura dei file
     * @throws FileNotFoundException eccezione sull'esistenza dei file
     */
    private void caricaRotori(String percorso) throws IOException, FileNotFoundException {
        BufferedReader br = new BufferedReader( new FileReader(percorso));
        String riga;
        while ((riga = br.readLine()) != null) {
            String[] elementi;
            ArrayList<String> dati = new ArrayList<>();
            elementi = riga.split(";");
            dati.add(elementi[1]);
            dati.add(elementi[2]);
            this.combinazioniRotori.put(elementi[0], dati);

        }
        br.close();
    }

    /**
     * carica il riflessore da file
     * @param percorso percorso file del riflessore
     * @throws IOException eccezione sull'apertura dei file
     * @throws FileNotFoundException eccezione sull'esistenza dei file
     */
    private void caricaRiflessori(String percorso) throws IOException, FileNotFoundException {
        BufferedReader br = new BufferedReader( new FileReader(percorso));
        String riga;
        while ((riga = br.readLine()) != null) {
            String[] elementi;
            elementi = riga.split(";");
            this.combinazioniRiflessori.put(elementi[0], elementi[1]);
        }
        br.close();
    }

    /**
     * modifica la combinazione di un rotore
     * @param nRotore numero del rotore
     * @param nomeRotore nome del rotore
     */
    public void modificaCombinazioneRotore (int nRotore, String nomeRotore) {
        this.rotori.get(nRotore).modificaCombinazione(this.combinazioniRotori.get(nomeRotore).getFirst(), combinazioniRotori.get(nomeRotore).getLast());
        //System.out.println(this.combinazioniRotori.get(nomeRotore).getFirst() + combinazioniRotori.get(nomeRotore).getLast());
    }

    /**
     * modifica la combinazione del riflessore
     * @param nomeRiflessore nome del riflessore
     */
    public void modificaCombinazioneRiflessori (String nomeRiflessore) {
        this.riflessore.modificaCombinazione(this.combinazioniRiflessori.get(nomeRiflessore));
        //System.out.println(this.combinazioniRiflessori.get(nomeRiflessore));

    }

    /**
     * gestisce le rotazioni dei rotori
     */
    public void ruota() {
        this.rotori.getFirst().ruota();
        if (this.rotori.getFirst().isCambioLettera()) {
            this.rotori.get(1).ruota();
            if (this.rotori.get(1).isCambioLettera()) {
                this.rotori.get(2).ruota();
            }
        }
        //System.out.println("r1: " + this.rotori.getFirst().getRotazione() + " r2: " + this.rotori.get(1).getRotazione() + " r3: " + this.rotori.getLast().getRotazione());
    }

    /**
     * cripta prima che la lettera passi dal riflessore
     * @param lettera lettera da criptare
     * @return lettera criptata
     */
    private char criptaAvanti(char lettera) {
        lettera = this.plugBoard.cifra(lettera);
        for (var i : this.rotori) {
            lettera = i.criptaAvanti(lettera);
        }
        return lettera;
    }

    /**
     * cripta dopo che la lettera passi dal riflessore
     * @param lettera lettera da criptare
     * @return lettera criptata
     */
    private char criptaIndietro(char lettera) {
        for (int i = this.rotori.size() - 1; i >= 0; i--) {
            lettera = this.rotori.get(i).criptaIndietro(lettera);
        }
        return this.plugBoard.cifra(lettera);
    }

    /**
     * cripta(indipendentemente dal riflessore)
     * @param lettera lettera da criptare
     * @return lettera criptata
     */
    public char cripta(char lettera) {

        return this.criptaIndietro(this.riflessore.cripta(this.criptaAvanti(lettera)));
    }

    /**
     * restituisce la combinazione del riflessore
     * @return combinazione dle riflessore
     */
    public Set<String> getCombinazioniRiflessori() {
        return this.combinazioniRiflessori.keySet();
    }

    /**
     * restituisce la combinazione dei rotori
     * @return combinazione dei rotori
     */
    public Set<String> getCombinazioniRotori() {
        return this.combinazioniRotori.keySet();
    }

    /**
     * restituisce la rotazione di un rotore
     * @param n_rotore numero del rotore
     * @return rotazione del rotore indicato in input
     */
    public int getRotazione(int n_rotore) {
        return this.rotori.get(n_rotore).getRotazione();
    }

    /**
     * imposta la rotazione di un rotore
     * @param n_rotore numero del rotore
     * @param rotazione rotazione del rotore
     */
    public void setRotazione(int n_rotore, int rotazione) {
        this.rotori.get(n_rotore).setRotazione(rotazione);
    }

    /**
     * imposta la rotazione di un rotore aumentandola
     * @param n_rotore numero del rotore
     * @param aumenta aumento della rotazione di un rotore
     */
    public void setRotazione(int n_rotore, boolean aumenta) {
        this.rotori.get(n_rotore).setRotazione(aumenta);
    }

    /**
     * aggiunge una coppia della plugboard
     * @param lettera1 prima lettera della coppia
     * @param lettera2 seconda lettera della coppia
     * @return ritorna true se la creazione della coppia è stata possibile altrimenti false
     */
    public boolean aggiungiCoppia(char lettera1, char lettera2) {
        return this.plugBoard.aggiungiCoppia(lettera1, lettera2);
    }

    /**
     * rimuove una coppia dalla plugboard
     * @param lettera prima lettera della coppia da rimuovere
     */
    public void rimuoviCoppia(char lettera) {
        this.plugBoard.rimuoviCoppia(lettera);
    }

    public void modificaCoppia(char vecchia, char nuova1, char nuova2) {
        this.plugBoard.modificaCoppia(vecchia, nuova1, nuova2);
    }

    /**
     * restituisce tutte le coppie della plugboard
     * @return arraylist delle coppie della plugboard
     */
    public ArrayList<String> getCoppiePlugBoard() {
        return this.plugBoard.getCombinazioni();
    }
}