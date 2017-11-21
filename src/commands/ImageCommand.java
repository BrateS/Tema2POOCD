package commands;

import java.awt.Color;
import java.util.Arrays;

public final class ImageCommand {
    private String command;
    private int[]numaricArgs;
    private Color[]colorArgs;

    public static ImageCommand getLineCommand(
            final int x1,
            final int y1,
            final int x2,
            final int y2,
            final Color color) {
        ImageCommand imageCommand = new ImageCommand();
        imageCommand.numaricArgs = new int[4];
        imageCommand.colorArgs = new Color[1];
        imageCommand.numaricArgs[0] = x1;
        imageCommand.numaricArgs[1] = y1;
        imageCommand.numaricArgs[2] = x2;
        imageCommand.numaricArgs[3] = y2;
        imageCommand.colorArgs[0] = color;
        return imageCommand;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public int[] getNumaricArgs() {
        return numaricArgs;
    }
    public Color[] getColorArgs() {
        return colorArgs;
    }

    public void addColor(final Color newColor) {
        if (colorArgs == null) {
            colorArgs = new Color[1];
            colorArgs[0] = newColor;
        } else {
            Color[]aux = new Color[colorArgs.length];
            System.arraycopy(colorArgs, 0, aux, 0, colorArgs.length);
            colorArgs = new Color[colorArgs.length + 1];
            System.arraycopy(aux, 0, colorArgs, 0, colorArgs.length - 1);
            colorArgs[colorArgs.length - 1] = newColor;
        }
    }

    @Override
    public String toString() {
        return "ImageCommand{" + "command='" + command + '\''
                + ", numaricArgs=" + Arrays.toString(numaricArgs)
                + ", colorArgs=" + Arrays.toString(colorArgs)
                + '}' + "\n";
    }

    public void addNumericArgs(final int newNumericArg) {
        if (numaricArgs == null) {
            numaricArgs = new int[1];
            numaricArgs[0] = newNumericArg;
        } else {
            int[]aux = new int[numaricArgs.length];
            System.arraycopy(numaricArgs, 0, aux, 0, numaricArgs.length);
            numaricArgs = new int[numaricArgs.length + 1];
            System.arraycopy(aux, 0, numaricArgs, 0, numaricArgs.length - 1);
            numaricArgs[numaricArgs.length - 1] = newNumericArg;
        }
    }
}
