package IM.Process.BandSelection;

import IM.Process.Process;

import java.awt.image.BufferedImage;

public class BandSelector extends Process {

    @Override
    protected int transform(BufferedImage image, int pixelX, int pixelY, Object obj) {
        return image.getRGB(pixelX, pixelY) & (int) obj;
    }
}
