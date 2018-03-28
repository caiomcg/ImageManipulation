package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private VBox vBox;

    @FXML
    private RadioButton rgbRadioButton;
    @FXML
    private RadioButton yiqRadioButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
