package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class Controller implements Initializable {
    @FXML
    private RadioButton rgbRadioButton;
    @FXML
    private RadioButton yiqRadioButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void openButton(ActionEvent event) {
        System.out.println("On open button clicked");
    }

    @FXML
    public void saveButton(ActionEvent event) {
        System.out.println("On save button clicked");
    }
}
