package kr.ac.kpu.game.kim2015182005.finalproject.framework.res.bitmap;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameTimer;

public class FrameAnimationBitmap {
    private static final String TAG = FrameAnimationBitmap.class.getSimpleName();
    private SharedBitmap sbmp;
    private int frameWidth;
    private int frames;
    private int fps;
    private Rect srcRect = new Rect();
    private RectF dstRect = new RectF();
    private GameTimer timer;
    private Paint paint;
    private boolean hit=false;
    private int index;
    public FrameAnimationBitmap(int resId, int framesPerSecond, int frameCount) {
        this.sbmp = SharedBitmap.load(resId);
        this.fps = framesPerSecond;
        if (frameCount == 0) {
            this.frameWidth = sbmp.getHeight();
            frameCount = sbmp.getWidth() / this.frameWidth;
        } else {
            this.frameWidth = sbmp.getWidth() / frameCount;
        }
        paint=new Paint();
        this.frames = frameCount;
        this.timer = new GameTimer(frames, framesPerSecond);
        srcRect.top = 0;
        srcRect.bottom = sbmp.getHeight();
    }

    public void draw(Canvas canvas, float x, float y) {
        int halfWidth = frameWidth / 2;
        int halfHeight = sbmp.getHeight() / 2;
        dstRect.left = x - halfWidth;
        dstRect.top = y - halfHeight;
        dstRect.right = x + halfWidth;
        dstRect.bottom = y + halfHeight;
        draw(canvas, dstRect, paint);
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public void update(){
         index= timer.getIndex();
    }
    public void draw(Canvas canvas, RectF rect, Paint paint) {
        srcRect.left = frameWidth * index;
        srcRect.right = srcRect.left + frameWidth;
        if(this.hit) {
            ColorFilter colorFilter = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            this.paint.setColorFilter(colorFilter);
        }
        canvas.drawBitmap(sbmp.getBitmap(), srcRect, rect, this.paint);
    }
    public void setBitmapResource(int resId) {
        sbmp = SharedBitmap.load(resId);
    }

    public void reset() {
        timer.reset();
    }

    public boolean done() {
        return timer.done();
    }

    public void draw(Canvas canvas, Matrix matrix) {
        canvas.drawBitmap(sbmp.getBitmap(), matrix, paint);
    }
    public void setAlpha(int alpha){
        paint.setAlpha(alpha);
    }
    public int getWidth() {
        return frameWidth;
    }
    public int getHeight() {
        return sbmp.getHeight();
    }

    public void draw(Canvas canvas, float x, float y, int radius) {
        dstRect.left = x - radius;
        dstRect.top = y - radius;
        dstRect.right = x + radius;
        dstRect.bottom = y + radius;
        draw(canvas, dstRect, paint);
    }
    public void draw(Canvas canvas, float x, float y, int xRadius, int yRadius) {
        dstRect.left = x - xRadius;
        dstRect.top = y - yRadius;
        dstRect.right = x + xRadius;
        dstRect.bottom = y + yRadius;
        draw(canvas, dstRect, paint);
    }
}
