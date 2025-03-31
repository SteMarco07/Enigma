package stellino.marco.enigma;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class EnigmaController {

    // Aggiungi l'array per l'ordine QWERTZ
    private static final char[] QWERTZ_ORDER = {
            'Q', 'W', 'E', 'R', 'T', 'Z', 'U', 'I', 'O',
            'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K',
            'P', 'Y', 'X', 'C', 'V', 'B', 'N', 'M', 'L'
    };

    @FXML
    private ChoiceBox<String> chbRiflector, chbRotor1, chbRotor2, chbRotor3;
    @FXML
    private Label lblReflector, lblPosR1, lblPosR2, lblPosR3;
    @FXML
    private GridPane gridLamps;
    @FXML
    private GridPane gridButtons;
    private Enigma enigma;

    private Button[] buttons;
    private Circle[] lamps;

    @FXML
    public void initialize() throws IOException {
        enigma = new Enigma("file/combinazioniRotori.csv", "file/combinazioniRiflessori.csv");
        gestisciGridButtons(60);
        gestisciGridLamps(25);
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
    }

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

    @FXML
    private void cripta(char lettera) {
        this.enigma.ruota();
        char criptataChar = enigma.cripta(lettera);
        criptataChar = Character.toUpperCase(criptataChar);

        int criptataIndex = -1;
        for (int i = 0; i < QWERTZ_ORDER.length; i++) {
            if (QWERTZ_ORDER[i] == criptataChar) {
                criptataIndex = i;
                break;
            }
        }

        for (Circle lamp : lamps) lamp.setFill(Color.WHITE);
        if (criptataIndex != -1) lamps[criptataIndex].setFill(Color.YELLOW);

        aggiornaPosizioni();
    }

    @FXML
    private void onKeyPressed(KeyEvent event) {
        if (event.getCode().isLetterKey()) {
            char inputChar = Character.toUpperCase(event.getCode().getChar().charAt(0));
            int pos = -1;
            for (int i = 0; i < QWERTZ_ORDER.length; i++) {
                if (QWERTZ_ORDER[i] == inputChar) {
                    pos = i;
                    break;
                }
            }
            if (pos != -1) {
                buttons[pos].fire();
                buttons[pos].requestFocus();
            }
        }
    }

    // Resto del codice invariato...
    private void aggiornaPosizioni() {
        lblPosR1.setText(String.valueOf((char)('A' + this.enigma.getRotazoine(0))));
        lblPosR2.setText(String.valueOf((char)('A' + this.enigma.getRotazoine(1))));
        lblPosR3.setText(String.valueOf((char)('A' + this.enigma.getRotazoine(2))));
    }

    private void aggiornaRotore(int nRotore, String val) {
        enigma.modificaCombinazioneRotore(nRotore, val);
        aggiornaPosizioni();
    }

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

    @FXML
    public void onBtnMinusR3(ActionEvent actionEvent) {
        this.enigma.setRotazoine(2, false);
        this.aggiornaPosizioni();
    }

    @FXML
    public void onBtnPlusR3(ActionEvent actionEvent) {
        this.enigma.setRotazoine(2, true);
        this.aggiornaPosizioni();
    }

    @FXML
    public void onBtnMinusR2(ActionEvent actionEvent) {
        this.enigma.setRotazoine(1, false);
        this.aggiornaPosizioni();
    }

    @FXML
    public void onBtnPlusR2(ActionEvent actionEvent) {
        this.enigma.setRotazoine(1, true);
        this.aggiornaPosizioni();
    }

    @FXML
    public void onBtnMinusR1(ActionEvent actionEvent) {
        this.enigma.setRotazoine(0, false);
        this.aggiornaPosizioni();
    }

    @FXML
    public void onBtnPlusR1(ActionEvent actionEvent) {
        this.enigma.setRotazoine(0, true);
        this.aggiornaPosizioni();
    }
}