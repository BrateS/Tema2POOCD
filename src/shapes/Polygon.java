package shapes;

import commands.ImageCommand;
import interfaces.Shape;
import interfaces.Visitable;
import interfaces.Visitor;

import java.awt.geom.Point2D;

public final class Polygon extends Shape implements Visitable {

    public Polygon(final ImageCommand imageCommand) {
        super(imageCommand);
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }

    public int getArea() {
        ImageCommand imageCommand = this.getImageCommand();
        int area = 0;
        int xPrev = imageCommand.getNumaricArgs()[1];
        int yPrev = imageCommand.getNumaricArgs()[2];
        for (int i = 3; i < imageCommand.getNumaricArgs()[0] * 2 - 2; i += 2) {
            int xCur = imageCommand.getNumaricArgs()[i];
            int yCur = imageCommand.getNumaricArgs()[i + 1];
            int auxSum = xCur * yPrev - xPrev * yCur;
            area += auxSum;
            xPrev = xCur;
            yPrev = yCur;
        }
        return area / 2;
    }

    public int[] getCenterOfGravity() {
        ImageCommand imageCommand = this.getImageCommand();
        int area = getArea();
        int xResult = 0;
        int yResult = 0;
        int xPrev = imageCommand.getNumaricArgs()[1];
        int yPrev = imageCommand.getNumaricArgs()[2];

        for (int i = 3; i < imageCommand.getNumaricArgs()[0] * 2 - 2; i += 2) {
            int xCur = imageCommand.getNumaricArgs()[i];
            int yCur = imageCommand.getNumaricArgs()[(i + 1)
                    % (imageCommand.getNumaricArgs()[0] * 2)];

            System.out.println(xCur + " " + yCur);
            int xFirstElement = xCur + xPrev;
            int yFirstElement = yCur + yPrev;
            int secondElement = xCur * yPrev - xPrev * yCur;
            int xAuxSum = xFirstElement * secondElement;
            int yAuxSum = yFirstElement * secondElement;
            xResult -= xAuxSum;
            yResult -= yAuxSum;

            xPrev = xCur;
            yPrev = yCur;
        }

        final int formulaConstant = 6;
        int[]result = new int[2];

        result[0] = (xResult / formulaConstant) / area;
        result[1] = (yResult / formulaConstant) / area;
        return result;
    }
}
