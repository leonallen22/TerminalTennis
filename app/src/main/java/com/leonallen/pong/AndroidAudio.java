package com.leonallen.pong;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.io.IOException;

public class AndroidAudio implements Audio
{
    AssetManager assets;
    SoundPool soundPool;
    MediaPlayer bounce, hit, score, menu;

    public AndroidAudio(Activity activity)
    {
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        this.assets = activity.getAssets();
        this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
        this.bounce = MediaPlayer.create(activity.getApplicationContext(), R.raw.bounce);
        this.hit = MediaPlayer.create(activity.getApplicationContext(), R.raw.hit);
        this.score = MediaPlayer.create(activity.getApplicationContext(), R.raw.score);
        this.menu = MediaPlayer.create(activity.getApplicationContext(), R.raw.menu);
        this.bounce.setVolume((float)0.1, (float)0.1);
        this.hit.setVolume((float)0.1, (float)0.1);
        this.score.setVolume((float)0.1, (float)0.1);
        this.menu.setVolume(1, 1);
    }

    public Music createMusic(String filename)
    {
        try
        {
            AssetFileDescriptor assetDescriptor = assets.openFd(filename);
            return new AndroidMusic(assetDescriptor);
        }
        catch(IOException e)
        {
            throw new RuntimeException("Couldn't load music '" + filename + ";'");
        }
    }

    public Sound createSound(String filename)
    {
        try
        {
            AssetFileDescriptor assetDescriptor = assets.openFd(filename);
            int soundId = soundPool.load(assetDescriptor, 0);
            return new AndroidSound(soundPool, soundId);
        }
        catch(IOException e)
        {
            throw new RuntimeException("Couldn't load sound '" + filename + "'");
        }
    }

    public void playSound(int sound)
    {
        switch(sound)
        {
            case 0:
                bounce.start();
                break;

            case 1:
                hit.start();
                break;

            case 2:
                score.start();
                break;
        }
    }

    public void playMenu()
    {
        if(!menu.isPlaying())
        {
            menu.setLooping(true);
            menu.start();
        }
    }

    public void stopSound()
    {
        if(bounce.isPlaying())
            bounce.stop();

        if(hit.isPlaying())
            hit.stop();

        if(score.isPlaying())
            score.stop();

        if(menu.isPlaying())
        {
            menu.setLooping(false);
            menu.stop();
        }
    }
}