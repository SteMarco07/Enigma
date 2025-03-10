module stellino.marco.enigma {
    requires javafx.controls;
    requires javafx.fxml;


    opens stellino.marco.enigma to javafx.fxml;
    exports stellino.marco.enigma;
}