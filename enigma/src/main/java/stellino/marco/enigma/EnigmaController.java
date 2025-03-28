package stellino.marco.enigma;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class EnigmaController {


    @FXML
    private GridPane gridLamps;
    @FXML
    private GridPane gridButtons;

    private Button[] buttons;
    private Circle[] lamps;

    @FXML
    public void initialize() {
        gestisciGridButtons(60);
        gestisciGridLamps(25);
    }

    private void gestisciGridButtons(int dim) {
        buttons = new Button[26];
        char lettera = 'A';
        gridButtons.setVgap(10);
        gridButtons.setHgap(10);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                int indice = i * 9 + j;
                buttons[indice] = new Button("" + lettera);
                buttons[indice].setPrefWidth(dim);
                buttons[indice].setPrefHeight(dim/2);
                final char letteraF = lettera;
                buttons[indice].setOnAction(e -> {
                    for (Circle lamp : lamps) {
                        lamp.setFill(Color.WHITE);
                    }
                    lamps[indice].setFill(Color.YELLOW);
                });
                gridButtons.add(buttons[indice], j, i);
                lettera++;
                if (lettera == '[') return;
            }
        }
    }

    private void gestisciGridLamps(int dim) {
        lamps = new Circle[26];
        char lettera = 'A';
        gridLamps.setVgap(10);
        gridLamps.setHgap(20);

        // Imposta l'allineamento al centro per tutte le celle della griglia
        gridLamps.setAlignment(Pos.CENTER);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                // Crea il cerchio
                Circle circle = new Circle(dim);
                circle.setFill(Color.WHITE);
                circle.setStroke(Color.BLACK);

                // Crea la label con la lettera
                Label label = new Label(String.valueOf(lettera));


                // Crea un container StackPane per centrare gli elementi
                StackPane stackPane = new StackPane();
                stackPane.setAlignment(Pos.CENTER);  // Centra sia cerchio che label
                stackPane.getChildren().addAll(circle, label);

                // Aggiungi alla griglia
                gridLamps.add(stackPane, j, i);

                // Memorizza il cerchio nell'array
                lamps[i * 9 + j] = circle;

                lettera++;
                if (lettera == '[') return;
            }
        }
    }

    @FXML
    private void onKeyPressed(KeyEvent event){
        if (event.getCode().isLetterKey()){
            int pos = event.getCode().getChar().charAt(0) - 'A';
            buttons[pos].fire();
            buttons[pos].requestFocus();
        }
    }
    @FXML
    public void onBtnMinusR3(ActionEvent actionEvent) {
    }

    @FXML
    public void onBtnPlusR3(ActionEvent actionEvent) {

    }

    @FXML
    public void onBtnMinusR2(ActionEvent actionEvent) {
    }

    @FXML
    public void onBtnPlusR2(ActionEvent actionEvent) {

    }

    @FXML
    public void onBtnMinusR1(ActionEvent actionEvent) {
    }

    @FXML
    public void onBtnPlusR1(ActionEvent actionEvent) {

    }


}