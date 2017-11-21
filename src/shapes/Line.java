package shapes;


import commands.ImageCommand;
import interfaces.Shape;
import interfaces.Visitable;
import interfaces.Visitor;

public final class Line extends Shape implements Visitable {

    public Line(final ImageCommand imageCommand) {
        super(imageCommand);
    }

    public void draw(final Canvas image, final ImageCommand imageCommand) {

    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
