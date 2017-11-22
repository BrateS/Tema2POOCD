package inputoutput;

import commands.ImageCommand;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public final class Reader {
    private BufferedReader bf;

    public Reader(final String filename) {
        try {
            bf = new BufferedReader(new FileReader(new File(filename)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Reads from the file line by line
     * and parses numericArgs and colorArgs.
     * @return an array of parsed commands
     */
    public ImageCommand[] getImageCommands() {
        final int hexBase = 16;
        ImageCommand[]imageCommands = null;
        try {
            //gets first line
            String nrOps = bf.readLine();
            int nrOperations = Integer.parseInt(nrOps);
            imageCommands = new ImageCommand[nrOperations];
            int aux = nrOperations;
            //runs nrOperations times
            while (aux > 0) {
                String operation = bf.readLine();
                String[] args = operation.split(" ");
                ImageCommand command = new ImageCommand();
                //sets the name of the command
                command.setCommand(args[0]);
                for (int i = 1; i < args.length; i++) {
                    //if the following arg is a color
                    if (args[i].startsWith("#")) {
                        int red = Integer.parseInt(""
                                + args[i].charAt(1)
                                + args[i].charAt(2),
                                hexBase);
                        int blue = Integer.parseInt(""
                                + args[i].charAt(3)
                                + args[i].charAt(4),
                                hexBase);
                        int gr = Integer.parseInt(""
                                + args[i].charAt(5)
                                + args[i].charAt(6),
                                hexBase);
                        int alpha = Integer.parseInt(args[i + 1]);
                        Color color = new Color(red, blue, gr, alpha);
                        command.addColor(color);
                        //skips one arg because color is one 2 args
                        i++;
                        //if the following args is a number
                    } else {
                        command.addNumericArgs(Integer.parseInt(args[i]));
                    }
                }
                // puts the created command in the array
                imageCommands[nrOperations - aux] = command;
                aux--;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageCommands;
    }
}
