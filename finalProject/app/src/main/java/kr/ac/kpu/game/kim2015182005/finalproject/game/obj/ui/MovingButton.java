package kr.ac.kpu.game.kim2015182005.finalproject.game.obj.ui;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.Touchable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameTimer;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;

public class MovingButton extends BitmapObject implements Touchable {
    private static final String TAG = MovingButton.class.getSimpleName();
    private final Drawable btn;
    private final GameTimer timer;
    private boolean capturing, pressed;
    private Runnable onClickRunnable;
    private boolean runOnDown;
    private boolean right;
    private int distance;
    private int count;
    private int phase;
    private boolean touchable=true;
    public MovingButton(float x, float y, int w,int h, int bgNormalResId,boolean right,int distance) {
        super(x, y, w, h, bgNormalResId);
        this.right=right;
        this.distance=distance;
        this.count=0;
        this.phase=0;
        this.btn = UiBridge.getResources().getDrawable(bgNormalResId);
        int left = (int)this.x - this.width / 2, top = (int)this.y - this.height / 2;
        this.btn.setBounds(left, top, left + this.width, top + this.height);
        alpha(150);
        timer=new GameTimer(1,10);
    }

    @Override
    public void update() {
        if(timer.done()&&touchable){
        if(right){
            if(phase==0){
                count+=1;
                x+=UiBridge.x(1);
                move(x,y);
                if(count>=distance){
                    phase=1;
                    count=0;
                }
            }
            if(phase==1){
                count+=1;
                x-=UiBridge.x(1);
                move(x,y);
                if(count>=distance*2){
                    phase=2;
                    count=0;
                }
            }
            if(phase==2){
                count+=1;
                x+=UiBridge.x(1);
                move(x,y);
                if(count>=distance){
                    phase=0;
                    count=0;
                }
            }
        }
        else{
            if(phase==0){
                count+=1;
                x-=UiBridge.x(1);
                move(x,y);
                if(count>=distance){
                    phase=1;
                    count=0;
                }
            }
            if(phase==1){
                count+=1;
                x+=UiBridge.x(1);
                move(x,y);
                if(count>=distance*2){
                    phase=2;
                    count=0;
                }
            }
            if(phase==2){
                count+=1;
                x-=UiBridge.x(1);
                move(x,y);
                if(count>=distance){
                    phase=0;
                    count=0;
                }
            }
        }
            timer.reset();
        }

    }

    public void setTouchable(boolean touchable) {
        this.touchable = touchable;
    }

    @Override
    public void draw(Canvas canvas) {
        if(pressed) {
            alpha(200);
        }else{
            alpha(150);
        }
        super.draw(canvas);
    }
    @Override
    public void move(float dx, float dy) {
        int left = (int)this.x - this.width / 2, top = (int)this.y - this.height / 2;
        this.btn.setBounds(left, top, left + this.width, top + this.height);
    }
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(touchable){
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (this.btn.getBounds().contains((int)e.getX(), (int)e.getY())) {
                    Log.d(TAG, "Down");
                    captureTouch();
                    capturing = true;
                    pressed = true;
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                pressed = this.btn.getBounds().contains((int)e.getX(), (int)e.getY());
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "Up");
                releaseTouch();
                capturing = false;
                pressed = false;
                if (this.btn.getBounds().contains((int)e.getX(), (int)e.getY())) {
                    Log.d(TAG, "TouchUp Inside");
                    if (onClickRunnable != null) {
                        onClickRunnable.run();
                    }
                }
                return true;
        }
        return false;}
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
