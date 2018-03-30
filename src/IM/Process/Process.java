package IM.Process;

import java.awt.image.BufferedImage;

public abstract class Process {

    public BufferedImage temp = null;

    public BufferedImage applyFilter(BufferedImage image, Object obj){
        if (temp == null){
            temp = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        }

        for (int y = 0; y < image.getHeight(); y++){
            for (int x = 0; x < image.getWidth(); x++){
                temp.setRGB(x, y, this.transform(image, x, y, obj));
            }
        }

        return temp;
    }

    protected abstract int transform(BufferedImage image, int pixelX, int pixelY, Object obj);
}
