package com.leonallen.pong;

import android.graphics.Rect;

public class Player extends Sprite
{
    private static final int INITIAL_X = 400;
    private static final int INITIAL_Y = 1160;

    public Player()
    {
        bounds = new Rect(INITIAL_X, INITIAL_Y, INITIAL_X+61, INITIAL_Y+20);
        width = bounds.width();
        height = bounds.height();
    }

    public void setdx(int dx)
    {
        this.dx = dx;
    }
    
    public void move()
    {
        if(!hit && recoil == 0)
        {
            bounds.offset(dx, 0);
        }
        
        else if(hit && recoil < 10)
        {
            bounds.offset(dx, 1);
            ++recoil;
        }
        
        else if(!hit)
        {
            bounds.offset(dx, -1);
            --recoil;
        }
        
        else
            hit = false;
    }
    
    public void reset()
    {
        bounds.offsetTo(INITIAL_X, INITIAL_Y);
    }
}
