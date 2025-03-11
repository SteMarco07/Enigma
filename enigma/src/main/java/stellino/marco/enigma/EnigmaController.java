package stellino.marco.enigma;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.transform.Rotate;

public class EnigmaController {
    @FXML
    private Label welcomeText;
    @FXML
    private Rotore rotore;


    @FXML
    public void initialize() {
        rotore = new Rotore("EKMFLGDQVZNTOWYHXUSPAIBRCJ");
    }



    @FXML
    protected void onHelloButtonClick() {
        this.rotore.ruota();
        String stringa = (char)(this.rotore.get_uscita_dritto('a')) + " numero: " + this.rotore.get_rotazione();
        welcomeText.setText(stringa);

    }
}