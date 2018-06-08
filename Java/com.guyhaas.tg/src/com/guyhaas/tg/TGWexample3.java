package com.guyhaas.tg;

class TGWexample3 extends TurtleGraphicsWindow {

    private void drawScene() {
        boolean status = loadpicture("Clouds.jpg");
        if (!status) {
            System.out.println("loadpicture() failed");
            return;
        }
        status = loadshape("Parrot.png", FIRST_USER_SUPPLIED_SHAPE);
        if (!status) {
            System.out.println("loadshape() failed");
            return;
        }
        status = setshape(FIRST_USER_SUPPLIED_SHAPE);
        if (!status) {
            System.out.println("setshape() failed");
        }
    }

    public static void main(String[] args) {
        TGWexample3 me = new TGWexample3();
        me.drawScene();
    }

} // end class TGWexample3
