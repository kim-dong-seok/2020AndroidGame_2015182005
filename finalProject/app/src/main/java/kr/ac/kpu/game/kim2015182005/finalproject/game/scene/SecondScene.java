package kr.ac.kpu.game.kim2015182005.finalproject.game.scene;

import android.app.Dialog;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameScene;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameTimer;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.AnimObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.FlashTextObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.ScoreObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.TextObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.bg.MapBackground;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.ui.Button;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.ui.TouchManager;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.BGBlack;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.Ball;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.CharacterBackground;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.MovingButton;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.Player;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.SelectWindow;

public class SecondScene extends GameScene {
    private static final String TAG = SecondScene.class.getSimpleName();

    public enum Layer {
        bg, enemy, player, ui,ui2, COUNT
    }

    private GameTimer timer;
    private MapBackground map;
    private CharacterBackground[] characterBackgrounds;
    private int[][] mapPos={{1640,1035},{1620,1300},{1310,1240},
            {930,1145},{850,980},{1050,700},
            {1310,710},{1435,830}};
    private int characterSelect=0;
    private boolean moveDone;
    private boolean changeDone;
    private BGBlack hint_bg1;
    private BGBlack hint_bg2;
    private TextObject hint_text;
    private boolean selectWindow=false;
    @Override
    protected int getLayerCount() {
        return Layer.COUNT.ordinal();
    }

    @Override
    public void update() {
        super.update();
//        Log.d(TAG, "Score: " + timer.getRawIndex());
//        if (timer.done()) {
//            scoreObject.add(100);
//            timer.reset();
//        }
        if(map.posCheck()&&!changeDone) {
            moveDone=true;
            characterBackgrounds[characterSelect].flash();
            changeDone=true;
        }
    }

    @Override
    public void enter() {
        super.enter();
        initObjects();
    }

    private void initObjects() {
        moveDone=true;
        changeDone=true;
        characterBackgrounds=new CharacterBackground[8];
        int sw = UiBridge.metrics.size.x;
        int sh = UiBridge.metrics.size.y;
        int cx = UiBridge.metrics.center.x;
        int cy = UiBridge.metrics.center.y;
        //mediaPlayer = MediaPlayer.create(UiBridge.getView().getContext() , R.raw.main );
        //mediaPlayer.start();
        int screenHight = UiBridge.metrics.size.y;
        int mdpi_100 = UiBridge.x(100);
        map=new MapBackground(UiBridge.metrics.center.x,UiBridge.metrics.center.y,UiBridge.metrics.size.x,UiBridge.metrics.size.y, 1650,1050,R.mipmap.world_map);
        SecondScene.get().getGameWorld().add(SecondScene.Layer.bg.ordinal(),map);

        hint_bg1=new BGBlack(0,UiBridge.metrics.size.y-screenHight/10,UiBridge.metrics.size.x,UiBridge.metrics.size.y,"#9a958a");
        SecondScene.get().getGameWorld().add(FirstScene.Layer.bg.ordinal(),hint_bg1);
        hint_bg2=new BGBlack(0,UiBridge.metrics.size.y-screenHight/10+UiBridge.y(2),UiBridge.metrics.size.x,UiBridge.metrics.size.y,"#000000");
        SecondScene.get().getGameWorld().add(FirstScene.Layer.bg.ordinal(),hint_bg2);
        hint_text=new TextObject("이야기를 시작할 주인공을 선택해주세요.",UiBridge.metrics.center.x-UiBridge.x(150),UiBridge.metrics.size.y-UiBridge.y(5),50,"#9a958a",true);
        SecondScene.get().getGameWorld().add(FirstScene.Layer.bg.ordinal(),hint_text);

        int screenWidth = UiBridge.metrics.size.x;
        RectF rbox = new RectF(UiBridge.x(-52), UiBridge.y(20), UiBridge.x(-20), UiBridge.y(62));
        timer = new GameTimer(2, 1);
        makeCB();
        characterBackgrounds[characterSelect].flash();
        int y = UiBridge.metrics.center.y;
//        y += UiBridge.y(100);
        y += UiBridge.y(100);
        final TouchManager tm = new TouchManager(UiBridge.metrics.size.x/4, UiBridge.metrics.size.y/4,UiBridge.metrics.size.x/4*3,UiBridge.metrics.size.y/4*3);
        tm.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                if(!selectWindow) {
                    selectWindow=true;
                    tm.setTouchable(false);
                    gameWorld.add(SecondScene.Layer.ui.ordinal(), new SelectWindow(UiBridge.metrics.center.x, UiBridge.metrics.center.y, "트레사의 이야기를 시작하시겠습니까?", "예", "아니요"));
                }
            }
        });
        gameWorld.add(SecondScene.Layer.ui.ordinal(), tm);


        MovingButton LButton = new MovingButton(cx-UiBridge.x(320), cy,UiBridge.x(30),UiBridge.y(100) , R.mipmap.left_btn,false,5);
        LButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                if(!selectWindow) {
                if(characterBackgrounds[characterSelect].flashDone()&&changeDone){
                    characterBackgrounds[characterSelect].flash();
                    characterSelect-=1;
                    if(characterSelect<0) {
                        characterSelect=7;
                    }
                    changeCB(characterSelect);
                    changeDone=false;
                }
                }
            }

        });
        gameWorld.add(SecondScene.Layer.ui.ordinal(), LButton);
        MovingButton RButton = new MovingButton(cx+UiBridge.x(320), cy,UiBridge.x(30),UiBridge.y(100) , R.mipmap.right_btn,true,5);
        RButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                if(!selectWindow) {
                if(characterBackgrounds[characterSelect].flashDone()&&changeDone){
                    characterBackgrounds[characterSelect].flash();
                    characterSelect+=1;
                    if(characterSelect>7) {
                        characterSelect=0;
                    }
                    changeCB(characterSelect);
                    changeDone=false;
                }
            }}
        });
        gameWorld.add(SecondScene.Layer.ui.ordinal(), RButton);

    }
    public static SecondScene get() {
        return (SecondScene) GameScene.getTop();
    }
    private void makeCB(){
        for(int i=0;i<8;++i){
            characterBackgrounds[i]=new CharacterBackground(i);
            characterBackgrounds[i].setCBAlpha(0);
        }
    }

    private void changeCB(int x){
        map.setNewPos(mapPos[x][0],mapPos[x][1]);
        moveDone=false;


    }
}
