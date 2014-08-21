package com.leonallen.pong;

import android.graphics.Rect;

public class Sprite
{
    private boolean visible;
    protected boolean hit;
    protected Rect bounds;
    protected int dx;
    protected int dy;
    protected int width;
    protected int height;
    protected int recoil;
    
    public Sprite()
    {
        visible = true;
        hit = false;
        recoil = 0;
    }
    
    public int getX()
    {
        return bounds.centerX();
    }
    
    public int getY()
    {
        return bounds.centerY();
    }

    public int getLeft()
    {
        return bounds.left;
    }

    public Rect getBounds()
    {
        return bounds;
    }
    
    public boolean isVisible()
    {
        return visible;
    }
    
    public boolean isHit()
    {
        return hit;
    }

    public void setX(int x)
    {
        bounds.offsetTo(x, bounds.top);
    }

    public void setY(int y)
    {
        bounds.offsetTo(bounds.left, y);
    }
    
    public void setHit(boolean hit)
    {
        this.hit = hit;
    }
    
    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
}
