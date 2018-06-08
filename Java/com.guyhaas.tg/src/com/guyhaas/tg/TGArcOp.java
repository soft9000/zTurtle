package com.guyhaas.tg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;

/**
 * This class implements a TurtleGraphics ARC graphics operation.
 * <p>
 * @author Guy Haas
 */
public class TGArcOp implements TGGraphicsOp {

    //
    // Symbolic Constants
    // -------- ---------

    /*
    * Class name as a String.
     */
    private static final String CLASS_NAME = "TGArcOp";

    /*
    * Number of degrees in one radian
     */
    private static final double DEGREES_PER_RADIAN = 180.0 / Math.PI;

    //
    // Class Fields
    // ----- ------

    /*
    * Origin of the arc
     */
    private TGPoint origin;


    /*
    * Radius of the arc
     */
    private double radius;


    /*
    * Top-left corner of the bounding rectangle
     */
    private TGPoint topLeftCorner;


    /*
    * Bounding rectangle's width and height
     */
    private int width;
    private int height;

    /*
    * Start angle in degrees; 0 degrees is positive X axis,
    * degrees increase counter-clockwise
     */
    private double startAngle;

    /*
    * Angular extent of the arc, relative to startAngle, in
    * degrees as with startAngle
     */
    private double arcAngle;

    /*
    * Pen color
     */
    private Color penColor;

    /*
    * Pen size - width of arc
     */
    private int penSize;

    /*
    * Dashed line pattern.  If null, a solid line is drawn.
    * Otherwise, even elements of the array are draw lengths
    * and odd elements are skip lengths.
     */
    private final float[] penPattern;

    //
    // Constructors
    // ------------
    /**
     * Return a TGArcOp object, a TGGraphicsOp, which can be queued for TGCanvas
     * to paint.
     *
     * @param origin center of the arc, a TGPoint
     * @param radius radius of the arc
     * @param startAng Start angle in radians; 0 is along the positive X axis,
     * radians increase counter-clockwise
     * @param arcAng Angular extent of the arc, relative to startAngle, in
     * radians as with startAngle
     * @param color pen color
     * @param penSz pen size, width of the arc
     */
    public TGArcOp(TGPoint origin, double radius, double startAng, double arcAng, Color color, int penSz) {
        this(origin, radius, startAng, arcAng, color, penSz, null);
    }

    /**
     * Return a TGArcOp object, a TGGraphicsOp, which can be queued for TGCanvas
     * to paint.
     *
     * @param origin center/origin of the arc, a TGPoint
     * @param radius radius of the arc
     * @param strtAng Start angle in radians; 0 is along the positive X axis,
     * radians increase counter-clockwise
     * @param arcAng Angular extent of the arc, relative to startAngle, in
     * radians as with startAngle
     * @param color pen color
     * @param penSz pen size, width of the arc
     * @param penPat dashed line pattern. even elements are draw amounts, odd
     * elements are skip amounts
     */
    /*
    * From Java documentation of Graphics.arc(): "The resulting arc covers
    * an area width + 1 pixels wide by height + 1 pixels tall." So, this +1
    * thing has to be accounted for.
     */
    public TGArcOp(TGPoint origin, double radius, double strtAng, double arcAng,
            Color color, int penSz, float[] penPat) {
        this.origin = origin;
        topLeftCorner = new TGPoint(origin.xDoubleValue() - radius,
                origin.yDoubleValue() + radius);
        this.radius = radius;
        width = height = (int) Math.round((2.0 * radius) - 1.0);
        startAngle = strtAng * DEGREES_PER_RADIAN;
        arcAngle = arcAng * DEGREES_PER_RADIAN;
        penColor = color;
        penSize = penSz;
        penPattern = penPat;
    }

    //
    // TGGraphicsOp Interface Methods
    // ------------ --------- -------
    /**
     * Perform an operation on (do something) with the provided Image and return
     * a Rectangle which is a cliprect for the area of the Image that was
     * changed.
     *
     * @param inMemoryImage the object on which to draw
     *
     * @return Rectangle bounds of the pixels in the Image that were changed
     */
    /*
    * From Java documentation of Graphics.arc(): "The resulting arc covers
    * an area width + 1 pixels wide by height + 1 pixels tall." So, this +1
    * thing has to be accounted for.
    *
    * Due to rounding issues, the clipping rectangle's width and
    * height are increased by two pixels and the origin is up and
    * left a single pixel.
     */
    public Rectangle doIt(BufferedImage inMemoryImage) {
        int canvasWidth = inMemoryImage.getWidth();
        if (canvasWidth <= 0) {
            return null;
        }
        int canvasHeight = inMemoryImage.getHeight();
        if (canvasHeight <= 0) {
            return null;
        }
        Graphics2D g2 = (Graphics2D) inMemoryImage.getGraphics();
        g2.setColor(penColor);
        BasicStroke bs = null;
        if (penPattern == null) {
            bs = new BasicStroke((float) penSize, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
        } else {
            bs = new BasicStroke((float) penSize, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, penPattern, 0);
        }
        g2.setStroke(bs);
        double imageLeftX = topLeftCorner.imageX((double) canvasWidth);
        double imageTopY = topLeftCorner.imageY((double) canvasHeight);
        Arc2D arc = new Arc2D.Double(Arc2D.OPEN);
        arc.setFrame(imageLeftX, imageTopY, radius * 2.0, radius * 2.0);
        arc.setAngleStart(startAngle);
        arc.setAngleExtent(arcAngle);
        Rectangle clipRect = new Rectangle((int) (Math.floor(imageLeftX - (penSize / 2.0))) - 1,
                (int) (Math.floor(imageTopY - (penSize / 2.0))) - 1,
                (int) (Math.ceil(((radius * 2.0) + penSize))) + 2,
                (int) (Math.ceil(((radius * 2.0) + penSize))) + 2);
        g2.setClip(clipRect.x, clipRect.y, clipRect.width, clipRect.height);
        g2.draw(arc);
        g2.dispose();
        return clipRect;
    }

} // end class TGArcOp
