# Fractal Cubes
# 2020/06/05, Rev 1.0

# Refactored:
#   https://gist.github.com/viebel/5349bcca144c41b8f83af39079bf59ad

import turtle

class Boxed(turtle.Turtle):

    def __init__(self, line=12):
        super().__init__()
        self.line = line

    def _left(self, depth):
        if depth < 0:
            return
        depth -= 1
        self.color('blue')
        self.left(90)
        self._right(depth)
        self.forward(self.line)
        self.right(90)
        self._left(depth)
        self.forward(self.line)
        self._left(depth)
        self.right(90)
        self.forward(self.line)
        self._right(depth)
        self.left(90)

    def _right(self, depth):
        if depth < 0:
            return
        depth -= 1
        self.color('red')
        self.right(90)
        self._left(depth)
        self.forward(self.line)
        self.left(90)
        self._right(depth)
        self.forward(self.line)
        self._right(depth)
        self.left(90)
        self.forward(self.line)
        self._left(depth)
        self.right(90)

    def draw(self, depth=4):
        self.up()
        self.goto(-200, 200)
        self.down()
        self._right(depth)


if __name__ == '__main__':
    fract = Boxed()
    fract.speed(0)
    fract.draw(3)
