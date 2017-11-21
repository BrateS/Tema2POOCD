package interfaces;

import commands.ImageCommand;

public abstract class Shape implements Visitable {

    private ImageCommand imageCommand = null;

    public Shape(final ImageCommand imageCommand) {
        this.imageCommand = imageCommand;
    }

    /**
     *
     * @return imageCommand
     */
    public ImageCommand getImageCommand() {
        return imageCommand;
    }
}
