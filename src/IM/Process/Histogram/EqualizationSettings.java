package IM.Process.Histogram;

import java.awt.image.BufferedImage;

public class EqualizationSettings {
    public int[] vectorQuantity = new int[256];
    public int[] vectorQuantitySum = new int[256];
    public int imageSize;
    public int L;

    public EqualizationSettings (int l, BufferedImage image) {
        imageSize = image.getHeight() * image.getWidth();
        L = l;
        getColorsQuantity(image);
    }

    public void getColorsQuantity (BufferedImage image) {
        for (int y = 0; y < image.getHeight(); y++){
            for (int x = 0; x < image.getWidth(); x++){
                int color = image.getRGB(x, y);
                vectorQuantity[((color>>16) & 0xff)]++;
            }
        }

        vectorQuantitySum[0] = vectorQuantity[0];
        for (int i = 1; i < vectorQuantity.length; i++) {
            vectorQuantitySum[i] = vectorQuantitySum[i-1] + vectorQuantity[i];
        }
    }
}
