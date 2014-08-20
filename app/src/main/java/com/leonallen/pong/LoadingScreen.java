package com.leonallen.pong;

import android.graphics.Color;

import com.leonallen.pong.Graphics.ImageFormat;

public class LoadingScreen extends Screen
{
    private static final String message = "LOADING...";

    public LoadingScreen(Game game)
    {
        super(game);
    }

    public void update(float deltaTime)
    {
        Graphics g = game.getGraphics();
        g.clearScreen(Color.BLACK);
        g.drawString(message, (GameScreen.BOARD_WIDTH / 2)-(message.length()*4), GameScreen.BOARD_HEIGHT / 2);
        game.setScreen(AndroidGame.menuScreen);
    }

    public void paint(float deltaTime)
    {


    }

    public void pause()
    {


    }

    public void resume()
    {


    }

    public void dispose()
    {


    }

    public void backButton()
    {


    }
}
