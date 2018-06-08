package com.guyhaas.tg;

class TGWexample7 extends TurtleGraphicsWindow {

    private int[] PASTELS = {WHITE, GRAY, WHEAT, PALEGREEN, LIGHTBLUE, KHAKI, PINK};

    public TGWexample7() {
        super(655, 675);
    }

    private double fiftyEightPercent(double num) {
        return 58.0 * (num / 100.0);
    }

    private double fortyOnePercent(double num) {
        return 41.0 * (num / 100.0);
    }

    private double isoTriBaseLength(double vertexAngle, double legLength) {
        double halfBase = legLength * Math.sin(vertexAngle / 2.0);
        return 2 * halfBase;
    }

    private int randomColor(int[] colorNumArray) {
        return colorNumArray[(int) Math.floor(colorNumArray.length * Math.random())];
    }

    private void drawCircle(TGPoint centerPos, double radius) {
        penup();
        setpos(centerPos);
        pendown();
        arc(360.0, radius);
    }

    private void fillCircle(TGPoint centerPos, double radius, int color) {
        setpencolor(BLACK);
        drawCircle(centerPos, radius);
        penup();
        setpos(centerPos);
        setpencolor(color);
        fill();
    }

    private void drawCirclesInCircle(int level, TGPoint centerPos, double radius) {
        fillCircle(centerPos, radius, randomColor(PASTELS));
        if (level > 1) {
            drawCirclesInCircle(level - 1,
                    new TGPoint(centerPos.x, centerPos.y + fiftyEightPercent(radius)),
                    fortyOnePercent(radius));
            drawCirclesInCircle(level - 1,
                    new TGPoint(centerPos.x + fiftyEightPercent(radius), centerPos.y),
                    fortyOnePercent(radius));
            drawCirclesInCircle(level - 1,
                    new TGPoint(centerPos.x, centerPos.y - fiftyEightPercent(radius)),
                    fortyOnePercent(radius));
            drawCirclesInCircle(level - 1,
                    new TGPoint(centerPos.x - fiftyEightPercent(radius), centerPos.y),
                    fortyOnePercent(radius));
        }
    }

    public static void main(String[] args) {
        TGWexample7 me = new TGWexample7();
        me.home();
        me.clean();
        me.hideturtle();
        me.setpensize(1);
        me.drawCirclesInCircle(6, new TGPoint(0, 0), 300);
    }

} // end class TGWexample7
