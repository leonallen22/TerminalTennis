package com.leonallen.pong;

public class Assets
{
    public static Sound hit, bounce, score;

    public static void load(Game game)
    {
        hit = game.getAudio().createSound("raw/hit.wav");
        bounce = game.getAudio().createSound("raw/bounce.wav");
        score = game.getAudio().createSound("raw/score.wav");
    }
}