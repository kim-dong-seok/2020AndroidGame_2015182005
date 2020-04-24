package kr.ac.kpu.game.exgame.gameobj;

import android.content.res.Resources;
import android.graphics.Canvas;

import kr.ac.kpu.game.exgame.R;
import kr.ac.kpu.game.exgame.util.FrameAnimationBitmap;

public class Bullet implements GameObject{

    private static final String TAG = Bullet.class.getSimpleName();
    public static final int FRAMES_PER_SECOND = 6;
    public static final int FRAME_COUNT = 13;
    private static float BULLET_SPEED=1500;
    private final FrameAnimationBitmap fab;
    private final int halfSize;

    private float x;
    private float y;


    public Bullet( float x, float y){
        GameWorld gw=GameWorld.get();
        Resources res=gw.getResources();
        fab= FrameAnimationBitmap.load(res,R.mipmap.metal_slug_missile,FRAMES_PER_SECOND, FRAME_COUNT);
        halfSize=fab.getHeight()/2;
        this.x=x;
        this.y=y;
    }

    public void update(){
        GameWorld gw=GameWorld.get();
//        x+=dx;
//        if(dx>0&&x>gw.getRight()-halfSize||dx<0&&x<gw.getLeft()+halfSize){
//            dx*=-1;
//        }
        y-=BULLET_SPEED*gw.getTimeDiffInSecond();
        if(y<gw.getTop()-halfSize){
            gw.remove(this);
        }
        //Log.d(TAG,"index="+index);
    }
    public void draw(Canvas canvas){
        fab.draw(canvas,x,y);
    }

}
