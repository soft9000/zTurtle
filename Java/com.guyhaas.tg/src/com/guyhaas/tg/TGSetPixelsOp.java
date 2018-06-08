package com.guyhaas.tg;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

/**
 * TGSetPixelsOp provides TurtleGraphics with a SETPIXELS graphics operation.
 * <p>
 * TGSetPixelsOp should be thought of as a BitBlt, a block transfer of pixels
 * from a source, an array of ARGB pixel values, to the BufferedImage being
 * displayed.
 * <p>
 * Invoking Turtle methods which paint pixels on the graphics canvas does not
 * result in immediate changes to TG's in-memory BufferedImage, only that they
 * will be performed soon.
 * <p>
 * @author Guy Haas
 */
public class TGSetPixelsOp implements TGGraphicsOp {

    //
    // Symbolic Constants
    // -------- ---------
    private static final String CLASS_NAME = "TGSetPixelsOp";

    //
    // Class Fields
    // ----- ------

    /*
    * PixRect to be painted into provided BufferedImage
     */
    private TGPoint pixRectTopLeft; // top-left corner of the pixel rectangle in TurtleSpace
    private int pixRectWidth;       // dimension for the rectangle (height is computed)
    private int[] pixRectPixels;    // array of pixels to be merged onto graphics canvas

    //
    // Constructors
    // ------------
    /**
     * Instantiate a graphics operation to set a rectangular area of the
     * graphics canvas to provided pixel values.
     *
     * @param topLeft Left-most x, top-most y, coordinates of rectangle of
     * pixels to be replaced, destination.
     * @param width Number of pixels constituting one row.
     * @param pixels Array of the source pixels to be stored.
     */
    public TGSetPixelsOp(TGPoint topLeft, int width, int[] pixels) {
        pixRectTopLeft = topLeft;
        pixRectWidth = width;
        pixRectPixels = pixels;
    }

    /**
     * Instantiate a graphics operation to set a rectangular area of the
     * graphics canvas to provided pixel values.
     *
     * @param topLeft Left-most x, top-most y, coordinates of rectangle of
     * pixels to be replaced - destination.
     * @param pixRect PixelRectangle which includes an array of the pixels to be
     * stored and the width (number of columns) in the rectangle.
     */
    public TGSetPixelsOp(TGPoint topLeft, PixelRectangle pixRect) {
        pixRectTopLeft = topLeft;
        pixRectWidth = pixRect.width;
        pixRectPixels = pixRect.pixels;
        //printPixels( 0, 0, 20, 10, pixRectPixels, pixRectWidth );
    }

    //
    // TGGraphicsOp Interface Methods
    // ------------ --------- -------
    /**
     * Perform a read/modify/write cycle on a rectangular area of pixels in the
     * provided inMemoryImage.
     *
     * @param inMemoryImage Where my pixRectPixels[] should be stored into.
     */
    public Rectangle doIt(BufferedImage inMemoryImage) {
        int imgWidth = inMemoryImage.getWidth();
        int imgHeight = inMemoryImage.getHeight();
        // convert TGPoint pixRectTopLeft from TurtleSpace to x,y coordinates
        // within the provided inMemoryImage
        int imageX = pixRectTopLeft.imageX(imgWidth);
        if (imageX < 0 || imageX >= imgWidth) {
            return null;
        }
        int imageY = pixRectTopLeft.imageY(imgHeight);
        if (imageY < 0 || imageY >= imgHeight) {
            return null;
        }
        // compute bounds for grab of pixels
        int imgRectWidth = pixRectWidth;
        if ((imageX + pixRectWidth) > imgWidth) {
            imgRectWidth = imgWidth - imageX;
        }
        int imgRectHeight = pixRectPixels.length / pixRectWidth;
        if ((imageY + imgRectHeight) > imgHeight) {
            imgRectHeight = imgHeight - imageY;
        }
        int pixRectIdx = 0;
        for (int y = imageY; y < imageY + imgRectHeight; y++) {
            for (int x = imageX; x < imageX + imgRectWidth; x++) {
                int pixel = pixRectPixels[pixRectIdx++];
                if (pixRectIdx >= pixRectPixels.length) {
                    pixRectIdx = 0;
                }
                if ((pixel & 0xFF000000) != 0) {
                    inMemoryImage.setRGB(x, y, pixel);
                }
            }
        }
        return new Rectangle(imageX, imageY, imgRectWidth, imgRectHeight);
    }

    //
    // Methods with scope limited to this class
    // ------- ---- ----- ------- -- ---- -----

    /*
    * DEBUG support...
    * print pixels from an array
    * x,y is top left pixel (0,0 is first pixel in array)
    * wd,ht are the dimensions of the rectangle of pixels to be printed
    * pixels is the array
    * pixelsWd is the number of pixels in the array that make up one row
     */
    //private void printPixels( int x, int y, int wd, int ht, int[] pixels, int pixelsWd )
    //{
    //   int column = x;
    //   int lastColumn = column + (wd-1);
    //   if ( lastColumn >= pixelsWd )
    //      lastColumn = pixelsWd-1;
    //   int pixelsHt = pixels.length / pixelsWd;
    //   int line = y;
    //   int lastLine = line + (ht-1);
    //   if ( lastLine >= pixelsHt )
    //      lastLine = pixelsHt-1;
    //   System.out.print( "printPixels(): x=" + x + ", y=" + y + ", wd=" + wd );
    //   System.out.println( ", ht " + ht + ", pixelsWd=" + pixelsWd );
    //   while ( line <= lastLine )
    //   {
    //      for ( int idx=column; idx <= lastColumn; idx++ )
    //      {
    //         int pixel = pixels[line*pixelsWd + idx];
    //         int alpha = (pixel & 0xFF000000) >>> 24;
    //         if ( alpha < 100 )
    //            System.out.print( " " );
    //         if ( alpha < 10 )
    //            System.out.print( " " );
    //         System.out.print( alpha + " " );
    //         int red = (pixel & 0xFF0000) >>> 16;
    //         if ( red < 100 )
    //            System.out.print( " " );
    //         if ( red < 10 )
    //            System.out.print( " " );
    //         System.out.print( red + " " );
    //         int green = (pixel & 0xFF00) >>> 8;
    //         if ( green < 100 )
    //            System.out.print( " " );
    //         if ( green < 10 )
    //            System.out.print( " " );
    //         System.out.print( green + " " );
    //         int blue = pixel & 0xFF;
    //         if ( blue < 100 )
    //            System.out.print( " " );
    //         if ( blue < 10 )
    //            System.out.print( " " );
    //         System.out.print( blue );
    //         if ( idx < lastColumn )
    //            System.out.print( " | " );
    //      }
    //      System.out.println( "" );
    //      line++;
    //   }
    //
    //} // end printPixels()
} // end class TGSetPixelsOp
