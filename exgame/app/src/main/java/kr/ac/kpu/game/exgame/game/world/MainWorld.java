package kr.ac.kpu.game.exgame.game.world;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

import kr.ac.kpu.game.exgame.R;
import kr.ac.kpu.game.exgame.game.framework.GameWorld;
import kr.ac.kpu.game.exgame.game.iface.GameObject;
import kr.ac.kpu.game.exgame.game.obj.Ball;
import kr.ac.kpu.game.exgame.game.obj.EnemyGenerator;
import kr.ac.kpu.game.exgame.game.obj.Fighter;
import kr.ac.kpu.game.exgame.game.obj.Plane;
import kr.ac.kpu.game.exgame.game.obj.ScoreObject;
import kr.ac.kpu.game.exgame.game.obj.bg.ImageScrollBackground;
import kr.ac.kpu.game.exgame.game.obj.bg.TileScrollBackground;

public class MainWorld extends GameWorld {
    private static final int BALL_COUNT = 10;
    private static final String PREF_KEY_HIGHSCORE = "highscore";
    private static final String TAG = MainWorld.class.getSimpleName();
    private Fighter fighter;
    private EnemyGenerator enemyGenerator=new EnemyGenerator();
    private Plane plane;
    private ScoreObject scoreObject;
    private ScoreObject highScoreObject;
    private static final String PREFS_NAME="scorePrefs";
    private PlayState playState=PlayState.normal;

    public static void create() {
        if(singleton==null){
            Log.e(TAG,"gameWorld not created");
        }
        singleton=new MainWorld();
    }

    public  static  MainWorld get(){
        return (MainWorld)singleton;
    }
    private enum PlayState{
        normal,paused,gameOver
    }
    public enum Layer{
        bg,missile,enemy,player,ui,COUNT,
    }
    protected int getLayerCount(){
        return Layer.COUNT.ordinal();
    }

    public void initObjects(){
        Resources res = view.getResources();
        //objects=new ArrayList<>();

        Random rand= new Random();
        for(int i=0;i<BALL_COUNT;i++){
            float x= rand.nextFloat()*1000;
            float y= rand.nextFloat()*1000;
            float dx= (float) (rand.nextFloat()*50.0-25.0);
            float dy= (float)(rand.nextFloat()*50.0-25.0);
            add(Layer.missile,new Ball(res,x,y,dx,dy));
            //objects.add(new Ball(res,x,y,dx,dy));
        }

        float playerY=rect.bottom-100;
        plane=new Plane(res,500,playerY,0.0f,0.0f);
        add(Layer.player,plane);
        //objects.add(new Plane(res,500,500,0.0f,0.0f));
        fighter=new Fighter( 200, 700);
        add(Layer.player, fighter);

        scoreObject =new ScoreObject(800,100, R.mipmap.number_64x84);
        add(Layer.ui,scoreObject);
        highScoreObject =new ScoreObject(800,20,R.mipmap.number_24x32);
        add(Layer.ui,highScoreObject);

        add(Layer.bg,new TileScrollBackground(R.raw.earth,TileScrollBackground.Orientation.vertical,-25));
        add(Layer.bg,new ImageScrollBackground(R.mipmap.clouds,ImageScrollBackground.Orientation.vertical,100));


        //        scorePaint.setTextSize(50);
//        scorePaint.setColor(Color.BLACK);
//        scoreAnimator =ObjectAnimator.ofInt(this,"scoreDisplay",0);
        startGame();
    }

    public void add(Layer layer, final GameObject obj){
        add(layer.ordinal(),obj);
    }



    private void startGame() {
        playState=PlayState.normal;
        scoreObject.reset();

        SharedPreferences prefs=view.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int highScore=prefs.getInt(PREF_KEY_HIGHSCORE,0);
        highScoreObject.setScore(highScore);
    }

    public void endGame() {
        playState=PlayState.gameOver;
        int score=scoreObject.getScore();
        Log.v(TAG,"score"+score);
        SharedPreferences prefs=view.getContext().getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        int highScore=prefs.getInt(PREF_KEY_HIGHSCORE,0);
        if(score>highScore) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(PREF_KEY_HIGHSCORE,score);
            editor.commit();
        }

    }

    @Override
    public void update(long frameTimeNanos) {
        super.update(frameTimeNanos);
        if(playState!=PlayState.normal){
            return;
        }
        enemyGenerator.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public void addScore(int score) {
        scoreObject.addScore(score);
    }
    public void doAction() {
        fighter.fire();
    }

    public ArrayList<GameObject> objectsAt(Layer layer) {
        return objectsAt(layer.ordinal());
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action=event.getAction();
        if(action==MotionEvent.ACTION_DOWN){
            if(playState== MainWorld.PlayState.gameOver){
                startGame();
                return false;
            }
            doAction();
            plane.head(event.getX(),event.getY());
        }
        else if (action==MotionEvent.ACTION_MOVE){
            plane.head(event.getX(),event.getY());
        }
        return true;
    }
}
