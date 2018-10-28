# Demo: How to draw a simple sine wave.
# 2018/10/28: Randall Nagy

import math
import turtle

line = 180
screen = turtle.Screen()
screen.bgcolor("black")
cursor = turtle.Turtle()
cursor.color("red")
cursor.goto(line, 0)
cursor.goto(-line, 0)
cursor.color("yellow")
cursor.shape("turtle")
for angle in range(360):
    y = math.sin(math.radians(angle))
    cursor.goto(angle - line, y * 100)

turtle.done()
