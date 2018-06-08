#!/usr/bin/python3

# Classic Fractal
# Sierpiński Arrowhead Curve

import turtle

angle = 360/6
def Curve(lines, scale, length=3):
    if lines > 0:
        lines -= 1
        Curve(lines, -scale, length)
        turtle.left(scale * angle)
        Curve(lines, scale, length)
        turtle.left(scale * angle)
        Curve(lines, -scale, length)
    else:
        turtle.forward(length)


turtle.speed(9)
turtle.hideturtle()
turtle.up();turtle.setpos(0, -300);turtle.down()
turtle.left(angle)
Curve(7, 1, 5)
