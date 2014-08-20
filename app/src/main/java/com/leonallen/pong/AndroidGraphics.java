package com.leonallen.pong;
import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

public class AndroidGraphics implements Graphics
{
    AssetManager assets;
    Bitmap frameBuffer;
    Bitmap background;
    Canvas backgroundCanvas;
    Canvas canvas;
    Paint paint;
    int color;
    Rect srcRect = new Rect();
    Rect dstRect = new Rect();

    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer)
    {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.background = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.backgroundCanvas = new Canvas(background);
        this.paint = new Paint();
        this.color = Color.GREEN;
        paint.setColor(color);
    }

    public int getColor()
    {
        return color;
    }

    public Image newImage(String filename, ImageFormat format)
    {
        Config config;

        config = Config.ARGB_8888;

        Options options = new Options();
        options.inPreferredConfig = config;

        InputStream in = null;
        Bitmap bitmap = null;

        try
        {
            in = assets.open(filename);
            bitmap = BitmapFactory.decodeStream(in, null, options);

            if(bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '" + filename + "'");
        }
        catch(IOException e)
        {
            throw new RuntimeException("Couldn't load bitmap from asset '" + filename + "'");
        }
        finally
        {
            if(in != null)
            {
                try
                {
                    in.close();
                }
                catch(IOException e)
                {

                }
            }
        }

        format = ImageFormat.ARGB8888;

        return new AndroidImage(bitmap, format);
    }

    public void setColor(int color)
    {
        this.color = color;
        paint.setColor(color);
    }

    public void setBackground()
    {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8, (color & 0xff));
    }

    public void clearScreen(int color)
    {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8, (color & 0xff));
    }

    public void drawLine(int x, int y, int x2, int y2)
    {
        canvas.drawLine(x, y, x2, y2, paint);
    }

    public void drawRect(int x, int y, int width, int height)
    {
        paint.setStyle(Style.STROKE);
        canvas.drawRect(x, y, x + width - 1, y + height, paint);
    }

    public void drawFilledRect(int x, int y, int width, int height)
    {
        paint.setStyle(Style.FILL);
        canvas.drawRect(x, y, x + width - 1, y + height, paint);
    }

    public void drawRect(Rect rect)
    {
        paint.setStyle(Style.STROKE);
        canvas.drawRect(rect, paint);
    }

    public void drawFilledRect(Rect rect)
    {
        paint.setStyle(Style.FILL);
        canvas.drawRect(rect, paint);
    }

    public void drawARGB(int a, int r, int g, int b)
    {
        paint.setStyle(Style.FILL);
        canvas.drawARGB(a, r, g, b);
    }

    public void drawString(String text, int x, int y, Paint paint)
    {
        paint.setStyle(Style.STROKE);
        canvas.drawText(text, x, y, paint);
    }

    public void drawString(String text, int x, int y)
    {
        paint.setTextSize(25);
        paint.setStyle(Style.STROKE);
        canvas.drawText(text, x, y, paint);
    }

    public void drawFilledString(String text, int x, int y, int size)
    {
        paint.setTextSize(size);
        paint.setStyle(Style.FILL);
        canvas.drawText(text, x, y, paint);
    }

    public void drawString(String text, int x, int y, int size)
    {
        paint.setTextSize(size);
        paint.setStyle(Style.FILL);
        canvas.drawText(text, x, y, paint);
    }

    public void drawImage(Image image, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight)
    {
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth;
        srcRect.bottom = srcY + srcHeight;

        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth;
        dstRect.bottom = y + srcHeight;

        canvas.drawBitmap(((AndroidImage)image).bitmap, srcRect, dstRect, null);
    }

    public void drawImage(Image image, int x, int y)
    {
        canvas.drawBitmap(((AndroidImage)image).bitmap, x, y, null);
    }

    public void drawScaledImage(Image image, int x, int y, int width, int height, int srcX, int srcY, int srcWidth, int srcHeight)
    {
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth;
        srcRect.bottom = srcY + srcHeight;

        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + width;
        dstRect.bottom = y + height;

        canvas.drawBitmap(((AndroidImage)image).bitmap, srcRect, dstRect, null);
    }

    public int getWidth()
    {
        return frameBuffer.getWidth();
    }

    public int getHeight()
    {
        return frameBuffer.getHeight();
    }
}