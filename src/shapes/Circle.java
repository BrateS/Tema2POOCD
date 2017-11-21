package shapes;

import commands.ImageCommand;
import interfaces.Shape;
import interfaces.Visitor;


public final class Circle extends Shape {

    public Circle(final ImageCommand imageCommand) {
        super(imageCommand);
    }
    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
