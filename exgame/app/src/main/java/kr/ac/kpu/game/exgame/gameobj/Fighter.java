package kr.ac.kpu.game.exgame.gameobj;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.animation.BounceInterpolator;

import kr.ac.kpu.game.exgame.R;
import kr.ac.kpu.game.exgame.sound.SoundEffects;
import kr.ac.kpu.game.exgame.util.FrameAnimationBitmap;

public class Fighter implements GameObject{

    private static final String TAG = Fighter.class.getSimpleName();
    private static final int FRAMES_PER_SECOND = 10;
    public static final int FRAME_COUNT = 5;
    private final FrameAnimationBitmap fabIdle;
    private final FrameAnimationBitmap fabShoot;
    private final int halfSize;
    private final int shootOffset;
    private float x;
    private float y;

    public void setScale(float scale){
        //Log.v(TAG,"setScale"+this.scale);
        this.scale=scale;
    }
    private float scale;
    //private long firedOn;

    public void fire() {
        if (state != State.idle) {
            return;
        }

       //firedOn=GameWorld.get().getCurrentTimeNanos();
        ObjectAnimator oa=ObjectAnimator.ofFloat(this,"scale",1.0f,2.0f);
        oa.setDuration(300);
        oa.setInterpolator(new BounceInterpolator());
        oa.start();
        state = State.shoot;
        fabShoot.reset();
     //   mediaPlayer.start();
        SoundEffects.get().play(R.raw.hadouken);
    }
    private void addFireBall(){
        int height=fabIdle.getHeight();
        int fx=(int)(x+height*0.8f);
        int fy=(int)(y-height*0.1f);

        int speed=halfSize/10;
        GameWorld gw=GameWorld.get();
        FireBall fb=new FireBall(fx,fy,speed);
        GameWorld.get().add(GameWorld.Layer.missile,fb);
        //gw.add(fb);


    }

    private  enum  State{
        idle,shoot
    }
    private State state=State.idle;
    public Fighter( float x, float y){
        GameWorld gw=GameWorld.get();
        Resources res = gw.getResources();
        fabIdle = FrameAnimationBitmap.load(res,R.mipmap.ryu,FRAMES_PER_SECOND, FRAME_COUNT);
        fabShoot = FrameAnimationBitmap.load(res,R.mipmap.ryu_1,FRAMES_PER_SECOND, FRAME_COUNT);
        shootOffset=fabShoot.getHeight()*32/100;
        halfSize= fabIdle.getHeight()/2;
        this.x=x;
        this.y=y;

       // Context context = gw.getContext();
      //  this.mediaPlayer= MediaPlayer.create(context,R.raw.hadouken);
    }

    public void update(){
        if(state==State.shoot){
            boolean done=fabShoot.done();
            if(done){
                state=State.idle;
                fabIdle.reset();
                addFireBall();
            }
        }
//        FrameAnimationBitmap fab=(state==State.idle)?fabIdle:fabShoot;
//        boolean done=fab.done();
//        if(done){
//            state=(state==State.idle)?State.shoot:State.idle;
//        }
    }
    public void draw(Canvas canvas) {
        if (state == State.idle) {
            fabIdle.draw(canvas, x , y);
        }
        else {
            //float now=GameWorld.get().getCurrentTimeNanos();
            //float scale= (float) (1+(now-firedOn)/1_000_000_000.0);
            canvas.save();
            canvas.scale(scale,scale,x,y);
            fabShoot.draw(canvas, x +shootOffset, y );
            canvas.restore();
        }
    }
}
