package IM.Process.SpatialFilters;

import java.awt.image.BufferedImage;

public class CustomFilter implements FilterAlgorithm {

    @Override
    public BufferedImage applyFilter(Filter filter) {
        int imgWidth = filter.getBufferedImage().getWidth();
        int imgHeight = filter.getBufferedImage().getHeight();
        int[][] kernel = filter.getKernel();

        // Custom filter only allows 3x3 kernel
        int maskSize = 1;

        BufferedImage filterResult = new BufferedImage(imgWidth, imgHeight, filter.getBufferedImage().getType());

        // Support to calculate the pixel value
        int buffer;

        for (int i = 0; i < imgWidth; i++) {
            for (int j = 0; j < imgHeight; j++) {

                buffer = 0;

                // Ignore edges
                if (i == 0 || i == imgWidth  - 1 || j == 0 || j == imgHeight - 1) {
                    filterResult.setRGB(i, j, filter.getBufferedImage().getRGB(i, j));
                    continue;
                }

                for(int row = i - maskSize; row <= i + maskSize; row++){
                    for(int column = j - maskSize; column <= j + maskSize; column++){
                        buffer += kernel[row % kernel.length][column % kernel.length] * filter.getBufferedImage().getRGB(row, column);
                    }
                }

                filterResult.setRGB(i, j, buffer);

            }
        }

        return filterResult;
 }
}
