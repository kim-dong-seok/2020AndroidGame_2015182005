package kr.ac.kpu.game.exgame.game.obj;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.kpu.game.exgame.R;
import kr.ac.kpu.game.exgame.game.iface.GameObject;
import kr.ac.kpu.game.exgame.game.res.bitmap.SharedBitmap;

public class Joystick implements GameObject {
    private static final String TAG = Joystick.class.getSimpleName();
    private final SharedBitmap sbmp;
    private final float x,y;
    private boolean down;
    private float xDown,yDown;
    private double angle;


    public Joystick(float x, float y) {
        this.x=x;
        this.y=y;
        this.sbmp= SharedBitmap.load(R.mipmap.joystick);
        this.down=false;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        sbmp.draw(canvas,x,y);
    }

    public void onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                xDown=event.getX();
                yDown=event.getY();
                down=true;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx=event.getX()-xDown;
                float dy=event.getY()-yDown;
                onMove(dx,dy);
                break;
            default:
                down=false;
        }
    }

    private void onMove(float dx, float dy) {
        this.angle=Math.atan2(dy,dx);
        Log.d(TAG,"angle = "+angle);
    }
    public int getHorzDirection(){
        if (!down) return 0;
        int dir = angle < Math.PI / 2 && angle > -Math.PI / 2 ? 1 : -1;
        Log.v(TAG, "Dir = " + dir);
        return dir;

    }
}
