package kr.ac.kpu.game.exgame.gameobj;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.media.MediaPlayer;

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

    public void fire() {
        if (state != State.idle) {
            return;
        }
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
        gw.add(fb);


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
            fabShoot.draw(canvas, x +shootOffset, y );
        }
    }
}
