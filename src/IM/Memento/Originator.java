package IM.Memento;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class Originator {
    private BufferedImage currentImage;

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.currentImage = this.copy(bufferedImage);
    }

    public BufferedImage getCurrentImage() {
        return currentImage;
    }

    public Memento save() {
        return new Memento(currentImage);
    }

    public void restore(Memento memento) {
        if (memento != null)
            currentImage = memento.getBufferedImage();
    }

    private BufferedImage copy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
