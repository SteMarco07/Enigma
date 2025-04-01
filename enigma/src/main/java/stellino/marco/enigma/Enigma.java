package stellino.marco.enigma;

import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Enigma {
    private ArrayList<Rotore> rotori;
    private Riflessore riflessore;
    private final TreeMap<String, ArrayList<String>> combinazioniRotori;
    private final TreeMap<String , String> combinazioniRiflessori;
    private PlugBoard plugBoard;

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

    public void modificaCombinazioneRotore (int nRotore, String nomeRotore) {
        this.rotori.get(nRotore).modificaCombinazione(this.combinazioniRotori.get(nomeRotore).getFirst(), combinazioniRotori.get(nomeRotore).getLast());
        //System.out.println(this.combinazioniRotori.get(nomeRotore).getFirst() + combinazioniRotori.get(nomeRotore).getLast());
    }

    public void modificaCombinazioneRiflessori (String nomeRiflessore) {
        this.riflessore.modificaCombinazione(this.combinazioniRiflessori.get(nomeRiflessore));
        //System.out.println(this.combinazioniRiflessori.get(nomeRiflessore));

    }

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


    private char criptaAvanti(char lettera) {
        lettera = this.plugBoard.cifra(lettera);
        for (var i : this.rotori) {
            lettera = i.criptaAvanti(lettera);
        }
        return lettera;
    }

    private char criptaIndietro(char lettera) {
        for (int i = this.rotori.size() - 1; i >= 0; i--) {
            lettera = this.rotori.get(i).criptaIndietro(lettera);
        }
        return this.plugBoard.cifra(lettera);
    }

    public char cripta(char lettera) {

        return this.criptaIndietro(this.riflessore.cripta(this.criptaAvanti(lettera)));
    }

    public Set<String> getCombinazioniRiflessori() {
        return this.combinazioniRiflessori.keySet();
    }

    public Set<String> getCombinazioniRotori() {
        return this.combinazioniRotori.keySet();
    }

    public int getRotazoine(int n_rotore) {
        return this.rotori.get(n_rotore).getRotazione();
    }

    public void setRotazoine(int n_rotore, int rotazine) {
        this.rotori.get(n_rotore).setRotazione(rotazine);
    }

    public void setRotazoine(int n_rotore, boolean aumenta) {
        this.rotori.get(n_rotore).setRotazione(aumenta);
    }

    public boolean aggiungiCoppia(char lettera1, char lettera2) {
        return this.plugBoard.aggiungiCoppia(lettera1, lettera2);
    }

    public void rimuoviCoppia(char lettera) {
        this.plugBoard.rimuoviCoppia(lettera);
    }

    /**
     * Rimuove tutte le combinazioni della plugboard
     */
    public void rimuoviTutteCoppie() {
        plugBoard.rimuoviTutto();
    }

    public void modificaCoppia(char vecchia, char nuova1, char nuova2) {
        this.plugBoard.modificaCoppia(vecchia, nuova1, nuova2);
    }

    public ArrayList<String> getCoppiePlugBoard() {
        return this.plugBoard.getCombinazioni();
    }
}