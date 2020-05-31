# Fractal Snowflake
# 2020/05/31, Rev 1.0

import turtle 

class snowflake(turtle.Turtle):
    ''' Draw / UnDraw the classical snowflake, with custom attributes '''

    def __init__(self, xpos, ypos, size, color="white", speed=-1):
        super().__init__()
        self.speed_ = speed
        self.xpos = xpos; self.ypos   = ypos
        self.size = size; self.color_ = color
        self.hideturtle()
        self.speed(self.speed_)

    def draw_flake(self):
        ''' Render the snowflake upon the default background '''
        self.penup() 
        self.goto(self.xpos, self.ypos) 
        self.forward(10 * self.size) 
        self.left(45) 
        self.pendown() 
        self.color(self.color_)
        for ignored in range(8): 
            self.draw_line()    
            self.left(45) 
        self.penup()

    def quick_draw(self, background='black'):
        ''' Quick-draw the snowflake in the color of your choice '''
        hold_  = self.color_
        self.color_ = background
        self.speed(-1)
        self.draw_flake()
        self.speed(self.speed_)
        self.color_ = hold_
        
    def draw_line(self):
        slice_ = 10 * (self.size / 3)
        for ignore in range(3): 
            for ignore in range(3): 
                self.forward(slice_) 
                self.backward(slice_) 
                self.right(45) 
            self.left(90) 
            self.backward(slice_) 
            self.left(45) 
        self.right(90)  
        self.forward(10.0 * self.size) 


if __name__ == '__main__':
    screen_ = turtle.Screen()
    screen_.setup(0.85, 0.80)
    screen_.bgcolor("black")

    flakes = [
        (-50,   50,  8, 'white',  2),
        (100,  -100, 6, 'grey',   4),
        (-70,  -125, 7, 'yellow', 6),
        (-190, -50,  4, 'green',  8),
        (120,   50,  3, 'gold'),
        ]

    for ss, flake in enumerate(flakes):
        flakes[ss] = snowflake(*flake)
        flakes[ss].draw_flake() 
    for flake in flakes:
        flake.quick_draw() # hide it


