package com.guyhaas.tg;

/**
 * This class provides the mechanism to abort an Interpreter Thread. The source
 * of the event is an InterruptedException occurring while in:
 *
 * (1) an Object.wait() monitor state, (2) in a Thread.sleep() state, or (3) in
 * PixelGrabber.grabPixels().
 * <p>
 * @author Guy Haas
 */
public class AbortException extends Exception {

    // ----------
    // Local Data
    // ----------

    /*
    * String describing source (method) throwing AbortException
     */
    private String thrower;

    // -----------
    // Constructor
    // -----------
    /**
     * Return an AbortException object to be thrown.
     */
    public AbortException(String source) {
        super(source);
        thrower = source;
    }

} // end class AbortException
