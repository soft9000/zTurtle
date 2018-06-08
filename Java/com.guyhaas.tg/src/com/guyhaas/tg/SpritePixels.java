package com.guyhaas.tg;

import java.awt.Color;

/**
 * SpritePixels is an abstract class that another class extends when it wants to
 * provide the pixels that make up the image of a Sprite.
 * <P>
 * @author Guy Haas
 */
public class SpritePixels {

    //
    // Symbolic Constants
    // -------- ---------
    /**
     * Class name as a String.
     */
    public static final String CLASS_NAME = "SpritePixels";

    /**
     * Default maximum sprite image height.
     */
    public static final int MAX_SPRITE_HEIGHT = 600;

    /**
     * Default maximum sprite image width.
     */
    public static final int MAX_SPRITE_WIDTH = 400;

    /**
     * Default minimum sprite image height.
     */
    public static final int MIN_SPRITE_HEIGHT = 6;

    /**
     * Default minimum sprite image width.
     */
    public static final int MIN_SPRITE_WIDTH = 6;

    /**
     * Value of a pixel which is solid black.
     */
    public static final int BLACK_OPAQUE_PIXEL = 0xff000000;


    /*
    * Value of a pixel which is solid white.
     */
    private static final int WHITE_OPAQUE_PIXEL = 0xffffffff;


    /*
    * Mask for isolating the bits specifying a pixel's opacity.
     */
    private static final int PIXEL_OPACITY_BITS = 0xff000000;


    /*
    * Mask for isolating the RGB color bits of a pixel.
     */
    private static final int PIXEL_COLOR_BITS = 0x00ffffff;


    /*
    * Value of RGB bits of a white pixel.
     */
    private static final int WHITE_RGB_BITS = WHITE_OPAQUE_PIXEL & PIXEL_COLOR_BITS;


    /*
    * Number of radians in half of a circle (180 degrees).
    * This is the west heading in radians.
     */
    private static final double HALF_CIRCLE_RADIANS = Math.PI;

    /*
    * Number of radians in a quarter of a circle (90 degrees).
    * This is the north heading in radians.
     */
    private static final double QTR_CIRCLE_RADIANS = Math.PI / 2.0;

    /*
    * Number of radians in a three quarters of a circle (270 degrees).
    * This is the south heading in radians.
     */
    private static final double THREEQTR_CIRCLE_RADIANS = HALF_CIRCLE_RADIANS + QTR_CIRCLE_RADIANS;


    /*
    * With the ability of loading an image file as a shape for
    * the turtle to take on, there comes a need for alternative
    * styles of rotation.
     */
    /**
     * Symbolic constant for standard 360 degree rotation style of turtle's
     * shape, its Sprite.
     */
    public static final int ROTATE_ON = 0;

    /**
     * Symbolic constant for no rotation of the turtle's shape.
     */
    public static final int ROTATE_OFF = 1;

    /**
     * Symbolic constant for rotation style where initial image faces left and
     * remains facing left for headings 181-359 degrees, but flips to facing
     * right for headings 0-180 degrees.
     */
    public static final int ROTATE_LEFT_RIGHT = 2;

    /**
     * Symbolic constant for rotation style where initial image faces right and
     * remains facing right for headings 0-180 degrees, but flips to facing left
     * for headings 181-359.
     */
    public static final int ROTATE_RIGHT_LEFT = 3;

    //
    // Private Fields
    // ------- ------

    /*
    * Used to determine which pixels in baseSpritePixels[] change
    * when the pen color changes.
     */
    private boolean[] colorFillMask;

    /*
    * This sprite's inner pixels Color. If this Sprite is incapable
    * of changing color (loaded sprite images), its value is null.
     */
    private Color spriteColor;

    /*
    * Underlying orientation of the turtle this Sprite represents.
    * The value is in radians in conventional/AWT manner, i.e.,
    * positive X axis is 0.
     */
    private double turtleHeading;

    /*
    * With the ability of loading an image file as a shape for
    * the turtle to take on, there's a need for multiple styles of
    * rotation of the image when turtleHeading changes, including
    * no rotation.
     */
    private int rotationStyle;

    /*
    * The width/height of all arrays that hold representations of
    * this Sprite.  This needs to be computed such that it is larger
    * than either the sprite's height or the sprite's width so the
    * corners of the image of the sprite are not clipped when drawn
    * at 45 degree angles.
     */
    private int spriteSideSize;

    /*
    * The height of the sprite as computed in the constructor
    * with the image in its base orientation.
     */
    private int spriteHeight;

    /*
    * The width of the sprite as computed in the constructor
    * with the image in its base orientation.
     */
    private int spriteWidth;

    /*
    * Root pixel array of a Sprite's image which is used to
    * construct spritePixels[], the current shape a turtle has.
    * The orientation of the image is depends upon the chosen
    * rotationStyle. As examples, for rotationStyles ROTATE_ON
    * and ROTATE_RIGHT_LEFT the pixels need to be in an eastern
    * orientation, i.e., headed along the positive X axis.
     */
    private int[] baseSpritePixels;

    /*
    * Pixel array for a Sprite's current Image - if it's visible.
    * Its orientation depends upon the current turtleHeading and
    * the current rotationStyle.
     */
    private int[] spritePixels;

    //
    // Constructors
    // ------------
    /**
     * Instantiate the pixels that make up a Sprite's Image given an array of
     * pixels. The assumed orientation of the PixelRectangle is upright, facing
     * the way you want for the turtle heading of 0, or north.
     */
    public SpritePixels(PixelRectangle pixRect) {
        this(pixRect, ROTATE_ON);
    }

    /**
     * Instantiate the pixels that make up a Sprite's Image given an array of
     * pixels. The assumed orientation of the PixelRectangle is upright, facing
     * the way you want for the turtle heading of 0, or north.
     *
     * Images are different from the built-in turtle shapes in the respect that
     * they may consist of an object that appears to have direction, e.g., the
     * Scratch cat facing right. A turtle that has such an assumed direction
     * must be flipped when its heading changes. Users do not expect a right
     * facing cat to be upside-down when its heading changes such that it's
     * facing west.
     */
    public SpritePixels(PixelRectangle pixRect, int rotationStyle) {
        int height = pixRect.pixels.length / pixRect.width;
        if (height > MAX_SPRITE_HEIGHT) {
            height = MAX_SPRITE_HEIGHT;
        }
        spriteHeight = height;
        int width = pixRect.width;
        if (width > MAX_SPRITE_WIDTH) {
            width = MAX_SPRITE_WIDTH;
        }
        spriteWidth = width;
        spriteSideSize = (int) Math.ceil(Math.sqrt((height * height) + (width * width)));
        if (spriteSideSize % 2 != 0) {
            spriteSideSize++;
        }
        baseSpritePixels = new int[spriteSideSize * spriteSideSize];
        for (int idx = 0; idx < baseSpritePixels.length; idx++) {
            baseSpritePixels[idx] = WHITE_OPAQUE_PIXEL;
        }
        this.rotationStyle = rotationStyle;
        switch (rotationStyle) {
            case ROTATE_ON:
                rotateBasePixelsRight90(pixRect);
                turtleHeading = QTR_CIRCLE_RADIANS;
                break;
            case ROTATE_OFF:
                centerInBasePixels(pixRect);
                turtleHeading = 0.0;
                break;
            case ROTATE_RIGHT_LEFT:
                centerInBasePixels(pixRect);
                turtleHeading = 0.0;
                break;
            case ROTATE_LEFT_RIGHT:
                centerInBasePixels(pixRect);
                turtleHeading = HALF_CIRCLE_RADIANS;
        }
        setTransparentPixels();
        updateSpritePixels();
    }

    /**
     * Instantiate a Sprite's Image given its dimensions, color, and an
     * implementation of the initSpritePixels() method. It is assumed that the
     * produced pixels are for the heading 0.0 radians, so a desired heading (in
     * radians) is also provided as a parameter.
     */
    public SpritePixels(int width, int height, Color color, double heading) {
        if (height > MAX_SPRITE_HEIGHT) {
            height = MAX_SPRITE_HEIGHT;
        }
        if (height < getMinimumHeight()) {
            height = getMinimumHeight();
        }
        if (width > MAX_SPRITE_WIDTH) {
            width = MAX_SPRITE_WIDTH;
        }
        if (width < getMinimumWidth()) {
            width = getMinimumWidth();
        }
        spriteHeight = height;
        spriteWidth = width;
        spriteSideSize = (int) Math.ceil(Math.sqrt((height * height) + (width * width)));
        if (spriteSideSize % 2 != 0) {
            spriteSideSize++;
        }
        baseSpritePixels = new int[spriteSideSize * spriteSideSize];
        for (int pixIdx = 0; pixIdx < baseSpritePixels.length; pixIdx++) {
            baseSpritePixels[pixIdx] = WHITE_OPAQUE_PIXEL;
        }
        initSpritePixels(spriteSideSize);
        setTransparentPixels();
        buildSpriteFillMask();
        spriteColor = color;
        int pixel = spriteColor.getRGB();
        pixel |= PIXEL_OPACITY_BITS;
        for (int pixIdx = 0; pixIdx < baseSpritePixels.length; pixIdx++) {
            if (colorFillMask[pixIdx] == true) {
                baseSpritePixels[pixIdx] = pixel;
            }
        }
        rotationStyle = ROTATE_ON;
        turtleHeading = heading;
        updateSpritePixels();
    }

    //
    // Class Support Methods
    // ----- ------- -------
    /* Build a mask of pixels that are to be changed when
    * the foreground color is changed.  Starting with
    * the center pixel of the turtle image, a flood-fill
    * algorithm is used to identify all pixels with the
    * same color value.  These pixels are set to true in
    * a mask array. colorSprite() uses this mask to change
    * the color of the turtle.
    *
    * Although baseSpritePixels is manipulated as an array
    * with a single index, it is really a two dimensional
    * array, a series of rows of pixels.  Each row is
    * spriteSideSize pixels in width.  This explains why
    * pixelIndex = rowNumber * spriteSideSize + columnNumber
    * is used to calculate a pixel's index.
     */
    private void buildSpriteFillMask() {
        float center = ((float) spriteSideSize) / 2.0F;
        int row = Math.round(center) - 1;
        int column = row;
        int color = baseSpritePixels[row * spriteSideSize + column];
        colorFillMask = new boolean[spriteSideSize * spriteSideSize];
        fillColor(row, column, color);
    }


    /*
    * Initialize baseSpritePixels[] by centering the specified
    * pixel rectangle.
     */
    private void centerInBasePixels(PixelRectangle pixRect) {

        int leftInset = (spriteSideSize - pixRect.width) / 2;
        int pixRectHeight = pixRect.pixels.length / pixRect.width;
        int topInset = (spriteSideSize - pixRectHeight) / 2;
        int iniDestIdx = (topInset * spriteSideSize) + leftInset;
        int destIdx = -1;
        int rowNum = 0;
        for (int srcIdx = 0; srcIdx < pixRect.pixels.length; srcIdx++) {
            if ((srcIdx % pixRect.width) == 0) {
                destIdx = iniDestIdx + (spriteSideSize * rowNum++);
            }
            baseSpritePixels[destIdx++] = pixRect.pixels[srcIdx];
        }
    }


    /* Fill eight pixels on the circle with a supplied RGB value
    *
    * Circles are eight-way symmetric.  Only pixels for a 45 degree
    * segment (one eighth of a circle) need to be computed - other
    * pixels are reflections of these pixels.
    *
    * @param ctrX center-point X value
    * @param ctrY center-point Y value
    * @param dX delta X value to a point on the circle
    * @param dY delta Y value to a point on the circle
    * @param value an ARGB color (alpha, red, green, blue)
     */
    private void circlePixels(int ctrX, int ctrY, int dX, int dY, int value) {
        setPixel(ctrX + dX, ctrY + dY, value);
        setPixel(ctrX + dY, ctrY + dX, value);
        setPixel(ctrX + dY, ctrY - dX, value);
        setPixel(ctrX + dX, ctrY - dY, value);
        setPixel(ctrX - dX, ctrY - dY, value);
        setPixel(ctrX - dY, ctrY - dX, value);
        setPixel(ctrX - dY, ctrY + dX, value);
        setPixel(ctrX - dX, ctrY + dY, value);
    }


    /*
    * Fill the internal pixels of baseSpritePixels[] with a specified
    * color. The determining factor whether or not a pixel is effected
    * is made by examining corresponding pixels in the colorFillMask[].
    *
    * A pure recursive implementation blows Java's stack...
     */
    private void fillColor(int row, int column, int color) {
        int pixIdx;
        int leftLimit = column, rightLimit = column;
        for (int i = column; i >= 0; i--) {
            pixIdx = row * spriteSideSize + i;
            if (baseSpritePixels[pixIdx] != color || colorFillMask[pixIdx]) {
                break;
            }
            colorFillMask[pixIdx] = true;
            leftLimit = i;
        }
        for (int i = column + 1; i < spriteSideSize; i++) {
            pixIdx = row * spriteSideSize + i;
            if (baseSpritePixels[pixIdx] != color || colorFillMask[pixIdx]) {
                break;
            }
            colorFillMask[pixIdx] = true;
            rightLimit = i;
        }
        if (row > 0) {
            for (int i = leftLimit; i <= rightLimit; i++) {
                int newRow = row - 1;
                pixIdx = newRow * spriteSideSize + i;
                if (baseSpritePixels[pixIdx] == color && !colorFillMask[pixIdx]) {
                    fillColor(newRow, i, color);
                }
            }
        }
        if (row < spriteSideSize - 1) {
            for (int i = leftLimit; i <= rightLimit; i++) {
                int newRow = row + 1;
                pixIdx = newRow * spriteSideSize + i;
                if (baseSpritePixels[pixIdx] == color && !colorFillMask[pixIdx]) {
                    fillColor(newRow, i, color);
                }
            }
        }
    }

    // NOTE: x1 MUST BE <= x2
    //
    private void fillHorizLine(int x1, int x2, int y, int value) {
        for (int x = x1; x <= x2; x++) {
            setPixel(x, y, value);
        }
    }

    // NOTE: y1 MUST BE <= y2
    //
    private void fillVertLine(int x, int y1, int y2, int value) {
        for (int y = y1; y <= y2; y++) {
            setPixel(x, y, value);
        }
    }


    /* Fill in pixels that make up a line where X is increasing in
    * integer units while Y values are changing by a specified delta.
    * NOTE: x0 MUST BE <= x1
     */
    private void fillXUnitLine(int x0, int y0, int x1, int y1, int value) {
        int dX = x1 - x0;
        boolean negSlope = false;
        int dY = y1 - y0;
        int y = y0;
        if (dY < 0) {
            negSlope = true;
            dY = -dY;
        }
        int d = 2 * dY - dX;
        int incrE = 2 * dY;
        int incrNE = 2 * (dY - dX);
        setPixel(x0, y0, value);
        while (x0 < x1) {
            x0++;
            if (d <= 0) {
                d += incrE;
            } else {
                d += incrNE;
                y++;
            }
            if (negSlope) {
                setPixel(x0, y0 - (y - y0), value);
            } else {
                setPixel(x0, y, value);
            }
        }

    } // end fillXUnitLine()


    /* Fill in pixels that make up a line where Y is increasing in
    * integer units while X values are changing by a specified delta.
    * NOTE: y1 MUST BE <= y2
     */
    private void fillYUnitLine(int x0, int y0, int x1, int y1, int value) {
        boolean negSlope = false;
        int dX = x1 - x0;
        int x = x0;
        if (dX < 0) {
            negSlope = true;
            dX = -dX;
        }
        int dY = y1 - y0;
        int d = 2 * dX - dY;
        int incrE = 2 * dX;
        int incrNE = 2 * (dX - dY);
        setPixel(x0, y0, value);
        while (y0 < y1) {
            y0++;
            if (d <= 0) {
                d += incrE;
            } else {
                d += incrNE;
                x++;
            }
            if (negSlope) {
                setPixel(x0 - (x - x0), y0, value);
            } else {
                setPixel(x, y0, value);
            }
        }
    }

    /*
    * Return the number (index) of the first column in spritePixels[]
    * that is not transparent.
     */
    private int findFirstNonTransparentColumn() {
        if (spritePixels == null) {
            return 0;
        }
        for (int colIdx = 0; colIdx < spriteSideSize; colIdx++) {
            for (int rowIdx = 0; rowIdx < spriteSideSize; rowIdx++) {
                int pixelOpacity = spritePixels[(rowIdx * spriteSideSize) + colIdx] & PIXEL_OPACITY_BITS;
                if (pixelOpacity != 0) {
                    return colIdx;
                }
            }
        }
        return spriteSideSize;
    }

    /*
    * Return the number (index) of the first line in spritePixels[]
    * that is not transparent.
     */
    private int findFirstNonTransparentLine() {
        if (spritePixels == null) {
            return 0;
        }
        int pixelIndex = 0;
        while (pixelIndex < spriteSideSize * spriteSideSize) {
            int pixelOpacity = spritePixels[pixelIndex] & PIXEL_OPACITY_BITS;
            if (pixelOpacity != 0) {
                return pixelIndex / spriteSideSize;
            }
            pixelIndex++;
        }
        return spriteSideSize - 1;
    }

    /*
    * Return the number (index) of the last column in spritePixels[]
    * that is not transparent.
     */
    private int findLastNonTransparentColumn() {
        if (spritePixels == null) {
            return 0;
        }
        for (int colIdx = spriteSideSize - 1; colIdx >= 0; colIdx--) {
            for (int rowIdx = 0; rowIdx < spriteSideSize; rowIdx++) {
                int pixelOpacity = spritePixels[(rowIdx * spriteSideSize) + colIdx] & PIXEL_OPACITY_BITS;
                if (pixelOpacity != 0) {
                    return colIdx;
                }
            }
        }
        return spriteSideSize - 1;
    }


    /*
    * Return the number (index) of the last line in spritePixels[]
    * that is not transparent.
     */
    private int findLastNonTransparentLine() {
        if (spritePixels == null) {
            return 0;
        }
        int pixelIndex = (spriteSideSize * spriteSideSize) - 1;
        while (pixelIndex >= 0) {
            int pixelOpacity = spritePixels[pixelIndex] & PIXEL_OPACITY_BITS;
            if (pixelOpacity != 0) {
                return pixelIndex / spriteSideSize;
            }
            pixelIndex--;
        }
        return spriteSideSize - 1;
    }


    /*
    * Fill spritePixels[] by flipping the pixels in baseSpritePixels[],
    * perform a reflection.
     */
    private void flipSpritePixels() {
        int destIdx = -1;
        int rowNum = 1;
        for (int srcIdx = 0; srcIdx < baseSpritePixels.length; srcIdx++) {
            if ((srcIdx % spriteSideSize) == 0) {
                destIdx = (spriteSideSize * rowNum++) - 1;
            }
            if (srcIdx != destIdx) {
                spritePixels[destIdx--] = baseSpritePixels[srcIdx];
            }
        }

    }


    /*
    * Return true if provided heading is in any western degree. In Logo
    * turtle space degrees, this means 181 - 359 degrees.
     */
    private boolean leftSideHeading(double heading) {
        return heading > QTR_CIRCLE_RADIANS && heading < THREEQTR_CIRCLE_RADIANS;
    }

    //private void printPixels(String what, int[] pixels, int width, int height)
    //{
    //   for ( int y=0; y < height; y++ )
    //   {
    //      for ( int x=0; x < width; x++ )
    //      {
    //         int i = y * width + x;
    //         int a = (pixels[i] >> 24) & 0xff;
    //         int r = (pixels[i] >> 16) & 0xff;
    //         int g = (pixels[i] >> 8) & 0xff;
    //         int b = pixels[i] & 0xff;
    //         System.out.println(what+"["+i+"] a="+a+", r="+r+", g="+g+", b="+b);
    //      }
    //      System.out.println( "----------" );
    //   }
    //}
    /*
    * Return true if the provided heading is north, south, or any degree
    * towards east. In Logo TurtleSpace degrees, this means 0 - 180 degrees.
     */
    private boolean rightSideHeading(double heading) {
        return heading <= QTR_CIRCLE_RADIANS || heading >= THREEQTR_CIRCLE_RADIANS;
    }


    /*
    * Initialize baseSpritePixels[] by centering and rotating
    * the specified pixel rectangle 90 degrees.
     */
    private void rotateBasePixelsRight90(PixelRectangle pixRect) {

        int leftInset = (spriteSideSize - spriteHeight) / 2;
        int topInset = (spriteSideSize - spriteWidth) / 2;
        int iniDestIdx = (topInset * spriteSideSize) + leftInset + spriteHeight;
        int destIdx = 0;
        int rowNum = 0;
        for (int srcIdx = 0; srcIdx < pixRect.pixels.length; srcIdx++) {
            if (srcIdx % spriteWidth == 0) {
                destIdx = iniDestIdx - ++rowNum;
            }
            baseSpritePixels[destIdx] = pixRect.pixels[srcIdx];
            destIdx += spriteSideSize;
        }
    }


    /*
    * Helper procedure for setTransparentPixels. It walks the perimeter
    * of baseSpritePixels[] and upon finding an opaque white pixel, invokes
    * setTPHelper() to flood-fill this pixel and other opaque white neighbors.
     */
    private void setTPHelper(int row, int column) {
        int pixIdx = (row * spriteSideSize) + column;
        baseSpritePixels[pixIdx] = 0;
        int rightLimit = column;
        for (int idx = column + 1; idx < spriteSideSize; idx++) {
            pixIdx = (row * spriteSideSize) + idx;
            if ((baseSpritePixels[pixIdx] & PIXEL_COLOR_BITS) != WHITE_RGB_BITS) {
                break;
            }
            baseSpritePixels[pixIdx] = 0;
            rightLimit = idx;
        }
        int leftLimit = column;
        for (int idx = column - 1; idx >= 0; idx--) {
            pixIdx = (row * spriteSideSize) + idx;
            if ((baseSpritePixels[pixIdx] & PIXEL_COLOR_BITS) != WHITE_RGB_BITS) {
                break;
            }
            baseSpritePixels[pixIdx] = 0;
            leftLimit = idx;
        }
        if (row > 0) {
            for (int idx = leftLimit; idx <= rightLimit; idx++) {
                int newRow = row - 1;
                pixIdx = (newRow * spriteSideSize) + idx;
                if ((baseSpritePixels[pixIdx] & PIXEL_COLOR_BITS) == WHITE_RGB_BITS) {
                    setTPHelper(newRow, idx);
                }
            }
        }
        if (row < spriteSideSize - 1) {
            for (int idx = leftLimit; idx <= rightLimit; idx++) {
                int newRow = row + 1;
                pixIdx = (newRow * spriteSideSize) + idx;
                if ((baseSpritePixels[pixIdx] & PIXEL_COLOR_BITS) == WHITE_RGB_BITS) {
                    setTPHelper(newRow, idx);
                }
            }
        }
    }


    /*
    * Set white pixels outside the perimeter of the Sprite's image to be transparent.
     */
    private void setTransparentPixels() {
        for (int row = 0; row < spriteSideSize; row++) {
            int idx = row * spriteSideSize;
            if ((baseSpritePixels[idx] & PIXEL_COLOR_BITS) == WHITE_RGB_BITS) {
                setTPHelper(row, 0);
            }
        }
        for (int row = 0; row < spriteSideSize; row++) {
            int idx = (row * spriteSideSize) + spriteSideSize - 1;
            if ((baseSpritePixels[idx] & PIXEL_COLOR_BITS) == WHITE_RGB_BITS) {
                setTPHelper(row, spriteSideSize - 1);
            }
        }
        for (int col = 0; col < spriteSideSize; col++) {
            if ((baseSpritePixels[col] & PIXEL_COLOR_BITS) == WHITE_RGB_BITS) {
                setTPHelper(0, col);
            }
        }
        for (int col = 0; col < spriteSideSize; col++) {
            int idx = ((spriteSideSize - 1) * spriteSideSize) + col;
            if ((baseSpritePixels[idx] & PIXEL_COLOR_BITS) == WHITE_RGB_BITS) {
                setTPHelper(spriteSideSize - 1, col);
            }
        }
    }

    private float slope(int x1, int y1, int x2, int y2) {
        return ((float) y2 - y1) / ((float) x2 - x1);
    }


    /*
    * Fill spritePixels[] to reflect current turtleHeading (in radians)
    * which is the amount of rotation needed since baseSpritePixels[] is
    * aligned to 0.0.  I use a 'Reverse-Rotation' algorithm, computing
    * which pixel in the original image maps to every pixel in the new
    * image (vs projecting forward) to avoid holes due to rounding errors.
    * Since I'm rotating around the center of the image (not its origin),
    * three steps are needed:
    * (1) translate origin to center,
    * (2) reverse rotatation of pixel x,y, and
    * (3) translate back to initial origin.
     */
    private void updateSpritePixels() {
        if (spritePixels == null) {
            spritePixels = new int[spriteSideSize * spriteSideSize];
            for (int i = 0; i < spriteSideSize * spriteSideSize; i++) {
                spritePixels[i] = baseSpritePixels[i];
            }
        }
        switch (rotationStyle) {
            case ROTATE_OFF:
                break;
            case ROTATE_LEFT_RIGHT:
                if (rightSideHeading(turtleHeading)) {
                    flipSpritePixels();
                } else {
                    System.arraycopy(baseSpritePixels, 0, spritePixels, 0, spritePixels.length);
                }
                break;
            case ROTATE_RIGHT_LEFT:
                if (leftSideHeading(turtleHeading)) {
                    flipSpritePixels();
                } else {
                    System.arraycopy(baseSpritePixels, 0, spritePixels, 0, spritePixels.length);
                }
                break;
            case ROTATE_ON:
                int center = spriteSideSize / 2;
                double cosTheta = Math.cos(turtleHeading);
                double sinTheta = Math.sin(turtleHeading);
                // first zap destination, make all pixels transparent
                for (int y = 0; y < spriteSideSize; y++) {
                    for (int x = 0; x < spriteSideSize; x++) {
                        spritePixels[y * spriteSideSize + x] = 0;
                    }
                }
                for (int row = 0; row < spriteSideSize; row++) {
                    int rowIdx = row * spriteSideSize;
                    int rowPrime = 2 * (row - center) + 1;
                    for (int col = 0; col < spriteSideSize; col++) {
                        int colPrime = 2 * (col - center) + 1;
                        int srcX = (int) Math.rint(colPrime * cosTheta - rowPrime * sinTheta);
                        srcX = (srcX - 1) / 2 + center;
                        int srcY = (int) Math.rint(colPrime * sinTheta + rowPrime * cosTheta);
                        srcY = (srcY - 1) / 2 + center;
                        if (srcX < 0 || srcX >= spriteSideSize) {
                            continue;
                        }
                        if (srcY < 0 || srcY >= spriteSideSize) {
                            continue;
                        }
                        int pixelIndex = srcY * spriteSideSize + srcX;
                        int pixel = baseSpritePixels[pixelIndex];
                        spritePixels[rowIdx + col] = pixel;
                    }
                }
        }

    } //end updateSpritePixels()

    //
    // Methods for Subclasses
    // ------- --- ----------
    /**
     * Return the default minimum height of this Sprite's image. Subclasses
     * should override this method if they support a different minimum height.
     */
    protected int getMinimumHeight() {
        return MIN_SPRITE_HEIGHT;
    }

    /**
     * Return the default minimum width of this Sprite's image. Subclasses
     * should override this method if they support a different minimum width.
     *
     */
    protected int getMinimumWidth() {
        return MIN_SPRITE_WIDTH;
    }

    /**
     * Initializes the pixels that make up the turtle's image. Overridden by the
     * child subclass. A SpritePixels constructor invokes this method.
     */
    protected void initSpritePixels(int spriteSideSize) {
    }

    /**
     * Set the value of pixels along an approximation of the circumference of a
     * circle.
     * <p>
     * This is an implementation of the simplest form of the midpoint algorithm
     * as described in Computer Graphics, Foley and van Dam
     *
     * @param ctrX the X coordinate of center of the circle.
     * @param ctrY the Y coordinate of center of the circle.
     * @param radius the distance from the center to pixels painted.
     * @param rgbVal the RGB value of the pixels effected.
     */
    protected void setCirclePixels(int ctrX, int ctrY, int radius, int rgbVal) {
        int deltaX = 0;
        int deltaY = radius;
        float midptVal = 5.0F / 4.0F - (float) radius;
        circlePixels(ctrX, ctrY, deltaX, deltaY, rgbVal);
        while (deltaY > deltaX) {
            if (midptVal < 0) {
                midptVal += 2.0F * (float) deltaX + 3.0F;
                deltaX++;
            } else {
                midptVal += 2.0F * (float) (deltaX - deltaY) + 5.0F;
                deltaX++;
                deltaY--;
            }
            circlePixels(ctrX, ctrY, deltaX, deltaY, rgbVal);
        }
    }

    /**
     * Set the value of pixels along an approximation of a line.
     *
     * @param x0 the X coordinate of one end of the line.
     * @param y0 the Y coordinate of one end of the line.
     * @param x1 the X coordinate of the other end of the line.
     * @param y1 the Y coordinate of the other end of the line.
     * @param rgbVal the RGB value of the pixels effected.
     */
    protected void setLinePixels(int x0, int y0, int x1, int y1, int rgbVal) {
        if (x0 == x1) {
            if (y0 <= y1) {
                fillVertLine(x0, y0, y1, rgbVal);
            } else {
                fillVertLine(x0, y1, y0, rgbVal);
            }
        } else if (y0 == y1) {
            if (x0 <= x1) {
                fillHorizLine(x0, x1, y0, rgbVal);
            } else {
                fillHorizLine(x1, x0, y0, rgbVal);
            }
        } else if (Math.abs(slope(x0, y0, x1, y1)) > 1.0F) {
            if (y0 < y1) {
                fillYUnitLine(x0, y0, x1, y1, rgbVal);
            } else {
                fillYUnitLine(x1, y1, x0, y0, rgbVal);
            }
        } else if (x0 < x1) {
            fillXUnitLine(x0, y0, x1, y1, rgbVal);
        } else {
            fillXUnitLine(x1, y1, x0, y0, rgbVal);
        }
    }

    /**
     * Fill the specified (x,y) pixel in the Sprite's image with an RGB value.
     * Used in child's initSpritePixels() to paint single pixels in the turtle's
     * image.
     * <p>
     * The origin for the coordinate system used is the top-left corner with X
     * values increasing left to right and Y values increasing top to bottom.
     *
     * @param x the X coordinate of the pixel.
     * @param y the Y coordinate of the pixel.
     * @param rgbVal the new RGB value of the pixel.
     */
    protected void setPixel(int x, int y, int rgbVal) {
        String me = "SpritePixels.setPixel: ";
        String outOfBounds = " out of bounds";
        if (x < 0 || x >= spriteSideSize) {
            System.err.println(me + "x=" + x + outOfBounds);
        } else if (y < 0 || y >= spriteSideSize) {
            System.err.println(me + "y=" + y + outOfBounds);
        } else {
            baseSpritePixels[x + (y * spriteSideSize)] = rgbVal;
        }
    }

    //
    // Public Methods
    // ------ -------
    /**
     * Return a copy of this Sprite's pixels.
     */
    public int[] getPixels() {
        return spritePixels;
    }

    /**
     * Return the length of a side of this Sprite's square array of pixels.
     */
    public int getSideSize() {
        return spriteSideSize;
    }

    /**
     * Return the current height of this Sprite's image. This includes taking
     * into account the current heading.
     */
    public int getHeight() {
        if (spritePixels == null) {
            return spriteHeight;
        }
        return (findLastNonTransparentLine() - findFirstNonTransparentLine()) + 1;
    }

    /**
     * Return the current width of this Sprite's image. This includes taking
     * into account the current heading.
     */
    public int getWidth() {
        if (spritePixels == null) {
            return spriteWidth;
        }
        return (findLastNonTransparentColumn() - findFirstNonTransparentColumn()) + 1;
    }

    /**
     * Fill inner pixels of the turtle with the specified color.
     */
    public boolean setSpriteColor(Color newColor) {
        if ((spriteColor != null) && (!newColor.equals(spriteColor))) {
            if (!(this instanceof UserTurtle)) {
                int pixel = PIXEL_OPACITY_BITS | newColor.getRGB();
                for (int pixIdx = 0; pixIdx < baseSpritePixels.length; pixIdx++) {
                    if (colorFillMask[pixIdx] == true) {
                        baseSpritePixels[pixIdx] = pixel;
                    }
                }
                updateSpritePixels();
            }
            spriteColor = newColor;
            return true;
        }
        return false;
    }

    /**
     * Rotate the turtle to a specified heading (radians). Return true if the
     * Sprite's image changed, false if not.
     */
    public boolean setSpriteHeading(double newHeading) {
        if (Math.abs(turtleHeading - newHeading) < 0.001) {
            return false;
        }
        switch (rotationStyle) {
            case ROTATE_OFF:
                turtleHeading = newHeading;
                break;
            case ROTATE_ON:
                turtleHeading = newHeading;
                updateSpritePixels();
                break;
            case ROTATE_LEFT_RIGHT:
            case ROTATE_RIGHT_LEFT:
                boolean curSideRight = rightSideHeading(turtleHeading);
                boolean newSideRight = rightSideHeading(newHeading);
                turtleHeading = newHeading;
                if (curSideRight != newSideRight) {
                    updateSpritePixels();
                }
                break;
        }
        return true;
    }

} // end class SpritePixels
