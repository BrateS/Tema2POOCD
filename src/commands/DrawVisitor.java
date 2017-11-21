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
                        imageCommand.getNumericArgs()[1],
                        imageCommand.getNumericArgs()[0]
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
                imageCommand.getNumericArgs()[0],
                imageCommand.getNumericArgs()[1],
                imageCommand.getNumericArgs()[2]);
        fill(image,
                imageCommand.getColorArgs()[0].getRGB(),
                imageCommand.getColorArgs()[1].getRGB(),
                imageCommand.getNumericArgs()[0],
                imageCommand.getNumericArgs()[1]);
    }

    @Override
    public void visit(final Diamond diamond) {
        //constructing imageCommand for polygon from diamond
        final int nrPointDiamond = 4;
        ImageCommand imageCommand = diamond.getImageCommand();
        Color contourColor = diamond.getImageCommand().getColorArgs()[0];
        int semiDiagOriz = Math.floorDiv(diamond.getImageCommand().getNumericArgs()[2], 2);
        int semiDiagVert = Math.floorDiv(diamond.getImageCommand().getNumericArgs()[3], 2);
        int xCenter = diamond.getImageCommand().getNumericArgs()[0];
        int yCenter = diamond.getImageCommand().getNumericArgs()[1];

        int xStart = xCenter;
        int yStart = yCenter - semiDiagVert;
        int xFinal = xCenter + semiDiagOriz;
        int yFinal = yCenter;
        Line line = new Line(getLineCommand(
                xStart,
                yStart,
                xFinal,
                yFinal ,
                contourColor));
        line.accept(this);

        xStart = xCenter + semiDiagOriz;
        yStart = yCenter;
        xFinal = xCenter;
        yFinal = yCenter + semiDiagVert;

        line = new Line(getLineCommand(
                xStart,
                yStart,
                xFinal,
                yFinal ,
                contourColor));
        line.accept(this);

        xStart = xCenter;
        yStart = yCenter + semiDiagVert;
        xFinal = xCenter - semiDiagOriz;
        yFinal = yCenter;

        line = new Line(getLineCommand(
                xStart,
                yStart,
                xFinal,
                yFinal ,
                contourColor));
        line.accept(this);

        xStart = xCenter;
        yStart = yCenter - semiDiagVert;
        xFinal = xCenter - semiDiagOriz;
        yFinal = yCenter;

        line = new Line(getLineCommand(
                xStart,
                yStart,
                xFinal,
                yFinal ,
                contourColor));
        line.accept(this);

        imageCommand.addNumericArgs(xCenter);
        imageCommand.addNumericArgs(yCenter - semiDiagVert);

        imageCommand.addNumericArgs(xCenter + semiDiagOriz);
        imageCommand.addNumericArgs(yCenter);

        imageCommand.addNumericArgs(xCenter);
        imageCommand.addNumericArgs(yCenter + semiDiagVert);

        imageCommand.addNumericArgs(xCenter - semiDiagOriz);
        imageCommand.addNumericArgs(yCenter);

        Canvas image = Canvas.getInstance();

        fill(image,
                imageCommand.getColorArgs()[0].getRGB(),
                imageCommand.getColorArgs()[1].getRGB(),
                imageCommand.getNumericArgs()[0],
                imageCommand.getNumericArgs()[1]);

    }

    @Override
    public void visit(final Line line) {
        Canvas image = Canvas.getInstance();

        final int yFinalPosition = 3;
        ImageCommand imageCommand = line.getImageCommand();
        int xStart = imageCommand.getNumericArgs()[0];
        int yStart = imageCommand.getNumericArgs()[1];
        int xFinal = imageCommand.getNumericArgs()[2];
        int yFinal = imageCommand.getNumericArgs()[yFinalPosition];
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
            if (!(x > image.getWidth() - 1 || y > image.getHeight() - 1 || x < 0 || y < 0)) {
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
        int xStart = imageCommand.getNumericArgs()[0];
        int yStart = imageCommand.getNumericArgs()[1];
        int width = imageCommand.getNumericArgs()[widthArrayLocation];
        int height = imageCommand.getNumericArgs()[2];


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
        imageCommand.addNumericArgs(imageCommand.getNumericArgs()[2]);
        Rectangle rectangle = new Rectangle(imageCommand);
        rectangle.accept(this);
    }

    @Override
    public void visit(final Polygon polygon) {
        Color contourColor = polygon.getImageCommand().getColorArgs()[0];
        Color interiorColor = polygon.getImageCommand().getColorArgs()[1];
        ImageCommand imageCommand = polygon.getImageCommand();

        // drawing the lines
        int xPrev = imageCommand.getNumericArgs()[1];
        int yPrev = imageCommand.getNumericArgs()[2];
        for (int i = 3; i < imageCommand.getNumericArgs()[0] * 2; i += 2) {
            int xCur = imageCommand.getNumericArgs()[i];
            int yCur = imageCommand.getNumericArgs()[i + 1];
            Line line = new Line(getLineCommand(
                    xPrev,
                    yPrev,
                    xCur,
                    yCur,
                    contourColor));
            line.accept(this);

            System.out.println(
                    "Line (" + xPrev + "," + yPrev
                            + ")-->(" + xCur + "," + xPrev + ")");
            xPrev = xCur;
            yPrev = yCur;
        }
        //last line
        int xCur = imageCommand.getNumericArgs()[1];
        int yCur = imageCommand.getNumericArgs()[2];

        Line line = new Line(getLineCommand(
                xPrev,
                yPrev,
                xCur,
                yCur,
                contourColor));
        line.accept(this);

        System.out.println(
                "Line (" + xPrev + "," + yPrev
                        + ")-->(" + xCur + "," + yCur + ")");

        //fill the shape
        Canvas image = Canvas.getInstance();
        int[]center = polygon.getCenterOfGravity();
        System.out.println(Arrays.toString(center));
        fill(image,
                contourColor.getRGB(),
                interiorColor.getRGB(),
                center[0],
                center[1]);
    }

    @Override
    public void visit(final Triangle triangle) {
        //constructing imageCommand for polygon from triangle
        final int nrPointTriangle = 3;
        ImageCommand imageCommand = new ImageCommand();
        imageCommand.addNumericArgs(nrPointTriangle);
        for (int i = 0; i < nrPointTriangle * 2; i++) {
            imageCommand.addNumericArgs(triangle.getImageCommand().getNumericArgs()[i]);
        }
        imageCommand.addColor(triangle.getImageCommand().getColorArgs()[0]);
        imageCommand.addColor(triangle.getImageCommand().getColorArgs()[1]);
        //calling polygon command
        System.out.println(imageCommand);
        Polygon polygon = new Polygon(imageCommand);
        polygon.accept(this);
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
    public static void fill(final Canvas image,
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
