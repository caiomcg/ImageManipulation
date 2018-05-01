package IM.Process.Histogram;

import IM.Process.Process;
import java.awt.image.BufferedImage;

public class Equalization extends Process {
    @Override
    protected int transform (BufferedImage image, int pixelX, int pixelY, Object obj) {
        EqualizationSettings config = (EqualizationSettings) obj;

        int color = image.getRGB(pixelX, pixelY);
        int newColor = (config.vectorQuantitySum[((color>>16) & 0xff)] * config.L) / config.imageSize;
        int p = (((color >> 24) & 0xFF) << 24) | (newColor << 16) | (newColor << 8) | newColor;

        return p;
    }
}
