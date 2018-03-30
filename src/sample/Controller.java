package sample;

import IM.Memento.CareTaker;
import IM.Memento.Originator;
import IM.Process.BandSelection.BandSelector;
import IM.Process.Colors.Conversor;
import IM.Process.Brightness.Additive;
import IM.Process.Brightness.Multiplicative;

import IM.Utils;
import com.sun.istack.internal.Nullable;
import com.sun.jndi.toolkit.url.Uri;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import javax.swing.text.LabelView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.security.KeyException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class Controller implements Initializable {

    // Band Selection
    //--------------------------------------------
    @FXML
    private CheckBox rCheckBox;
    @FXML
    private Label rLabel;
    @FXML
    private Slider rSlider;

    @FXML
    private CheckBox gCheckBox;
    @FXML
    private Label gLabel;
    @FXML
    private Slider gSlider;

    @FXML
    private CheckBox bCheckBox;
    @FXML
    private Label bLabel;
    @FXML
    private Slider bSlider;
    //--------------------------------------------

    // UI
    //--------------------------------------------
    @FXML
    private HBox hBox;
    @FXML
    private Label statusLabel;
    @FXML
    private RadioButton rgbRadioButton;
    @FXML
    private ImageView imageView;
    //--------------------------------------------

    // Brightness Selection
    //--------------------------------------------
    @FXML
    private Slider sliderAditiveBrightness;
    @FXML
    private Label labelAditiveBrightness;
    @FXML
    private Slider sliderMultiplicativeBrightness;
    @FXML
    private Label labelMultiplicativeBrightness;
    //--------------------------------------------

    // Memento
    //--------------------------------------------
    private final CareTaker careTaker = new CareTaker();
    private final Originator originator = new Originator();
    //--------------------------------------------
    private ToggleGroup group;

    private final KeyCombination keyCombinationCtrlO = new KeyCodeCombination(
            KeyCode.O, KeyCombination.CONTROL_DOWN);

    private final KeyCombination keyCombinationCtrlS = new KeyCodeCombination(
            KeyCode.S, KeyCombination.CONTROL_DOWN);

    private final KeyCombination keyCombinationCtrlZ = new KeyCodeCombination(
            KeyCode.Z, KeyCombination.CONTROL_DOWN);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        group = rgbRadioButton.getToggleGroup();

        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (group.getSelectedToggle() != null) {
                try {
                    BufferedImage out = new Conversor().applyFilter(SwingFXUtils.fromFXImage(imageView.getImage(), null), ((RadioButton)group.getSelectedToggle()).getText().equals("YIQ"));
                    this.originator.setBufferedImage(SwingFXUtils.fromFXImage(imageView.getImage(), null));;
                    this.careTaker.addMemento(this.originator.save());
                    imageView.setImage(SwingFXUtils.toFXImage(out, null));
                } catch (NullPointerException e) {
                    this.presentBadImageAlert("Um erro ocorreu :(", "Nenhuma imagem encontrada,\npor" +
                            " favor abra uma imagem\nutilizando o menu acima.");
                }

            }
        });

        rSlider.valueProperty().addListener((observable, oldValue, newValue) ->
            rLabel.textProperty().setValue(String.valueOf((int) rSlider.getValue()))
        );

        gSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                gLabel.textProperty().setValue(String.valueOf((int) gSlider.getValue()))
        );

        bSlider.valueProperty().addListener((observable, oldValue, newValue) ->
            bLabel.textProperty().setValue(String.valueOf((int) bSlider.getValue()))
        );

        sliderAditiveBrightness.valueProperty().addListener((observable, oldValue, newValue) ->
                labelAditiveBrightness.textProperty().setValue(String.valueOf((int) sliderAditiveBrightness.getValue()))
        );

        sliderMultiplicativeBrightness.valueProperty().addListener((observable, oldValue, newValue) ->
                labelMultiplicativeBrightness.textProperty().setValue(String.valueOf((int) sliderMultiplicativeBrightness.getValue()))
        );

        imageView.fitWidthProperty().bind(hBox.widthProperty());
        imageView.fitHeightProperty().bind(hBox.heightProperty());
    }

    @FXML
    public void openButton(ActionEvent event) {
        FileChooser fileChooser = Utils.createFileChooser("Open File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPEG files (*.jpg)", "*.jpg"));
        File file = fileChooser.showOpenDialog(rgbRadioButton.getScene().getWindow());

        if (file != null) {
            this.statusLabel.setText("Opened: " + file.toURI().toString());
            this.imageView.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    public void saveButton(ActionEvent event) {
        File file = Utils.createFileChooser("Save File").showSaveDialog(rgbRadioButton.getScene().getWindow());

        if(file != null){
            String extension = Utils.getFileExtension(file.getPath());
            if (extension.isEmpty()) {
                file = new File(file.getAbsolutePath() + ".png");
            }
            this.statusLabel.setText("Saved: " + file.toURI().toString());

            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(this.imageView.snapshot(null, null), null);
            try {
                ImageIO.write(bufferedImage, "png", file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void applyBandSelection(ActionEvent event) {
        this.statusLabel.setText("Applying band selection");

        int channels = 0x00000000;

        if (rCheckBox.isSelected()) {
            channels |= 0xFF000000 | Integer.parseInt(rLabel.getText()) << 16;
        }
        if (gCheckBox.isSelected()) {
            channels |= 0xFF000000 | Integer.parseInt(gLabel.getText()) << 8;
        }
        if (bCheckBox.isSelected()) {
            channels |= 0xFF000000 | Integer.parseInt(bLabel.getText());
        }
        if (channels == 0x00) {
            channels = 0xFFFFFFFF;
        }
        try {
            BufferedImage out = new BandSelector().applyFilter(SwingFXUtils.fromFXImage(imageView.getImage(), null), channels);
            this.originator.setBufferedImage(SwingFXUtils.fromFXImage(imageView.getImage(), null));
            this.careTaker.addMemento(this.originator.save());
            imageView.setImage(SwingFXUtils.toFXImage(out, null));
        } catch (NullPointerException e) {
            this.presentBadImageAlert("Um erro ocorreu :(", "Nenhuma imagem encontrada,\npor" +
                    " favor abra uma imagem\nutilizando o menu acima.");
        }

    }

    @FXML
    private void keyReleased(KeyEvent keyEvent) {
        if (keyCombinationCtrlO.match(keyEvent)) {
            this.openButton(null);
        } else if (keyCombinationCtrlS.match(keyEvent)) {
            this.saveButton(null);
        } else if (keyCombinationCtrlZ.match(keyEvent)) {
            this.undo();
        }
    }


    @FXML
    public void applyBrightness(ActionEvent event) {
        this.statusLabel.setText("Applying Brightness properties selection");

        BufferedImage outImage = SwingFXUtils.fromFXImage(imageView.getImage(), null);
        int additiveValue = Integer.parseInt(labelAditiveBrightness.getText());
        int multiplicativeValue = Integer.parseInt(labelMultiplicativeBrightness.getText());

        try {
            if (additiveValue != 0)
                outImage = new Additive().applyFilter(outImage, additiveValue);
            if (multiplicativeValue != 0)
                outImage = new Multiplicative().applyFilter(outImage, multiplicativeValue);
            imageView.setImage(SwingFXUtils.toFXImage(outImage, null));
        } catch (NullPointerException e) {
            this.presentBadImageAlert();
        }
    }

    public void undo() {

        this.originator.restore(this.careTaker.getMemento());
        try {
            this.imageView.setImage(SwingFXUtils.toFXImage(this.originator.getCurrentImage(), null));
            this.statusLabel.setText("Undoing");
        } catch (NullPointerException e) {}
    }

    private void presentBadImageAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ImageManipulation");
        alert.setHeaderText(title);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
