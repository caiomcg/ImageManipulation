package IM.Process.SpatialFilters;

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


                int c00 = filter.getBufferedImage().getRGB(x - 1, y - 1);
                int c01 = filter.getBufferedImage().getRGB(x - 1, y);
                int c02 = filter.getBufferedImage().getRGB(x - 1, y + 1);

                int c10 = filter.getBufferedImage().getRGB(x, y - 1);
                int c11 = filter.getBufferedImage().getRGB(x, y);
                int c12 = filter.getBufferedImage().getRGB(x, y + 1);

                int c20 = filter.getBufferedImage().getRGB(x + 1, y - 1);
                int c21 = filter.getBufferedImage().getRGB(x + 1, y);
                int c22 = filter.getBufferedImage().getRGB(x + 1, y + 1);

                int r = kernel[0][0] * (c00 >> 16 & 0xFF) + kernel[0][1] * (c01 >> 16 & 0xFF) + kernel[0][2] * (c02 >> 16 & 0xFF) +
                        kernel[1][0] * (c10 >> 16 & 0xFF) + kernel[1][1] * (c11 >> 16 & 0xFF) + kernel[1][2] * (c12 >> 16 & 0xFF) +
                        kernel[2][0] * (c20 >> 16 & 0xFF) + kernel[2][1] * (c21 >> 16 & 0xFF) + kernel[2][2] * (c22 >> 16 & 0xFF);

                int g = kernel[0][0] * (c00 >> 8 & 0xFF) + kernel[0][1] * (c01 >> 8 & 0xFF) + kernel[0][2] * (c02 >> 8 & 0xFF) +
                        kernel[1][0] * (c10 >> 8 & 0xFF) + kernel[1][1] * (c11 >> 8 & 0xFF) + kernel[1][2] * (c12 >> 8 & 0xFF) +
                        kernel[2][0] * (c20 >> 8 & 0xFF) + kernel[2][1] * (c21 >> 8 & 0xFF) + kernel[2][2] * (c22 >> 8 & 0xFF);

                int b = kernel[0][0] * (c00 & 0xFF) + kernel[0][1] * (c01 & 0xFF) + kernel[0][2] * (c02 & 0xFF) +
                        kernel[1][0] * (c10 & 0xFF) + kernel[1][1] * (c11 & 0xFF) + kernel[1][2] * (c12 & 0xFF) +
                        kernel[2][0] * (c20 & 0xFF) + kernel[2][1] * (c21 & 0xFF) + kernel[2][2] * (c22 & 0xFF);

                r = Math.min(255, Math.max(0, r));
                g = Math.min(255, Math.max(0, g));
                b = Math.min(255, Math.max(0, b));

                filterResult.setRGB(x, y, (0xFF << 24) | (r << 16) | (g << 8) | b);
            }
        }

        return filterResult;
    }
}