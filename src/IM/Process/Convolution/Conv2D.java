package IM.Process.Convolution;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Conv2D {

    private float[][] mask;
    private BufferedImage image;

    public Conv2D(BufferedImage image, float[][] mask) {
        this.image = image;
        this.mask = new MatrixUtil().rotate180(mask);
    }

    public BufferedImage applyConv() {
        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();
        int maskSize = mask.length;

        BufferedImage convResult = new BufferedImage(imgWidth, imgHeight, image.getType());

        int targetPixelRED;
        int targetPixelGREEN;
        int targetPixelBLUE;

        int pixelRGB;

        for (int y = maskSize / 2; y < imgWidth - (maskSize/2); y++) {
            for (int x = maskSize / 2; x < imgHeight - (maskSize/2); x++) {

                targetPixelRED = 0;
                targetPixelGREEN = 0;
                targetPixelBLUE = 0;

                for (int ki = - maskSize / 2; ki <= maskSize / 2; ki++) {
                    for (int kj = - maskSize / 2; kj <= maskSize / 2; kj++) {
                        pixelRGB = image.getRGB(y+ki, x+kj);

                        targetPixelRED += ((pixelRGB >> 16) & 0xFF) * mask[ki+maskSize/2][kj+maskSize/2];
                        targetPixelGREEN += ((pixelRGB >> 8) & 0xFF) * mask[ki+maskSize/2][kj+maskSize/2];
                        targetPixelBLUE += (pixelRGB & 0xFF) * mask[ki+maskSize/2][kj+maskSize/2];
                    }
                }

                if (targetPixelRED < 0)
                    targetPixelRED = 0;
                else if (targetPixelRED > 255)
                    targetPixelRED = 255;

                if (targetPixelGREEN < 0)
                    targetPixelGREEN = 0;
                else if (targetPixelGREEN > 255)
                    targetPixelGREEN = 255;

                if (targetPixelBLUE < 0)
                    targetPixelBLUE = 0;
                else if (targetPixelBLUE > 255)
                    targetPixelBLUE = 255;

                convResult.setRGB(y, x, new Color(targetPixelRED, targetPixelGREEN, targetPixelBLUE).getRGB());
            }
        }

        return convResult;
    }

}
