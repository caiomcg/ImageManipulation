package IM.Process.SpatialFilters;

import java.awt.image.BufferedImage;

public class Filter {

    public static final int MEAN = 1;
    public static final int MEDIAN = 2;
    public static final int CUSTOM = 3;

    protected int filterM;
    protected int filterN;
    protected int[][] kernel;

    protected FilterAlgorithm filter;
    protected BufferedImage image;

    public Filter(int filterType, int filterM, int filterN, BufferedImage image, int[][] kernel) {
        this.filterM = filterM;
        this.filterN = filterN;
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
        }
    }

    public BufferedImage applyFilter() {
        return filter.applyFilter(this);
    }

    public int getFilterDimensionM() {
        return filterM;
    }

    public int getFilterDimensionN() {
        return filterN;
    }

    public BufferedImage getBufferedImage() {
        return image;
    }

    public int[][] getKernel() {
        return kernel;
    }
}
