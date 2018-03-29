package IM.Process.BandSelection;

import IM.Process.Process;

import java.awt.image.BufferedImage;

public class BandSelector extends Process {

    @Override
    protected int transform(BufferedImage image, int pixelX, int pixelY, Object obj) {
        int channels = (int) obj;
        int pixel = image.getRGB(pixelX, pixelY);

        pixel &= channels;
        return pixel;
    }
}
