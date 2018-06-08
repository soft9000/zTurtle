package com.guyhaas.tg;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

/**
 * Perform a TurtleGraphics FILL graphics operation.
 * <p>
 * @author Guy Haas
 */
public class TGFillOp implements TGGraphicsOp {

    //
    // Class Fields
    // ----- ------
    private Color fillColor;    // new Color pixels will be set to
    private TGPoint fillPoint;  // initial point of the FILL operation

    // Image that's the source of pixels to be examined and
    // conditionally changed to fillColor.
    private BufferedImage sourceImage;
    private int sourceHeight;
    private int sourceWidth;

    // maximum and minimum X and Y values computed so that we can
    // return the rectangle that encompasses the modified pixels
    private int maxFloodX;
    private int maxFloodY;
    private int minFloodX;
    private int minFloodY;

    //
    // Constructor
    // -----------
    /**
     * Instantiation of a flood fill graphics operation.
     * <p>
     * @param color New Color to paint pixels with.
     * @param point Location in TurtleSpace of first pixel, start of flood fill
     * process.
     */
    public TGFillOp(TGPoint point, Color color) {
        fillColor = color;
        fillPoint = point;
    }

    //
    // TGGraphicsOp Interface Methods
    // ------------ --------- -------
    /**
     * DoIt - a Flood Fill Operation. Get Color under the fillPoint and fill
     * this point and all its same-colored neighbors, and their neighbors,
     * etc... with this operation's fillColor.
     */
    // *NOTE* The point where the fill operation is to start may not be
    //        within current bounds of the graphics Image.  In this case,
    //        the operation can not be performed.
    //        
    public Rectangle doIt(BufferedImage graphicsImage) {
        sourceImage = graphicsImage;
        sourceWidth = graphicsImage.getWidth();
        sourceHeight = graphicsImage.getHeight();
        // convert TGPoint origin of the FILL operation to the x,y
        // coordinates within the provided Image
        int imageX = fillPoint.imageX(sourceWidth);
        if (imageX < 0 || imageX >= sourceWidth) {
            return null;
        }
        int imageY = fillPoint.imageY(sourceHeight);
        if (imageY < 0 || imageY >= sourceHeight) {
            return null;
        }
        int curRGB = graphicsImage.getRGB(imageX, imageY);
        curRGB &= 0xFFFFFF;
        int newRGB = fillColor.getRGB();
        newRGB &= 0xFFFFFF;
        if (curRGB == newRGB) {
            return null;
        }
        maxFloodX = minFloodX = imageX;
        maxFloodY = minFloodY = imageY;
        // perform the flood fill operation
        floodFill(imageX, imageY, sourceWidth, sourceHeight, curRGB, newRGB);
        int floodWidth = (maxFloodX + 1) - minFloodX;
        int floodHeight = (maxFloodY + 1) - minFloodY;
        return new Rectangle(minFloodX, minFloodY, floodWidth, floodHeight);

    } // end doIt()

    //
    // Methods with scope limited to the class
    //
    //
    // *NOTE* There is a simple, elegant algorithm for doing this. But it's
    //        recursive and exceeds the default stack size Java gives us...
    //        So... I've implemented a hybrid algorithm that's partially
    //        recursive, partially iterative.
    private void floodFill(int x, int y, int wd, int ht, int curRGB, int newRGB) {
        //int pixIdx;
        int pixel;

        int leftLimit = x, rightLimit = x;
        if (y < minFloodY) {
            minFloodY = y;
        }
        if (y > maxFloodY) {
            maxFloodY = y;
        }
        for (int i = x; i >= 0; i--) {
            pixel = sourceImage.getRGB(i, y);
            if ((pixel & 0xFFFFFF) != curRGB) {
                break;
            }
            sourceImage.setRGB(i, y, (pixel & 0xFF000000) | newRGB);
            leftLimit = i;
        }
        if (leftLimit < minFloodX) {
            minFloodX = leftLimit;
        }
        for (int i = x + 1; i < wd; i++) {
            pixel = sourceImage.getRGB(i, y);
            if ((pixel & 0xFFFFFF) != curRGB) {
                break;
            }
            sourceImage.setRGB(i, y, (pixel & 0xFF000000) | newRGB);
            rightLimit = i;
        }
        if (rightLimit > maxFloodX) {
            maxFloodX = rightLimit;
        }
        if (y > 0) {
            for (int i = leftLimit; i <= rightLimit; i++) {
                int newY = y - 1;
                pixel = sourceImage.getRGB(i, newY);
                if ((pixel & 0xFFFFFF) == curRGB) {
                    floodFill(i, newY, wd, ht, curRGB, newRGB);
                }
            }
        }
        if (y < ht - 1) {
            for (int i = leftLimit; i <= rightLimit; i++) {
                int newY = y + 1;
                pixel = sourceImage.getRGB(i, newY);
                if ((pixel & 0xFFFFFF) == curRGB) {
                    floodFill(i, newY, wd, ht, curRGB, newRGB);
                }
            }
        }

    } // end floodFill


    /*
    * DEBUG support...
    * Print pixels in TG-coordinate-based rectangle, i.e., x and y origins
    * are at center of the graphicsImage. X parameter is leftmostEdge of the
    * rectangle, Y is the topRow.
     */
    //private void printPixels( int x, int y, int wd, int ht )
    //{
    //   int xCenter = sourceWidth / 2;
    //   int column = xCenter + x;
    //   int lastColumn = column + (wd-1);
    //   int yCenter = sourceHeight / 2;
    //   int line = yCenter - y;
    //   int lastLine = line + (ht-1);
    //   System.out.print( "printPixels(): sourceWidth=" + sourceWidth + ", XCenter=" + xCenter );
    //   System.out.println( ", columns " + column + " .. " + lastColumn );
    //   System.out.print( "               sourceHeight=" + sourceHeight + ", YCenter=" + yCenter );
    //   System.out.println( ", lines " + line + " .. " + lastLine );
    //   while ( line <= lastLine )
    //   {
    //      if ( x >= 0 )
    //         System.out.print( " " );
    //      if ( x < 100 )
    //         System.out.print( " " );
    //      if ( x < 10 )
    //         System.out.print( " " );
    //      System.out.print( x + "," );
    //      if ( y >= 0 )
    //         System.out.print( " " );
    //      if ( y < 100 )
    //         System.out.print( " " );
    //      if ( y < 10 )
    //         System.out.print( " " );
    //      System.out.print( y + ": " );
    //      for ( int idx=column; idx <= lastColumn; idx++ )
    //      {
    //         System.out.print( " " );
    //         int pixel = pixels[line*sourceWidth + idx];
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
    //      y--;
    //   }
    //  
    //} // end printPixels()
} // end class TGFillOp
