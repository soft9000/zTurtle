package com.guyhaas.tg;

class TGWexample5 extends TurtleGraphicsWindow {

    public TGWexample5() {
        super(400, 280);
    }

    private static final int LINE_LENGTH = 250;
    private static final int LINE_LEFT_X = -LINE_LENGTH / 2;
    private static final int LINE_SPACING = 60;
    private static final int LINE_WIDTH = 30;

    private static final int DASHED_LINE_HEIGHT = 2 * LINE_SPACING + 2 * LINE_WIDTH;

    private void drawLine(int yCor, int typeLinecap) {
        setpensize(LINE_WIDTH);
        setpencolor(GRAY);
        setlinecap(typeLinecap);
        penup();
        setxy(LINE_LEFT_X, yCor);
        pendown();
        setheading(EAST);
        forward(LINE_LENGTH);
    }

    private void drawLines() {
        // top line
        drawLine(LINE_SPACING, LINECAP_BUTT);
        // middle line
        drawLine(0, LINECAP_ROUND);
        // bottom line
        drawLine(-LINE_SPACING, LINECAP_SQUARE);
    }

    private void drawDashedLines() {
        setpensize(1);
        setpencolor(BLACK);
        setlinecap(LINECAP_BUTT);
        setPenPattern(3);
        penup();
        setxy(LINE_LEFT_X, -(DASHED_LINE_HEIGHT / 2));
        pendown();
        setheading(NORTH);
        forward(DASHED_LINE_HEIGHT);
        penup();
        setxy(LINE_LENGTH / 2, -(DASHED_LINE_HEIGHT / 2));
        pendown();
        forward(DASHED_LINE_HEIGHT);
    }

    private void labelLines() {
        setpencolor(WHITE);
        setlabelfont(SANS_SERIF_BOLD);
        setlabelheight(LINE_WIDTH / 2);
        // top line
        String label = "BUTT";
        penup();
        setxy(-(labelwidth(label) / 2), (LINE_SPACING - LINE_WIDTH / 4));
        label(label);
        // middle line
        label = "ROUND";
        setxy(-(labelwidth(label) / 2), -(LINE_WIDTH / 4));
        label(label);
        // bottom line
        label = "SQUARE";
        penup();
        setxy(-(labelwidth(label) / 2), (-LINE_SPACING - LINE_WIDTH / 4));
        label(label);
    }

    public static void main(String[] args) {
        TGWexample5 me = new TGWexample5();
        me.home();
        me.clean();
        me.hideturtle();
        me.drawLines();
        me.labelLines();
        me.drawDashedLines();
    }

} // end class TGWexample5
