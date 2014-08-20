package com.leonallen.pong;

import android.graphics.Color;
import android.graphics.Rect;

import java.util.List;
import java.util.ArrayList;
import com.leonallen.pong.Input.TouchEvent;

public class MainMenuScreen extends Screen
{
    ArrayList<Rect> items;
    ArrayList<String> labels;
    ArrayList<ArrayList<Particle>> particles;
    Audio audio;
    Graphics graphics;
    String title = "TERMINAL TENNIS";
    int counter;
    int size;

    public MainMenuScreen(Game game)
    {
        super(game);
        items = new ArrayList<Rect>();
        labels = new ArrayList<String>();
        particles = new ArrayList<ArrayList<Particle>>();
        audio = game.getAudio();
        graphics = game.getGraphics();

        for(int i=0; i < 21; ++i)
            particles.add(new ArrayList<Particle>());

        counter = 0;
        size = 1;
        Graphics g = game.getGraphics();
        int centerWidth = g.getWidth()/2;
        int centerHeight = g.getHeight()/2;

        items.add(new Rect(centerWidth-75, centerHeight-75, centerWidth+75, centerHeight));
        items.add(new Rect(centerWidth-75, centerHeight+20, centerWidth+75, centerHeight+85));
        labels.add("START");
        labels.add("SETTINGS");

        int count = 0;

        for(ArrayList<Particle> list : particles)
        {
            for (int i = 0; i < 60; ++i)
            {
                Particle p = new Particle((count*40), 55 + (i * 20), 0, 0, 14, 0);
                p.setXOscillation(50);
                list.add(p);
            }
            ++count;
        }

        audio.playMenu();
    }

    public void update(float deltaTime)
    {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();


        int len = touchEvents.size();

        for (int i = 0; i < len; i++)
        {
            TouchEvent event = touchEvents.get(i);

            if (event.type == TouchEvent.TOUCH_UP)
            {
                for(int j=0; j < items.size(); ++j)
                {
                    Rect rect = items.get(j);

                    if(j == 0)
                    {
                        if(rect.contains(event.x, event.y))
                        {
                            //START GAME
                            audio.stopSound();
                            game.setScreen(AndroidGame.gameScreen);
                        }
                    }

                    else if(j == 1)
                    {
                        if(rect.contains(event.x, event.y))
                        {
                            //GO TO SETTINGS
                            game.setScreen(AndroidGame.settingsScreen);
                        }
                    }
                }
            }
        }
    }

    public void paint(float deltaTime)
    {
        Rect rect1 = items.get(0);
        Rect rect2 = items.get(1);
        graphics.clearScreen(Color.BLACK);
        graphics.drawRect(rect1);
        graphics.drawRect(rect2);

        graphics.drawString(title, GameScreen.BOARD_WIDTH / 2 - 160, GameScreen.BOARD_HEIGHT / 4, 40);
        graphics.drawString(labels.get(0), rect1.centerX()-37, rect1.centerY()+10);
        graphics.drawString(labels.get(1), rect2.centerX()-57, rect2.centerY()+10);

        for(ArrayList<Particle> list : particles)
        {
            for (int i = 0; i < size; ++i)
            {
                Particle p = list.get(i);
                p.xOscillationRender(graphics);
            }
        }

        switch(size)
        {
            case 60:
                break;

            default:
            ++counter;

            if (counter % 2 == 0)
                ++size;
            break;
        }
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
        //Display "Exit Game?" Box
    }
}
