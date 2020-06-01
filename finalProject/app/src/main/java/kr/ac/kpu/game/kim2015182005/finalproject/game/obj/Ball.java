package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import android.content.res.Resources;
import android.graphics.Canvas;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.game.framework.GameWorld;
import kr.ac.kpu.game.kim2015182005.finalproject.game.iface.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.game.res.bitmap.FrameAnimationBitmap;

public class Ball implements GameObject {

    private static final String TAG = Ball.class.getSimpleName();
    private static final int FRAMES_PER_SECOND = 6;
    public static final int FRAME_COUNT = 0;
    private final FrameAnimationBitmap fab;
    private final int halfSize;
    private float dy;
    private float dx;
    private float x;
    private float y;


    public Ball(Resources res, float x, float y, float dx, float dy){
        fab=new FrameAnimationBitmap(R.mipmap.fireball_128_24f,FRAMES_PER_SECOND, FRAME_COUNT);
        halfSize=fab.getHeight()/2;
        this.x=x;
        this.y=y;
        this.dy=dy;
        this.dx=dx;
    }

    public void update(){
        GameWorld gw=GameWorld.get();
        x+=dx;
        if(dx>0&&x>gw.getRight()-halfSize||dx<0&&x<gw.getLeft()+halfSize){
            dx*=-1;
        }
        y+=dy;
        if(dy>0&&y>gw.getBottom()-halfSize||dy<0&&y<gw.getTop()+halfSize){
            dy*=-1;
        }
        //Log.d(TAG,"index="+index);
    }
    public void draw(Canvas canvas){
        fab.draw(canvas,x,y);
    }

}
