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

    public void modificaCombinazioneRotore (int nRotore, int nCombinazione) {
        this.rotori.get(nRotore).modificaCombinazione(this.combinazioniRotori.get(nCombinazione)[0], combinazioniRotori.get(nCombinazione)[1]);
    }

    public void modificaCombinazioneRiflessori (int nCombinazione) {
        this.riflessore.modificaCombinazione(this.combinazioniRiflessori.get(nCombinazione));
    }

    public void ruota() {
        this.rotori.getFirst().ruota();
        if (this.rotori.getFirst().isCambioLettera()) {
            this.rotori.get(1).ruota();
            if (this.rotori.get(1).isCambioLettera()) {
                this.rotori.get(2).ruota();
            }
        }
        System.out.println("r1: " + this.rotori.getFirst().getRotazione() + " r2: " + this.rotori.get(1).getRotazione() + " r3: " + this.rotori.getLast().getRotazione());
    }


    private String criptaAvanti(String lettera) {
        for (var i : this.rotori) {
            lettera = i.criptaAvanti(lettera);
        }
        return lettera;
    }

    private String criptaIndietro(String lettera) {
        for (int i = this.rotori.size() - 1; i >= 0; i--) {
            lettera = this.rotori.get(i).criptaIndietro(lettera);
        }
        return lettera;
    }

    public String cripta(String lettera) {

        return this.criptaIndietro(this.riflessore.cripta(this.criptaAvanti(lettera)));
    }


}
