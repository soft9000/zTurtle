# Cricular, Reflexive, Polylogical Symmetries (CRPS)
#
# 2020/06/05, Rev 1.0
# -Randall Nagy

import turtle

class Poly360(turtle.Turtle):
    ''' Alternate between rendering a LEFT and a RIGHT
        colorized-polygon so as to visualize a single,
        closed, fractal image - whose number of shapes
        are circularly-reated to the number of the sides
        in the polygon.
    '''

    def __init__(self, sides=4, line=12, eclr='red', oclr='blue'):
        super().__init__()
        self.line = line
        self._sides = sides
        self._frac = 360/sides
        self.ecolor = eclr
        self.ocolor = oclr

    def _left(self, depth):
        if depth < 0:
            return
        depth -= 1
        self.color(self.ocolor)
        for arc in range(self._sides):
            self.left(self._frac)
            self.forward(self.line)
        return depth

    def _right(self, depth):
        if depth < 0:
            return
        depth -= 1
        self.color(self.ecolor)
        for arc in range(self._sides):
            self.right(self._frac)
            self.forward(self.line)
        return depth

    def draw(self, xpos, ypos):
        ''' Rendering is fast when using same colors.
            Automatically uses .draw3D(), when best.
        '''
        if self.ecolor != self.ocolor:
            return self.draw3D(xpos, ypos) # Concoct z-order
        self.up()
        self.goto(xpos, ypos)
        self.down()
        depth = self._sides
        while depth:
            self._right(depth)
            depth = self._left(depth)
            self.forward(self.line)
            self.right(self._frac)

    def draw3D(self, xpos, ypos):
        ''' Simply tell us where to draw. The rest is fractastic. '''
        self.up()
        self.goto(xpos, ypos)
        self.down()
        depth = self._sides
        while depth: # Base colorization (z+0)
            depth = self._left(depth)
            self.up()
            self.forward(self.line)
            self.right(self._frac)
            self.down()
        depth = self._sides
        while depth: # Real colorization (z+1)
            depth = self._right(depth)
            self.up()
            self.forward(self.line)
            self.right(self._frac)
            self.down()
        
    @staticmethod
    def ShapeShow(speed=0, pen=3, turtle_=False, color1='red', color2='green'):
        ''' The test pattern draws many polygons to
        demonstrate an observed principal of circular,
        geometric, side-to-circle reflexitivity.

        I've never seen the relationship expressed
        before. Anyone else? '''
        
        YSTART = 200
        xstart = -200; ystart = YSTART; offs = 0
        for which, sides in enumerate(range(3, 12), 1):
            fract = Poly360(sides, 16, color1, color2)
            if not turtle_:
                fract.ht()
            fract.speed(speed)
            fract.pensize(pen)
            xpos = xstart 
            cell = 200 * offs; ypos = ystart - cell
            fract.draw(xpos - 60, ypos + 90)
            fract.ht()
            fract.up();fract.goto(xpos - 90, ypos - 50)
            fract.write(f"{sides:02} sides",
                        font=("Arial", 14, "bold"))
            if which % 3 == 0:
                xstart += 200
                ystart = YSTART
                offs = 0
            else:
                offs += 1
        
    @staticmethod
    def FastShapeShow(speed=0, pen=3):
        ''' Fast-draw, without z-order colorization. '''
        Poly360.ShapeShow(speed, pen, False, 'red', 'red')

    @staticmethod
    def BestOfShow(sides=8, speed=0, pen=3, seg=60):
        ''' Review a single shape study, in-motion. '''
        fract = Poly360(sides, seg, 'blue', 'purple')
        fract.speed(speed)
        fract.pensize(pen)
        fract.ht()
        fract.draw(-seg / 2, seg / 2)


if __name__ == '__main__':
    # Poly360.FastShapeShow(-1, 1)
    # Poly360.ShapeShow(6, 2, True)
    Poly360.BestOfShow(8, 9, 5)
