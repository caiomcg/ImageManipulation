package IM.Process.SpatialFilters;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class MedianFilter implements FilterAlgorithm {

    @Override
    public BufferedImage applyFilter(Filter filter) {
        int imgWidth = filter.getBufferedImage().getWidth();
        int imgHeight = filter.getBufferedImage().getHeight();

        BufferedImage filterResult = new BufferedImage(imgWidth, imgHeight, filter.getBufferedImage().getType());

        // Support to calculate the median
        int buffer[];

        for (int i = 0; i < imgWidth; i++) {
            for (int j = 0; j < imgHeight; j++) {

                buffer = new int[filter.getFilterDimensionM() * filter.getFilterDimensionN()];

                // Ignore edges
                if (i == 0 || i == imgWidth  - 1 || j == 0 || j == imgHeight - 1) {
                    filterResult.setRGB(i, j, filter.getBufferedImage().getRGB(i, j));
                    continue;
                }

                int it = 0;
                for (int row = 0; row < filter.getFilterDimensionM(); row++) {
                    for (int column = 0; column < filter.getFilterDimensionN(); column++) {
                        buffer[it] = filter.getBufferedImage().getRGB(row, column);
                        it++;
                    }
                }

                Arrays.sort(buffer);
                filterResult.setRGB(i, j, buffer[it/2]);

            }
        }

        return filterResult;
    }
}
