package com.guyhaas.tg;

class TGWexample1 extends TurtleGraphicsWindow {

    public TGWexample1() {
        super(600, 600);
    }

    void box(int sideSize) {
        for (int i = 4; i > 0; i--) {
            forward(sideSize);
            right(90);
        }
    }

    void drawSomething() {
        setpensize(1);
        home();
        setheading(NORTH);
        // draw 30 boxes while rotating the turtle around itself
        for (int count = 30; count > 0; count--) {
            box(180);
            right(360 / 30);
        }
        penup();
        home();
        setheading(NORTH);
        // fill half of resulting triangles with ascending color numbers
        right(1.5);
        for (int colorNumber = 1; colorNumber < 32; colorNumber++) {
            if (colorNumber == WHITE) {
                continue;
            }
            forward(90);
            setpencolor(colorNumber);
            fill();
            home();
            right(360 / 30);
        }
        home();
        setheading(NORTH);
        // fill other half with descending color numbers
        right(1.5 + 360 / 60);
        for (int colorNumber = 31; colorNumber > 0; colorNumber--) {
            if (colorNumber == WHITE) {
                continue;
            }
            forward(90);
            setpencolor(colorNumber);
            fill();
            home();
            right(360 / 30);
        }
    }

    public static void main(String[] args) {
        TGWexample1 me = new TGWexample1();
        me.hideturtle();
        me.drawSomething();
    }

} // end class TGWexample1
