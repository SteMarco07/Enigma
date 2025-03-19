package stellino.marco.enigma;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Enigma {
    private ArrayList<Rotore> rotori;
    ArrayList<String[]> combinazioniRotori;
    ArrayList<String> combinazioniRiflessori;
    private Riflessore riflessore;



    public Enigma(String pRotori, String pRiflessori) throws IOException, FileNotFoundException {
        this.rotori = new ArrayList<>();
        this.combinazioniRotori = new ArrayList<>();
        this.combinazioniRiflessori = new ArrayList<>();
        this.caricaRotori(pRotori);
        this.caricaRiflessori(pRiflessori);
        this.rotori.add(new Rotore(this.combinazioniRotori.get(0)[0], this.combinazioniRotori.get(0)[1]));
        this.rotori.add(new Rotore(this.combinazioniRotori.get(1)[0], this.combinazioniRotori.get(1)[1]));
        this.rotori.add(new Rotore( this.combinazioniRotori.get(2)[0], this.combinazioniRotori.get(2)[1]));
        this.riflessore = new Riflessore(this.combinazioniRiflessori.getFirst());


    }


    private void caricaRotori(String percorso) throws IOException, FileNotFoundException {
        BufferedReader br = new BufferedReader( new FileReader(percorso));
        String riga;
        String[] elementi;
        while ((riga = br.readLine()) != null) {
            elementi = riga.split(";");
            this.combinazioniRotori.add(elementi);
        }
    }

    private void caricaRiflessori(String percorso) throws IOException, FileNotFoundException {
        BufferedReader br = new BufferedReader( new FileReader(percorso));
        String riga;
        while ((riga = br.readLine()) != null) {
            this.combinazioniRiflessori.add(riga);
        }
    }

    public void modificaCombinazione (int nRotore, int nCombinazione) {
        this.rotori.get(nRotore).modificaCombinazione(this.combinazioniRotori.get(nCombinazione)[0], combinazioniRotori.get(nCombinazione)[1]);
    }


}
