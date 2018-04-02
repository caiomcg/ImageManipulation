package IM.Process.SpatialFilters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SobelFilter implements FilterAlgorithm {
    @Override
    public BufferedImage applyFilter(Filter filter) {

        BufferedImage image = filter.getBufferedImage();

        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();

        BufferedImage filterResult = new BufferedImage(imgWidth, imgHeight, filter.getBufferedImage().getType());

        int[][] edgeColors = new int[imgWidth][imgHeight];
        int maxGradient = -1;

        for (int i = 1; i < imgWidth - 1; i++) {
            for (int j = 1; j < imgHeight - 1; j++) {

                int value00 = getGrayScale(image.getRGB(i - 1, j - 1));
                int value01 = getGrayScale(image.getRGB(i - 1, j));
                int value02 = getGrayScale(image.getRGB(i - 1, j + 1));
                int value10 = getGrayScale(image.getRGB(i, j - 1));
                int value11 = getGrayScale(image.getRGB(i, j));
                int value12 = getGrayScale(image.getRGB(i, j + 1));
                int value20 = getGrayScale(image.getRGB(i + 1, j - 1));
                int value21 = getGrayScale(image.getRGB(i + 1, j));
                int value22 = getGrayScale(image.getRGB(i + 1, j + 1));

                int gx =  ((-1 * value00) + (value02))
                        + ((-2 * value10) + (2 * value12))
                        + ((-1 * value20) + (value22));

                int gy =  ((-1 * value00) + (-2 * value01) + (-1 * value02))
                        //+ (( 0 * value10) + ( 0 * value11) + ( 0 * value12))
                          + ((value20) + ( 2 * value21) + (value22));

                int g = (int)Math.sqrt((gx * gx) + (gy * gy));

                if(maxGradient < g) {
                    maxGradient = g;
                }

                edgeColors[i][j] = g;
            }
        }

        double scale = 255.0 / maxGradient;

        for (int i = 1; i < imgWidth - 1; i++) {
            for (int j = 1; j < imgHeight - 1; j++) {
                int edgeColor = edgeColors[i][j];
                edgeColor = (int)(edgeColor * scale);
                edgeColor = 0xff000000 | (edgeColor << 16) | (edgeColor << 8) | edgeColor;

                filterResult.setRGB(i, j, edgeColor);
            }
        }
        return filterResult;

    }

    private static int  getGrayScale(int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        return (int)(0.2126 * r + 0.7152 * g + 0.0722 * b);
    }
}
