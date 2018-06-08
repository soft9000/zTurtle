package com.guyhaas.tg;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

/**
 * TGGraphicsOp is an interface that a class implements when it provides
 * TGCanvas with support for a graphics operation.
 * <p>
 * TGGraphicsOp objects are queued to be performed by TGCanvas. Think of them as
 * messages/requests sent from the Logo interpreter to TGCanvas.
 * <p>
 * As examples, queueing a TGLineOp is a request to draw a line on a provided
 * Image, queueing a TGLabelOp is a request to draw a String on a provided
 * Image.
 * <p>
 * @author Guy Haas
 */
public interface TGGraphicsOp {

    /**
     * Perform an operation on (do something) with the provided Image and return
     * a Rectangle which is a cliprect for the area of the Image that was
     * changed.
     *
     * @param image the object on which to draw
     * @return Rectangle bounds of the pixels in the Image that were changed
     */
    public Rectangle doIt(BufferedImage image);

} // end interface TGGraphicsOp
