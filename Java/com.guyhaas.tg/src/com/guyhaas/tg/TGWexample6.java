package com.guyhaas.tg;

class TGWexample6 extends TurtleGraphicsWindow {

    public TGWexample6() {
        super(500, 320);
    }

    private static final int LINE_LENGTH = 400;
    private static final int LINE_LEFT_X = -LINE_LENGTH / 2;
    private static final int LINE_SPACING = 70;
    private static final int LINE_WIDTH = 30;

    private void drawLine(int yCor, int segLen) {
        setpensize(LINE_WIDTH);
        setpencolor(LIGHTGRAY);
        setPenPattern(segLen);
        penup();
        setxy(LINE_LEFT_X, yCor);
        pendown();
        setheading(EAST);
        forward(LINE_LENGTH);
        setpensize(2);
        setpencolor(BLACK);
        penup();
        setxy(LINE_LEFT_X, yCor);
        pendown();
        forward(LINE_LENGTH);
    }

    private void drawLine(int yCor, int[] pattern) {
        setpensize(LINE_WIDTH);
        setpencolor(LIGHTGRAY);
        setPenPattern(pattern);
        penup();
        setxy(LINE_LEFT_X, yCor);
        pendown();
        setheading(EAST);
        forward(LINE_LENGTH);
        setpensize(2);
        setpencolor(BLACK);
        penup();
        setxy(LINE_LEFT_X, yCor);
        pendown();
        forward(LINE_LENGTH);
    }

    private void drawLines() {
        // top line
        drawLine(LINE_SPACING, 3);
        // middle line
        drawLine(0, new int[]{40, 10});
        // bottom line
        drawLine(-LINE_SPACING, new int[]{40, 5, 10, 5});
    }

    private void drawLabels() {
        setpencolor(BLACK);
        setlabelfont(SANS_SERIF_BOLD);
        setlabelheight(LINE_WIDTH / 3);
        // top line
        String label = "setPenPattern( 3 );";
        penup();
        setxy(-(labelwidth(label) / 2), (LINE_SPACING - LINE_SPACING / 2));
        label(label);
        // middle line
        label = "setPenPattern( new int[ ] {40, 10} );";
        penup();
        setxy(-(labelwidth(label) / 2), -(LINE_SPACING / 2));
        label(label);
        // bottom line
        label = "setPenPattern( new int[ ] {40, 5, 10, 5} );";
        penup();
        setxy(-(labelwidth(label) / 2), (-LINE_SPACING - LINE_SPACING / 2));
        label(label);
    }

    public static void main(String[] args) {
        TGWexample6 me = new TGWexample6();
        me.home();
        me.clean();
        me.hideturtle();
        me.drawLines();
        me.drawLabels();
    }

} // end class TGWexample6
