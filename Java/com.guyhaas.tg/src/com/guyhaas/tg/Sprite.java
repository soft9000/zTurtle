package com.guyhaas.tg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.MemoryImageSource;

/**
 * Sprite provides support for a turtle's graphics state. This consists of stuff
 * like current heading, current location in TurtleSpace, the set of of pixels
 * that represent it, etc... Associated with this state are the methods to
 * access and manipulate it, e.g., support for FORWARD, RIGHT, POS, HEADING,
 * etc... Commands and Operators.
 * <p>
 * Sprite must extend Component so that it can use createImage() to supply
 * TGCanvas with its Image.
 * <p>
 * @author Guy Haas
 */
public class Sprite extends Component {

    // Symbolic Constants
    // -------- ---------
    /**
     * Class' name as String.
     */
    public static final String CLASS_NAME = "Sprite";

    /**
     * jLogo comes with a few basic shapes (images) that the turtle may take on.
     * Each is assigned a fixed shape number. The initial shape roughly looks
     * like a turtle (come-on... think artististic license). This default turtle
     * image is shape number 0.
     */
    public static final int TURTLE = 0;

    /**
     * jLogo comes with a few basic shapes (images) that the turtle may take on.
     * Each is assigned a fixed shape number. An image which resembles an arrow
     * is shape number 1.
     */
    public static final int ARROW = 1;

    /**
     * jLogo comes with a few basic shapes (images) that the turtle may take on.
     * Each is assigned a fixed shape number. An image which is a filled circle,
     * a ball, is shape number 2.
     */
    public static final int BALL = 2;

    /**
     * jLogo comes with a few basic shapes (images) that the turtle may take on.
     * Each is assigned a fixed shape number. An image which is a filled
     * rectangle, a box, is shape number 3.
     */
    public static final int BOX = 3;

    /**
     * jLogo comes with a few basic shapes (images) that the turtle may take on.
     * Each is assigned a fixed shape number. An image which resembles a filled
     * plus-sign, a cross, is shape number 4.
     */
    public static final int CROSS = 4;

    /**
     * jLogo comes with a few basic shapes (images) that the turtle may take on.
     * Each is assigned a fixed shape number. An image which is a filled
     * triangle is shape number 5.
     */
    public static final int TRIANGLE = 5;

    /**
     * jLogo comes with a few basic shapes (images) that the turtle may take on.
     * Each is assigned a fixed shape number. An image which is a filled diamond
     * is shape number 6.
     */
    public static final int DIAMOND = 6;

    /**
     * Maximum shape number that Sprite knows how to construct.
     */
    public static final int MAX_BUILTIN_SHAPENUM = 6;


    /*
    * Lines the turtle draws can have caps (decorations) on their ends.
    * The following symbolic constants are mapped to similarly named
    * counterparts from java.awt.BasicStroke.
     */
    /**
     * Symbolic constant for setLinecap() which selects a basic line with no
     * endcap.
     */
    public static final int LINECAP_BUTT = 0;

    /**
     * Symbolic constant for setLinecap() to which selects a rounded endcap.
     * This is the default.
     */
    public static final int LINECAP_ROUND = 1;

    /**
     * Symbolic constant for setLinecap() to which selects a squared-off endcap.
     */
    public static final int LINECAP_SQUARE = 2;

    /*
    * The turtle can draw Strings of characters onto the graphics
    * canvas in a few different fonts and styles. The setlabelfont()
    * method has an integer parameter that represents a font and
    * style combination.  Following are symbolic constants for the
    * available fonts and styles.
     */
    /**
     * Symbolic constant for setlabelfont() to select a fixed-width Courier
     * font.
     */
    public static final int COURIER = 0;

    /**
     * Symbolic constant for setlabelfont() to select an emboldened fixed-width
     * Courier font.
     */
    public static final int COURIER_BOLD = 1;

    /**
     * Symbolic constant for setlabelfont() to select an italicized fixed-width
     * Courier font.
     */
    public static final int COURIER_ITALIC = 2;

    /**
     * Symbolic constant for setlabelfont() to select an emboldened and
     * italicized fixed-width Courier font.
     */
    public static final int COURIER_BOLD_ITALIC = 3;

    /**
     * Symbolic constant for setlabelfont() to select the Sans Serif font.
     */
    public static final int SANS_SERIF = 4;

    /**
     * Symbolic constant for setlabelfont() to select an emboldened Sans Serif
     * font.
     */
    public static final int SANS_SERIF_BOLD = 5;

    /**
     * Symbolic constant for setlabelfont() to select an italicized Sans Serif
     * font.
     */
    public static final int SANS_SERIF_ITALIC = 6;

    /**
     * Symbolic constant for setlabelfont() to select an emboldened and
     * italicized Sans Serif font.
     */
    public static final int SANS_SERIF_BOLD_ITALIC = 7;

    /**
     * Symbolic constant for setlabelfont() to select the Serif font.
     */
    public static final int SERIF = 0;

    /**
     * Symbolic constant for setlabelfont() to select an emboldened Serif font.
     */
    public static final int SERIF_BOLD = 1;

    /**
     * Symbolic constant for setlabelfont() to select an italicized Serif font.
     */
    public static final int SERIF_ITALIC = 2;

    /**
     * Symbolic constant for setlabelfont() to select an emboldened and
     * italicized Serif font.
     */
    public static final int SERIF_BOLD_ITALIC = 3;

    /**
     * Largest supported CAPITAL letter ascent
     */
    public static final int MAX_LABEL_ASCENT = 100;

    /**
     * Minimum supported CAPITAL letter ascent
     */
    public static final int MIN_LABEL_ASCENT = 6;

    /**
     * To aid debugging and/or optimizing a grapical program it can be helpful
     * to artificially slow down the drawing. A Sprite can be throttled by
     * invoking setThrottleSleepAmt() with some positive value, an amount of
     * time (milliseconds). Each time a method which visibly changes this
     * Sprite's state finishes its action, the Sprite takes a nap. But, bounds
     * must be set to catch unreasonble requests. Currently the maximum throttle
     * value is five seconds.
     */
    public static final int MAX_THROTTLE_AMT = 5000;


    /*
    * Number of degrees in one radian
     */
    private static final double DEGREES_PER_RADIAN = 180.0 / Math.PI;


    /*
    * Number of radians in a complete circle
     */
    private static final double RADIANS_PER_CIRCLE = Math.PI * 2.0;


    /*
    * Number of radians in one degree
     */
    private static final double RADIANS_PER_DEGREE = Math.PI / 180.0;


    /*
    * Number of radians in a complete circle
     */
    private static final double RADIANS_PER_HALF_CIRCLE = Math.PI;


    /*
    * Number of radians in a quarter of a circle
     */
    private static final double RADIANS_PER_QTR_CIRCLE = Math.PI / 2.0;


    /*
    * Default width of lines drawn by turtle movement.
     */
    private static final int INITIAL_PEN_SIZE = 2;


    /*
    * Default color for stuff drawn by the turtle.
     */
    private static final Color INITIAL_FOREGROUND = Color.black;

    // Class Fields
    // ----- ------

    /*
    * setlabelheight used to expect a font size as its input. This led to
    * guessing which size of font on your system gets you letters of your
    * needed height.  I want the input to setlabelheight to be the height
    * of CAPITAL letters in the chosen font.  I thought this would be easy
    * to do - wrong.  The following fudge factors were computed by running
    * test programs, doing screen captures, magnifying them, and counting
    * pixels. Nice, huh?
    *
    * Fudge factors are in the order of font numbers, i.e., Courier,
    * Sans Serif, Serif
     */
    private float[] macLabelHeightFudges = {76.0f, 75.0f, 89.0f};
    private float[] winLabelHeightFudges = {56.3f, 70.0f, 65.0f};


    /*
    * To aid debugging and/or optimizing a grapical program it can be
    * helpful to artificially slow down the drawing. When throttleSleepAmt
    * is greater than zero, methods that visibly change this Sprite's
    * state, take a nap. The current Thread is suspended for throttleSleepAmt
    * milliseconds.
     */
    private int throttleSleepAmt = 0;


    /*
    * Reference to the graphics canvas this Sprite draws on.  It may
    * come and go.  When there is no current canvas, all of this Sprite's
    * state is maintained as if there was one, just no drawing takes place.
     */
    private TGCanvas canvas;

    /*
    * stuff related to the pixels composing this Sprite's representation
     */
    private Image spriteImage;
    private MemoryImageSource imageProducer;
    private SpritePixels spritePixels;

    /*
    * State of this Sprite...
    * - its current location in TurtleSpace,
    * - its heading (in radians, conventional/AWT form),
    * - whether or not it is visible,
    * - its pen's size, color, the pattern drawn, and
    *   the type of ends on lines it draws,
    * - whether its pen is up or down, and
    * - the Font it uses for LABELs drawn on the canvas.
     */

 /*
    * Color of lines drawn by Sprite movement with the pen down and text
    * drawn (the LABEL command).
     */
    private Color curColor;

    /*
    * Location of Sprite in TurtleSpace.
    * After its initialization in the constructor, it is only changed
    * via the setCurPoint() method so that visible stuff the Sprite
    * does can be throttled.
     */
    private TGPoint curPoint;

    /*
    * Direction Sprite is pointing in radians; zero is positive X axis.
    * After its initialization in the constructor, it is only changed
    * via the setCurHeading() method so that visible stuff the Sprite
    * does can be throttled.
     */
    private double curHeading;

    /*
    * true if this Sprite's image is being displayed on the graphics canvas
     */
    private boolean showTurtle;

    /*
    * true if the Sprite's pen is down, any movement leaves/draws a line on
    * the graphics canvas.
     */
    private boolean penDown;

    /*
    * A pattern of lengths of down and up pen states when drawing lines.
    * If null, draw solid lines. Otherwise, the array's even elements are
    * distances to draw and the odd elements are distances to skip over.
     */
    private float[] penPattern;

    /*
    * Width of the line drawn by the Sprite when its pen is down and it
    * moves, e.g. FORWARD, SETXY, etc...
     */
    private int curPenSize;

    /*
    * The type of ends on lines drawn. Lines the turtle draws can have
    * caps (decorations) on their ends. See the LINECAP_xxx symbolic
    * constants above. These values are mapped to their counterparts
    * from java.awt.BasicStroke which are stored here and supplied to
    * TGLineOp constructors.
     */
    private int typeLinecap;

    /*
    * Initial (default) Font Capital Letter Ascent.
     */
    private static final int INITIAL_FONT_ASCENT = 20;

    /**
     * Current CAPITAL letter ascent
     */
    private int curTextAscent;


    /*
    * Font Styles
     */
    private static final int[] FONT_STYLES = new int[4];

    static {
        FONT_STYLES[0] = Font.PLAIN;
        FONT_STYLES[1] = Font.BOLD;
        FONT_STYLES[2] = Font.ITALIC;
        FONT_STYLES[3] = Font.BOLD | Font.ITALIC;
    }

    private static final String[] FONT_NAMES = new String[3];

    static {
        FONT_NAMES[0] = "Courier";
        FONT_NAMES[1] = "SansSerif";
        FONT_NAMES[2] = "Serif";
    }
    ;

   /**
    * Number of fonts supported. The font number argument to
    * setlabelfont() must be in the range 0..NUM_FONTS-1.
    */
   public static final int NUM_FONTS = FONT_NAMES.length * FONT_STYLES.length;


    /*
    * Font used to draw text on the graphics canvas.
     */
    private Font curFont;


    /*
    * Logo number for the current Font.
     */
    private int curFontNumber;

    /*
    * Turtle steps (pixels) to Font-size maps, one for each Font.
    * Each element is an array of  shorts. A short's index is the
    * ascent height of a capital letter.  The contents of the short
    * at this index is the Font size that is closest to this height.
    *
    * As an example, here are a few elements on a Windows 7 system
    * for font number 0 (Courier, PLAIN):
    *
    * fontSizes[0][10] = 10
    * ...
    * fontSizes[0][20] = 19
    *
     */
    private short[][] fontSizes;

    // Constructor
    // -----------
    /**
     * Instantiate the graphics sprite associated with a turtle. The sprite is
     * setup with a default location (home), heading, image, font and pen.
     */
    public Sprite(TGCanvas canvas) {
        fontSizes = new short[NUM_FONTS][];
        curFontNumber = 0;
        curTextAscent = INITIAL_FONT_ASCENT;
        loadFontSizes(curFontNumber);
        curFont = new Font(FONT_NAMES[curFontNumber / 4],
                FONT_STYLES[0],
                fontSizes[curFontNumber][INITIAL_FONT_ASCENT]);
        curColor = INITIAL_FOREGROUND;
        curPenSize = INITIAL_PEN_SIZE;
        curPoint = new TGPoint(0, 0);
        curHeading = RADIANS_PER_QTR_CIRCLE;
        spritePixels = new TurtleTurtle(curColor, curHeading);
        penDown = true;
        penPattern = null;
        typeLinecap = java.awt.BasicStroke.CAP_BUTT;
        this.canvas = canvas;
        if (canvas != null) {
            canvas.addSprite(this);
            showTurtle = true;
        } else {
            showTurtle = false;
        }
    }

    // Private Utility Stuff
    // ------- ------- -----
    /*
    * Paint pixels composing a line given a starting point, a heading,
    * and a distance.  Return the other endpoint of the line.
    *
    * @param p1 one endpoint of the line segment to be drawn
    * @param steps distance to other endpoint of the line segment
    * @param heading radians from p1 to the other end point
    * @param color java.awt.Color of line
    * @param width line width in pixels
     */
    private TGPoint drawLine(TGPoint p1, double steps, double heading, Color color, int width) {
        if (steps < 0) {
            heading -= RADIANS_PER_HALF_CIRCLE;
            if (heading < 0) {
                heading += RADIANS_PER_CIRCLE;
            }
            steps = -steps;
        }
        TGPoint p2 = p1.otherEndPoint(heading, steps);
        if (canvas != null) {
            canvas.addGraphOp(new TGLineOp(p1, p2, color, width, penPattern, typeLinecap));
        }
        return p2;
    }


    /*
    * Return the heading (in radians) from one point on the
    * graphics canvas to another.
     */
    private double getRadiansTwds(TGPoint frmPt, TGPoint toPt) {
        double retVal = 0.0;
        double frmX = frmPt.xDoubleValue();
        double frmY = frmPt.yDoubleValue();
        double toX = toPt.xDoubleValue();
        double toY = toPt.yDoubleValue();
        if (frmX == toX) {
            // special case of vertical line
            retVal = (frmY < toY) ? RADIANS_PER_QTR_CIRCLE : RADIANS_PER_HALF_CIRCLE + RADIANS_PER_QTR_CIRCLE;
        } else {
            double slope = (toY - frmY) / (toX - frmX);
            retVal = Math.atan(slope);
            if (retVal == -0.0) {
                retVal = 0.0;
            }
            if (frmX > toX) {
                retVal = retVal + RADIANS_PER_HALF_CIRCLE;
            }
            if (retVal < 0.0) {
                retVal += RADIANS_PER_CIRCLE;
            }
        }
        return retVal;
    }


    /*
    * Initialize the Font sizes needed for getting maxAscents heights.
    * The index into fontSizes[fontNumber][idx] is the ascent height of
    * a capital letter and the contents of the short at this index is
    * the Font size that gets us that ascent height.
     */
    private void loadFontSizes(int fontNumber) {
        if (fontSizes[fontNumber] != null) {
            return;
        }
        fontSizes[fontNumber] = new short[MAX_LABEL_ASCENT + 1];
        float fudgeFactor = 100.0f;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.startsWith("win")) {
            fudgeFactor = winLabelHeightFudges[fontNumber / 4];
        } else if (os.startsWith("mac")) {
            fudgeFactor = macLabelHeightFudges[fontNumber / 4];
        }
        int fontSize = 1;
        Font font = new Font(FONT_NAMES[fontNumber / 4], FONT_STYLES[fontNumber % 4], fontSize);
        while (font == null) {
            fontSize++;
            font = new Font(FONT_NAMES[fontNumber / 4], FONT_STYLES[fontNumber % 4], fontSize);
            continue;
        }
        FontMetrics fm = getFontMetrics(font);
        float fontMaxAscent = (float) fm.getMaxAscent();
        int capsAscent = Math.round((fontMaxAscent * fudgeFactor) / 100.0f);
        for (int ascentHeightIdx = 1; ascentHeightIdx <= MAX_LABEL_ASCENT; ascentHeightIdx++) {
            if (ascentHeightIdx <= capsAscent) {
                fontSizes[fontNumber][ascentHeightIdx] = (short) fontSize;
                continue;
            } else {
                while (ascentHeightIdx > capsAscent) {
                    fontSize++;
                    font = new Font(FONT_NAMES[fontNumber / 4], FONT_STYLES[fontNumber % 4], fontSize);
                    while (font == null) {
                        fontSize++;
                        font = new Font(FONT_NAMES[fontNumber / 4], FONT_STYLES[fontNumber % 4], fontSize);
                    }
                    fm = getFontMetrics(font);
                    fontMaxAscent = (float) fm.getMaxAscent();
                    capsAscent = Math.round((fontMaxAscent * fudgeFactor) / 100.0f);
                }
                fontSizes[fontNumber][ascentHeightIdx] = (short) fontSize;
            }
        }
    }


    /*
    * Change the sprite's current heading.
    * The curHeading variable is only changed here so that a throttle
    * can be maintained.
     */
    private void setCurHeading(double newCurHeading) throws AbortException {
        curHeading = newCurHeading;
        if (canvas != null && (throttleSleepAmt > 0) && showTurtle) {
            try {
                Thread.sleep(throttleSleepAmt);
            } catch (InterruptedException ie) {
                throw new AbortException("Sprite.setCurHeading()");
            }
        }
    }


    /*
    * Change the sprite's current location - curPoint.
    * The curPoint variable is only changed here so that a throttle
    * can be maintained.
     */
    private void setCurPoint(TGPoint newCurPoint) throws AbortException {
        curPoint = newCurPoint;
        if (canvas != null && (throttleSleepAmt > 0) && showTurtle) {
            try {
                Thread.sleep(throttleSleepAmt);
            } catch (InterruptedException ie) {
                throw new AbortException("Sprite.setCurPoint()");
            }
        }
    }


    /*
    * Print an error message to System.err, tying it to
    * this class
     */
    private static void sysErr(String errTxt) {
        System.err.println(CLASS_NAME + errTxt);
    }

    // Public Methods
    // ------ -------
    /**
     * Draws an arc of a circle, with the Sprite at the center, with the
     * specified radius, starting at the Sprite's heading and extending
     * clockwise through the specified angle. The Sprite is not moved.
     *
     * @param angle clockwise rotation from current heading (in degrees)
     * @param radius distance from center of Sprite to the drawn arc, to the
     * center of the arc line if the pen width is greater than 1.
     */
    public void arc(double angle, double radius) {
        if (canvas == null || (!penDown)) {
            return;
        }
        if (angle == 0.0) {
            return;
        }
        if (angle > 360.0) {
            angle = 360.0;
        }
        double radiansAngle = -(angle * RADIANS_PER_DEGREE);
        canvas.addGraphOp(new TGArcOp(curPoint, radius, curHeading, radiansAngle, curColor, curPenSize, penPattern));
        canvas.repaint();
    }

    /**
     * Draws an arc of a circle, with the Sprite at the center, with the
     * specified radius, starting at the Sprite's heading and extending
     * clockwise through the specified angle. The Sprite is not moved.
     *
     * @param angle clockwise rotation from current heading (in degrees)
     * @param radius distance from center of Sprite to the drawn arc, to the
     * center of the arc line if the pen width is greater than 1.
     */
    public void arc(int angle, int radius) {
        arc((double) angle, (double) radius);
    }

    /**
     * Move this Sprite backwards along its current heading. If the pen is
     * currently in the DOWN position, a line is drawn.
     * <P>
     * Long name for <B>bk()</B>. Both spellings need to provided for
     * compatibility.
     * <P>
     * @param steps Number of pixels (in this implementation) to take.
     * @see #bk
     */
    public void back(double steps) throws AbortException {
        bk(steps);
    }

    public void back(float steps) throws AbortException {
        bk((double) steps);
    }

    public void back(int steps) throws AbortException {
        bk((double) steps);
    }

    /**
     * Move this Sprite backwards along its current heading. If the pen is
     * currently in the DOWN position, a line is drawn.
     * <P>
     * Abbreviation for <B>back()</B>. Both spellings need to provided for
     * compatibility.
     * <P>
     * @param steps Number of pixels (in this implementation) to take.
     * @see #back
     */
    public void bk(float steps) throws AbortException {
        bk((double) steps);
    }

    public void bk(int steps) throws AbortException {
        bk((double) steps);
    }

    public void bk(double steps) throws AbortException {
        TGPoint newCurPoint;
        if (penDown) {
            newCurPoint = drawLine(curPoint, -steps, (double) curHeading, curColor, curPenSize);
        } else {
            newCurPoint = curPoint.otherEndPoint((double) curHeading, -steps);
        }
        if (canvas != null && (penDown || showTurtle)) {
            canvas.repaint();
        }
        setCurPoint(newCurPoint);
    }

    /**
     * Clean up before going away.
     */
    public void close() {
        if (canvas != null && showTurtle) {
            canvas.removeSprite(this);
        }
        showTurtle = false;
    }

    /**
     * Return an array of the unique colors this Sprite's image is positioned
     * over.
     * <p>
     * @see #pencolor
     * @see #setpc
     * @see #setpencolor
     */
    public int[] colorsunder() {
        if (canvas == null) {
            return null;
        }
        int imgSideSiz = getImageSideSize();
        double leftX = curPoint.xDoubleValue() - imgSideSiz / 2;
        double topY = curPoint.yDoubleValue() + imgSideSiz / 2;
        int[] canvasPixels = canvas.getPixels(new TGPoint(leftX, topY), imgSideSiz, imgSideSiz);
        int[] maskPixels = spritePixels.getPixels();
        int[] uniqueColors = new int[canvasPixels.length];
        int numUniqueColors = 0;
        for (int idx = 0; idx < canvasPixels.length; idx++) {
            if (maskPixels[idx] == 0) {
                continue;
            }
            int colorIndex = 0;
            int pixelColor = canvasPixels[idx] & 0xffffff;
            while (colorIndex < numUniqueColors) {
                if (pixelColor == uniqueColors[colorIndex]) {
                    break;
                }
                colorIndex++;
            }
            if (colorIndex == numUniqueColors) {
                uniqueColors[numUniqueColors++] = pixelColor;
            }
        }
        int[] retArray = new int[numUniqueColors];
        System.arraycopy(uniqueColors, 0, retArray, 0, numUniqueColors);
        return retArray;

    }

    /**
     * Return the color this Sprite is sitting on, -1 if there is not a current
     * graphics canvas to draw on.
     * <p>
     * @see #pencolor
     * @see #setpc
     * @see #setpencolor
     */
    public int colorunder() {
        if (canvas == null) {
            return -1;
        }
        int[] pixel = canvas.getPixels(curPoint, 1, 1);
        return TGCanvas.rgbToLogoColor(pixel[0] & 0xFFFFFF);
    }

    /**
     * Fill a bounded area in the graphics image.
     * <p>
     * Perform a flood-fill operation starting at the Sprite's current position.
     * The current pixel, and any of its neighbors that are the same color as it
     * (and any of their neighbors that are the same color as it, etc...) are
     * changed to the current color.
     * <p>
     * For more * information on flood-fill, see the wikipedia entry or google
     * for it.
     */
    public void fill() {
        if (canvas != null) {
            canvas.addGraphOp(new TGFillOp(curPoint, curColor));
            canvas.repaint();
        }
    }

    /**
     * Move this Sprite forward along its current heading. If the pen is
     * currently in the DOWN position, a line is drawn.
     * <P>
     * Abbreviation for <B>forward()</B>. Both spellings need to provided for
     * compatibility.
     * <P>
     * @param steps Distance in TurtleSpace to move.
     * @see #forward
     */
    public void fd(float steps) throws AbortException {
        fd((double) steps);
    }

    public void fd(int steps) throws AbortException {
        fd((double) steps);
    }

    public void fd(double steps) throws AbortException {
        TGPoint newCurPoint;
        if (canvas != null && penDown) {
            newCurPoint = drawLine(curPoint, steps, (double) curHeading, curColor, curPenSize);
        } else {
            newCurPoint = curPoint.otherEndPoint((double) curHeading, steps);
        }
        if (canvas != null && (penDown || showTurtle)) {
            canvas.repaint();
        }
        setCurPoint(newCurPoint);
    }

    /**
     * Move this Sprite forward along its current heading. If the pen is
     * currently in the DOWN position, a line is drawn.
     * <P>
     * Long name for <B>fd()</B>. Both spellings need to provided for
     * compatibility.
     * <P>
     * @param steps Number of pixels (in this implementation) to take.
     * @see #fd
     */
    public void forward(double steps) throws AbortException {
        fd(steps);
    }

    public void forward(float steps) throws AbortException {
        fd((double) steps);
    }

    public void forward(int steps) throws AbortException {
        fd((double) steps);
    }

    /**
     * Return this Sprite's Image so it can be drawn on a Canvas by a Graphics
     * object.
     */
    public Image getImage() {
        while (spriteImage == null) {
            int[] pixels = spritePixels.getPixels();
            int sideSize = spritePixels.getSideSize();
            // create spriteImage to match the array of pixels
            // AWT Graphics only supports painting of Image objects, no kind
            // of BitBlt for arrays of pixel values (?who know's why?)
            if (imageProducer == null) {
                imageProducer = new MemoryImageSource(sideSize, sideSize, pixels, 0, sideSize);
            }
            spriteImage = createImage(imageProducer);
        }
        return spriteImage;
    }

    /**
     * Return the size of a side of this Sprite's Image.
     * <p>
     * @see #getImage
     */
    public int getImageSideSize() {
        return spritePixels.getSideSize();
    }

    /**
     * Return the current CAPITAL letter ascent height (pixels).
     *
     * @see #label
     * @see #setlabelheight
     */
    public int getLabelHeight() {
        return curTextAscent;
    }

    /**
     * Set the type of linecaps applied to the ends of lines drawn.
     *
     * @see #setLinecap
     * @see #LINECAP_BUTT
     * @see #LINECAP_ROUND
     * @see #LINECAP_SQUARE
     */
    public int getLinecap() {
        return typeLinecap;
    }

    /**
     * Return the width of the provided String in turtle steps (pixels).
     *
     * @see #label
     */
    public int getLabelWidth(String label) {
        if (label == null || label.length() == 0) {
            return 0;
        }
        FontMetrics fm = getFontMetrics(curFont);
        return fm.stringWidth(label);
    }

    /**
     * Return null or an array of pairs of draw/skip distances. If null, the
     * turtle's pen is drawing solid lines. If an array of draw/skip pairs is
     * returned, the turtle's pen is drawing some sort of dashed line, e.g.,
     * element [0] is distance to draw, [1] is distance to skip. Pairs are used
     * in order until the end of the array is reached, at which point the cycle
     * begins again with elements [0] and [1].
     *
     * @see #setPenPattern
     */
    public float[] getPenPattern() {
        return penPattern;
    }

    /**
     * Return this Sprite's SpritePixels object.
     */
    public SpritePixels getSpritePixels() {
        return spritePixels;
    }

    /**
     * Get the amount of time (milliseconds) that drawing is suspended each time
     * a method which visibly changes the Sprite's state is performed. To aid
     * debugging and/or optimizing a grapical program it can be helpful to
     * artificially slow down the drawing. When throttleSleepAmt is greater than
     * zero, concerned methods suspend the current Thread for it's amount.
     */
    public int getThrottleSleepAmt() {
        return throttleSleepAmt;
    }

    /**
     * Return this Sprite's heading in degrees.
     * <P>
     * Logo's TurtleSpace does not match the mathematical convention of
     * measuring angles counter-clockwise from the positive X axis. Logo defines
     * NORTH (+Y axis) as 0 degrees and degrees increase in the clockwise
     * direction, so East is 90 degrees, South is 180 degrees and West is 270
     * degrees.
     *
     * @see #seth
     * @see #setheading
     */
    // The module-wide variable curHeading contains the
    // mathematically-correct heading in radians, not Logo's
    // point of view.  So, we must convert curHeading to
    // TurtleSpace's scheme.
    //
    public double heading() {
        double degrees = curHeading * DEGREES_PER_RADIAN;
        double clockwiseDegrees = 360.0 - degrees;
        double turtleSpaceDegrees = clockwiseDegrees + 90.0;
        return turtleSpaceDegrees % 360.0;
    }

    /**
     * Hide this Sprite; make it invisible.
     * <P>
     * Long name for <B>ht()</B>. Both spellings need to provided for
     * compatibility.
     * <P>
     * @see #ht
     * @see #showturtle
     * @see #st
     */
    public void hideturtle() {
        ht();
    }

    /**
     * Move this Sprite to the center of the display. If the pen is currently in
     * the DOWN position, a line is drawn.
     * <P>
     * Home is equivilent to setxy( 0, 0 )
     * <P>
     * @see #setxy
     */
    public void home() throws AbortException {
        setxy(0, 0);
    }

    /**
     * Hide this Sprite; make it invisible.
     * <P>
     * Abbreviation for <B>hideturtle()</B>. Both spellings need to provided for
     * compatibility.
     * <P>
     * @see #hideturtle
     * @see #showturtle
     * @see #st
     */
    public void ht() {
        if (canvas != null && showTurtle) {
            canvas.removeSprite(this);
            canvas.repaint();
            showTurtle = false;
        }
    }

    /**
     * Return the current status of the pen.
     *
     * Return true if this Sprite's pen is down or false if it in the up
     * position.
     *
     * @see #pendown
     * @see #penup
     * @see #pd
     * @see #pu
     */
    public boolean ispendown() {
        return penDown;
    }

    /**
     * Draws a String of characters onto the graphics canvas. The text is drawn
     * in the current pen's color, starting at the current position of the
     * Sprite.
     * <p>
     * The text is always drawn in the standard horizontal manner, i.e., the
     * heading of this Sprite is ignored.
     * <p>
     * @param text characters to be drawn on the display.
     */
    public void label(String text) {
        if (canvas != null && text != null) {
            canvas.addGraphOp(new TGLabelOp(text, curPoint, curFont, curColor));
            canvas.repaint();
        }
    }

    /**
     * Rotate this Sprite counterclockwise by the specified angle, measured in
     * degrees.
     * <P>
     * @param degrees Angle to change this Sprite's heading by.
     * @see #lt
     */
    public void left(double degrees) throws AbortException {
        lt(degrees);
    }

    public void left(float degrees) throws AbortException {
        lt((double) degrees);
    }

    public void left(int degrees) throws AbortException {
        lt((double) degrees);
    }

    public void left(long degrees) throws AbortException {
        lt((double) degrees);
    }

    /**
     * Rotate this Sprite counterclockwise by the specified angle, measured in
     * degrees.
     * <P>
     * Abbreviation for <B>left()</B>. Both spellings need to provided for
     * compatibility.
     * <P>
     * @param degrees Angle to change this Sprite's heading by.
     * @see #left
     */
    // Logo degrees increase in the clockwise direction.
    // Since this does not match the mathematical convention
    // of measuring angles counterclockwise, we must convert
    // the parameter.
    public void lt(float degrees) throws AbortException {
        lt((double) degrees);
    }

    public void lt(int degrees) throws AbortException {
        lt((double) degrees);
    }

    public void lt(long degrees) throws AbortException {
        lt((double) degrees);
    }

    public void lt(double degrees) throws AbortException {
        double radians = degrees * RADIANS_PER_DEGREE;
        double newCurHeading = curHeading + radians;
        if (newCurHeading < 0.0) {
            newCurHeading += RADIANS_PER_CIRCLE;
        } else if (newCurHeading > RADIANS_PER_CIRCLE) {
            newCurHeading -= RADIANS_PER_CIRCLE;
        }
        if (canvas != null && showTurtle) {
            if (spritePixels.setSpriteHeading(newCurHeading)) {
                spriteImage = null;
                canvas.repaint();
            }
        }
        setCurHeading(newCurHeading);
    }

    /**
     * Put this Sprite's pen in the down position.
     * <p>
     * When this Sprite moves, it will leave a trace from its current position
     * to its destination (its new position).
     *
     * @see #ispendown
     * @see #pendown
     * @see #pu
     * @see #penup
     */
    public void pd() {
        penDown = true;
    }

    /**
     * Return the color the pen is currently drawing in.
     * <P>
     * @see #setpc
     * @see #setpencolor
     */
    public int pencolor() {
        return TGCanvas.rgbToLogoColor(curColor.getRGB());
    }

    /**
     * Put this Sprite's pen in the down position.
     * <p>
     * When this Sprite moves, it will leave a trace from its current position
     * to its destination (its new position).
     *
     * @see #ispendown
     * @see #pd
     * @see #pu
     * @see #penup
     */
    public void pendown() {
        pd();
    }

    /**
     * Return the width of the pen this Sprite is currently drawing with.
     *
     * @see #setpensize
     */
    public int pensize() {
        return curPenSize;
    }

    /**
     * Return the current position of theis Sprite.
     *
     * @return TGPoint a virtual point, a point in TurtleSpace. In TurtleSpace,
     * 0.0,0.0 is at the center of the graphics canvas.
     */
    public TGPoint pos() {
        return curPoint;
    }

    /**
     * Put this Sprite's pen in the up position.
     * <p>
     * When this Sprite moves, it will leave no trace.
     *
     * @see #ispendown
     * @see #pd
     * @see #pendown
     * @see #penup
     */
    public void pu() {
        penDown = false;
    }

    /**
     * Put this Sprite's pen in the up position.
     * <p>
     * When this Sprite moves, it will leave no trace.
     *
     * @see #ispendown
     * @see #pd
     * @see #pendown
     * @see #pu
     */
    public void penup() {
        pu();
    }

    /**
     * Rotate this Sprite clockwise by the specified angle, measured in degrees.
     * <P>
     * @param degrees Angle to change this Sprite's heading by.
     * @see #rt
     */
    public void right(double degrees) throws AbortException {
        rt(degrees);
    }

    public void right(float degrees) throws AbortException {
        rt((double) degrees);
    }

    public void right(int degrees) throws AbortException {
        rt((double) degrees);
    }

    public void right(long degrees) throws AbortException {
        rt((double) degrees);
    }

    /**
     * Rotate this Sprite clockwise by the specified angle, measured in degrees.
     * <P>
     * Abbreviation for <B>right()</B>. Both spellings need to provided for
     * compatibility.
     * <P>
     * @param degrees Angle to change this Sprite's heading by.
     * @see #right
     */
    // Logo degrees increase in the clockwise direction.
    // Since this does not match the mathematical convention
    // of measuring angles counterclockwise, we must convert
    // the parameter.
    public void rt(float degrees) throws AbortException {
        rt((double) degrees);
    }

    public void rt(int degrees) throws AbortException {
        rt((double) degrees);
    }

    public void rt(long degrees) throws AbortException {
        rt((double) degrees);
    }

    public void rt(double degrees) throws AbortException {
        double radians = degrees * RADIANS_PER_DEGREE;
        double newCurHeading = curHeading - radians;
        if (newCurHeading < 0.0) {
            newCurHeading += RADIANS_PER_CIRCLE;
        } else if (newCurHeading > RADIANS_PER_CIRCLE) {
            newCurHeading -= RADIANS_PER_CIRCLE;
        }
        if (canvas != null && showTurtle) {
            if (spritePixels.setSpriteHeading(newCurHeading)) {
                spriteImage = null;
                canvas.repaint();
            }
        }
        setCurHeading(newCurHeading);
    }

    /**
     * Set/clear the reference to this Sprite's graphics canvas.
     */
    public void setCanvas(TGCanvas canvas) {
        this.canvas = canvas;
    }

    /**
     * Turns this Sprite to the specified absolute heading. The heading is
     * specified in degrees (units of 1/360th of a circle) with 0 being North
     * (+Y axis), increasing clockwise. So, East is 90 degrees, South is 180
     * degrees, and West is 270 degrees.
     * <P>
     * Abbreviation for <B>setheading()</B>. Both spellings need to provided for
     * compatibility.
     * <P>
     * @param turtleSpaceDegrees number of 1/360ths increments clockwise from
     * the positive Y axis.
     * @see #setheading
     */
    // Logo defines North (+Y axis) as 0 degrees and degrees
    // increase in the clockwise direction, so East is 90
    // degrees, South is 180 degrees and West is 270 degrees.
    // Since this does not match the mathematical convention
    // of measuring angles counterclockwise from the positive
    // X axis, we must convert the parameter.  The module-wide
    // variable (curHeading) contains the mathematically-
    // correct heading, not Logo's point of view.
    //
    public void seth(float turtleSpaceDegrees) throws AbortException {
        seth((double) turtleSpaceDegrees);
    }

    public void seth(int turtleSpaceDegrees) throws AbortException {
        seth((double) turtleSpaceDegrees);
    }

    public void seth(long turtleSpaceDegrees) throws AbortException {
        seth((double) turtleSpaceDegrees);
    }

    public void seth(double turtleSpaceDegrees) throws AbortException {
        double degrees = 360.0 - (turtleSpaceDegrees % 360.0);
        degrees += 90.0;
        degrees %= 360.0;
        double newHeading = degrees * RADIANS_PER_DEGREE;
        if (canvas != null && showTurtle) {
            if (spritePixels.setSpriteHeading(newHeading)) {
                spriteImage = null;
                canvas.repaint();
            }
        }
        setCurHeading(newHeading);
    }

    /**
     * Turns this Sprite to the specified absolute heading. The heading is
     * specified in degrees (units of 1/360th of a circle) with 0 being North
     * (+Y axis), increasing clockwise. So, East is 90 degrees, South is 180
     * degrees, and West is 270 degrees.
     *
     * @param turtleSpaceDegrees number of 1/360ths increments clockwise from
     * the positive Y axis.
     * @see #seth
     */
    public void setheading(double turtleSpaceDegrees) throws AbortException {
        seth(turtleSpaceDegrees);
    }

    public void setheading(float turtleSpaceDegrees) throws AbortException {
        seth((double) turtleSpaceDegrees);
    }

    public void setheading(int turtleSpaceDegrees) throws AbortException {
        seth((double) turtleSpaceDegrees);
    }

    public void setheading(long turtleSpaceDegrees) throws AbortException {
        seth((double) turtleSpaceDegrees);
    }

    /**
     * Set the font for LABELs.
     *
     * @param fontNumber an integer representing a font face and font style.
     * <p>
     * <pre>
     * Number   Font         Style
     * ------   ----------   -----------
     *    0     Courier      Plain
     *    1     Courier      Bold
     *    2     Courier      Italic
     *    3     Courier      Bold Italic
     *    4     Sans Serif   Plain
     *    5     Sans Serif   Bold
     *    6     Sans Serif   Italic
     *    7     Sans Serif   Bold Italic
     *    8     Serif        Plain
     *    9     Serif        Bold
     *   10     Serif        Italic
     *   11     Serif        Bold Italic
     * </pre>
     *
     * @see #label
     * @see #getLabelHeight
     * @see #setlabelheight
     * @see #COURIER
     * @see #COURIER_BOLD
     * @see #COURIER_ITALIC
     * @see #COURIER_BOLD_ITALIC
     * @see #SANS_SERIF
     * @see #SANS_SERIF_BOLD
     * @see #SANS_SERIF_ITALIC
     * @see #SANS_SERIF_BOLD_ITALIC
     * @see #SERIF
     * @see #SERIF_BOLD
     * @see #SERIF_ITALIC
     * @see #SERIF_BOLD_ITALIC
     */
    public void setlabelfont(int fontNumber) {
        if (fontNumber == curFontNumber) {
            return;
        }
        if (fontNumber < 0 || fontNumber >= NUM_FONTS) {
            return;
        }
        int curFontSize = curFont.getSize();
        int capsHt = fontSizes[curFontNumber].length - 1;
        while (capsHt >= 0) {
            if (fontSizes[curFontNumber][capsHt] <= curFontSize) {
                break;
            }
            capsHt--;
        }
        loadFontSizes(fontNumber);
        curFontNumber = fontNumber;
        curFont = new Font(FONT_NAMES[curFontNumber / 4],
                FONT_STYLES[curFontNumber % 4],
                fontSizes[curFontNumber][capsHt]);
    }

    /**
     * Set the size of the text displayed in the graphics area.
     *
     * @see #label
     * @see #getLabelHeight
     */
    public void setlabelheight(int size) {
        if (size < MIN_LABEL_ASCENT) {
            size = MIN_LABEL_ASCENT;
        }
        if (size > MAX_LABEL_ASCENT) {
            size = MAX_LABEL_ASCENT;
        }
        curTextAscent = size;
        curFont = new Font(FONT_NAMES[curFontNumber / 4],
                FONT_STYLES[curFontNumber % 4],
                fontSizes[curFontNumber][size]);
    }

    /**
     * Set the type of linecaps applied to the ends of lines drawn. If the input
     * is not a valid type, the default LINECAP_BUTT is used.
     *
     * @param type an integer representing a linecap
     *
     * <pre>
     * Number   Linecap
     * ------   -------
     *    0     BUTT
     *    1     ROUND
     *    2     SQUARE
     * </pre>
     *
     * @see #arc
     * @see #forward
     * @see #getLinecap
     * @see #LINECAP_BUTT
     * @see #LINECAP_ROUND
     * @see #LINECAP_SQUARE
     */
    public void setLinecap(int type) {
        switch (type) {
            case LINECAP_BUTT:
                typeLinecap = java.awt.BasicStroke.CAP_BUTT;
                break;
            case LINECAP_ROUND:
                typeLinecap = java.awt.BasicStroke.CAP_ROUND;
                break;
            case LINECAP_SQUARE:
                typeLinecap = java.awt.BasicStroke.CAP_SQUARE;
                break;
            default:
                typeLinecap = java.awt.BasicStroke.CAP_ROUND;
        }
    }

    /**
     * Sets the color of this Sprite's pen to the supplied number.
     *
     * @param logoColorNum numbers 0-31 are:
     * <p>
     * <pre>
     * Number Color        Number Color        Number Color
     * ------ ----------   ------ ----------   ------ ---------
     *    0   black          11   aqua           22   gold
     *    1   blue           12   salmon         23   lightgray
     *    2   green          13   purple         24   peru
     *    3   cyan           14   orange         25   wheat
     *    4   red            15   grey           26   palegreen
     *    5   magenta        16   navy           27   lightblue
     *    6   yellow         17   skyblue        28   khaki
     *    7   white          18   lime           29   pink
     *    8   brown          19   steelblue      30   lawngreen
     *    9   light brown    20   chocolate      31   olive
     *   10   forest         21   purple
     * </pre>
     * <p>
     * Color numbers greater than 31 will be assumed to be RGB values with the
     * red component in bits 8-15, the green component in bits 16-23, and the
     * blue component in bits 24-31. The actual color used in rendering will
     * depend on finding the best match given the color space available for a
     * given display.
     *
     * @see #pencolor
     * @see #setpencolor
     */
    public void setpc(int logoColorNum) {
        Color color = TGCanvas.logoColorToJavaColor(logoColorNum);
        if (curColor.getRGB() != color.getRGB()) {
            curColor = color;
            if (canvas != null && showTurtle) {
                if (spritePixels.setSpriteColor(color)) {
                    spriteImage = null;
                    canvas.repaint();
                }
            }
        }
    }

    /**
     * Sets the color of this Sprite's pen to the supplied number.
     *
     * @param colorNum numbers 0-31 are:
     * <p>
     * <pre>
     * Number Color        Number Color        Number Color
     * ------ ----------   ------ ----------   ------ ---------
     *    0   black          11   aqua           22   gold
     *    1   blue           12   salmon         23   lightgray
     *    2   green          13   purple         24   peru
     *    3   cyan           14   orange         25   wheat
     *    4   red            15   grey           26   palegreen
     *    5   magenta        16   navy           27   lightblue
     *    6   yellow         17   skyblue        28   khaki
     *    7   white          18   lime           29   pink
     *    8   brown          19   steelblue      30   lawngreen
     *    9   light brown    20   chocolate      31   olive
     *   10   forest         21   purple
     * </pre>
     *
     * Color numbers greater than 15 will be assumed to be RGB values with the
     * red component in bits 8-15, the green component in bits 16-23, and the
     * blue component in bits 24-31. The actual color used in rendering will
     * depend on finding the best match given the color space available for a
     * given display.
     *
     * @see #setpc
     */
    public void setpencolor(int colorNum) {
        setpc(colorNum);
    }

    /**
     * Set the pattern used to draw lines. A pattern consists of lengths of down
     * and up pen states when drawing lines. If the pattern is null or empty,
     * solid lines are drawn. Otherwise, the array's even elements are distances
     * to draw and the odd elements are distances to skip over.
     *
     * @param pattern array of draw/skip amounts. If pattern has an odd number
     * of elements, the last draw amount is duplicated for the skip amount,
     * producing an array one element larger.
     *
     * @see #arc
     * @see #back
     * @see #forward
     * @see #getPenPattern
     * @see #pendown
     * @see #penup
     * @see #pd
     * @see #pu
     */
    public void setPenPattern(int[] pattern) {
        if (pattern == null || pattern.length == 0) {
            penPattern = null;
            return;
        }
        int penPatternSize = pattern.length;
        if ((pattern.length % 2) == 1) {
            penPatternSize++;
        }
        penPattern = new float[penPatternSize];
        int patIdx = 0;
        int patternElement = pattern[0];
        while (true) {
            penPattern[patIdx] = (float) patternElement;
            if (++patIdx >= pattern.length) {
                break;
            }
            patternElement = pattern[patIdx];
        }
        if ((pattern.length % 2) == 1) {
            penPattern[patIdx] = (float) patternElement;
        }
    }

    /**
     * Sets the width of this Sprite's pen to the supplied number.
     *
     * @param width small positive number; 1 (or less) results in a single pixel
     * line.
     */
    public void setpensize(int width) {
        if (width == curPenSize) {
            return;
        }
        if (width < 1) {
            curPenSize = 1;
        } else {
            curPenSize = width;
        }
    }

    /**
     * Sets the shape of this Sprite - its pixel image. Returns true if shape
     * successfully set, else false failure.
     * <p>
     * @param shapeNum small positive number; 0 for default Sprite; see
     * constants (e.g., BALL, BOX, etc...) for other Sprite image shapes...
     * @param params an optional int array containing sizing information hints,
     * e.g. radius of a ball, width and height of a box, etc...
     * <p>
     * <pre>
     * Number  Shape      Optional Size Parameters
     * ------  --------   ------------------------
     *    0    Turtle
     *    1    Arrow      width, height
     *    2    Ball       diameter
     *    3    Box        width, height
     *    4    Cross      width, height
     *    5    Triangle   width, height
     *    6    Diamond    width, height
     * </pre>
     *
     * @see #TURTLE
     * @see #ARROW
     * @see #BALL
     * @see #BOX
     * @see #CROSS
     * @see #TRIANGLE
     * @see #DIAMOND
     */
    public boolean setshape(int shapeNum, int[] params) {
        int width = 0;
        if ((params != null) && (params.length > 0)) {
            width = params[0];
        }
        int height = width;
        if ((params != null) && (params.length > 1)) {
            height = params[1];
        }
        SpritePixels newSpritePixels = null;
        switch (shapeNum) {
            case TURTLE:
                newSpritePixels = new TurtleTurtle(curColor, curHeading);
                break;
            case ARROW:
                if (params != null) {
                    newSpritePixels = new ArrowTurtle(width, height, curColor, curHeading);
                } else {
                    newSpritePixels = new ArrowTurtle(curColor, curHeading);
                }
                break;
            case BALL:
                if (params != null) {
                    newSpritePixels = new BallTurtle(width, curColor, curHeading);
                } else {
                    newSpritePixels = new BallTurtle(curColor, curHeading);
                }
                break;
            case BOX:
                if (params != null) {
                    newSpritePixels = new BoxTurtle(width, height, curColor, curHeading);
                } else {
                    newSpritePixels = new BoxTurtle(curColor, curHeading);
                }
                break;
            case CROSS:
                if (params != null) {
                    newSpritePixels = new CrossTurtle(width, height, curColor, curHeading);
                } else {
                    newSpritePixels = new CrossTurtle(curColor, curHeading);
                }
                break;
            case DIAMOND:
                if (params != null) {
                    newSpritePixels = new DiamondTurtle(width, height, curColor, curHeading);
                } else {
                    newSpritePixels = new DiamondTurtle(curColor, curHeading);
                }
                break;
            case TRIANGLE:
                if (params != null) {
                    newSpritePixels = new TriangleTurtle(width, height, curColor, curHeading);
                } else {
                    newSpritePixels = new TriangleTurtle(curColor, curHeading);
                }
                break;
        }
        if (newSpritePixels == null) {
            return false;
        }
        setshape(newSpritePixels);
        return true;
    }

    /**
     * Sets the shape of this Sprite - its pixel image.
     * <p>
     * @param newSpritePixels - instance of an object extending SpritePixels
     * which provides an image for a Sprite.
     */
    public void setshape(SpritePixels newSpritePixels) {
        if (newSpritePixels != null) {
            newSpritePixels.setSpriteHeading(curHeading);
            spritePixels = newSpritePixels;
            spriteImage = null;
            imageProducer = null;
            if (canvas != null) {
                canvas.repaint();
            }
        }
    }

    /**
     * Set the amount of time (milliseconds) that drawing is suspended each time
     * a method which visibly changes the Sprite's state is performed. To aid
     * debugging and/or optimizing a grapical program it can be helpful to
     * artificially slow down the drawing. When throttleSleepAmt is greater than
     * zero, concerned methods suspend the current Thread for it's amount.
     */
    public synchronized void setThrottleSleepAmt(int newThrottleSleepAmt) {
        if (newThrottleSleepAmt <= 0) {
            throttleSleepAmt = 0;
        } else if (newThrottleSleepAmt > MAX_THROTTLE_AMT) {
            throttleSleepAmt = MAX_THROTTLE_AMT;
        } else {
            throttleSleepAmt = newThrottleSleepAmt;
        }
    }

    /**
     * Move this Sprite to an absolute display position.
     * <p>
     * Move this Sprite horizontally to a new location specified as an X
     * coordinate argument.
     *
     * @param newX the X-coordinate of destination.
     * @see #home
     * @see #setxy
     * @see #sety
     */
    public void setx(float newX) throws AbortException {
        setx((double) newX);
    }

    public void setx(int newX) throws AbortException {
        setx((double) newX);
    }

    public void setx(long newX) throws AbortException {
        setx((double) newX);
    }

    public void setx(double newX) throws AbortException {
        TGPoint p2 = new TGPoint(newX, curPoint.yDoubleValue());
        if (canvas != null && penDown) {
            canvas.addGraphOp(new TGLineOp(curPoint, p2, curColor, curPenSize, penPattern, typeLinecap));
        }
        if (canvas != null && (penDown || showTurtle)) {
            canvas.repaint();
        }
        setCurPoint(p2);
    }

    /**
     * Move this Sprite to an absolute display position.
     * <p>
     * Move this Sprite to the x and y coordinates provided as arguments.
     *
     * @param newX the X-coordinate of destination.
     * @param newY the Y-coordinate of destination.
     * @see #home
     * @see #setx
     * @see #sety
     */
    public void setxy(double newX, double newY) throws AbortException {
        setxy(new TGPoint(newX, newY));
    }

    public void setxy(float newX, float newY) throws AbortException {
        setxy(new TGPoint(newX, newY));
    }

    public void setxy(int newX, int newY) throws AbortException {
        setxy(new TGPoint(newX, newY));
    }

    public void setxy(long newX, long newY) throws AbortException {
        setxy(new TGPoint(newX, newY));
    }

    /**
     * Move this Sprite to an absolute display position.
     * <p>
     * Move this Sprite to the x and y coordinates provided in the TGPoint
     * parameter.
     *
     * @param newPt a TGPoint objext containing the X-coordinate and
     * Y-coordinate of destination.
     * @see #home
     * @see #setx
     * @see #sety
     */
    public void setxy(TGPoint newPt) throws AbortException {
        if (curPoint.equals(newPt)) {
            return;
        }
        if (canvas != null && penDown) {
            canvas.addGraphOp(new TGLineOp(curPoint, newPt, curColor, curPenSize, penPattern, typeLinecap));
        }
        if (canvas != null && (penDown || showTurtle)) {
            canvas.repaint();
        }
        setCurPoint(newPt);
    }

    /**
     * Move this Sprite to an absolute display position.
     * <p>
     * Move this Sprite vertically to a new location specified as an Y
     * coordinate argument.
     *
     * @param newY the Y-coordinate of destination.
     * @see #home
     * @see #setx
     * @see #setxy
     */
    public void sety(float newY) throws AbortException {
        sety((double) newY);
    }

    public void sety(int newY) throws AbortException {
        sety((double) newY);
    }

    public void sety(long newY) throws AbortException {
        sety((double) newY);
    }

    public void sety(double newY) throws AbortException {
        TGPoint p2 = new TGPoint(curPoint.xDoubleValue(), newY);
        if (canvas != null && penDown) {
            canvas.addGraphOp(new TGLineOp(curPoint, p2, curColor, curPenSize, penPattern, typeLinecap));
        }
        if (canvas != null && (penDown || showTurtle)) {
            canvas.repaint();
        }
        setCurPoint(p2);
    }

    /**
     * Return true if this Sprite's image is being displayed on the graphics
     * canvas, else return false.
     * <p>
     * @see #hideturtle
     * @see #ht
     * @see #showturtle
     * @see #st
     */
    public boolean shownp() {
        return showTurtle;
    }

    /**
     * Show this Sprite; make it visible.
     * <P>
     * Long name for <B>st()</B>. Both spellings need to provided for
     * compatibility.
     * <P>
     * @see #hideturtle
     * @see #ht
     * @see #st
     */
    public void showturtle() {
        st();
    }

    /**
     * Show this Sprite; make it visible.
     * <P>
     * Abbreviation for <B>showturtle()</B>. Both spellings need to provided for
     * compatibility.
     * <P>
     * @see #hideturtle
     * @see #ht
     * @see #showturtle
     */
    public void st() {
        if (!showTurtle) {
            if (spritePixels.setSpriteColor(curColor)) {
                spriteImage = null;
            }
            if (spritePixels.setSpriteHeading(curHeading)) {
                spriteImage = null;
            }
            if (canvas != null) {
                canvas.addSprite(this);
                canvas.repaint();
            }
            showTurtle = true;
        }
    }

    /**
     * Draw the current Sprite Image onto the graphics canvas.
     */
    public void stamp() {
        spritePixels.setSpriteColor(curColor);
        spritePixels.setSpriteHeading(curHeading);
        int imgSideSiz = getImageSideSize();
        double leftX = xcor() - imgSideSiz / 2;
        double topY = ycor() + imgSideSiz / 2;
        TGPoint topLeftPoint = new TGPoint(leftX, topY);
        int[] srcPix = spritePixels.getPixels();
        int[] shapePixels = new int[srcPix.length];
        System.arraycopy(srcPix, 0, shapePixels, 0, srcPix.length);
        PixelRectangle pixRect = new PixelRectangle(shapePixels, imgSideSiz);
        canvas.addGraphOp(new TGSetPixelsOp(topLeftPoint, pixRect));
        canvas.repaint();
    }

    /**
     * Return String representation of this Sprite consisting of lots of its
     * state; to be used when debugging.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer("[Sprite@");
        sb.append(curPoint.toString());
        sb.append(" heading=");
        sb.append(curHeading);
        if (showTurtle) {
            sb.append(" visible, ");
        } else {
            sb.append(" invisible, ");
        }
        sb.append(" with pen ");
        if (penDown) {
            sb.append("down");
        } else {
            sb.append("up");
        }
        sb.append(" and width=");
        sb.append(curPenSize);
        sb.append(']');
        return sb.toString();
    }

    /**
     * Return this Sprite's X-coordinate
     *
     * @see #setxy
     * @see #setx
     * @see #sety
     * @see #ycor
     */
    public double xcor() {
        return curPoint.xDoubleValue();
    }

    /**
     * Return this Sprite's Y-coordinate
     *
     * @see #setxy
     * @see #setx
     * @see #sety
     * @see #xcor
     */
    public double ycor() {
        return curPoint.yDoubleValue();
    }

} // end class Sprite
