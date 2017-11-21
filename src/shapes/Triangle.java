package shapes;

import commands.ImageCommand;
import interfaces.Shape;
import interfaces.Visitable;
import interfaces.Visitor;

public final class Triangle extends Shape implements Visitable {
    public Triangle(final ImageCommand imageCommand) {
        super(imageCommand);
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
