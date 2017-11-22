package commands;

import java.awt.Color;
import java.util.Arrays;

public final class ImageCommand {
    private String command;
    private int[] numericArgs;
    private Color[]colorArgs;

    /**
     * Creates an imageCommand formatted for a line drawing
     * @param x1 xStart
     * @param y1 yStart
     * @param x2 xFinal
     * @param y2 yFinal
     * @param color colorToPaint
     * @return formatted imageCommand
     */
    static ImageCommand getLineCommand(
            final int x1,
            final int y1,
            final int x2,
            final int y2,
            final Color color) {
        ImageCommand imageCommand = new ImageCommand();
        imageCommand.numericArgs = new int[4];
        imageCommand.colorArgs = new Color[1];
        imageCommand.numericArgs[0] = x1;
        imageCommand.numericArgs[1] = y1;
        imageCommand.numericArgs[2] = x2;
        imageCommand.numericArgs[3] = y2;
        imageCommand.colorArgs[0] = color;
        return imageCommand;
    }

    String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public int[] getNumericArgs() {
        return numericArgs;
    }
    Color[] getColorArgs() {
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

    public void addNumericArgs(final int newNumericArg) {
        if (numericArgs == null) {
            numericArgs = new int[1];
            numericArgs[0] = newNumericArg;
        } else {
            int[]aux = new int[numericArgs.length];
            System.arraycopy(numericArgs, 0, aux, 0, numericArgs.length);
            numericArgs = new int[numericArgs.length + 1];
            System.arraycopy(aux, 0, numericArgs, 0, numericArgs.length - 1);
            numericArgs[numericArgs.length - 1] = newNumericArg;
        }
    }
}
