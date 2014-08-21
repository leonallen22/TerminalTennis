package com.leonallen.pong;

import android.graphics.Rect;

public class Opponent extends Sprite
{
    private static final int INITIAL_X = 400;
    private static final int INITIAL_Y = 80;
    
    public Opponent()
    {
        bounds = new Rect(INITIAL_X, INITIAL_Y, INITIAL_X + 61, INITIAL_Y + 20);
        width = bounds.width();
        height = bounds.height();
    }

    public void setdx(int dx)
    {
        this.dx = dx;
    }

    public void move(Ball ball)
    {
        int x = bounds.centerX();

        if(ball.isReturned())
        {
            int ball_x = ball.getX();
            int ball_dx = ball.getdx();
            int diff_x = Math.abs(x - ball_x);

            if(x <= GameScreen.BOARD_LEFT_BOUND)
                dx = 1;

            else if(x >= GameScreen.BOARD_WIDTH)
                dx = -1;

            else if(ball_dx >= 10 || ball_dx <= -10 || diff_x >= 150 || diff_x >= 100)
            {
                if(x > ball_x)
                    dx = -10;

                else
                    dx = 10;
            }

            else if(ball_dx == 8 || ball_dx == -8 || diff_x > 30)
            {
                if(x > ball_x)
                    dx = -8;

                else if(x < ball_x)
                    dx = 8;
            }

            else if(ball_dx == 6 || ball_dx == -6)
            {
                if(x > ball_x)
                    dx = -6;

                else
                    dx = 6;
            }

            else if(ball_dx == 4 || ball_dx == -4)
            {
                if(x > ball_x)
                    dx = -4;

                else
                    dx = 4;
            }

            else if(ball_dx == 2 || ball_dx == -2)
            {
                if(x > ball_x)
                    dx = -2;

                else
                    dx = 2;
            }

            else
                dx = 0;
        }

        else
        {
            if(x > INITIAL_X+30)
                dx = -6;

            else if(x < INITIAL_X-30)
                dx = 6;

            else
                dx = 0;
        }

        if(!hit && recoil == 0)
        {
            bounds.offset(dx, 0);
        }

        else if(hit && recoil < 10)
        {
            bounds.offset(dx, -1);
            ++recoil;
        }

        else if(!hit)
        {
            bounds.offset(dx, 1);
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
