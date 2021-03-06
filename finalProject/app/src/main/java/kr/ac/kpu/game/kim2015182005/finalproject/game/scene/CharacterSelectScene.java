package kr.ac.kpu.game.kim2015182005.finalproject.game.scene;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameScene;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameTimer;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.AnimObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.RotateBitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.TextObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.bg.MapBackground;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.ui.TouchManager;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.BGBlack;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.bg.CharacterBackground;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.ui.MovingButton;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.ui.SelectWindow;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.ui.SelectWindowOneAnswer;

public class CharacterSelectScene extends GameScene {
    private static final String TAG = CharacterSelectScene.class.getSimpleName();

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
    public boolean selectWindowOn,selectWindowA1On;
    private SelectWindow SWindow,backPressedWindow;
    private SelectWindowOneAnswer SWindow1a;
    private TouchManager tm;
    private MovingButton LButton;
    private MovingButton RButton;
    private AnimObject select_op;
    private static CharacterSelectScene instance;
    private boolean selectBPWindow,start,sceneChange;
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

       // Log.d(TAG,"isFlashOn()"+characterBackgrounds[0].isFlashOn()+"characterBackgrounds[characterSelect].isFlashOn()"+characterBackgrounds[0].isFlashDone());
        if(map.posCheck()&&!changeDone) {
            //Log.e(TAG,"isFlashOn()"+characterBackgrounds[0].isFlashOn()+"characterBackgrounds[characterSelect].isFlashOn()"+characterBackgrounds[0].isFlashDone());
            moveDone=true;
            characterBackgrounds[characterSelect].flash();

            changeDone=true;
        }

       //Log.d(TAG,"characterBackgrounds[characterSelect]"+characterBackgrounds[characterSelect].getOp().getAlpha());
        if(start){
            if(!sceneChange) {
                TitleScene.get().getSoundEffects().play(R.raw.character_select_tressa, 1.0f);

                select_op = new AnimObject(UiBridge.metrics.center.x + UiBridge.x(10), UiBridge.metrics.center.y + UiBridge.y(10), UiBridge.x(172), UiBridge.y(183), R.mipmap.tressa_select, 3, 8);
                CharacterSelectScene.get().getGameWorld().add(CharacterSelectScene.Layer.ui.ordinal(), select_op);
                sceneChange=true;
            }else{
                if(select_op.getFab().done()){
                TitleScene.get().getBgmPlayer().stopBGM();
                StoryScene scene=new StoryScene();
                scene.push();
                }
            }

        }



        //Log.d(TAG,"sadsdasdafegqwewqdad"+selectWindowOn);
    }



    @Override
    public void enter() {
        super.enter();
        LoadingScene scene1 = new LoadingScene();
        scene1.push();
        instance=this;
        initObjects();
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public CharacterBackground[] getCharacterBackgrounds() {
        return characterBackgrounds;
    }

    public void setSelectWindowOn(boolean selectWindowON) {
        this.selectWindowOn = selectWindowOn;
        //Log.d(TAG,"sadsdasdafegqwewqdad"+selectWindowOn);
    }
    public boolean getSelectWindowOn() {
        return selectWindowOn;
    }

    public int getCharacterSelect() {
        return characterSelect;
    }

    private void initObjects() {
        sceneChange=false;
        start=false;
        moveDone=true;
        changeDone=true;
        characterBackgrounds=new CharacterBackground[8];
        selectWindowOn=true;
        selectWindowA1On=true;
        int sw = UiBridge.metrics.size.x;
        int sh = UiBridge.metrics.size.y;
        int cx = UiBridge.metrics.center.x;
        int cy = UiBridge.metrics.center.y;
        //mediaPlayer = MediaPlayer.create(UiBridge.getView().getContext() , R.raw.main );
        //mediaPlayer.start();
        int screenHight = UiBridge.metrics.size.y;
        int mdpi_100 = UiBridge.x(100);
        map=new MapBackground(UiBridge.metrics.center.x,UiBridge.metrics.center.y,UiBridge.metrics.size.x,UiBridge.metrics.size.y, 1650,1050,R.mipmap.world_map,2);
        CharacterSelectScene.get().getGameWorld().add(CharacterSelectScene.Layer.bg.ordinal(),map);
        selectBPWindow=false;


        RotateBitmapObject sun=new RotateBitmapObject(UiBridge.metrics.center.x-UiBridge.metrics.center.x/10, UiBridge.metrics.center.y/3,UiBridge.x(30),UiBridge.y(30),R.mipmap.sun,0,6,true);
        gameWorld.add(CharacterSelectScene.Layer.ui2.ordinal(),sun );
        sun.setMax(45);
        sun.setReite(true);
        RotateBitmapObject moon=new RotateBitmapObject(UiBridge.metrics.center.x+UiBridge.metrics.center.x/10, UiBridge.metrics.center.y/3,UiBridge.x(31),UiBridge.y(31),R.mipmap.moon,0,6,false);
        moon.setMax(45);
        moon.setReite(true);
        gameWorld.add(CharacterSelectScene.Layer.ui2.ordinal(),moon );

        gameWorld.add(CharacterSelectScene.Layer.ui2.ordinal(), new RotateBitmapObject(UiBridge.metrics.center.x, UiBridge.metrics.center.y/10,UiBridge.x(117),UiBridge.y(117),R.mipmap.compass1,0,4,true));
        gameWorld.add(CharacterSelectScene.Layer.ui2.ordinal(), new RotateBitmapObject(UiBridge.metrics.center.x, UiBridge.metrics.center.y/10,UiBridge.x(113),UiBridge.y(113),R.mipmap.compass2,180,4,false));
        gameWorld.add(CharacterSelectScene.Layer.ui2.ordinal(),new BitmapObject(UiBridge.metrics.center.x, UiBridge.metrics.center.y/6,UiBridge.x(240),UiBridge.y(60),R.mipmap.ribbon));


        hint_bg1=new BGBlack(0,UiBridge.metrics.size.y-screenHight/10,UiBridge.metrics.size.x,UiBridge.metrics.size.y,"#9a958a");
        CharacterSelectScene.get().getGameWorld().add(CharacterSelectScene.Layer.bg.ordinal(),hint_bg1);
        hint_bg2=new BGBlack(0,UiBridge.metrics.size.y-screenHight/10+UiBridge.y(2),UiBridge.metrics.size.x,UiBridge.metrics.size.y,"#000000");
        CharacterSelectScene.get().getGameWorld().add(CharacterSelectScene.Layer.bg.ordinal(),hint_bg2);
        hint_text=new TextObject("이야기를 시작할 주인공을 선택해주세요.",UiBridge.metrics.center.x-UiBridge.x(150),UiBridge.metrics.size.y-UiBridge.y(15),50,"#9a958a",true);
        CharacterSelectScene.get().getGameWorld().add(CharacterSelectScene.Layer.bg.ordinal(),hint_text);

        int screenWidth = UiBridge.metrics.size.x;
        RectF rbox = new RectF(UiBridge.x(-52), UiBridge.y(20), UiBridge.x(-20), UiBridge.y(62));
        timer = new GameTimer(2, 1);
        makeCB();
        characterBackgrounds[characterSelect].flash();
        int y = UiBridge.metrics.center.y;
//        y += UiBridge.y(100);
        y += UiBridge.y(100);
        SWindow=new SelectWindow(UiBridge.metrics.center.x, UiBridge.metrics.center.y, "트레사의 이야기를 시작하시겠습니까?", "예", "아니요");
        gameWorld.add(CharacterSelectScene.Layer.ui.ordinal(), SWindow);
        SWindow.setAlpha(0);
        SWindow1a=new SelectWindowOneAnswer(UiBridge.metrics.center.x, UiBridge.metrics.center.y,  "추후 업데이트를 기대해주세요!", "확인");
        gameWorld.add(CharacterSelectScene.Layer.ui.ordinal(), SWindow1a);
        SWindow1a.setAlpha(0);

        LButton= new MovingButton(cx-UiBridge.x(320), cy,UiBridge.x(30),UiBridge.y(100) , R.mipmap.left_btn,false,5);
        LButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                if(selectWindowOn&&!selectBPWindow) {
                if(characterBackgrounds[characterSelect].flashDone()&&changeDone){
                    characterBackgrounds[characterSelect].setFlashOn(false);
                    characterBackgrounds[characterSelect].setCBAlpha(0);
                    characterBackgrounds[characterSelect].storyBgMove(false);
                    TitleScene.get().getSoundEffects().play(R.raw.menu_move,1.0f);
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
        gameWorld.add(CharacterSelectScene.Layer.ui.ordinal(), LButton);
        RButton = new MovingButton(cx+UiBridge.x(320), cy,UiBridge.x(30),UiBridge.y(100) , R.mipmap.right_btn,true,5);
        RButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                if(selectWindowOn&&!selectBPWindow) {
                if(characterBackgrounds[characterSelect].flashDone()&&changeDone){
                    characterBackgrounds[characterSelect].setFlashOn(false);
                    characterBackgrounds[characterSelect].setCBAlpha(0);
                    characterBackgrounds[characterSelect].storyBgMove(false);
                    characterSelect+=1;
                    TitleScene.get().getSoundEffects().play(R.raw.menu_move,1.0f);
                    if(characterSelect>7) {
                        characterSelect=0;

                    }
                    changeCB(characterSelect);
                    changeDone=false;
                }
            }}
        });
        gameWorld.add(CharacterSelectScene.Layer.ui.ordinal(), RButton);
        tm = new TouchManager(UiBridge.metrics.size.x/4, UiBridge.metrics.size.y/4,UiBridge.metrics.size.x/4*3,UiBridge.metrics.size.y/4*3);
        tm.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                TitleScene.get().getSoundEffects().play(R.raw.menu_window,1.0f);
                if(selectWindowOn&&SWindow.isSWFlashDone()&&SWindow1a.isSWFlashDoneA1()&&!selectBPWindow){

                if(characterSelect==0) {
                    TitleScene.get().getSoundEffects().play(R.raw.select_button,1.0f);
                    setTouchable(false);
                    SWindow.flashSW();
                } else {
                    TitleScene.get().getSoundEffects().play(R.raw.select_button,1.0f);
                    setTouchable(false);
                    //Log.d(TAG,"characterSelect)"+characterSelect);
                    SWindow1a.flashSW1A();
                    }
                }

            }
        });
        gameWorld.add(CharacterSelectScene.Layer.ui.ordinal(), tm);

    }
    public void setTouchable(boolean touchable){
        selectWindowOn=touchable;
        tm.setTouchable(touchable);
        RButton.setTouchable(touchable);
        LButton.setTouchable(touchable);
    }

    public static CharacterSelectScene get() {
        return instance;
    }
    private void makeCB(){
        for(int i=0;i<8;++i){
            characterBackgrounds[i]=new CharacterBackground(i);
            gameWorld.add(CharacterSelectScene.Layer.ui.ordinal(), characterBackgrounds[i]);
            characterBackgrounds[i].setCBAlpha(0);
        }
    }

    private void changeCB(int x){
        map.setNewPos(mapPos[x][0],mapPos[x][1]);
        moveDone=false;


    }
    @Override
    public void onBackPressed() {
        if(selectWindowOn&&SWindow.isSWFlashDone()&&SWindow1a.isSWFlashDoneA1()){
        TitleScene.get().getSoundEffects().play(R.raw.menu_window,1.0f);
        CharacterSelectBackPressScene scene = new CharacterSelectBackPressScene();
        scene.push();}

    }
}
