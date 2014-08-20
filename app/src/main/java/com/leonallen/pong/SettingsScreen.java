package com.leonallen.pong;

import android.graphics.Color;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class SettingsScreen extends Screen
{
    private ArrayList<Rect> items;
    private ArrayList<String> labels;
    private ArrayList<Particle> particles;
    private ArrayList<Particle> trails;
    private String title;

    public SettingsScreen(Game game)
    {
        super(game);

        items = new ArrayList<Rect>();
        labels = new ArrayList<String>();
        particles = new ArrayList<Particle>();
        trails = new ArrayList<Particle>();
        title = "SETTINGS";

        Graphics g = game.getGraphics();
        int centerWidth = g.getWidth()/2;
        int centerHeight = g.getHeight()/2;

        items.add(new Rect(centerWidth-75, centerHeight-75, centerWidth+75, centerHeight));
        items.add(new Rect(centerWidth-75, centerHeight+20, centerWidth+75, centerHeight+85));
        labels.add("COLOR");
        labels.add("MAIN MENU");

        for(int i=0; i < 50; ++i)
        {
            Particle p = new Particle((int) (Math.random() * 760), -(int) (Math.random() * 100 * i), 0, (int) (Math.random()*40), 20, 0);
            p.setVisible(true);
            particles.add(p);
        }
    }

    public void update(float deltaTime)
    {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();

        for(Particle p : particles)
        {
            if(p.getY() > 1500)
                p.reset();

            p.update();
        }

        for(int i=0; i < trails.size(); ++i)
        {
            Particle p = trails.get(i);

            if(p.update())
                trails.remove(i);
        }

        int len = touchEvents.size();

        for (int i = 0; i < len; i++)
        {
            Input.TouchEvent event = touchEvents.get(i);

            if (event.type == Input.TouchEvent.TOUCH_UP)
            {
                for(int j=0; j < items.size(); ++j)
                {
                    Rect rect = items.get(j);

                    if(j == 0)
                    {
                        if(rect.contains(event.x, event.y))
                        {
                            //GO TO COLOR CHANGE SCREEN
                            game.setScreen(AndroidGame.colorScreen);
                        }
                    }

                    else if(j == 1)
                    {
                        if(rect.contains(event.x, event.y))
                        {
                            //GO MAIN MENU
                            game.setScreen(AndroidGame.menuScreen);
                        }
                    }
                }
            }
        }
    }

    public void drawTrails()
    {
        for(Particle p : particles)
        {
            int x = p.getX()+5;
            int y = p.getY()-15;
            int life;

            life = (int)(Math.random()*30);

            Particle d = new Particle(".", x, y, 0, 0, 5, life);
            d.setVisible(true);
            trails.add(d);
        }
    }

    public void paint(float deltaTime)
    {
        Graphics g = game.getGraphics();
        Rect rect1 = items.get(0);
        Rect rect2 = items.get(1);
        g.clearScreen(Color.BLACK);
        g.drawRect(rect1);
        g.drawRect(rect2);

        g.drawString(title, GameScreen.BOARD_WIDTH / 2 - (title.length() * 12), GameScreen.BOARD_HEIGHT / 4, 40);
        g.drawString(labels.get(0), rect1.centerX() - 37, rect1.centerY() + 10);
        g.drawString(labels.get(1), rect2.centerX() - 67, rect2.centerY() + 10);

        for(Particle p : particles)
            p.render(g);

        if(Math.random() <= 0.3)
            drawTrails();

        for(Particle p : trails)
            p.render(g);
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
