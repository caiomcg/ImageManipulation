package IM.Process.SpatialFilters;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class MeanFilter implements FilterAlgorithm {

    @Override
    public BufferedImage applyFilter(Filter filter) {
        int imgWidth = filter.getBufferedImage().getWidth();
        int imgHeight = filter.getBufferedImage().getHeight();
        int maskSize = filter.getFilterSize();

        BufferedImage filterResult = new BufferedImage(imgWidth, imgHeight, filter.getBufferedImage().getType());

        // Support to calculate the mean of each channel of pixel
        int pixelRGB;
        int redSum, greenSum, blueSum;

        for (int i = 0; i < imgHeight ; i++) {
            for (int j = 0; j < imgWidth; j++) {

                redSum = 0;
                greenSum = 0;
                blueSum = 0;

                int it = 0;
                for (int row = i - (maskSize/2); row <= i + (maskSize/2); row++) {
                    for (int column = j - (maskSize/2); column <= j + (maskSize/2); column++) {

                        // Ignore edges
                        if(row < 0 || row >= imgHeight || column < 0 || column >= imgWidth) {
                            filterResult.setRGB(i, j, filter.getBufferedImage().getRGB(i, j));
                            continue;
                        }

                        pixelRGB = filter.getBufferedImage().getRGB(row, column);

                        redSum += (pixelRGB >> 16) & 0xFF;
                        greenSum += (pixelRGB >> 8) & 0xFF;
                        blueSum += pixelRGB & 0xFF;
                        it++;
                    }
                }

                filterResult.setRGB(i, j, new Color(redSum/it, greenSum/it, blueSum/it).getRGB());

             }
        }

        return filterResult;
    }
}


