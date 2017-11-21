package commands;

import interfaces.Visitor;
import shapes.*;
import java.awt.Color;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static commands.ImageCommand.getLineCommand;

public final class DrawVisitor implements Visitor {

    public DrawVisitor(final ImageCommand imageCommand) {
        Canvas image = Canvas
                .getInstance(
                        imageCommand.getNumaricArgs()[1],
                        imageCommand.getNumaricArgs()[0]
                );
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                image.setRGB(x, y, imageCommand.getColorArgs()[0].getRGB());
            }
        }
    }

    public Canvas getImage() {
        return Canvas.getInstance();
    }


    @Override
    public void visit(final Circle circle) {
        ImageCommand imageCommand = circle.getImageCommand();
        Canvas image = Canvas.getInstance();
        drawContour(image,
                imageCommand.getColorArgs()[0],
                imageCommand.getNumaricArgs()[0],
                imageCommand.getNumaricArgs()[1],
                imageCommand.getNumaricArgs()[2]);
        apply(image,
                imageCommand.getColorArgs()[0].getRGB(),
                imageCommand.getColorArgs()[1].getRGB(),
                imageCommand.getNumaricArgs()[0],
                imageCommand.getNumaricArgs()[1]);
    }

    @Override
    public void visit(final Diamond diamond) {

    }

    @Override
    public void visit(final Line line) {
        Canvas image = Canvas.getInstance();

        final int yFinalPosition = 3;
        ImageCommand imageCommand = line.getImageCommand();
        int xStart = imageCommand.getNumaricArgs()[0];
        int yStart = imageCommand.getNumaricArgs()[1];
        int xFinal = imageCommand.getNumaricArgs()[2];
        int yFinal = imageCommand.getNumaricArgs()[yFinalPosition];
        int x = xStart;
        int y = yStart;
        int deltaX = Math.abs(xFinal - xStart);
        int deltaY = Math.abs(yFinal - yStart);
        int s1 = Integer.signum(xFinal - xStart);
        int s2 = Integer.signum(yFinal - yStart);

        // interchange delta_x and delta_y, depending on the slope of the line
        boolean interchanged = false;
        if (deltaY > deltaX) {
            int aux = deltaX;
            deltaX = deltaY;
            deltaY = aux;
            interchanged = true;
        }

        // initialize the error term to compensate for a nonzero intercept
        int error = 2 * deltaY - deltaX;

        for (int i = 0; i <= deltaX; i++) {
            if (!(x > image.getWidth() - 1 || y > image.getHeight() - 1) || x < 0 || y < 0) {
                image.setRGB(x, y, imageCommand.getColorArgs()[0].getRGB());
            }
            while (error > 0) {
                if (interchanged) {
                    x = x + s1;
                } else {
                    y = y + s2;
                }
                error = error - 2 * deltaX;
            }

            if (interchanged) {
                y = y + s2;
            } else {
                x = x + s1;
            }

            error = error + 2 * deltaY;
        }
    }
    @Override
    public void visit(final Rectangle rectangle) {
        Canvas image = Canvas.getInstance();

        final int widthArrayLocation = 3;

        ImageCommand imageCommand = rectangle.getImageCommand();
        Color contourColor = imageCommand.getColorArgs()[0];
        Color interiorColor = imageCommand.getColorArgs()[1];
        int xStart = imageCommand.getNumaricArgs()[0];
        int yStart = imageCommand.getNumaricArgs()[1];
        int width = imageCommand.getNumaricArgs()[widthArrayLocation];
        int height = imageCommand.getNumaricArgs()[2];


        Line line = new Line(getLineCommand(
                xStart,
                yStart,
                xStart + width - 1,
                yStart,
                contourColor));
        line.accept(this);

        line = new Line(getLineCommand(
                xStart + width - 1,
                yStart,
                xStart + width - 1,
                yStart + height - 1,
                contourColor));
        line.accept(this);

        line = new Line(getLineCommand(
                xStart + width - 1,
                yStart + height - 1,
                xStart,
                yStart + height - 1,
                contourColor));
        line.accept(this);

        line = new Line(getLineCommand(
                xStart,
                yStart,
                xStart,
                yStart + height - 1,
                contourColor));
        line.accept(this);

        for (int x = xStart + 1; x < xStart + width - 1; x++) {
            for (int y = yStart + 1; y < yStart + height - 1; y++) {
                if (!(x > image.getWidth() - 1 || y > image.getHeight() - 1)) {
                    image.setRGB(x, y, interiorColor.getRGB());
                }
            }
        }
    }

    @Override
    public void visit(final Square square) {
        ImageCommand imageCommand = square.getImageCommand();
        imageCommand.addNumericArgs(imageCommand.getNumaricArgs()[2]);
        Rectangle rectangle = new Rectangle(imageCommand);
        rectangle.accept(this);
    }

    @Override
    public void visit(final Polygon polygon) {
        Color contourColor = polygon.getImageCommand().getColorArgs()[0];
        Color interiorColor = polygon.getImageCommand().getColorArgs()[1];
        ImageCommand imageCommand = polygon.getImageCommand();
        int xPrev = imageCommand.getNumaricArgs()[1];
        int yPrev = imageCommand.getNumaricArgs()[2];
        for (int i = 3; i < imageCommand.getNumaricArgs()[0] * 2 - 2; i += 2) {
            int xCur = imageCommand.getNumaricArgs()[i];
            int yCur = imageCommand.getNumaricArgs()[i + 1];
            Line line = new Line(getLineCommand(
                    xPrev,
                    yPrev,
                    xCur,
                    yCur,
                    contourColor));
            line.accept(this);
            xPrev = xCur;
            yPrev = yCur;
        }
        //last line
        int xCur = imageCommand.getNumaricArgs()[1];
        int yCur = imageCommand.getNumaricArgs()[2];

        Line line = new Line(getLineCommand(
                xPrev,
                yPrev,
                xCur,
                yCur,
                contourColor));
        line.accept(this);

        System.out.println(Arrays.toString(polygon.getCenterOfGravity()));

        System.out.println("Done.");
    }

    @Override
    public void visit(final Triangle triangle) {

    }

    public static void drawContour(final Canvas image,
                                   final Color color,
                                   final int xc,
                                   final int yc,
                                   final int r) {
        int x = 0, y = r;
        int d = 3 - 2 * r;
        while (y >= x) {
            // for each pixel we will
            // draw all eight pixels
            drawCircle(image, color, xc, yc, x, y);
            x++;

            // check for decision parameter
            // and correspondingly
            // update d, x, y
            if (d > 0) {
                y--;
                d = d + 4 * (x - y) + 10;
            } else {
                d = d + 4 * x + 6;
            }
            drawCircle(image, color, xc, yc, x, y);
        }
    }
    private static void drawCircle(final Canvas image,
                                   final Color color,
                                   final int xc,
                                   final int yc,
                                   final int x,
                                   final int y) {

        setRGBCheck(image, xc + x, yc + y, color.getRGB());
        setRGBCheck(image, xc - x, yc + y, color.getRGB());
        setRGBCheck(image, xc + x, yc - y, color.getRGB());
        setRGBCheck(image, xc - x, yc - y, color.getRGB());

        setRGBCheck(image, xc + y, yc + x, color.getRGB());
        setRGBCheck(image, xc - y, yc + x, color.getRGB());
        setRGBCheck(image, xc + y, yc - x, color.getRGB());
        setRGBCheck(image, xc - y, yc - x, color.getRGB());
    }
    private static void setRGBCheck(final Canvas image, final int x, final int y, final int rgb) {
        if (x > image.getWidth() - 1 || y > image.getHeight() - 1 || x < 0 || y < 0) {
            return;
        }
        image.setRGB(x, y, rgb);
    }
    private static boolean checkCoords(final Canvas image,
                                       final int x, final int y,
                                       final int contourRGB,
                                       final int rgb) {
        if (x > image.getWidth() - 1 || y > image.getHeight() - 1 || x < 0 || y < 0) {
            return false;
        }
        if (image.getRGB(x, y) == contourRGB || image.getRGB(x, y) == rgb) {
            return false;
        }
        return true;
    }
    public static void apply(final Canvas image,
                             final int contourColor,
                             final int colorToPaint,
                             final int x,
                             final int y) {
        final class Coords {
            private int x, y;
            private Coords(final int x, final int y) {
                this.x = x;
                this.y = y;
            }
        }
        Queue<Coords> queue = new LinkedList<>();
        queue.add(new Coords(x, y));
        while (!queue.isEmpty()) {
            Coords coords = queue.remove();
            if (coords == null) {
                break;
            }
            if (checkCoords(image, coords.x, coords.y, contourColor, colorToPaint)) {
                setRGBCheck(image, coords.x, coords.y, colorToPaint);
                int a = coords.x;
                int b = coords.y;
                queue.add(new Coords(a + 1, b));
                queue.add(new Coords(a - 1, b));
                queue.add(new Coords(a, b + 1));
                queue.add(new Coords(a, b - 1));
            }
        }
    }

}
