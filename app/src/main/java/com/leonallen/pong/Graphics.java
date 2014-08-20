package com.leonallen.pong;
import android.graphics.Paint;
import android.graphics.Rect;

public interface Graphics
{
    public static enum ImageFormat{ARGB8888, ARGB4444, RGB565}

    public int getColor();

    public Image newImage(String fileName, ImageFormat format);

    public void setColor(int color);

    public void clearScreen(int color);

    public void drawLine(int x, int y, int x2, int y2);

    public void drawRect(int x, int y, int width, int height);

    public void drawFilledRect(int x, int y, int width, int height);

    public void drawRect(Rect rect);

    public void drawFilledRect(Rect rect);

    public void drawImage(Image image, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight);

    public void drawImage(Image image, int x, int y);

    public void drawString(String text, int x, int y, Paint paint);

    public void drawString(String text, int x, int y);

    public void drawFilledString(String text, int x, int y, int size);

    public void drawString(String text, int x, int y, int size);

    public int getWidth();

    public int getHeight();

    public void drawARGB(int i, int j, int k, int l);
}