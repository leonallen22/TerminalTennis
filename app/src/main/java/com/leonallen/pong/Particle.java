package com.leonallen.pong;

public class Particle
{
    private int x;
    private int y;
    private int initial_x;
    private int initial_y;
    private int dx;
    private int dy;
    private int size;
    private int life;
    private String symbol;
    private int x_oscillation;
    private int y_oscillation;
    private boolean x_oscillation_direction;
    private boolean y_oscillation_direction;
    private boolean visible;

    public Particle()
    {
        this.x = 0;
        this.y = 0;
        this.initial_x = x;
        this.initial_y = y;
        this.dx = 0;
        this.dy = 0;
        this.size = 0;
        this.life = 0;
        this.x_oscillation = 0;
        this.y_oscillation = 0;
        this.x_oscillation_direction = true;
        this.y_oscillation_direction = true;
        this.visible = false;

        if(Math.random() < 0.5)
            symbol = "0";

        else
            symbol = "1";
    }

    public Particle(int x, int y, int dx, int dy, int size, int life)
    {
        this.x = x;
        this.y = y;
        this.initial_x = x;
        this.initial_y = y;
        this.dx = dx;
        this.dy = dy;
        this.size = size;
        this.life = life;
        this.x_oscillation = 0;
        this.y_oscillation = 0;
        this.x_oscillation_direction = true;
        this.y_oscillation_direction = true;

        if(Math.random() < 0.5)
            symbol = "0";
        
        else
            symbol = "1";
    }

    public Particle(String symbol, int x, int y, int dx, int dy, int size, int life)
    {
        this.x = x;
        this.y = y;
        this.initial_x = x;
        this.initial_y = y;
        this.dx = dx;
        this.dy = dy;
        this.size = size;
        this.life = life;
        this.symbol = symbol;
        this.x_oscillation = 0;
        this.y_oscillation = 0;
        this.x_oscillation_direction = true;
        this.y_oscillation_direction = true;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getXOscillation()
    {
        return x_oscillation;
    }

    public int getYOscillation()
    {
        return y_oscillation;
    }

    public boolean isVisible()
    {
        return visible;
    }
    
    public boolean update()
    {
        x += dx;
        y += dy;
        
        --life;
        
        if(life <= 0)
            return true;
        
        return false;
    }

    public void testUpdate()
    {
        x += dx;
        y += dy;

        --life;

        if(life <= 0)
            visible = false;
    }

    public void setParams(int x, int y, int dx, int dy, int size, int life)
    {
        this.x = x;
        this.initial_x = x;
        this.initial_y = y;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.size = size;
        this.life = life;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }

    public void setXOscillation(int oscillation)
    {
        this.x_oscillation = oscillation;
    }

    public void setYOscillation(int oscillation)
    {
        this.y_oscillation = oscillation;
    }

    public void render(Graphics g)
    {
        if(visible)
        g.drawFilledString(symbol, x, y, size);
    }

    public void xOscillationRender(Graphics g)
    {
        if(x_oscillation != 0)
        {
            int diff = x - initial_x;

            if(x_oscillation_direction)
            {
                if(diff < x_oscillation && diff < (x_oscillation - x_oscillation/4))
                {
                    x += 3;
                    g.drawFilledString(symbol, x, y, size);
                }

                else if(diff < x_oscillation)
                {
                    ++x;
                    g.drawFilledString(symbol, x, y, size);
                }

                else
                {
                    x_oscillation_direction = false;
                    g.drawFilledString(symbol, x, y, size);
                }
            }

            else
            {
                if(diff > (x_oscillation * -1) && diff > (x_oscillation*-1 + x_oscillation/4))
                {
                    x -= 3;
                    g.drawFilledString(symbol, x, y, size);
                }

                else if(diff > (x_oscillation * -1))
                {
                    --x;
                    g.drawFilledString(symbol, x, y, size);
                }

                else
                {
                    x_oscillation_direction = true;
                    g.drawFilledString(symbol, x, y, size);
                }
            }
        }

        else
            g.drawFilledString(symbol, x, y, size);
    }

    public void reset()
    {
        this.x = initial_x;
        this.y = initial_y;
    }
}
