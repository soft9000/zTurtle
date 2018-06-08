package com.guyhaas.tg;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

/**
 * This class implements a TurtleGraphics LABEL graphics operation.
 * <p>
 * @author Guy Haas
 */
public class TGLabelOp implements TGGraphicsOp {

    private Color color;
    private Font font;
    private String text;
    private TGPoint where;

    //
    // constructor
    //
    public TGLabelOp(String label, TGPoint where, Font font, Color color) {
        this.color = color;
        this.font = font;
        this.text = label;
        this.where = where;
    }

    public Rectangle doIt(BufferedImage inMemoryImage) {
        int canvasWidth = inMemoryImage.getWidth();
        if (canvasWidth < 0) {
            return null;
        }
        int imageX = where.imageX(canvasWidth);
        int canvasHeight = inMemoryImage.getHeight();
        if (canvasHeight < 0) {
            return null;
        }
        int imageY = where.imageY(canvasHeight);
        Graphics g = inMemoryImage.getGraphics();
        g.setColor(color);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int crX = imageX;
        int crWidth = fm.stringWidth(text);
        int crHeight = fm.getHeight();
        int crY = imageY - fm.getMaxAscent();
        g.setClip(crX, crY, crWidth, crHeight);
        g.drawString(text, imageX, imageY);
        g.dispose();
        Rectangle clipRect = new Rectangle(crX, crY, crWidth, crHeight);
        return clipRect;
    }

} // end class TGLabelOp
