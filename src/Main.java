import inputoutput.Reader;
import commands.CommandFactory;
import commands.DrawVisitor;
import commands.ImageCommand;
import interfaces.Shape;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public final class Main {

    private Main() { }

    public static void main(final String[] args) {

        //Reader reader = new Reader("/home/brate/Desktop/Tema2POOCD/Tema2POOCD/input/test30.in");
        Reader reader = new Reader(args[0]);
        ImageCommand[]imageCommands = reader.getImageCommands();
        System.out.println(Arrays.toString(imageCommands));
        System.out.println(imageCommands.length);

        DrawVisitor visitor = new DrawVisitor(imageCommands[0]);

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
