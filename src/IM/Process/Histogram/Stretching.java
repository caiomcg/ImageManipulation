package IM.Process.Histogram;

import IM.Process.Process;
import java.awt.image.BufferedImage;

public class Stretching extends Process {

    @Override
    protected int transform (BufferedImage image, int pixelX, int pixelY, Object obj) {
        StretchingSettings config = (StretchingSettings) obj;

        int color = image.getRGB(pixelX, pixelY);
        double doubleColor = ((color>>16) & 0xff);
        double calc = ((doubleColor - config.minR)/(config.maxR-config.minR));
        int newColor = (int) (calc * config.L);
        int p = (((color >> 24) & 0xFF) << 24) | (newColor << 16) | (newColor << 8) | newColor;

        return p;
    }

    public StretchingSettings getStretchConfig (BufferedImage image) {
        int min = 999999999;
        int max = 0;

        for (int y = 0; y < image.getHeight(); y++){
            for (int x = 0; x < image.getWidth(); x++){
                int color = image.getRGB(x, y);
                if (((color>>16) & 0xff) < min) {
                    min = ((color>>16) & 0xff);
                } else if (((color>>16) & 0xff) > max) {
                    max = ((color>>16) & 0xff);
                }
            }
        }
        return new StretchingSettings (min, max, 255);
    }
}
