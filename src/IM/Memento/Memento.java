package IM.Memento;

import java.awt.image.BufferedImage;

public class Memento {
    private BufferedImage bufferedImage;

    public Memento(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }
}
