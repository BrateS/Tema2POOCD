import inputoutput.Reader;
import commands.CommandFactory;
import commands.DrawVisitor;
import commands.ImageCommand;
import interfaces.Shape;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public final class Main {

    private Main() { }

    public static void main(final String[] args) {

        Reader reader = new Reader(args[0]);
        ImageCommand[]imageCommands = reader.getImageCommands();

        //Creates visitor and Canvas
        DrawVisitor visitor = new DrawVisitor(imageCommands[0]);

        // index from 1 to avoid first ( "CANVAS" ) command
        int indexCommand = 1;
        while (imageCommands.length > indexCommand) {
            ImageCommand imageCommand = imageCommands[indexCommand];
            Shape shape = CommandFactory.getCommand(imageCommand);
            shape.accept(visitor);
            indexCommand++;
        }

        File outfile = new File("drawing.png");
        try {
            ImageIO.write(visitor.getImage(), "png", outfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
