package com.example.carpc.instruments;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.view.SurfaceHolder;

import androidx.annotation.RequiresApi;

class DrawThread extends Thread {

    private boolean running = false;
    private SurfaceHolder surfaceHolder;
    public static double capacity;
    Paint paint = new Paint();
    RectF rectf;

    public DrawThread(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void updateCapacity(Double capacity) {
        this.capacity = capacity;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void run() {
        Canvas canvas;
        while (running) {
            canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas(null);
                if (canvas == null) {
                    continue;
                }

                float borderWidth = canvas.getHeight() / 25f;
                float borderRadius = borderWidth * 3f;
                float batteryPlus = borderWidth * 3f;

                paint.setARGB(255, 170, 170, 170);
                RectF rectangle1 = new RectF(0, 0,
                        canvas.getWidth() - batteryPlus,
                        canvas.getHeight());
                canvas.drawRoundRect(rectangle1, borderRadius, borderRadius, paint);

                paint.setColor(Color.BLACK);
                RectF rectangle2 = new RectF(borderWidth, borderWidth,
                        canvas.getWidth() - batteryPlus - borderWidth,
                        canvas.getHeight() - borderWidth);
                canvas.drawRoundRect(rectangle2, borderRadius, borderRadius, paint);

                int a = 255;
                int r = capacity < 30 ? 220 : 0;
                int g = r > 0 ? 0 : 130;
                int b = 0;
                paint.setARGB(a, r, g, b);

                RectF rectangle3 = new RectF(
                        borderWidth * 2f,
                        borderWidth * 2f,
                        (float) ((canvas.getWidth() - batteryPlus - borderWidth * 2f) / 100f * capacity),
                        canvas.getHeight() - borderWidth * 2f);
                canvas.drawRoundRect(rectangle3, borderRadius, borderRadius, paint);

                paint.setARGB(a, 170, 170, 170);
                RectF rectangle4 = new RectF(canvas.getWidth() - batteryPlus,
                        canvas.getHeight() / 4f,
                        canvas.getWidth() - batteryPlus / 2f,
                        canvas.getHeight() / 4f * 3f);
                canvas.drawRoundRect(rectangle4, borderRadius, borderRadius, paint);

                paint.setTextSize(canvas.getHeight() / 2f);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(String.valueOf(capacity) + "%",
                        (float) canvas.getWidth() / 2f - batteryPlus,
                        ((float) canvas.getHeight() / 2f) + (paint.getTextSize() / 3f),
                        paint);
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}