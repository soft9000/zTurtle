package com.guyhaas.tg;

class TGWexample2 extends TurtleGraphicsWindow {

    public TGWexample2() {
        super(600, 600);
    }

    public void tgKeyReleased(int keyNum) {
        switch (keyNum) {
            case TGKeyHandler.DOWN_ARROW:
                back(5);
                break;
            case TGKeyHandler.LEFT_ARROW:
                left(5);
                break;
            case TGKeyHandler.RIGHT_ARROW:
                right(5);
                break;
            case TGKeyHandler.UP_ARROW:
                forward(5);
                break;
        }
    }

    public void tgKeyTyped(char keyChar) {
        switch (keyChar) {
            case 'd':
                setpencolor(BLACK);
                pendown();
                break;
            case 'u':
                setpencolor(WHITE);
                penup();
                break;
        }
    }

    public void tgMouseClicked(int x, int y) {
        System.out.println("tgMouseClicked( " + x + ", " + y + ")");
        setxy(x, y);
    }

    public static void main(String[] args) {
        TGWexample2 me = new TGWexample2();
        me.setpensize(8);
        me.setshape(ARROW);
    }

} // end class TGWexample2
