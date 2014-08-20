package com.leonallen.pong;

import android.graphics.Color;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class ColorScreen extends Screen
{
    private ArrayList<Rect> items;
    private ArrayList<String> labels;
    private ArrayList<Particle> particles;
    private ArrayList<Particle> trails;
    private String title;

    public ColorScreen(Game game)
    {
        super(game);

        items = new ArrayList<Rect>();
        labels = new ArrayList<String>();
        particles = new ArrayList<Particle>();
        trails = new ArrayList<Particle>();
        title = "SETTINGS";

        Graphics g = game.getGraphics();
        int centerWidth = g.getWidth() / 2;
        int centerHeight = g.getHeight() / 2;

        items.add(new Rect(centerWidth - 175, centerHeight - 75, centerWidth - 25, centerHeight));
        items.add(new Rect(centerWidth - 175, centerHeight + 20, centerWidth - 25, centerHeight + 95));
        items.add(new Rect(centerWidth - 175, centerHeight + 115, centerWidth - 25, centerHeight + 190));
        items.add(new Rect(centerWidth - 175, centerHeight + 210, centerWidth - 25, centerHeight + 285));
        items.add(new Rect(centerWidth + 25, centerHeight - 75, centerWidth + 175, centerHeight));
        items.add(new Rect(centerWidth + 25, centerHeight + 20, centerWidth + 175, centerHeight + 95));
        items.add(new Rect(centerWidth + 25, centerHeight + 115, centerWidth + 175, centerHeight + 190));
        items.add(new Rect(centerWidth + 25, centerHeight + 210, centerWidth + 175, centerHeight + 285));
        items.add(new Rect(centerWidth - 75, centerHeight + 305, centerWidth + 75, centerHeight + 380));

        labels.add("GREEN");
        labels.add("BLUE");
        labels.add("CYAN");
        labels.add("RED");
        labels.add("GRAY");
        labels.add("MAGENTA");
        labels.add("WHITE");
        labels.add("YELLOW");
        labels.add("BACK");

        for(int i=0; i < 50; ++i)
        {
            Particle p = new Particle((int) (Math.random() * 760), 0 - (int) (Math.random() * 100 * i), 0, 5 * (int) (Math.random() * 7), 20, 0);
            p.setVisible(true);
            particles.add(p);
        }
    }

    public void update(float deltaTime)
    {
        Graphics g = game.getGraphics();
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

                    switch(j)
                    {
                        case 0:
                            if (rect.contains(event.x, event.y))
                                g.setColor(Color.GREEN);
                            break;

                        case 1:
                            if (rect.contains(event.x, event.y))
                                g.setColor(Color.BLUE);
                            break;

                        case 2:
                            if (rect.contains(event.x, event.y))
                                g.setColor(Color.CYAN);
                            break;

                        case 3:
                            if (rect.contains(event.x, event.y))
                                g.setColor(Color.RED);
                            break;

                        case 4:
                            if (rect.contains(event.x, event.y))
                                g.setColor(Color.LTGRAY);
                            break;

                        case 5:
                            if (rect.contains(event.x, event.y))
                                g.setColor(Color.MAGENTA);
                            break;

                        case 6:
                            if (rect.contains(event.x, event.y))
                                g.setColor(Color.WHITE);
                            break;

                        case 7:
                            if (rect.contains(event.x, event.y))
                                g.setColor(Color.YELLOW);
                            break;

                        default:
                            if (rect.contains(event.x, event.y))
                                game.setScreen(AndroidGame.settingsScreen);
                            break;
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
        Rect rect3 = items.get(2);
        Rect rect4 = items.get(3);
        Rect rect5 = items.get(4);
        Rect rect6 = items.get(5);
        Rect rect7 = items.get(6);
        Rect rect8 = items.get(7);
        Rect rect9 = items.get(8);
        g.clearScreen(Color.BLACK);
        g.drawRect(rect1);
        g.drawRect(rect2);
        g.drawRect(rect3);
        g.drawRect(rect4);
        g.drawRect(rect5);
        g.drawRect(rect6);
        g.drawRect(rect7);
        g.drawRect(rect8);
        g.drawRect(rect9);


        g.drawString(title, GameScreen.BOARD_WIDTH / 2 - (title.length() * 12), GameScreen.BOARD_HEIGHT / 4, 40);
        g.drawString(labels.get(0), rect1.centerX() - 37, rect1.centerY() + 10);
        g.drawString(labels.get(1), rect2.centerX() - 32, rect2.centerY() + 10);
        g.drawString(labels.get(2), rect3.centerX() - 32, rect3.centerY() + 10);
        g.drawString(labels.get(3), rect4.centerX() - 25, rect4.centerY() + 10);
        g.drawString(labels.get(4), rect5.centerX() - 37, rect5.centerY() + 10);
        g.drawString(labels.get(5), rect6.centerX() - 57, rect6.centerY() + 10);
        g.drawString(labels.get(6), rect7.centerX() - 37, rect7.centerY() + 10);
        g.drawString(labels.get(7), rect8.centerX() - 47, rect8.centerY() + 10);
        g.drawString(labels.get(8), rect9.centerX() - 35, rect9.centerY() + 10);

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
