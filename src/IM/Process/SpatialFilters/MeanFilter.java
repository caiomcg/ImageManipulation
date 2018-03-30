package IM.Process.SpatialFilters;

import java.awt.image.BufferedImage;
import java.lang.Math;

public class MeanFilter implements FilterAlgorithm {

    @Override
    public BufferedImage applyFilter(Filter filter) {
        int imgWidth = filter.getBufferedImage().getWidth();
        int imgHeight = filter.getBufferedImage().getHeight();
        int maskSize = filter.getFilterSize();

        BufferedImage filterResult = new BufferedImage(imgWidth, imgHeight, filter.getBufferedImage().getType());

        for (int i = 0; i < imgWidth; i++) {
            for (int j = 0; j < imgHeight; j++) {
                // Ignore edges
                if (i == 0 || i == imgWidth  - 1 || j == 0 || j == imgHeight - 1) {
                    filterResult.setRGB(i, j, filter.getBufferedImage().getRGB(i, j));
                    continue;
                }

                for (int row = i - (maskSize/2); row <= i + (maskSize/2); row++) {
                    for (int column = j - (maskSize/2); column <= j + (maskSize/2); column++) {
                        int rgbSum  = filterResult.getRGB(i, j) + filter.getBufferedImage().getRGB(row, column);
                        filterResult.setRGB(i, j, rgbSum);
                    }
                }

                float rgbMean = filterResult.getRGB(i, j) / (maskSize * maskSize);
                filterResult.setRGB(i, j, Math.round(rgbMean));

             }
        }

        return filterResult;
    }
}


