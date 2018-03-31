package IM.Process.SpatialFilters;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class MedianFilter implements FilterAlgorithm {

    @Override
    public BufferedImage applyFilter(Filter filter) {
        int imgWidth = filter.getBufferedImage().getWidth();
        int imgHeight = filter.getBufferedImage().getHeight();
        int maskSize = filter.getFilterSize();

        BufferedImage filterResult = new BufferedImage(imgWidth, imgHeight, filter.getBufferedImage().getType());

        // Support to calculate the median
        int[] buffer = new int[maskSize * maskSize];

        for (int i = 0; i < imgWidth; i++) {
            for (int j = 0; j < imgHeight; j++) {

                // Ignore edges
                if (i <= maskSize || i >= imgWidth - maskSize || j <= maskSize || j >= imgHeight - maskSize) {
                    filterResult.setRGB(i, j, filter.getBufferedImage().getRGB(i, j));
                    continue;
                }

                int it = 0;
                for(int row = i - (maskSize/2); row <= i + (maskSize/2); row++){
                    for(int column = j - (maskSize/2); column <= j + (maskSize/2); column++){
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
