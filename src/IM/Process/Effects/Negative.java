package IM.Process.Effects;

import java.awt.image.BufferedImage;
import IM.Process.Process;

public class Negative extends Process {
    @Override
    protected int transform(BufferedImage image, int pixelX, int pixelY, Object obj) {
        int modifier = (int) obj;
        int pixel = image.getRGB(pixelX, pixelY);

        pixel ^= (0xFF << 16);
        pixel ^= ((modifier & 0xFF) << 8);
        pixel ^= (modifier & 0xFF);

        return pixel;
    }
}
