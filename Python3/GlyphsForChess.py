import turtle

turtle.setup(800, 800)
turtle.up()
turtle.goto(-300, 300)
for ss in range(9812, 9824):
    turtle.write(chr(ss), font=('Ariel', 48, 'normal'))
    turtle.forward(48)

