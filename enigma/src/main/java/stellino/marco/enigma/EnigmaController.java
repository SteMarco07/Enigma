package stellino.marco.enigma;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EnigmaController {

    // Aggiungi l'array per l'ordine QWERTZ
    private static final char[] QWERTZ_ORDER = {
            'Q', 'W', 'E', 'R', 'T', 'Z', 'U', 'I', 'O',
            'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K',
            'P', 'Y', 'X', 'C', 'V', 'B', 'N', 'M', 'L'
    };

    @FXML
    private VBox vBoxCombo1, vBoxCombo2;
    @FXML
    private ChoiceBox<String> chbRiflector, chbRotor1, chbRotor2, chbRotor3;
    @FXML
    private Label lblReflector, lblPosR1, lblPosR2, lblPosR3;
    @FXML
    private GridPane gridLamps;
    @FXML
    private GridPane gridButtons;
    @FXML
    private GridPane gridButtons2;
    @FXML
    private TextArea txaIn, txaOut;

    private Enigma enigma;

    private Button[] buttons;
    private Circle[] lamps;
    private Button[] buttons2;

    private boolean isProcessingText = false;
    private int lastTextLength = 0;

    /**
     * inizializza tutti gli elementi utilizzati dal programma
     * @throws IOException eccezione di apertura dei file
     */
    @FXML
    public void initialize() throws IOException {
        enigma = new Enigma("file/combinazioniRotori.csv", "file/combinazioniRiflessori.csv");
        gestisciGridButtons(60);
        gestisciGridLamps(25);
        gestisciGridButtons2(60);
        chbRiflector.getItems().setAll(this.enigma.getCombinazioniRiflessori());
        chbRiflector.getSelectionModel().select(1);
        chbRotor1.getItems().setAll(this.enigma.getCombinazioniRotori());
        chbRotor1.getSelectionModel().select(0);
        chbRotor2.getItems().setAll(this.enigma.getCombinazioniRotori());
        chbRotor2.getSelectionModel().select(1);
        chbRotor3.getItems().setAll(this.enigma.getCombinazioniRotori());
        chbRotor3.getSelectionModel().select(2);
        aggiornaPosizioni();
        aggiornaRotoriListener();
        configuraTextArea();
    }

    /**
     * crea e gestisce graficamente una tastiera formata da 26 pulsanti
     * @param dim dimensione dinale dellla tastiera
     */
    private void gestisciGridButtons(int dim) {
        buttons = new Button[26];
        gridButtons.setVgap(10);
        gridButtons.setHgap(10);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                int indice = i * 9 + j;
                if (indice >= QWERTZ_ORDER.length) return;

                char lettera = QWERTZ_ORDER[indice];
                buttons[indice] = new Button(String.valueOf(lettera));
                buttons[indice].setPrefWidth(dim);
                buttons[indice].setPrefHeight(dim/2);
                char finalLettera = lettera;
                buttons[indice].setOnAction(e -> cripta(finalLettera));
                gridButtons.add(buttons[indice], j, i);
            }
        }
    }


    private char lettera1 = '\0';
    private char lettera2 = '\0';


    /**
     * crea e gestisce graficamente una seconda tastiera formata da 26 pulsanti
     * @param dim dimensione dinale dellla tastiera
     */
    private void gestisciGridButtons2(int dim) {
        buttons2 = new Button[26];
        gridButtons2.setVgap(10);
        gridButtons2.setHgap(10);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                int indice = i * 9 + j;
                if (indice >= QWERTZ_ORDER.length) return;

                char lettera = QWERTZ_ORDER[indice];
                buttons2[indice] = new Button(String.valueOf(lettera));
                buttons2[indice].setPrefWidth(dim);
                buttons2[indice].setPrefHeight(dim / 2);

                buttons2[indice].setOnAction(e -> gestisciClick(lettera));
                gridButtons2.add(buttons2[indice], j, i);
            }
        }
    }

    /**
     * gestisce l'inserimento delle coppie dalla tastiera della plugboard
     * @param lettera lettera premuta(pulsante premuto a cui corrisponde una lettera)
     */
    private void gestisciClick(char lettera) {
        if (lettera1 == '\0'){
            lettera1 = lettera;
        } else if (lettera2 == '\0') {
            lettera2 = lettera;
            if (enigma.aggiungiCoppia(lettera1, lettera2))
                aggiornaCombinazioni();
            lettera1 = '\0';
            lettera2 = '\0';
        }

    }

    /**
     * elimina le combinazioni della plugboard
     * @param actionEvent evento della pressione del pulsante
     */
    public void onBtnClearPB(ActionEvent actionEvent) {
        this.vBoxCombo1.getChildren().clear();
        this.vBoxCombo2.getChildren().clear();
        for (var i : enigma.getCoppiePlugBoard()){
            enigma.rimuoviCoppia(i.charAt(0));
        }
    }

    /**
     * aggiorna le coppie della plugboard
     */
    private void aggiornaCombinazioni() {
        ArrayList <String> combinazioni = this.enigma.getCoppiePlugBoard();
        Label l = new Label(combinazioni.getLast());
        l.setFont(new Font(18));
        if (combinazioni.size() <= 3 ){
            vBoxCombo1.getChildren().add(l);
        } else {
            vBoxCombo2.getChildren().add(l);
        }
    }


    /**
     * crea e gestisce graficamente la griglia di lampadine
     * @param dim dimensione finale della grigliua di lampadine
     */
    private void gestisciGridLamps(int dim) {
        lamps = new Circle[26];
        gridLamps.setVgap(10);
        gridLamps.setHgap(20);
        gridLamps.setAlignment(Pos.CENTER);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                int indice = i * 9 + j;
                if (indice >= QWERTZ_ORDER.length) return;

                Circle circle = new Circle(dim);
                circle.setFill(Color.WHITE);
                circle.setStroke(Color.BLACK);

                Label label = new Label(String.valueOf(QWERTZ_ORDER[indice]));
                StackPane stackPane = new StackPane();
                stackPane.setAlignment(Pos.CENTER);
                stackPane.getChildren().addAll(circle, label);

                gridLamps.add(stackPane, j, i);
                lamps[indice] = circle;
            }
        }
    }

    /**
     * crea e gestisce graficamente i campi di testo in input e output
     */
    private void configuraTextArea() {
        // Filtra l'input solo per lettere e converte in maiuscolo
        txaIn.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!isProcessingText) {
                isProcessingText = true;

                // Gestisci solo nuovi caratteri aggiunti
                if (newVal.length() > lastTextLength) {
                    String addedText = newVal.substring(lastTextLength).toUpperCase();
                    StringBuilder validChars = new StringBuilder();

                    for (char c : addedText.toCharArray()) {
                        if (Character.isLetter(c)) {
                            validChars.append(c);
                            processaCarattere(c);
                        }
                    }

                    // Aggiorna il testo mantenendo solo i caratteri validi
                    String currentText = txaIn.getText().toUpperCase();
                    String cleanedText = currentText.replaceAll("[^A-Z]", "");
                    txaIn.setText(cleanedText);
                    txaIn.positionCaret(cleanedText.length());
                }

                lastTextLength = txaIn.getText().length();
                isProcessingText = false;
            }
        });
    }

    /**
     * cripta il carattere e lo aggiunge all'output
     * @param c carattere
     */
    private void processaCarattere(char c) {
        // Cripta il carattere e aggiungi all'output
        c  = Character.toLowerCase(c);
        if (c >= 'a' && c <= 'z') {
            char encrypted = criptaChar(c);
            if (txaOut.getText().length() % 5 == 0) {
                txaOut.appendText(" ");
            }

            txaOut.appendText(String.valueOf(encrypted));
        }

    }

    /**
     * cripta un carattere e gestisce l'accensione dellla rispettiva lampada
     * @param lettera lettera da criptare
     * @return lettera criptata
     */
    private char criptaChar(char lettera) {
        this.enigma.ruota();
        lettera = enigma.cripta(lettera);
        lettera = Character.toUpperCase(lettera);

        // Aggiorna lampade
        int criptataIndex = -1;
        for (int i = 0; i < QWERTZ_ORDER.length; i++) {
            if (QWERTZ_ORDER[i] == lettera) {
                criptataIndex = i;
                break;
            }
        }

        for (Circle lamp : lamps) lamp.setFill(Color.WHITE);
        if (criptataIndex != -1) lamps[criptataIndex].setFill(Color.YELLOW);
        aggiornaPosizioni();
        return lettera;
    }

    // Modifica i metodi esistenti per usare processaCarattere

    /**
     * cripta una lettera e lo aggiunge al campo di testo dell'input
     * @param lettera
     */
    @FXML
    private void cripta(char lettera) {
        txaIn.appendText(String.valueOf(lettera));
    }

    /**
     * gestisce la pressione di un pulsante lettera(tastiera fisica o grafica)
     * @param event evento dell apressione di un tasto
     */
    @FXML
    private void onKeyPressed(KeyEvent event) {
        if (event.getCode().isLetterKey()) {
            char inputChar = Character.toUpperCase(event.getCode().getChar().charAt(0));
            for (int i = 0; i < QWERTZ_ORDER.length; i++) {
                if (QWERTZ_ORDER[i] == inputChar) {
                    txaIn.appendText(String.valueOf(QWERTZ_ORDER[i]));
                    break;
                }
            }
            System.out.println("ciao sono entrato");
            event.consume();
        }
    }

    /**
     * aggiorna la posizione dei tre rotori
     */
    private void aggiornaPosizioni() {
        lblPosR1.setText(String.valueOf((char)('A' + this.enigma.getRotazione(0))));
        lblPosR2.setText(String.valueOf((char)('A' + this.enigma.getRotazione(1))));
        lblPosR3.setText(String.valueOf((char)('A' + this.enigma.getRotazione(2))));
    }

    /**
     * aggiorna la configurazione di un rotore
     * @param nRotore numero del reotore
     * @param val valore di configurazione di un rotore
     */
    private void aggiornaRotore(int nRotore, String val) {
        enigma.modificaCombinazioneRotore(nRotore, val);
        aggiornaPosizioni();
    }

    /**
     * permette l'interazione grafica dei choicebox e la conseguente modifica delle configurazioi dei rotori
     */
    private void aggiornaRotoriListener() {
        chbRotor1.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> aggiornaRotore(0, newVal));
        chbRotor2.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> aggiornaRotore(1, newVal));
        chbRotor3.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> aggiornaRotore(2, newVal));
        chbRiflector.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> enigma.modificaCombinazioneRiflessori(newVal));
    }

    /**
     * decremento della posizione del rotore 3
     * @param actionEvent pressione del pulsante -
     */
    @FXML
    public void onBtnMinusR3(ActionEvent actionEvent) {
        this.enigma.setRotazione(2, false);
        this.aggiornaPosizioni();
    }

    /**
     * incremento della posizione del rotore 3
     * @param actionEvent pressione del pulsante +
     */
    @FXML
    public void onBtnPlusR3(ActionEvent actionEvent) {
        this.enigma.setRotazione(2, true);
        this.aggiornaPosizioni();
    }

    /**
     * decremento della posizione del rotore 2
     * @param actionEvent pressione del pulsante -
     */
    @FXML
    public void onBtnMinusR2(ActionEvent actionEvent) {
        this.enigma.setRotazione(1, false);
        this.aggiornaPosizioni();
    }

    /**
     * incremento della posizione del rotore 2
     * @param actionEvent pressione del pulsante +
     */
    @FXML
    public void onBtnPlusR2(ActionEvent actionEvent) {
        this.enigma.setRotazione(1, true);
        this.aggiornaPosizioni();
    }

    /**
     * decremento della posizione del rotore 1
     * @param actionEvent pressione del pulsante -
     */
    @FXML
    public void onBtnMinusR1(ActionEvent actionEvent) {
        this.enigma.setRotazione(0, false);
        this.aggiornaPosizioni();
    }

    /**
     * incremento della posizione del rotore 1
     * @param actionEvent pressione del pulsante +
     */
    @FXML
    public void onBtnPlusR1(ActionEvent actionEvent) {
        this.enigma.setRotazione(0, true);
        this.aggiornaPosizioni();
    }

    /**
     * gestisce il pulsante cleartext, svuota i campi di testo
     * @param actionEvent pressione del pulsante cleartext
     */
    public void onBtnClearTxa(ActionEvent actionEvent) {
        this.txaIn.clear();
        this.txaOut.clear();
    }

    /**
     * gestisce il pulsante resetpos, ripristina le posizioni dei rotori
     * @param actionEvent pressione del pulsante resetpos
     */
    public void onBtnResetPos(ActionEvent actionEvent) {
        this.enigma.setRotazione(0,0);
        this.enigma.setRotazione(1,0);
        this.enigma.setRotazione(2,0);
        aggiornaPosizioni();
    }


}