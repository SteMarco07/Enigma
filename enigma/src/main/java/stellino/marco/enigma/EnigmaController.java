package stellino.marco.enigma;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.transform.Rotate;

import java.io.IOException;

public class EnigmaController {
    @FXML
    private Label welcomeText;
    @FXML
    private Enigma enigma;


    @FXML
    public void initialize() throws IOException {
        enigma = new Enigma("file/combinazioniRotori.csv", "file/combinazioniRiflessori.csv");
    }



    @FXML
    protected void onHelloButtonClick() {
        this.enigma.ruota();
        String stringa = enigma.cripta("a");
        welcomeText.setText(stringa);

    }
}