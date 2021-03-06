package com.leonallen.pong;

public interface Audio
{
    public Music createMusic(String file);

    public Sound createSound(String file);

    public void playSound(int sound);

    public  void stopSounds();

    public void playMenu();

    public void pauseTrack();

    public void stopSound();
}