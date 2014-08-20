package com.leonallen.pong;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public abstract class AndroidGame extends Activity implements Game
{
    private AndroidFastRenderView renderView;
    private Graphics graphics;
    private Audio audio;
    private Input input;
    private FileIO fileIO;
    private Screen screen;
    public static MainMenuScreen menuScreen;
    public static SettingsScreen settingsScreen;
    public static ColorScreen colorScreen;
    public static GameScreen gameScreen;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        int frameBufferWidth = isPortrait ? 800: 1280;
        int frameBufferHeight = isPortrait ? 1280: 800;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Config.RGB_565);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        float scaleX = (float)frameBufferWidth / size.x;
        float scaleY = (float)frameBufferHeight / size.y;

        renderView = new AndroidFastRenderView(this, frameBuffer);
        graphics = new AndroidGraphics(getAssets(), frameBuffer);
        fileIO = new AndroidFileIO(this);
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderView, scaleX, scaleY);
        screen = getInitScreen();
        menuScreen = new MainMenuScreen(this);
        settingsScreen = new SettingsScreen(this);
        colorScreen = new ColorScreen(this);
        gameScreen = new GameScreen(this);
        setContentView(renderView);
    }

    public void onResume()
    {
        super.onResume();
        screen.resume();
        renderView.resume();
    }

    public void onPause()
    {
        super.onPause();
        renderView.pause();
        screen.pause();

        if(isFinishing())
            screen.dispose();
    }

    public Input getInput()
    {
        return input;
    }

    public FileIO getFileIO()
    {
        return fileIO;
    }

    public Graphics getGraphics()
    {
        return graphics;
    }

    public Audio getAudio()
    {
        return audio;
    }

    public void setScreen(Screen screen)
    {
        if(screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    public Screen getCurrentScreen()
    {
        return screen;
    }
}