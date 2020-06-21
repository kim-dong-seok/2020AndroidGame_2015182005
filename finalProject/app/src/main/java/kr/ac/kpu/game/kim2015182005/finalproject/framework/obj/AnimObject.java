package kr.ac.kpu.game.kim2015182005.finalproject.framework.obj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.res.bitmap.FrameAnimationBitmap;

public class AnimObject extends GameObject {
    private static final String TAG = AnimObject.class.getSimpleName();
    protected FrameAnimationBitmap fab;
    protected final RectF dstRect;
    protected int width;
    protected int height;
    protected int hp;

    public AnimObject(float x, float y, int width, int height, int resId, int fps, int count) {
        paint=new Paint();
        this.alphaNum=0;
        this.flashDone=true;
        this.flashOn=false;
        this.flash=false;
        fab = new FrameAnimationBitmap(resId, fps, count);
        this.x = x;
        this.y = y;
        this.dstRect = new RectF();
        if (width == 0) {
            width = UiBridge.x(fab.getWidth());
        } else if (width < 0) {
            width = UiBridge.x(fab.getWidth()) * -width / 100;
        }
        this.width = width;
        if (height == 0) {
            height = UiBridge.x(fab.getHeight());
        } else if (height < 0) {
            height = UiBridge.x(fab.getHeight()) * -height / 100;
        }
        this.height = height;
    }

    @Override
    public void update() {
        super.update();
        this.fab.update();
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public FrameAnimationBitmap getFab() {
        return fab;
    }

    public void setFab(FrameAnimationBitmap fab) {
        this.fab = fab;
    }

    public int getHp() {
        return hp;
    }

    @Override
    public float getRadius() {
        return this.width / 2;
    }
    public void setColor(String color){
        this.paint.setColor(Color.parseColor(color));
    }
    public void draw(Canvas canvas) {
        float halfWidth = width / 2;
        float halfHeight = height / 2;
        dstRect.left = x - halfWidth;
        dstRect.top = y - halfHeight;
        dstRect.right = x + halfWidth;
        dstRect.bottom = y + halfHeight;
        Paint pnt = new Paint();
        pnt.setTextSize(100);
        pnt.setColor(Color.RED);
        //canvas.drawText(String.valueOf(this.hp), this.x,this.y-height,pnt);
        fab.draw(canvas, dstRect, paint);

    }
}
