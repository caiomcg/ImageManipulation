package IM.Process.Colors;

import IM.Process.Process;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Threshold extends Process {

    @Override
    protected int transform(BufferedImage image, int pixelX, int pixelY, Object obj) {
        int constant = (int) obj;

        int pixel = image.getRGB(pixelX, pixelY);

        if (constant > 0)
            pixel = (pixel & ~(0xFF << 16) | (this.yThreshold((pixel >> 16) & 0xFF, constant) << 16));

        return pixel;
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
                sumYValues += (image.getRGB(x,y) >> 16) & 0xFF;
            }
        }

        return sumYValues / (image.getHeight() * image.getWidth());
    }
}