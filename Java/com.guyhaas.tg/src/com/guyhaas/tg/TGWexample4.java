package com.guyhaas.tg;

class TGWexample4 extends TurtleGraphicsWindow {

    public TGWexample4() {
        super(320, 320);
    }

    private void drawArcs() {
        penup();
        setxy(-100, -100);
        setpensize(1);
        setpencolor(BLACK);
        setheading(NORTH);
        pendown();
        for (int i = 0; i < 4; i++) {
            forward(200);
            right(90);
        }
        penup();
        home();
        pendown();
        setheading(NORTH);
        setpensize(40);
        setpencolor(YELLOW);
        arc(90, 100);
        setheading(EAST);
        setpensize(30);
        setpencolor(ORANGE);
        arc(90, 100);
        setheading(SOUTH);
        setpensize(20);
        setpencolor(CHOCOLATE);
        arc(90, 100);
        setheading(WEST);
        setpensize(10);
        setpencolor(BROWN);
        arc(90, 100);
        setpensize(1);
        setpencolor(BLACK);
        arc(360, 100);
    }

    public static void main(String[] args) {
        TGWexample4 me = new TGWexample4();
        me.home();
        me.clean();
        me.hideturtle();
        me.drawArcs();
    }

} // end class TGWexample4
