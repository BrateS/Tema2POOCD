package interfaces;

import shapes.Circle;
import shapes.Diamond;
import shapes.Line;
import shapes.Square;
import shapes.Rectangle;
import shapes.Polygon;
import shapes.Triangle;

public interface Visitor {
    void visit(Circle circle);
    void visit(Diamond diamond);
    void visit(Line line);
    void visit(Rectangle rectangle);
    void visit(Square square);
    void visit(Polygon polygon);
    void visit(Triangle triangle);
}
