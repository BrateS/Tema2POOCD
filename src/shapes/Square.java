package shapes;

import commands.ImageCommand;
import interfaces.Shape;
import interfaces.Visitable;
import interfaces.Visitor;

public final class Square extends Shape implements Visitable {

    public Square(final ImageCommand imageCommand) {
        super(imageCommand);
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
