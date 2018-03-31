package IM.Process.Colors;

import IM.Process.Process;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Threshold extends Process {

    @Override
    protected int transform(BufferedImage image, int pixelX, int pixelY, Object obj) {
        int constant = (int) obj;
        int new_y = 0;

        Color colors = new Color(image.getRGB(pixelX, pixelY));
        if (constant > 0)
            new_y = this.yThreshold(colors.getRed(), constant);

        return new Color(new_y, colors.getGreen(), colors.getBlue()).getRGB();
    }

    private int yThreshold(int yBand, int constant) {
        int newColor = 0;

        if (yBand <= constant)
            newColor = 255;
        return newColor;
    }

    public int getYMean(BufferedImage image) {
        int sumYValues = 0;

        for (int y = 0; y < image.getHeight(); y++){
            for (int x = 0; x < image.getWidth(); x++){
                Color color = new Color(image.getRGB(x, y));
                sumYValues += color.getRed();
            }
        }

        return sumYValues / (image.getHeight() * image.getWidth());
    }
}