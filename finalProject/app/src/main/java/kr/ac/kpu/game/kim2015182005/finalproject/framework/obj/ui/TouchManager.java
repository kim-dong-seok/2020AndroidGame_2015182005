package kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.ui;

import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.Touchable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;

public class TouchManager extends GameObject implements Touchable {
    private static final String TAG = TouchManager.class.getSimpleName();
    private boolean capturing, pressed;
    private Runnable onClickRunnable;
    private boolean runOnDown;
    private RectF rectf;
    public TouchManager(float x, float y,float w,float h) {

        rectf=new RectF(x, y, w, h);

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (rectf.contains((int)e.getX(), (int)e.getY())) {
                    Log.d(TAG, "Down");
                    captureTouch();
                    capturing = true;
                    pressed = true;
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                pressed = rectf.contains((int)e.getX(), (int)e.getY());
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "Up");
                releaseTouch();
                capturing = false;
                pressed = false;
                if (rectf.contains((int)e.getX(), (int)e.getY())) {
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
