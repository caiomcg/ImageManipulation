package IM.Process.Brightness;

import java.awt.Color;
import java.awt.image.BufferedImage;

import IM.Process.Process;

public class Multiplicative extends Process {

    @Override
    protected int transform(BufferedImage image, int pixelX, int pixelY, Object obj) {
        int constant = (int) obj;
        int rgb[] = null;

        Color colors = new Color(image.getRGB(pixelX, pixelY));
        if (constant > 0)
            rgb = this.multiplyBrightness(colors, constant);
        else
            rgb = this.divideBrightness(colors, Math.abs(constant));

        return new Color(rgb[0], rgb[1], rgb[2]).getRGB();
    }

    public int[] multiplyBrightness(Color colors, int constant) {
        int r = colors.getRed();
        int g = colors.getGreen();
        int b = colors.getBlue();

        int br = r * constant;
        int bg = g * constant;
        int bb = b * constant;

        if (br > 255)
            br = 255;
        if (bg > 255)
            bg = 255;
        if (bb > 255)
            bb = 255;

        return new int[]{br, bg, bb};
    }

    public int[] divideBrightness(Color colors, int constant) {
        int r = colors.getRed();
        int g = colors.getGreen();
        int b = colors.getBlue();

        int br = r / constant;
        int bg = g / constant;
        int bb = b / constant;

        if (br < 0)
            br = 0;
        if (bg < 0)
            bg = 0;
        if (bb < 0)
            bb = 0;

        return new int[]{br, bg, bb};
    }
}
