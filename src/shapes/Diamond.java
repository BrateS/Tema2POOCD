package shapes;

import commands.ImageCommand;
import interfaces.Shape;
import interfaces.Visitable;
import interfaces.Visitor;

public final class Diamond extends Shape implements Visitable {

    public Diamond(final ImageCommand imageCommand) {
        super(imageCommand);
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
