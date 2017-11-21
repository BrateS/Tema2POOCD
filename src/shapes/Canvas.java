package shapes;

import java.awt.image.BufferedImage;

public final class Canvas extends BufferedImage {
    private static Canvas instance;
    private Canvas(final int width, final int height) {
        super(width, height, TYPE_4BYTE_ABGR);
    }

    public static Canvas getInstance(final int width, final int height) {
        if (instance == null) {
            instance = new Canvas(width, height);
        }
        return instance;
    }
    public static Canvas getInstance() {
        return instance;
    }
}
