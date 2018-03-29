package IM.Process.Brightness;

import java.awt.Color;
import java.awt.image.BufferedImage;

import IM.Process.Process;

public class Multiplicative extends Process {

    @Override
    protected int transform(BufferedImage image, int pixelX, int pixelY, Object obj) {
        float constant = (float) obj;
        int rgb[] = null;

        Color colors = new Color(image.getRGB(pixelX, pixelY));
        if (constant > 0)
            rgb = this.multiplyBrightness(colors, constant);
        else
            rgb = this.divideBrightness(colors, Math.abs(constant));

        return new Color(rgb[0], rgb[1], rgb[2]).getRGB();
    }

    public int[] multiplyBrightness(Color colors, float constant) {
        float r = colors.getRed();
        float g = colors.getGreen();
        float b = colors.getBlue();

        float br = r * constant;
        float bg = g * constant;
        float bb = b * constant;

        //System.out.println("R: " + br + " - G: " + bg + " - B: " + bb);

        if (br > 255)
            br = 255;
        if (bg > 255)
            bg = 255;
        if (bb > 255)
            bb = 255;

        return new int[]{Math.round(br), Math.round(bg), Math.round(bb)};
    }

    public int[] divideBrightness(Color colors, float constant) {
        float r = colors.getRed();
        float g = colors.getGreen();
        float b = colors.getBlue();

        float br = r / constant;
        float bg = g / constant;
        float bb = b / constant;

        System.out.println("R: " + br + " - G: " + bg + " - B: " + bb);

        if (br < 0)
            br = 0;
        if (bg < 0)
            bg = 0;
        if (bb < 0)
            bb = 0;

        return new int[]{Math.round(br), Math.round(bg), Math.round(bb)};
    }
}
