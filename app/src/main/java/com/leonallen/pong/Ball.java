package com.leonallen.pong;

import android.graphics.Rect;

public class Ball extends Sprite
{
    private static final int INITIAL_X = 430;
    private static final int INITIAL_Y = 600;
    public static final int INITIAL_DY = 12;
    public static final int INITIAL_DX = 0;
    private boolean returned;
    private boolean served;
    
    public Ball()
    {
        width = 15;
        height = 15;
        bounds = new Rect(INITIAL_X, INITIAL_Y, INITIAL_X+width, INITIAL_Y+height);
        returned = false;
        served = false;
        dy = INITIAL_DY;
        dx = INITIAL_DX;
    }

    public Ball(int x, int y)
    {
        width = 10;
        height = 10;
        bounds = new Rect (x, y, x+width, y+height);
        returned = false;
        served = false;
        dy = INITIAL_DY;
        dx = INITIAL_DX;
    }
    
    public int getdx()
    {
        return dx;
    }
    
    public int getdy()
    {
        return dy;
    }
    
    public boolean isReturned()
    {
        return returned;
    }
    
    public boolean isServed()
    {
        return served;
    }
    
    public void setdx(int dx)
    {
        this.dx = dx;
    }
    
    public void setdy(int dy)
    {
        this.dy = dy;
    }
    
    public void setReturned(boolean returned)
    {
        this.returned = returned;
    }
    
    public void setServed(boolean served)
    {
        this.served = served;
    }
    
    public void move()
    {
        bounds.offset(dx, dy);
    }
    
    public void reset()
    {
        returned = false;
        served = false;
        dy = 0;
        dx = 0;
        bounds.offsetTo(INITIAL_X, INITIAL_Y);
    }
}
