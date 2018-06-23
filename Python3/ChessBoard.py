'''
Part of an upcomming training offering on Trurle Graphics,
I thought that we would share ZBoard with our friends.

This ZBoard class is YA contribution to the Public Domain.
Feel free to use ZBoard in your own creations without care,
concerns, or constraint!

Enjoy,

Randall Nagy
r.a.nagy@soft9000.com

'''
import turtle

class ZBoard:
    
    ''' Drawing a classic chess / checker game board. '''
    
    def __init__(self, zturtle=turtle.Turtle(), length=500, w='white', b='gray'):
        self.robo = zturtle
        self.length = round(length)
        half = round(self.length/2)
        self.home = (-half, half)
        self.white = w
        self.black = b
        self.scale = 1
        self.robo.ht()
        self.speed()

    def _square(self, pos, length, fill):
        zangle = 90
        hold = self.robo.pos()
        self.goto(pos)
        if fill is not None:
            colors = self.robo.color()
            self.robo.color(colors[0], fill)
            self.robo.begin_fill()
        for side in range(4):
            self.robo.forward(length)
            self.robo.right(zangle)
        if fill is not None:
            self.robo.end_fill()
            self.robo.color(colors[0], colors[1])
        
    def _draw_checkers(self):
        self.goto(self.home)
        cell_length = round((self.length * self.scale) / 8) + 1
        coord = list(self.home)
        bEven = True
        for zy in range(8):
            for zx in range(8):
                zcolor = self.black
                if bEven:
                    zcolor = self.white
                    bEven = False
                else:
                    zcolor = self.black
                    bEven = True
                self._square(coord, cell_length, zcolor)
                coord[0] += cell_length
            coord[1] -= cell_length
            coord[0] = self.home[0]
            self.goto(coord)
            if bEven:
                bEven = False
            else:
                bEven = True
        self.goto(self.home)

    def speed(self, speed=0):
        ''' Set the speed for the next rendering. '''
        if speed is 0:
            global turtle
            turtle.tracer()
            turtle.delay(0)
        self.robo.speed(speed)

    def goto(self, pos):
        ''' Safe goto '''
        self.robo.up()
        self.robo.goto(pos)
        self.robo.down()       
    
    def draw(self, pos=None, scale=None):
        ''' Main entry point.
        Parameter 'pos' is the x, y coordiante, and
        'scale' is the scaling factor (see below for
        a basic test case - floating point okay.)
        '''
        if pos is not None:
            self.home = pos
        if scale is not None:
            self.scale = scale
        self._draw_checkers()



if __name__ == '__main__':
    board = ZBoard()
    board.speed()
    board.draw(scale=0.75)

    turtle.done()


