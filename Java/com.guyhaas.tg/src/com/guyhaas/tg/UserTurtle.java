package com.guyhaas.tg;

/**
 * This class provides the turtle's appearance, the array of pixels that make up
 * its image. In this case, the image is supplied by the current program, the
 * user.
 * <p>
 * @author Guy Haas
 */
public class UserTurtle extends SpritePixels {

    //
    // Constructor
    // -----------
    /**
     * Instantiate this Sprite's image based on the supplied PixelRectangle.
     *
     * It is assumed that the PixelRectangle is oriented north, towards the top
     * of the graphics canvas.
     */
    public UserTurtle(PixelRectangle pixRect) {
        super(pixRect);
    }

    /**
     * Instantiate this Sprite's image based on the supplied PixelRectangle and
     * the way the image is to be transformed when its heading changes.
     *
     * It is assumed that the PixelRectangle is oriented north, towards the top
     * of the graphics canvas.
     */
    public UserTurtle(PixelRectangle pixRect, int rotationStyle) {
        super(pixRect, rotationStyle);
    }

}
