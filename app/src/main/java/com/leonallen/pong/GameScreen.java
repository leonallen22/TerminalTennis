package com.leonallen.pong;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.leonallen.pong.Input.TouchEvent;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends Screen
{
    enum GameState
    {
        Ready, Running, Paused, GameOver
    }

    GameState state = GameState.Ready;

    // Variable Setup
    private ArrayList<Particle> sparks;
    private ArrayList<Particle> burst;
    private ArrayList<Particle> trail;
    private Player player;
    private Opponent opponent;
    private Ball ball;
    private int[] dx_values;
    private ArrayList<Particle> playerGoal;
    private ArrayList<Particle> opponentGoal;
    protected static final int BOARD_LEFT_BOUND = 20;
    protected static final int BOARD_UPPER_BOUND = 20;
    protected static final int BOARD_WIDTH = 760;
    protected static final int BOARD_HEIGHT = 1250;
    protected static final int MULT = 2;
    private static final int SPARKS = 25;
    private static final int BURST = 250;
    private static Rect pause;
    private int playerScore;
    private int opponentScore;
    Paint paint;

    public GameScreen(Game game)
    {
        super(game);

        // Initialize game objects here
        sparks = new ArrayList<Particle>(SPARKS);
        burst = new ArrayList<Particle>(BURST);
        trail = new ArrayList<Particle>(SPARKS);
        player = new Player();
        opponent = new Opponent();
        ball = new Ball();
        dx_values = new int[61];
        pause = new Rect(BOARD_WIDTH-20, BOARD_UPPER_BOUND-20, BOARD_WIDTH+30, BOARD_UPPER_BOUND+26);
        playerGoal = new ArrayList<Particle>();
        opponentGoal = new ArrayList<Particle>();
        playerScore = 0;
        opponentScore = 0;

        for(int i=0; i < BURST; ++i)
            burst.add(new Particle());

        for(int i=0; i < SPARKS; ++i)
            sparks.add(new Particle());

        for(int i=0; i < SPARKS; ++i)
            trail.add(new Particle());

        for(int i=0; i < 30; ++i)
        {
            Particle p = new Particle(0 - (50 * i), 1250, 15, 0, 20, 1);
            p.setVisible(true);
            playerGoal.add(p);
        }

        for(int i=0; i < 30; ++i)
        {
            Particle p = new Particle(1280 + (20 * i), 20, -15, 0, 20, 1);
            p.setVisible(true);
            opponentGoal.add(p);
        }

        for(int i=0; i < 61; ++i)
        {
            if(i < 3)
                dx_values[i] = -6*MULT;

            else if(i < 10)
                dx_values[i] = -5*MULT;

            else if(i < 16)
                dx_values[i] = -4*MULT;

            else if(i < 21)
                dx_values[i] = -3*MULT;

            else if(i < 26)
                dx_values[i] = -2*MULT;

            else if(i < 31)
                dx_values[i] = -MULT;

            else if(i == 31)
                dx_values[i] = 0;

            else if(i < 36)
                dx_values[i] = MULT;

            else if(i < 41)
                dx_values[i] = 2*MULT;

            else if(i < 46)
                dx_values[i] = 3*MULT;

            else if(i < 52)
                dx_values[i] = 4*MULT;

            else if(i < 59)
                dx_values[i] = 5*MULT;

            else if(i <= 61)
                dx_values[i] = 6*MULT;
        }

        // Defining a paint object
        paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
    }

    public void update(float deltaTime)
    {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        if (state == GameState.Ready)
            updateReady(touchEvents);

        if (state == GameState.Running)
            updateRunning(touchEvents, deltaTime);

        if (state == GameState.Paused)
            updatePaused(touchEvents);

        if (state == GameState.GameOver)
            updateGameOver(touchEvents);
    }

    private void updateReady(List<TouchEvent> touchEvents)
    {
        if (touchEvents.size() > 0)
            state = GameState.Running;

        updateParticles();
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime)
    {
        for (int i=0; i < touchEvents.size(); ++i)
        {
            TouchEvent event = touchEvents.get(i);

            if(event.type == TouchEvent.TOUCH_DOWN)
            {
                int event_x = event.x;
                int event_y = event.y;
                int player_x = player.getX();

                if(ball.getdx() == 0 && ball.getdy() == 0)
                {
                    ball.setdy(Ball.INITIAL_DY);
                    ball.setdx(Ball.INITIAL_DX);
                }

                if(pause.contains(event_x, event_y))
                {
                    //Pause game
                    state = GameState.Paused;
                    touchEvents.clear();
                }

                else if(event.x < BOARD_WIDTH/2)
                {
                    // Move left.
                    if(player_x > BOARD_LEFT_BOUND)
                        player.setdx(-10);
                }

                else if(event.x > BOARD_WIDTH/2)
                {
                    // Move right.
                    if(player_x < BOARD_WIDTH)
                        player.setdx(10);
                }
            }

            if(event.type == TouchEvent.TOUCH_UP)
                player.setdx(0);
        }

        if(playerScore >= 10 || opponentScore >= 10)
        {
            if(Math.abs(playerScore - opponentScore) >= 2)
                state = GameState.GameOver;
        }

        player.move();
        opponent.move(ball);
        ball.move();

        updateParticles();
        checkCollision();
    }

    private void updatePaused(List<TouchEvent> touchEvents)
    {
        for (TouchEvent event : touchEvents)
        {
            if (event.type == TouchEvent.TOUCH_DOWN)
                state = GameState.Running;
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents)
    {
        Rect replay = new Rect(BOARD_WIDTH/2-150, BOARD_HEIGHT/2+100, BOARD_WIDTH/2-30, BOARD_HEIGHT/2+150);
        Rect quit = new Rect(BOARD_WIDTH/2+80, BOARD_HEIGHT/2+100, BOARD_WIDTH/2+200, BOARD_HEIGHT/2+150);

        for(TouchEvent event : touchEvents)
        {
            if(event.type == TouchEvent.TOUCH_UP)
            {
                if(replay.contains(event.x, event.y))
                    resetGame();

                else if(quit.contains(event.x, event.y))
                {
                    nullify();
                    resetGame();
                    game.setScreen(AndroidGame.menuScreen);
                }
            }
        }
    }

    public void drawScore(Graphics g)
    {
        g.drawString(Integer.toString(playerScore), 740, 1260, 24);
        g.drawString(Integer.toString(opponentScore), 20, 30, 24);
    }

    public void resetRound()
    {
        player.reset();
        opponent.reset();
        ball.reset();
        ball.setVisible(true);
    }

    public void resetGame()
    {
        player.reset();
        opponent.reset();
        ball.reset();
        ball.setVisible(true);
        playerScore = 0;
        opponentScore = 0;
        state = GameState.Ready;
    }

    public void addSpark(int x, int y, int direction)
    {
        for(Particle p : sparks)
        {
            int particle_dx;
            int particle_dy;
            int ball_dx = ball.getdx();
            int life = (int) (Math.random() * 20);

            switch (ball_dx)
            {
                case 6:
                    if (Math.random() < 0.95)
                        particle_dx = (int) (Math.random()*10);

                    else
                        particle_dx = (int) (Math.random()*-10);
                    break;

                case 5:
                    if (Math.random() < 0.9)
                        particle_dx = (int) (Math.random()*10);

                    else
                        particle_dx = (int) (Math.random()*-10);
                    break;

                case 4:
                    if (Math.random() < 0.8)
                        particle_dx = (int) (Math.random()*10);

                    else
                        particle_dx = (int) (Math.random()*-10);
                    break;

                case 3:
                    if (Math.random() < 0.7)
                        particle_dx = (int) (Math.random()*10);

                    else
                        particle_dx = (int) (Math.random()*-10);
                    break;

                case 2:
                    if (Math.random() < 0.6)
                        particle_dx = (int) (Math.random()*10);

                    else
                        particle_dx = (int) (Math.random()*-10);
                    break;

                case 1:
                    if (Math.random() < 0.5)
                        particle_dx = (int) (Math.random()*10);

                    else
                        particle_dx = (int) (Math.random()*-10);
                    break;

                case 0:
                    if (Math.random() < 0.5)
                        particle_dx = (int) (Math.random()*10);

                    else
                        particle_dx = (int) (Math.random()*-10);
                    break;

                case -1:
                    if (Math.random() < 0.5)
                        particle_dx = (int) (Math.random()*10);

                    else
                        particle_dx = (int) (Math.random()*-10);
                    break;

                case -2:
                    if (Math.random() < 0.4)
                        particle_dx = (int) (Math.random()*10);

                    else
                        particle_dx = (int) (Math.random()*-10);
                    break;

                case -3:
                    if (Math.random() < 0.3)
                        particle_dx = (int) (Math.random()*10);

                    else
                        particle_dx = (int) (Math.random()*-10);
                    break;

                case -4:
                    if (Math.random() < 0.2)
                        particle_dx = (int) (Math.random()*10);

                    else
                        particle_dx = (int) (Math.random()*-10);
                    break;

                case -5:
                    if (Math.random() < 0.1)
                        particle_dx = (int) (Math.random()*10);

                    else
                        particle_dx = (int) (Math.random()*-10);
                    break;

                case -6:
                    if (Math.random() < 0.05)
                        particle_dx = (int) (Math.random()*10);

                    else
                        particle_dx = (int) (Math.random()*-10);
                    break;

                default:
                    if (Math.random() < 0.5)
                        particle_dx = (int) (Math.random()*10);

                    else
                        particle_dx = (int) (Math.random()*-10);
                    break;
            }

            if (direction == 0)
                particle_dy = (int) (Math.random()*-10);

            else
                particle_dy = (int) (Math.random()*10);

            p.setParams(x, y, particle_dx, particle_dy, 10, life);
            p.setVisible(true);
        }
    }

    public void addBurst(int x, int y, int direction)
    {
        int particle_dx;
        int particle_dy;
        int life;

        for(Particle p : burst)
        {
            life = (int)(Math.random()*60);

            if(Math.random() < 0.5)
                particle_dx = (int)(Math.random()*-7);

            else
                particle_dx = (int)(Math.random()*7);

            if(direction == 0)
                particle_dy = (int)(Math.random()*-7);

            else
                particle_dy = (int)(Math.random()*7);

            p.setParams(x, y, particle_dx, particle_dy, 10, life);
            p.setVisible(true);
        }
    }

    public void updateParticles()
    {
        for(Particle p : burst)
        {
            if(p.update())
                p.setVisible(false);
        }

        for(Particle p : sparks)
        {
            if(p.update())
                p.setVisible(false);
        }

        for(Particle p : trail)
        {
            if(p.update())
                p.setVisible(false);
        }

        for(Particle p : playerGoal)
            p.update();

        for(Particle p : opponentGoal)
            p.update();
    }

    public void renderParticles(Graphics g)
    {
        for(Particle p : burst)
        {
            if(p.isVisible())
            {
                p.render(g);
            }
        }

        for(Particle p : sparks)
        {
            if(p.isVisible())
                p.render(g);
        }

        for(Particle p : trail)
        {
            if(p.isVisible())
                p.render(g);
        }
    }

    public void renderGoals(Graphics g)
    {
        for(Particle p : playerGoal)
        {
            if(p.getX() > 1280)
                p.reset();

            p.render(g);
        }

        for(Particle p : opponentGoal)
        {
            if(p.getX() < 0)
                p.reset();

            p.render(g);
        }

    }

    public void drawPlayer(Graphics g)
    {
        if(player.isVisible())
            g.drawFilledRect(player.getBounds());
    }

    public void drawOpponent(Graphics g)
    {
        if(opponent.isVisible())
            g.drawFilledRect(opponent.getBounds());
    }

    public void drawBall(Graphics g)
    {
        if(ball.isVisible())
        {
            g.drawFilledRect(ball.getBounds());

            if(Math.random() < 0.3)
                drawTrail();
        }

        else
            resetRound();
    }

    public void drawTrail()
    {
        int x = ball.getX();
        int y = ball.getY()+2;
        int life;
        int rand = (int)(Math.random()*100);

        if(rand < 25)
            x += 1;

        else if(rand < 50)
            x += 2;

        else if(rand < 75)
            x -= 1;

        else
            x -= 2;

        if(ball.getdx() == 0)
            life = 25;

        else
            life = 20;

        for(Particle p : trail)
        {
            if(!p.isVisible())
            {
                p.setParams(x, y, 0, 0, 10, life);
                p.setVisible(true);
                break;
            }
        }
    }

    public void paint(float deltaTime)
    {
        Graphics g = game.getGraphics();
        g.clearScreen(Color.BLACK);

        drawPlayer(g);
        drawOpponent(g);
        drawBall(g);
        drawScore(g);
        renderParticles(g);
        renderGoals(g);

        if (state == GameState.Ready)
            drawReadyUI();

        if (state == GameState.Running)
            drawRunningUI();

        if (state == GameState.Paused)
            drawPausedUI();

        if (state == GameState.GameOver)
            drawGameOverUI();
    }

    public void checkCollision()
    {
        Audio audio = game.getAudio();
        Rect playerbound = player.getBounds();
        int player_x = player.getX();

        if(player_x >= BOARD_WIDTH)
        {
            player.setX(playerbound.left);
            player.setdx(0);
        }

        else if(player_x <= BOARD_LEFT_BOUND)
        {
            player.setX(playerbound.left);
            player.setdx(0);
        }


        Rect opponentbound = opponent.getBounds();
        int opponent_x = opponent.getX();

        if(opponent_x >= BOARD_WIDTH)
        {
            opponent.setX(opponentbound.left);
            opponent.setdx(0);
        }

        else if(opponent_x <= BOARD_LEFT_BOUND)
        {
            opponent.setX(opponentbound.left);
            opponent.setdx(0);
        }


        Rect ballbound = ball.getBounds();
        int ball_x = ball.getX();
        int ball_y = ball.getY();
        int dx = ball.getdx();

        if(ball_x >= BOARD_WIDTH)
        {
            audio.playSound(0);
            ball.setdx(-dx);
        }

        else if(ball_x <= BOARD_LEFT_BOUND)
        {
            audio.playSound(0);
            ball.setdx(-dx);
        }

        else if(ball_y <= BOARD_UPPER_BOUND)
        {
            ball.setVisible(false);
            audio.playSound(2);

            addBurst(ball_x, ball_y, 1);

            ++playerScore;
        }

        else if(ball_y > BOARD_HEIGHT-10)
        {
            ball.setVisible(false);
            audio.playSound(2);

            addBurst(ball_x, ball_y, 0);

            ++opponentScore;
        }

        else if(Rect.intersects(ballbound, playerbound))
        {
            int player_y = player.getY();
            int dy = ball.getdy();
            int relative_x = ball_x - player.getLeft();
            ball.setReturned(true);
            audio.playSound(1);
            Log.i("Relative X: ", Integer.toString(relative_x));

            if(!player.isHit())
                player.setHit(true);

            if(relative_x < 0)
                relative_x = 0;

            else if(relative_x > 60)
                relative_x = 60;

            if(ball_y <= player_y+4)
            {
                ball.setdy(-dy);
                ball.setdx(dx_values[relative_x]);
            }

            else
                ball.setdx(dx_values[relative_x]);

            addSpark(ball_x, ball_y, 0);
        }

        else if(Rect.intersects(ballbound, opponentbound))
        {
            int opponent_y = (opponent.getY()-19);
            int dy = ball.getdy();
            int relative_x = ball_x - opponent.getLeft();
            ball.setReturned(false);
            audio.playSound(1);
            Log.i("Opponent Relative X: ", Integer.toString(relative_x));

            if(!opponent.isHit())
                opponent.setHit(true);

            if(relative_x < 0)
                relative_x = 0;

            else if(relative_x > 60)
                relative_x = 60;

            if(ball_y >= opponent_y+4)
            {
                ball.setdy(-dy);
                ball.setdx(dx_values[relative_x]);
            }

            else
                ball.setdx(dx_values[relative_x]);

            addSpark(ball_x, ball_y, 1);
        }
    }

    public void gameOver(Graphics g)
    {
        String gameOver;
        String replay_str = "REPLAY";
        String quit_str = "QUIT";
        Rect replay = new Rect(BOARD_WIDTH/2-150, BOARD_HEIGHT/2+100, BOARD_WIDTH/2-30, BOARD_HEIGHT/2+150);
        Rect quit = new Rect(BOARD_WIDTH/2+80, BOARD_HEIGHT/2+100, BOARD_WIDTH/2+200, BOARD_HEIGHT/2+150);

        if(playerScore > opponentScore)
            gameOver = "You Win!";

        else
            gameOver = "You Lose!";

        g.drawString(gameOver, BOARD_WIDTH/2-(gameOver.length()*8), BOARD_HEIGHT/2, 46);
        g.drawString(replay_str, replay.centerX()-45, replay.centerY()+10);
        g.drawString(quit_str, quit.centerX()-28, quit.centerY()+10);
        g.drawRect(replay);
        g.drawRect(quit);
    }

    private void nullify()
    {
        paint = null;

        System.gc();
    }

    private void drawReadyUI()
    {
        Graphics g = game.getGraphics();

        g.drawARGB(155, 0, 0, 0);
        String hint_1 = "Tap to begin round";
        String hint_2 = "Pause button is at top right of screen";
        String hint_3 = "Touch Left & Right side of screen to control paddle";

        g.drawFilledString(hint_1, (BOARD_WIDTH / 2)-(hint_1.length()*4), BOARD_HEIGHT/2 - 25, 24);
        g.drawFilledString(hint_2, (BOARD_WIDTH / 2)-(hint_2.length()*4), BOARD_HEIGHT / 2, 24);
        g.drawFilledString(hint_3, (BOARD_WIDTH / 2)-(hint_3.length()*4), BOARD_HEIGHT/2 + 25, 24);

    }

    private void drawRunningUI()
    {
        Graphics g = game.getGraphics();
        g.drawRect(pause);
        g.drawFilledRect(pause.centerX()-15, pause.centerY()-12, 15, 25);
        g.drawFilledRect(pause.centerX()+2, pause.centerY()-12, 15, 25);
    }

    private void drawPausedUI()
    {
        Graphics g = game.getGraphics();
        String message = "PAUSED";

        g.drawFilledString(message, (BOARD_WIDTH / 2)-(message.length()*4), BOARD_HEIGHT/2 - 50, 24);
        String hint_1 = "Tap to resume game";
        String hint_2 = "Pause button is at top right of screen";
        String hint_3 = "Touch Left & Right side of screen to control paddle";

        g.drawFilledString(hint_1, (BOARD_WIDTH / 2)-(hint_1.length()*4), BOARD_HEIGHT/2 - 25, 24);
        g.drawFilledString(hint_2, (BOARD_WIDTH / 2)-(hint_2.length()*4), BOARD_HEIGHT / 2, 24);
        g.drawFilledString(hint_3, (BOARD_WIDTH / 2)-(hint_3.length()*4), BOARD_HEIGHT/2 + 25, 24);

    }

    private void drawGameOverUI()
    {
        Graphics g = game.getGraphics();
        g.clearScreen(Color.BLACK);
        gameOver(g);
    }

    public void pause()
    {
        if (state == GameState.Running)
            state = GameState.Paused;
    }

    public void resume()
    {

    }

    public void dispose()
    {

    }

    public void backButton()
    {
        pause();
    }
}
