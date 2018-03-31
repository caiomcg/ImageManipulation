package IM.Process.SpatialFilters;

import java.awt.image.BufferedImage;

public class Filter {

    public static final int MEAN = 1;
    public static final int MEDIAN = 2;
    public static final int CUSTOM = 3;
    public static final int SOBEL = 4;

    protected int filterSize;
    protected int[][] kernel;

    protected FilterAlgorithm filter;
    protected BufferedImage image;

    public Filter(int filterType, int filterSize, BufferedImage image, int[][] kernel) {
        this.filterSize = filterSize;
        this.image = image;
        this.kernel = kernel;

        switch (filterType) {
            case MEAN:
                filter = new MeanFilter();
                break;
            case MEDIAN:
                filter = new MedianFilter();
                break;
            case CUSTOM:
                filter = new CustomFilter();
                break;
            case SOBEL:
                filter = new SobelFilter();
                break;
        }
    }

    public BufferedImage applyFilter() {
        return filter.applyFilter(this);
    }

    public int getFilterSize() {
        return filterSize;
    }

    public BufferedImage getBufferedImage() {
        return image;
    }

    public int[][] getKernel() {
        return kernel;
    }
}
