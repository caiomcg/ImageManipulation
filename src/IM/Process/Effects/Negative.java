package IM.Process.Effects;

import java.awt.Color;
import java.awt.image.BufferedImage;
import IM.Process.Process;
import IM.Process.Colors.ColorSpace;

public class Negative extends Process {
    private int current_color_space;

    @Override
    protected int transform(BufferedImage image, int pixelX, int pixelY, Object obj){
        Color color = new Color(image.getRGB(pixelX, pixelY));

        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        r = 255 - r;
        if(this.current_color_space != ColorSpace.YIQ) {
            g = 255 - g;
            b = 255 - b;
        }

        return new Color(r, g, b).getRGB();
    }

    public Negative(int current_color_space) {
        this.current_color_space = current_color_space;
    }
}
