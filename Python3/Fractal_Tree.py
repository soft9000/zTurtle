#!/usr/bin/python3

# Classic Fractal
# Colorizations just make it funner-er?

import turtle

colors = (
    "teal",
    "green",
    "red",
    "brown"
    )

def Draw(level):
    if level > 4:
        turtle.pensize(level/10)
        color = int(level % len(colors))
        turtle.color(colors[color])
        turtle.forward(level)
        turtle.right(33)
        Draw(level *.7)
        turtle.left(66)
        Draw(level *.7)
        turtle.right(33)
        turtle.back(level)

turtle.speed(9)
turtle.setheading(90)
turtle.up();turtle.goto(0,-300);turtle.down()
Draw(50)
