package commands;

import interfaces.Shape;
import shapes.*;

public final class CommandFactory {
    private CommandFactory() { }

    public static Shape getCommand(final ImageCommand imageCommand) {
        String commandType = imageCommand.getCommand();
        if (commandType.equals("LINE")) {
            return new Line(imageCommand);
        }
        if (commandType.equals("SQUARE")) {
            return new Square(imageCommand);
        }
        if (commandType.equals("RECTANGLE")) {
            return new Rectangle(imageCommand);
        }
        if (commandType.equals("CIRCLE")) {
            return new Circle(imageCommand);
        }
        if (commandType.equals("TRIANGLE")) {
            return new Triangle(imageCommand);
        }
        if (commandType.equals("POLYGON")) {
            return new Polygon(imageCommand);
        }
        if (commandType.equals("DIAMOND")) {
            return new Diamond(imageCommand);
        }

        throw new IllegalArgumentException(
                "The Shape type "
                        + commandType
                        + " is not recognized.");
    }
}
