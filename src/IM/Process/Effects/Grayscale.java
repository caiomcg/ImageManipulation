package IM.Process.Effects;

import IM.Process.Process;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Grayscale extends Process {
    @Override
    protected int transform(BufferedImage image, int pixelX, int pixelY, Object obj) {
        int pixel = image.getRGB(pixelX, pixelY);

        int average = (((pixel>>16) & 0xff) + ((pixel>>8) & 0xff) + (pixel & 0xff)) / 3;

        int p = (((pixel >> 24) & 0xFF) << 24) | (average << 16) | (average << 8) | average;

        System.err.println(Integer.toBinaryString(p));

        return p;
    }
}