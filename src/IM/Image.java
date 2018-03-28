package IM;

public class Image {
    private int width;
    private int height;
    private String path;

    Image(String path, int width, int height) {
        this.path = path;
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "Image: " + path + " " + width + "x" + height;
    }
}
