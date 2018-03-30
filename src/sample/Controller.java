package sample;

import IM.Process.BandSelection.BandSelector;
import IM.Process.Colors.Conversor;
import IM.Utils;
import com.sun.jndi.toolkit.url.Uri;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class Controller implements Initializable {
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

    @FXML
    private HBox hBox;
    @FXML
    private Label statusLabel;
    @FXML
    private RadioButton rgbRadioButton;
    @FXML
    private ImageView imageView;

    private ToggleGroup group;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        group = rgbRadioButton.getToggleGroup();

        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (group.getSelectedToggle() != null) {
                Conversor conversor = new Conversor();
                if (((RadioButton)group.getSelectedToggle()).getText().equals("YIQ")) {
                    BufferedImage out = conversor.applyFilter(SwingFXUtils.fromFXImage(imageView.getImage(), null), true);
                    imageView.setImage(SwingFXUtils.toFXImage(out, null));
                } else {
                    BufferedImage out = conversor.applyFilter(SwingFXUtils.fromFXImage(imageView.getImage(), null), false);
                    imageView.setImage(SwingFXUtils.toFXImage(out, null));
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

        BufferedImage out = new BandSelector().applyFilter(SwingFXUtils.fromFXImage(imageView.getImage(), null), channels);
        imageView.setImage(SwingFXUtils.toFXImage(out, null));
    }
}
