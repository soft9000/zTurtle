package com.guyhaas.tg;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.geom.Line2D;
import java.awt.Rectangle;

/**
 * TGLineOp provides a TurtleGraphics Line graphics Operation. A line of a
 * specified penWidth is drawn between two TurtleSpace points.
 *
 * @author Guy Haas
 */

/*
 * *NOTE*
 * The points of a TGLineOp object are set such that lines
 * are always drawn left to right (increasing X values). This
 * consistency is necessary to insure identical pixels are painted
 * for fat lines that should be identical lines, say erasing a fat
 * black line by drawing an identical white line over it, e.g.,
 *
 *     SETPENSIZE 3
 *     SETPENCOLOR 0 FORWARD 100
 *     WAIT 1000
 *     SETPENCOLOR 7 BACK 100
 *
 * For an angular line, rounding differences can cause the BACK to
 * generate a line that has a different width.  The above sequence
 * with the turtle heading at 45 degrees is a good example.
 */
public class TGLineOp implements TGGraphicsOp {

    //
    // Symbolic Constants
    // -------- ---------

    /*
    * Class name as a String.
     */
    private static final String CLASS_NAME = "TGLineOp";

    //
    // Class Data
    // ----- ----

    /*
    * Endpoints of this line segment.
    * endPt1 is always the left-most point, i.e., its X value is <= endPt2's X value.
     */
    public final TGPoint endPt1, endPt2;


    /*
    * Turtle heading  (radians -PI/2 through PI/2) for this
    * line segment from endPt1 to endPt2.
     */
    //private double heading;
    /*
    * Color and width of this line segment
     */
    private final Color penColor;
    private final int penWidth;


    /*
    * Dashed line pattern.  If null, a solid line is drawn.
    * Otherwise, even elements of the array are draw lengths
    * and odd elements are skip lengths.
     */
    private final float[] penPattern;


    /*
    * The type of ends on lines drawn. Lines the turtle draws can have
    * caps (decorations) on their ends. See java.awt.BasicStroke for
    * the CAP_xxx symbolic constants determining the types. This field
    * can be set with a TGLineOp constructor.
     */
    private int typeLineCap;

    // Constructors
    // ------------
    /**
     * Return a TGLineOp, a line drawing operation.
     *
     * Input points in the object may be swapped
     *
     * @param pt1 one end TGPoint of the line
     * @param pt2 other end TGPoint of the line
     * @param color AWT Color for line's pixels
     * @param width number of pixels for width of the line
     */
    public TGLineOp(TGPoint pt1, TGPoint pt2, Color color, int width) {
        this(pt1, pt2, color, width, null);
    }

    /**
     * Return a TGLineOp, a line drawing operation.
     *
     * Input points in the object may be swapped
     *
     * @param pt1 one end TGPoint of the line
     * @param pt2 other end TGPoint of the line
     * @param color AWT Color for line's pixels
     * @param width number of pixels for width of the line
     * @param lineCap type of endcaps on the line
     */
    public TGLineOp(TGPoint pt1, TGPoint pt2, Color color, int width, int lineCap) {
        this(pt1, pt2, color, width, null, lineCap);
    }

    /**
     * Return a TGLineOp, a line drawing operation.
     *
     * Input points in the object may be swapped
     *
     * @param pt1 one end TGPoint of the line
     * @param pt2 other end TGPoint of the line
     * @param color AWT Color for line's pixels
     * @param width number of pixels for width of the line
     * @param pattern dashed line pattern. even elements are draw amounts, odd
     * elements are skip amounts
     */
    public TGLineOp(TGPoint pt1, TGPoint pt2, Color color, int width, float[] pattern) {
        this(pt1, pt2, color, width, pattern, BasicStroke.CAP_BUTT);
    }

    /**
     * Return a TGLineOp, a line drawing operation.
     *
     * Input points in the object may be swa
     *
     * @param pt1 one end TGPoint of the line
     * @param pt2 other end TGPoint of the line
     * @param color AWT Color for line's pixels
     * @param width number of pixels for width of the line
     * @param pattern dashed line pattern. even elements are draw amounts, odd
     * elements are skip amounts
     * @param lineCap type of endcaps on the line
     */
    public TGLineOp(TGPoint pt1, TGPoint pt2, Color color, int width, float[] pattern, int lineCap) {
        if (pt1.xDoubleValue() <= pt2.xDoubleValue()) {
            this.endPt1 = pt1;
            this.endPt2 = pt2;
        } else {
            this.endPt1 = pt2;
            this.endPt2 = pt1;
        }
        this.penColor = color;
        this.penWidth = width;
        this.penPattern = pattern;
        this.typeLineCap = lineCap;
    }

    //
    // TGGraphicsOp interface methods
    // ------------ --------- -------
    /**
     * Draw the line defined by this TGLineOp object.
     *
     * @param inMemoryImage where to draw
     */
    /*
    * Due to rounding issues, the clipping rectangle's width and
    * height are increased by two pixels and the origin is up and
    * left a single pixel.
     */
    public Rectangle doIt(BufferedImage inMemoryImage) {
        int imageWidth = inMemoryImage.getWidth();
        if (imageWidth <= 0) {
            return null;
        }
        int imageHeight = inMemoryImage.getHeight();
        if (imageHeight <= 0) {
            return null;
        }
        Graphics2D g2 = (Graphics2D) inMemoryImage.getGraphics();
        g2.setColor(penColor);
        BasicStroke bs = null;
        if (penPattern == null) {
            bs = new BasicStroke((float) penWidth, typeLineCap, BasicStroke.JOIN_BEVEL);
        } else {
            bs = new BasicStroke((float) penWidth, typeLineCap, BasicStroke.JOIN_BEVEL, 0, penPattern, 0);
        }
        g2.setStroke(bs);
        double p1X = endPt1.imageX((double) imageWidth);
        double p1Y = endPt1.imageY((double) imageHeight);
        double p2X = endPt2.imageX((double) imageWidth);
        double p2Y = endPt2.imageY((double) imageHeight);
        Line2D line = new Line2D.Double(p1X, p1Y, p2X, p2Y);
        Rectangle clipRect = new Rectangle((int) (Math.floor(p1X - (penWidth / 2.0))) - 1,
                (int) (Math.floor((p1Y < p2Y ? p1Y : p2Y) - (penWidth / 2.0))) - 1,
                (int) (Math.ceil(Math.abs(p1X - p2X) + penWidth)) + 2,
                (int) (Math.ceil(Math.abs(p1Y - p2Y) + penWidth)) + 2);
        g2.setClip(clipRect.x, clipRect.y, clipRect.width, clipRect.height);
        g2.draw(line);
        g2.dispose();
        return clipRect;
    }


    /*
   * return String: "TGLineOp[color=xx, width=nn, endPt1={x,y}, endPt2={x,y}]"
     */
    public String toString() {
        return "TGLineOp[color=" + penColor + ",width=" + penWidth + ",endPt1=" + endPt1 + ",endPt2=" + endPt2 + "]";
    }

} // end class TGLineOp
