package IM.Process.Brightness;

import java.awt.Color;
import java.awt.image.BufferedImage;

import IM.Process.Process;

public class Multiplicative extends Process {

    @Override
    protected int transform(BufferedImage image, int pixelX, int pixelY, Object obj) {
        double constant = (double) obj;
        int rgb[] = null;

        Color colors = new Color(image.getRGB(pixelX, pixelY));
        rgb = this.multiplyBrightness(colors, constant);

        return new Color(rgb[0], rgb[1], rgb[2]).getRGB();
    }

    public int[] multiplyBrightness(Color colors, double constant) {
        double r = colors.getRed();
        double g = colors.getGreen();
        double b = colors.getBlue();

        double br = r * constant;
        double bg = g * constant;
        double bb = b * constant;

        if (br > 255.0)
            br = 255.0;
        if (bg > 255.0)
            bg = 255.0;
        if (bb > 255.0)
            bb = 255.0;

        return new int[]{(int)br, (int)bg, (int)bb};
    }
}
