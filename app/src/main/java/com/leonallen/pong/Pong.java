package com.leonallen.pong;

public class Pong extends AndroidGame
{
    public Screen getInitScreen()
    {
        return new LoadingScreen(this);
    }
}
