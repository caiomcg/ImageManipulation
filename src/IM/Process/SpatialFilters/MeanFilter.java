package IM.Process.SpatialFilters;

import java.awt.Color;
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
                if (i <= maskSize || i >= imgWidth - maskSize || j <= maskSize || j >= imgHeight - maskSize) {
                    filterResult.setRGB(i, j, filter.getBufferedImage().getRGB(i, j));
                    continue;
                }

                int rgbSum = 0;
                for (int row = i - (maskSize/2); row <= i + (maskSize/2); row++) {
                    for (int column = j - (maskSize/2); column <= j + (maskSize/2); column++) {
                        rgbSum  = filterResult.getRGB(i, j) + filter.getBufferedImage().getRGB(row, column);
                        filterResult.setRGB(i, j, rgbSum);
                    }
                }

                Color color = new Color(rgbSum);

                int maskPow = maskSize * maskSize;
                int redMean = color.getRed() / maskPow;
                int greenMean = color.getGreen() / maskPow;
                int blueMean = color.getBlue() / maskPow;

                filterResult.setRGB(i, j, new Color(redMean, greenMean, blueMean).getRGB());

             }
        }

        return filterResult;
    }
}


