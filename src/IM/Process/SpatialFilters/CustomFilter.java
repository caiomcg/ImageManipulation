package IM.Process.SpatialFilters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CustomFilter implements FilterAlgorithm {

    @Override
    public BufferedImage applyFilter(Filter filter) {
        int imgWidth = filter.getBufferedImage().getWidth();
        int imgHeight = filter.getBufferedImage().getHeight();
        int[][] kernel = filter.getKernel();

        BufferedImage filterResult = new BufferedImage(imgWidth, imgHeight, filter.getBufferedImage().getType());

        for (int y = 1; y < imgHeight - 1; y++) {
            for (int x = 1; x < imgWidth - 1; x++) {


                Color c00 = new Color(filter.getBufferedImage().getRGB(x - 1, y - 1));
                Color c01 = new Color(filter.getBufferedImage().getRGB(x - 1, y));
                Color c02 = new Color(filter.getBufferedImage().getRGB(x - 1, y + 1));

                Color c10 = new Color(filter.getBufferedImage().getRGB(x, y - 1));
                Color c11 = new Color(filter.getBufferedImage().getRGB(x, y));
                Color c12 = new Color(filter.getBufferedImage().getRGB(x, y + 1));

                Color c20 = new Color(filter.getBufferedImage().getRGB(x + 1, y - 1));
                Color c21 = new Color(filter.getBufferedImage().getRGB(x + 1, y));
                Color c22 = new Color(filter.getBufferedImage().getRGB(x + 1, y + 1));

                int r = kernel[0][0]*c00.getRed() + kernel[0][1]*c01.getRed() + kernel[0][2]*c02.getRed() +
                        kernel[1][0]*c10.getRed() + kernel[1][1]*c11.getRed() + kernel[1][2]*c12.getRed() +
                        kernel[2][0]*c20.getRed() + kernel[2][1]*c21.getRed() + kernel[2][2]*c22.getRed();

                int g = kernel[0][0]*c00.getGreen() + kernel[0][1]*c01.getGreen() + kernel[0][2]*c02.getGreen() +
                        kernel[1][0]*c10.getGreen() + kernel[1][1]*c11.getGreen() + kernel[1][2]*c12.getGreen() +
                        kernel[2][0]*c20.getGreen() + kernel[2][1]*c21.getGreen() + kernel[2][2]*c22.getGreen();

                int b = kernel[0][0]*c00.getBlue() + kernel[0][1]*c01.getBlue() + kernel[0][2]*c02.getBlue() +
                        kernel[1][0]*c10.getBlue() + kernel[1][1]*c11.getBlue() + kernel[1][2]*c12.getBlue() +
                        kernel[2][0]*c20.getBlue() + kernel[2][1]*c21.getBlue() + kernel[2][2]*c22.getBlue();

                r = Math.min(255, Math.max(0, r));
                g = Math.min(255, Math.max(0, g));
                b = Math.min(255, Math.max(0, b));

                Color color = new Color(r, g, b);

                filterResult.setRGB(x, y, color.getRGB());
            }
        }

        return filterResult;
    }
}
