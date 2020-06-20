package kr.ac.kpu.game.kim2015182005.finalproject.game.scene;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.util.Log;
import android.util.LogPrinter;
import android.view.animation.OvershootInterpolator;

import java.util.ArrayList;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameScene;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameTimer;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.RotateBitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.TextObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.ui.Button;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.ui.TouchManager;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.BGBlack;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.JumpText;

public class StoryScene extends GameScene {
    private static final String TAG = StoryScene.class.getSimpleName();
    private GameTimer timer;
    private GameTimer jumpTimer;
    private BGBlack bgBlack,bgBlack2;
    private BitmapObject bg,bg_frame;
    private boolean start=false;
    private int to_count1,to_count2,to_OrginCount2;

    private TextObject[][] to;
    private String[][] s={
            {"-","-","-","이"," ","세","상","은",","," ","보","물","로"," ","가","득"," ","차"," ","있","다"," "," "," "," "},
            {"찾","고","자"," ","하","는"," ","것","이"," ","어","딘","가","에"," ","반","드","시"," ","있","을"," ","것","이","다"},
            { "자",","," ","바","다"," ","저","편","으","로"," ","저","어"," ","나","가","자"," "," "," "," "," "," "," "," "},
            {"큰"," ","세","계","가",","," ","그","곳","에"," ","기","다","리","고"," ","있","다","-","-","-"," "," "," "," "}
    };

    public enum Layer {
        bg, enemy, player, ui, COUNT
    }
    private int jumpCount=0;
    private static StoryScene instance;

    @Override
    protected int getLayerCount() {
        return Layer.COUNT.ordinal();
    }

    @Override
    public void enter() {
        super.enter();
        LoadingScene lscene = new LoadingScene();
        lscene.push();
        TitleScene.get().getBgmPlayer().setBGM(R.raw.a_sea_breeze_blows);
        TitleScene.get().getBgmPlayer().startBGM();
        instance=this;
        setTransparent(true);
        initObjects();

        int mdpi_100 = UiBridge.y(100);

    }



    @Override
    public void update() {
        super.update();
       // Log.d(TAG,"bgBlack.isFlashDone()"+start);
        if(bgBlack.isFlashDone()){
        if(to_count1<4&&to_count2==0){
            to[to_count1][to_count2].flash(255);
        }
        if(to_count1<4&&to[to_count1][to_count2].isFlashDone()) {

            to_count2+=1;
            if(to_count2>=25){
                to_count1+=1;
                to_count2=0;
            }
            else{
                to[to_count1][to_count2].flash(255);
            }

        }
        if(start &&bgBlack.isFlashDone()){
            MainScene scene=new MainScene();
            scene.push();
        }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);


    }

    private void initObjects() {
        to_count1=0;
        to_count2=0;
        to_OrginCount2=0;
        int screenWidth = UiBridge.metrics.size.x;
        int screenHeight = UiBridge.metrics.size.y;

        int cx = UiBridge.metrics.center.x;
        int cy = UiBridge.metrics.center.y;
        timer=new GameTimer(3,2);
        jumpTimer=new GameTimer(1,5);



        bg=new BitmapObject(cx,cy,screenWidth,screenHeight,R.mipmap.tressa_chapter1_story_bg);
        gameWorld.add(Layer.bg.ordinal(),bg);

        bgBlack2=new BGBlack(0,0,screenWidth,screenHeight,"#000000");
        bgBlack2.alpha(150);
        gameWorld.add(Layer.bg.ordinal(), bgBlack2);
        bg_frame=new BitmapObject(cx,cy,screenWidth,screenHeight,R.mipmap.story_bg_frame);
        bg_frame.alpha(200);
        gameWorld.add(Layer.bg.ordinal(), bg_frame);
        bgBlack=new BGBlack(0,0,screenWidth,screenHeight,"#000000");
        gameWorld.add(Layer.ui.ordinal(), bgBlack);
        bgBlack.setFlashOn(true);
        bgBlack.alpha(255);
        bgBlack.setFlashSpeed(5);

        bgBlack.flash(255);
        //Log.d(TAG,"asdasfgasfda"+s.length+"asdasdsad"+s[0].length);
        to=new TextObject[s.length][s[0].length];
        for(int i=0;i<(s.length);i++){
            for(int j=0;j<s[i].length;j++) {
                to[i][j] = new TextObject(s[i][j], 0+UiBridge.x(100)+UiBridge.x(20)* j,0+UiBridge.y(80)+UiBridge.y(70)* i , 60, "#FFFFFF", true);
                to[i][j].alpha(0);
                to[i][j].setFlashSpeed(90);
                gameWorld.add(Layer.enemy.ordinal(), to[i][j]);
            }
        }
        TouchManager tm = new TouchManager(UiBridge.metrics.size.x/4, UiBridge.metrics.size.y/4,UiBridge.metrics.size.x/4*3,UiBridge.metrics.size.y/4*3);
        tm.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                if(!start) {
                    start = true;
                    bgBlack.flash(255);
                }
            }
        });
        gameWorld.add(Layer.ui.ordinal(), tm);
    }




    @Override
    public void onBackPressed() {
    }

    public static StoryScene get(){
        return instance;
    }
}
