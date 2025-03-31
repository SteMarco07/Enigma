package stellino.marco.enigma;

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
    private Plugboard plugboard;




    public Enigma(String pRotori, String pRiflessori) throws IOException, FileNotFoundException {
        this.rotori = new ArrayList<>();
        this.combinazioniRotori = new TreeMap<>();
        this.combinazioniRiflessori = new TreeMap<>();
        this.caricaRotori(pRotori);
        this.caricaRiflessori(pRiflessori);
        this.rotori.add(new Rotore(this.combinazioniRotori.get("I").getFirst(), this.combinazioniRotori.get("I").getLast()));
        this.rotori.add(new Rotore(this.combinazioniRotori.get("II").getFirst(), this.combinazioniRotori.get("II").getLast()));
        this.rotori.add(new Rotore(this.combinazioniRotori.get("III").getFirst(), this.combinazioniRotori.get("III").getLast()));
        this.riflessore = new Riflessore(this.combinazioniRiflessori.get("A"));
        this.plugboard = new Plugboard();
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

    public void modificaCombinazioneRotore (int nRotore, String combinazione) {
        this.rotori.get(nRotore).modificaCombinazione(this.combinazioniRotori.get(combinazione).getFirst(), combinazioniRotori.get(combinazione).getLast());
        //System.out.println(this.combinazioniRotori);
    }

    public void modificaCombinazioneRiflessori (String combinazione) {
        this.riflessore.modificaCombinazione(this.combinazioniRiflessori.get(combinazione));
        //System.out.println(this.combinazioniRiflessori);

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

    public String cripta(String lettera) {      //modificato per la plugboard
        char letteraCifrata = plugboard.cifra(lettera.charAt(0));

        String s = this.criptaIndietro(this.riflessore.cripta(this.criptaAvanti(String.valueOf(letteraCifrata))));
        return String.valueOf(plugboard.cifra(s.charAt(0)));
    }

    // public Plugboard getPlugboard() {
    //return plugboard;
    //}

    public Set<String> getCombinazioniRiflessori() {
        return this.combinazioniRiflessori.keySet();
    }

    public Set<String> getCombinazioniRotori() {
        return this.combinazioniRotori.keySet();
    }


}