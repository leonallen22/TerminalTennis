package com.leonallen.pong;
import com.leonallen.pong.Graphics.ImageFormat;

public interface Image
{
    public int getWidth();
    public int getHeight();
    public ImageFormat getFormat();
    public void dispose();
}