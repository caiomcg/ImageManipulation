package IM.Process.Colors;

import java.awt.Color;
import java.awt.image.BufferedImage;
import IM.Process.Process;

public class Conversor extends Process {
    @Override
    protected int transform(BufferedImage image, int pixelX, int pixelY, Object obj) {
        boolean yiq_status = (boolean) obj;

        Color color = new Color(image.getRGB(pixelX, pixelY));
        int[] rgb = null;

        if (yiq_status) {
            float[] yiq = this.RGBtoYIQ(color);
            rgb = new int[]{(int)yiq[0], (int)yiq[1], (int)yiq[2]};
        } else
            rgb = this.YIQtoRGB(color);

        return new Color(rgb[0], rgb[1], rgb[2]).getRGB();
    }

    public float[] RGBtoYIQ(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        float y = ((0.299f * r) + (0.587f * g) + (0.114f * b));
        float i = ((0.596f * r) - (0.275f * g) - (0.321f * b));
        float q = ((0.212f * r) - (0.523f * g) + (0.311f * b));

        if (i < 0)
            i = 0;
        if (q < 0)
            q = 0;

        return new float[]{y, i, q};
    }

    public int[] YIQtoRGB(Color color) {
        int y = color.getRed();
        int i = color.getGreen();
        int q = color.getBlue();

        int r = (int)Math.round(y + (0.956 * i) + (0.621 * q));
        int g = (int)Math.round(y - (0.272 * i) - (0.647 * q));
        int b = (int)Math.round(y - (1.106 * i) + (1.703 * q));

        if (r > 255)
            r = 255;
        if (r < 0)
            r = 0;
        if (g > 255)
            g = 255;
        if (g < 0)
            g = 0;
        if (b > 255)
            b = 255;
        if (b < 0)
            b = 0;


        return new int[]{r, g, b};
    }
}
