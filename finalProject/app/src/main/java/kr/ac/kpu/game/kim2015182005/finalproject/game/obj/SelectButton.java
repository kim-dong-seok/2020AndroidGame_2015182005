package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.Touchable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameTimer;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.TextObject;

public class SelectButton extends BitmapObject implements Touchable {
    private static final String TAG = SelectButton.class.getSimpleName();
    private final Drawable bgNormal;
    private final Drawable bgPress;
    private boolean capturing, pressed;
    private Runnable onClickRunnable;
    private boolean runOnDown;
    private TextObject btnText;
    private int w,h;

    public SelectButton(float x, float y, int w, int h,String text, int bgNormalResId,int bgPressResId) {
        super(x, y, 0, 0, bgNormalResId);
        this.w=w;
        this.h=h;
        this.btnText=new TextObject(text,x,y,h,"#FFFFFF",true);
        this.bgNormal = (Drawable) UiBridge.getResources().getDrawable(bgNormalResId);
        this.bgPress = (Drawable) UiBridge.getResources().getDrawable(bgPressResId);
        int left = (int)this.x - this.w / 2, top = (int)this.y - this.h / 2;
        this.bgNormal.setBounds(left, top, left + this.w, top + this.h);
        this.bgPress.setBounds(left, top, left + this.w, top + this.h);

        Log.d(TAG, "draw: "+left+","+top+","+left + this.w+","+top + this.h);

    }

    @Override
    public void draw(Canvas canvas) {
        Drawable bg = pressed ? bgPress : bgNormal;
        bg.draw(canvas);
        btnText.draw(canvas);

    }
    @Override
    public void move(float dx, float dy) {
        super.move(dx, dy);
        int left = (int)this.x - this.w / 2, top = (int)this.y - this.h / 2;
        this.bgNormal.setBounds(left, top, left + this.w, top + this.h);
        this.bgPress.setBounds(left, top, left + this.w, top + this.h);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (this.bgNormal.getBounds().contains((int)e.getX(), (int)e.getY())) {
                    Log.d(TAG, "Down");
                    captureTouch();
                    capturing = true;
                    pressed = true;
                    Log.d(TAG, "draw: "+pressed);
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                pressed = this.bgNormal.getBounds().contains((int)e.getX(), (int)e.getY());
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "Up");
                releaseTouch();
                capturing = false;
                pressed = false;
                if (this.bgNormal.getBounds().contains((int)e.getX(), (int)e.getY())) {
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
