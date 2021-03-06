package sample;



import IM.Memento.CareTaker;
import IM.Memento.Originator;
import IM.Process.BandSelection.BandSelector;
import IM.Process.Brightness.Additive;
import IM.Process.Brightness.Multiplicative;
import IM.Process.Colors.Conversor;
import IM.Process.Colors.Threshold;
import IM.Process.Convolution.Conv2D;
import IM.Process.Effects.Grayscale;
import IM.Process.Effects.Negative;
import IM.Process.Histogram.Equalization;
import IM.Process.Histogram.EqualizationSettings;
import IM.Process.Histogram.Stretching;
import IM.Process.Histogram.StretchingSettings;
import IM.Process.SpatialFilters.Filter;
import IM.Process.SpatialFilters.FilterType;
import IM.Process.SpatialFilters.MeanFilter;
import IM.Utils;
import com.sun.istack.internal.Nullable;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.util.converter.NumberStringConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


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

    // Threshold
    //--------------------------------------------
    @FXML
    private ToggleButton averageThresholdToggleButton;
    @FXML
    private Label customThresholdLabel;
    @FXML
    private Slider customThresholdSlider;
    //--------------------------------------------

    // Filters
    //--------------------------------------------
    @FXML
    private ToggleButton sobelFiterToggleButton;
    @FXML
    private ToggleButton laplaceFiterToggleButton;
    @FXML
    private ToggleButton negativeFiterToggleButton;
    @FXML
    private ToggleButton customFiterToggleButton;
    @FXML
    private ToggleButton convolutionFilterToggleButton;
    @FXML
    private ComboBox<Integer> meanFilterComboBox;
    @FXML
    private ComboBox<Integer> medianFilterComboBox;
    @FXML
    private GridPane gridPane;

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
                    BufferedImage out = new Conversor().applyFilter(this.getImage(), ((RadioButton)group.getSelectedToggle()).getText().equals("YIQ"));
                    this.addToMemento(this.getImage());
                    this.setImage(out);
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
                labelMultiplicativeBrightness.textProperty().setValue(String.valueOf( Math.floor(
                        sliderMultiplicativeBrightness.getValue() * 100) / 100))
        );

        customThresholdSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                customThresholdLabel.textProperty().setValue(String.valueOf((int) customThresholdSlider.getValue()))
        );

        meanFilterComboBox.getItems().addAll(0,3,5,7,9);
        meanFilterComboBox.getSelectionModel().select(0);

        medianFilterComboBox.getItems().addAll(0,3,5,7,9);
        medianFilterComboBox.getSelectionModel().select(0);

        this.setGridMask();

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

        if (!rCheckBox.isSelected() && !gCheckBox.isSelected() && !bCheckBox.isSelected()) {
            try {
                System.err.println("Aplying grayscale");
                BufferedImage out = new Grayscale().applyFilter(this.getImage(), ((RadioButton)group.getSelectedToggle()).getText().equals(("YIQ")));
                this.addToMemento(this.getImage());
                this.setImage(out);
            } catch (NullPointerException e) {
                this.presentBadImageAlert("Um erro ocorreu :(", "Nenhuma imagem encontrada,\npor" +
                        " favor abra uma imagem\nutilizando o menu acima.");
            }
            return;
        }

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
            BufferedImage out = new BandSelector().applyFilter(this.getImage(), channels);
            this.addToMemento(this.getImage());
            this.setImage(out);
            this.statusLabel.setText("Applying band selection");
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

    int counter = 1;

    @FXML
    public void applyBrightness(ActionEvent event) {
        this.statusLabel.setText("Aplicando brilho!");

        try {
            BufferedImage originalImage = this.getImage();

            this.addToMemento(originalImage);

            int additiveValue = (int)sliderAditiveBrightness.getValue();
            double multiplicativeValue = Double.parseDouble(labelMultiplicativeBrightness.getText());

            if (additiveValue != 0)
                originalImage = new Additive().applyFilter(originalImage, additiveValue);
            if (multiplicativeValue != 0.0)
                originalImage = new Multiplicative().applyFilter(originalImage, multiplicativeValue);
            if (originalImage != null) {
                this.statusLabel.setText("Aplicando brilho!");
                this.setImage(originalImage);
            } else {
                this.statusLabel.setText("Falha ao aplicar brilho!");
                this.undo();
            }
        } catch (NullPointerException e) {
            this.undo();
            this.presentBadImageAlert("Um erro ocorreu :(", "Nenhuma imagem encontrada,\npor" +
                    " favor abra uma imagem\nutilizando o menu acima.");
        }
    }

    @FXML
    private void onEqualize(ActionEvent event) {
        this.statusLabel.setText("Equalizando...");
        try {
            EqualizationSettings config = new EqualizationSettings(255, this.getImage());
            BufferedImage out = new Equalization().applyFilter(this.getImage(), config);
            this.addToMemento(this.getImage());
            this.setImage(out);
        } catch (NullPointerException e) {
            this.presentBadImageAlert("Um erro ocorreu :(", "Nenhuma imagem encontrada,\npor" +
                    " favor abra uma imagem\nutilizando o menu acima.");
        }
    }

    @FXML
    private void onExpand(ActionEvent event) {
        this.statusLabel.setText("Expandindo...");
        try {
            StretchingSettings strConfig = new Stretching().getStretchConfig(this.getImage());
            BufferedImage out = new Stretching().applyFilter(this.getImage(), strConfig);
            this.addToMemento(this.getImage());
            this.setImage(out);
        } catch (NullPointerException e) {
            this.presentBadImageAlert("Um erro ocorreu :(", "Nenhuma imagem encontrada,\npor" +
            " favor abra uma imagem\nutilizando o menu acima.");
        }
    }

    @FXML
    private void onApplyThreshold(ActionEvent event) {
        if (!((RadioButton)rgbRadioButton.getToggleGroup().getSelectedToggle()).getText().equals("YIQ")){
            this.presentBadImageAlert("Um erro ocorreu :(", "Por favor selecione YIQ");
        } else {
            try {
                BufferedImage originalImage = this.getImage();
                int meanY = Integer.parseInt(this.customThresholdLabel.getText());

                if (averageThresholdToggleButton.isSelected()) {
                    meanY = new Threshold().getYMean(originalImage);
                }
                BufferedImage newImage = new Threshold().applyFilter(originalImage, meanY);
                this.addToMemento(originalImage);
                this.setImage(newImage);
            } catch (NullPointerException e) {
                this.presentBadImageAlert("Um erro ocorreu :(", "Nenhuma imagem encontrada,\npor" +
                        " favor abra uma imagem\nutilizando o menu acima.");
            }
        }
    }

    @FXML
    private void onApplyFilters(ActionEvent event) {
        byte appliedFilters = 0x00;
        BufferedImage originalImage = this.getImage();
        this.statusLabel.setText("Aplicando filtros!");

        if (originalImage != null) {
            this.addToMemento(originalImage);
        } else {
            this.statusLabel.setText("Os filtros não puderam ser aplicados");
        }

        if (!meanFilterComboBox.getValue().equals(0)) {
            appliedFilters = 0x01;
            this.statusLabel.setText("Aplicando o filtro de Média");
            originalImage = new Filter(FilterType.MEAN, meanFilterComboBox.getValue(), originalImage, null).applyFilter();
        }

        if (!medianFilterComboBox.getValue().equals(0)) {
            appliedFilters += 0x01 << 1;
            this.statusLabel.setText("Aplicando o filtro de Mediana");
            originalImage = new Filter(FilterType.MEDIAN, medianFilterComboBox.getValue(), originalImage, null).applyFilter();
        }

        if (sobelFiterToggleButton.isSelected()) {
            appliedFilters += 0x01 << 2;
            this.statusLabel.setText("Aplicando o filtro de Sobel");
            originalImage = new Filter(FilterType.SOBEL, medianFilterComboBox.getValue(), originalImage, null).applyFilter();
        }
        if (laplaceFiterToggleButton.isSelected()) {
            appliedFilters += 0x01 << 3;
            this.statusLabel.setText("Aplicando o filtro Laplaciano");
            originalImage = new Filter(FilterType.LAPLACE, meanFilterComboBox.getValue(), originalImage, null).applyFilter();
        }

        if (negativeFiterToggleButton.isSelected()) {
            appliedFilters += 0x01 << 4;
            this.statusLabel.setText("Aplicando negativo");
            originalImage = new Negative().applyFilter(originalImage, ((RadioButton)group.getSelectedToggle())
                    .getText().equals("YIQ") ? 0x00 : 0xFF);
        }

        if (customFiterToggleButton.isSelected()) {
            appliedFilters += 0x01 << 5;
            this.statusLabel.setText("Aplicando filtro customizado");
            originalImage = new Filter(FilterType.CUSTOM, meanFilterComboBox.getValue(), originalImage, this.getKernelMatrix()).applyFilter();
        }

        if (convolutionFilterToggleButton.isSelected()) {
            appliedFilters += 0x01 << 6;
            this.statusLabel.setText("Aplicando filtro de convolução");
            originalImage = new Conv2D(originalImage, this.getKernelMatrixAsFloat()).applyConv();
        }

        if (appliedFilters == 0x00) {
            this.statusLabel.setText("Os filtros não puderam ser aplicados");
            this.originator.restore(this.careTaker.getMemento());
        } else {
            this.setImage(originalImage);
        }

    }

    private void undo() {
        this.originator.restore(this.careTaker.getMemento());
        try {
            this.setImage(this.originator.getCurrentImage());
            this.statusLabel.setText("Undoing");
        } catch (NullPointerException e) {}
    }

    private void setGridMask() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                TextField textField = getNodeFromGridPane(i, j);
                textField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
                textField.setText("0");
            }
        }
    }

    private int[][] getKernelMatrix() {
        int[][] arr = new int[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                TextField textField = getNodeFromGridPane(i, j);
                try {
                    arr[i][j] = Integer.parseInt(textField.getText());
                } catch (NumberFormatException e) {
                    textField.setText("0");
                    arr[i][j] = 0;
                }
            }
        }
        return arr;
    }

    private float[][] getKernelMatrixAsFloat() {
        float[][] arr = new float[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                TextField textField = getNodeFromGridPane(i, j);
                try {
                    arr[i][j] = Float.parseFloat(textField.getText());
                } catch (NumberFormatException e) {
                    textField.setText("0");
                    arr[i][j] = 0.0f;
                }
            }
        }
        return arr;
    }

    private TextField getNodeFromGridPane(int col, int row) {
        return (TextField) (gridPane.getChildren().get(col*3+row));
    }

    private void presentBadImageAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ImageManipulation");
        alert.setHeaderText(title);
        alert.setContentText(text);
        alert.showAndWait();
    }

    private void setImage(BufferedImage image) {
        this.imageView.setImage(SwingFXUtils.toFXImage(image, null));
    }

    @Nullable
    private BufferedImage getImage() {
        try {
            return SwingFXUtils.fromFXImage(this.imageView.getImage(), null);
        } catch (NullPointerException e) {
            return null;
        }
    }


    private void addToMemento(BufferedImage image) {
        this.originator.setBufferedImage(image);
        this.careTaker.addMemento(this.originator.save());
    }
}
