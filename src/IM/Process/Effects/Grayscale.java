package IM.Process.Effects;

import IM.Process.Process;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Grayscale extends Process {
    @Override
    protected int transform(BufferedImage image, int pixelX, int pixelY, Object obj) {
        boolean yiq = (boolean) obj;

        int pixel = image.getRGB(pixelX, pixelY);

        int average = yiq ?(pixel>>16) & 0xff : (((pixel>>16) & 0xff) + ((pixel>>8) & 0xff) + (pixel & 0xff)) / 3;

        return (((pixel >> 24) & 0xFF) << 24) | (average << 16) | (average << 8) | average;
    }
}