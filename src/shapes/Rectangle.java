package shapes;


import commands.ImageCommand;
import interfaces.Shape;
import interfaces.Visitor;

public final class Rectangle extends Shape {

    public Rectangle(final ImageCommand imageCommand) {
        super(imageCommand);
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
