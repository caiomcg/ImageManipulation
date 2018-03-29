package IM;

import com.sun.istack.internal.Nullable;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class Utils {
    @Nullable
    public static FileChooser createFileChooser() {
        FileChooser fileChooser = new FileChooser();
        //Set extension filter
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"));
        //Show save file dialog
        return fileChooser;
    }

    public static String getFileExtension(String url) {
        int index = url.lastIndexOf(".");
        if (index < 0) {
            return "";
        }
        return url.substring(index + 1);
    }
}
