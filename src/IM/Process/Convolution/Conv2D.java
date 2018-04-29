package IM.Process.Convolution;

import java.awt.image.BufferedImage;

public class Conv2D {

    private int[][] mask;
    private BufferedImage image;

    public Conv2D(BufferedImage image, int[][] mask) {
        this.image = image;
        this.mask = new MatrixUtil().rotate180(mask);
    }

    public BufferedImage applyConv() {
        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();
        int maskSize = mask.length;

        BufferedImage convResult = new BufferedImage(imgWidth, imgHeight, image.getType());

        int targetPixelValue;

        for (int y = maskSize / 2; y < imgWidth - (maskSize/2); y++) {
            for (int x = maskSize / 2; x < imgHeight - (maskSize/2); x++) {

                targetPixelValue = 0;

                for (int ki = - maskSize / 2; ki <= maskSize / 2; ki++) {
                    for (int kj = - maskSize / 2; kj <= maskSize / 2; kj++) {
                        targetPixelValue += image.getRGB(y+ki, x+kj) * mask[ki+maskSize/2][kj+maskSize/2];
                    }
                }

                convResult.setRGB(y, x, targetPixelValue);

            }
        }

        return convResult;
    }

}
