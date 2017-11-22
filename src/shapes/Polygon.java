package shapes;

import commands.ImageCommand;
import interfaces.Shape;
import interfaces.Visitable;
import interfaces.Visitor;
import java.util.ArrayList;

public final class Polygon extends Shape implements Visitable {

    public Polygon(final ImageCommand imageCommand) {
        super(imageCommand);
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
    final class Point {
        private float x, y;

        float getX() {
            return x;
        }

        float getY() {
            return y;
        }
        Point(final float x, final float y) {
            this.x = x;
            this.y = y;
        }
    }

    private ArrayList<Point> getPoints() {
        ImageCommand imageCommand = this.getImageCommand();
        ArrayList<Point> list = new ArrayList<>();
        // goes through the (x,y) pairs and puts them in the array
        for (int i = 1; i < (imageCommand.getNumericArgs()[0]) * 2; i += 2) {
            int xCur = imageCommand.getNumericArgs()[i];
            int yCur = imageCommand.getNumericArgs()[i + 1];
            list.add(new Point(xCur, yCur));
        }
        return list;
    }

    private float getArea() {
        float area = 0;
        ArrayList<Point> points = getPoints();

        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            Point nextPoint = points.get((i + 1) % points.size());
            float auxSum = point.getX() * nextPoint.getY()
                    - nextPoint.getX() * point.getY();
            area += auxSum;
        }
        return area / 2;
    }
    public int[] getCenterOfGravity() {
        int[]result = new int[2];
        float area = getArea();
        ArrayList<Point> points = getPoints();
        float x = 0, y = 0;

        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            Point nextPoint = points.get((i + 1) % points.size());

            float xFirstTerm = point.getX() + nextPoint.getX();
            float yFirstTerm = point.getY() + nextPoint.getY();
            float secondTerm = point.getX() * nextPoint.getY()
                    - nextPoint.getX() * point.getY();
            x += xFirstTerm * secondTerm;
            y += yFirstTerm * secondTerm;
        }
        final int formulaConstant = 6;
        result[0] = Math.round((x / formulaConstant) / area);
        result[1] = Math.round((y / formulaConstant) / area);
        return result;
    }
}
