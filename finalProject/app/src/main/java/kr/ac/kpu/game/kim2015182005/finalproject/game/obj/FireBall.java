package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import android.content.res.Resources;
import android.graphics.Canvas;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.game.framework.GameWorld;
import kr.ac.kpu.game.kim2015182005.finalproject.game.iface.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.game.res.bitmap.FrameAnimationBitmap;

public class FireBall implements GameObject {

    private static final String TAG = FireBall.class.getSimpleName();
    private static final int FRAMES_PER_SECOND = 10;
    public static final int FRAME_COUNT_FIRE = 2;
    public static final int FRAME_COUNT_FLY = 6;
    private final FrameAnimationBitmap fabFire;
    private final FrameAnimationBitmap fabFly;
    //private final int halfSize;
    private final int speed;
    private float x;
    private float y;

    public void fire() {
        if(state!= State.fire){
            return;
        }
        state= State.fly;
        fabFly.reset();

    }

    private  enum  State{
        fire, fly
    }
    private State state= State.fire;
    public FireBall(float x, float y, int speed){
        GameWorld gw=GameWorld.get();
        Resources res=gw.getResources();
        fabFire = new FrameAnimationBitmap(R.mipmap.hadoken1,FRAMES_PER_SECOND, FRAME_COUNT_FIRE);
        fabFly = new FrameAnimationBitmap(R.mipmap.hadoken2,FRAMES_PER_SECOND, FRAME_COUNT_FLY);
       // halfSize= fabFire.getHeight()/2;
        this.x=x;
        this.y=y;
        this.speed=speed;
    }

    public void update(){
        x+=speed;
        GameWorld gw=GameWorld.get();
        if(x>gw.getRight()){
            gw.remove(this);
            return;
        }
        if(state== State.fire){
            boolean done= fabFly.done();
            if(done){
                state= State.fly;
                fabFire.reset();
            }
        }
//        FrameAnimationBitmap fab=(state==State.idle)?fabIdle:fabShoot;
//        boolean done=fab.done();
//        if(done){
//            state=(state==State.idle)?State.shoot:State.idle;
//        }
    }
    public void draw(Canvas canvas) {
        if (state == State.fire) {
            fabFire.draw(canvas, x , y);
        }
        else {
            fabFly.draw(canvas, x , y );
        }
    }
}
