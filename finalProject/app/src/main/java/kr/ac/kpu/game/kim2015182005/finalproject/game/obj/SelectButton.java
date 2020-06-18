package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Random;

import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.Touchable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameTimer;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.TextObject;

public class SelectButton extends GameObject implements Touchable {
    private static final String TAG = SelectButton.class.getSimpleName();
    private boolean capturing, pressed;
    private Runnable onClickRunnable;
    private boolean runOnDown;
    private TextObject btnText;
    private int w,h,size,alpha;
    private float x,y;
    private BitmapObject pressBit,normalBit,drawBit;
    private Rect bound;
    private Random rand = new Random();
    public SelectButton(float x, float y, int w, int h,int alpha,String text,int size, int bgNormalResId,int bgPressResId) {
        this.w=w;
        this.h=h;
        this.x=x;
        this.y=y;
        this.size=size;
        this.alpha=alpha;
        this.btnText=new TextObject(text,x,y,size,"#FFFFFF",true);
        this.pressBit =new BitmapObject(x,y,w,h,bgNormalResId);
        this.normalBit= new BitmapObject(x,y,w,h,bgPressResId);
        int left = (int)this.x - this.w / 2, top = (int)this.y - this.h / 2;
        bound=new Rect( left, top, left + this.w, top + this.h);
        setAlpha(alpha);
       // Log.d(TAG,"asdasdasdasdsasdadas"+bound.left+","+bound.top+","+bound.right+","+bound.bottom);
    }

    @Override
    public void update() {
        pressBit.update();
        normalBit.update();
        btnText.update();
    }

    @Override
    public void draw(Canvas canvas) {
        drawBit=pressed?normalBit:pressBit;
        drawBit.draw(canvas);
        btnText.draw(canvas);
    }

    public void setAlpha(int alpha) {
        pressBit.alpha(alpha);
        normalBit.alpha(alpha);
        btnText.alpha(alpha);
    }

    public void buttonFlash(int maxAlpha) {
        pressBit.flash(200);
        normalBit.flash(200);
        btnText.flash(255);
    }

    @Override
    public void setFlashSpeed(int speed) {
        super.setFlashSpeed(speed);
        pressBit.setFlashSpeed(speed);
        normalBit.setFlashSpeed(speed);
        btnText.setFlashSpeed(speed);
    }

    @Override
    public boolean isFlashDone() {
        return (pressBit.isFlashDone()&&normalBit.isFlashDone()&&btnText.isFlashDone());
    }

    @Override
    public void move(float dx, float dy) {
        super.move(dx, dy);
        int left = (int)this.x - this.w / 2, top = (int)this.y - this.h / 2;
        bound.set(left, top, left + this.w, top + this.h);

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (this.bound.contains((int)e.getX(), (int)e.getY())) {
                    Log.d(TAG, "Down");
                    captureTouch();
                    capturing = true;
                    pressed = true;
                    Log.d(TAG, "draw: "+pressed);
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                pressed =this.bound.contains((int)e.getX(), (int)e.getY());
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "Up");
                releaseTouch();
                capturing = false;
                pressed = false;
                if (this.bound.contains((int)e.getX(), (int)e.getY())) {
                    Log.d(TAG, "TouchUp Inside");
                    if (onClickRunnable != null) {
                        onClickRunnable.run();
                    }
                }
                return true;
        }
        return false;
    }
    public void setOnClickRunnable(Runnable runnable) {
        setOnClickRunnable(false, runnable);
//        this.runOnDown = false;
//        this.onClickRunnable = runnable;
    }

    public void setOnClickRunnable(boolean runOnDown, Runnable runnable) {
        this.runOnDown = runOnDown;
        this.onClickRunnable = runnable;
    }
}
