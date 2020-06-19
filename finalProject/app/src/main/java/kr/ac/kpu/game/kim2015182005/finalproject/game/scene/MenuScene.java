package kr.ac.kpu.game.kim2015182005.finalproject.game.scene;

import android.graphics.RectF;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameScene;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameTimer;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.RotateBitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.TextObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.bg.MapBackground;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.ui.TouchManager;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.BGBlack;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.CharacterBackground;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.MovingButton;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.SelectButton;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.SelectWindow;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.SelectWindowOneAnswer;

public class MenuScene extends GameScene {
    private static final String TAG = MenuScene.class.getSimpleName();

    public enum Layer {
        bg, enemy, player, ui,ui2, COUNT
    }
    private static final String[] ch_button = {
            "여행기록","스테이터스","아이템",
            "옵션","타이틀로","게임 종료"
    };
    private GameTimer timer;
    private MapBackground map;
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
    private SelectWindow SWindow;
    private SelectWindowOneAnswer SWindow1a;
    private TouchManager tm;
    private MovingButton LButton;
    private MovingButton RButton;
    private static MenuScene instance;
    private SelectButton selectButton1,selectButton2,selectButton3,selectButton4,selectButton5,selectButton6;
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


        //Log.d(TAG,"sadsdasdafegqwewqdad"+selectWindowOn);
    }

    @Override
    public void enter() {
        super.enter();
        instance=this;
        initObjects();
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
        moveDone=true;
        changeDone=true;
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
        map=new MapBackground(UiBridge.metrics.center.x,UiBridge.metrics.center.y,UiBridge.metrics.size.x,UiBridge.metrics.size.y, 760,400,R.mipmap.world_map2,2);
        gameWorld.add(MenuScene.Layer.bg.ordinal(),map);
        gameWorld.add(SecondScene.Layer.bg.ordinal(),new BitmapObject(UiBridge.metrics.center.x,UiBridge.metrics.center.y-UiBridge.metrics.size.y/20,UiBridge.metrics.size.x,UiBridge.metrics.size.y-UiBridge.metrics.size.y/10,R.mipmap.map_frame));
        RotateBitmapObject sun=new RotateBitmapObject(UiBridge.x(300), UiBridge.y(70),UiBridge.x(30),UiBridge.y(30),R.mipmap.sun,0,6,true);
        gameWorld.add(LoadingScene.Layer.bg.ordinal(),sun );
        sun.setMax(45);
        sun.setReite(true);
        RotateBitmapObject moon=new RotateBitmapObject(UiBridge.x(380), UiBridge.y(72),UiBridge.x(31),UiBridge.y(31),R.mipmap.moon,0,6,false);
        moon.setMax(45);
        moon.setReite(true);
        gameWorld.add(LoadingScene.Layer.bg.ordinal(),moon );

        gameWorld.add(LoadingScene.Layer.bg.ordinal(), new RotateBitmapObject(UiBridge.x(343), UiBridge.y(22),UiBridge.x(117),UiBridge.y(117),R.mipmap.compass1,0,4,true));
        gameWorld.add(LoadingScene.Layer.bg.ordinal(), new RotateBitmapObject(UiBridge.x(343), UiBridge.y(22),UiBridge.x(113),UiBridge.y(113),R.mipmap.compass2,180,4,false));
        gameWorld.add(LoadingScene.Layer.bg.ordinal(),new BitmapObject(UiBridge.x(339), UiBridge.y(32),UiBridge.x(240),UiBridge.y(60),R.mipmap.ribbon));

        hint_bg1=new BGBlack(0,UiBridge.metrics.size.y-screenHight/10,UiBridge.metrics.size.x,UiBridge.metrics.size.y,"#9a958a");
        gameWorld.add(LoadingScene.Layer.bg.ordinal(),hint_bg1);
        hint_bg2=new BGBlack(0,UiBridge.metrics.size.y-screenHight/10+UiBridge.y(2),UiBridge.metrics.size.x,UiBridge.metrics.size.y,"#000000");
        gameWorld.add(LoadingScene.Layer.bg.ordinal(),hint_bg2);
        hint_text=new TextObject("항목을 선택해 주세요.",UiBridge.metrics.center.x-UiBridge.x(230),UiBridge.metrics.size.y-UiBridge.y(15),50,"#9a958a",true);
        gameWorld.add(LoadingScene.Layer.bg.ordinal(),hint_text);

        int screenWidth = UiBridge.metrics.size.x;
        RectF rbox = new RectF(UiBridge.x(-52), UiBridge.y(20), UiBridge.x(-20), UiBridge.y(62));
        timer = new GameTimer(2, 1);

        int y = UiBridge.metrics.center.y;
//        y += UiBridge.y(100);
        y += UiBridge.y(100);
        BGBlack menu_bg1=new BGBlack(0,0,UiBridge.metrics.size.x/4,UiBridge.y(50),"#9a958a");
        menu_bg1.alpha(150);
        gameWorld.add(MenuScene.Layer.bg.ordinal(),menu_bg1);
        BGBlack menu_bg2=new BGBlack(0,0,UiBridge.metrics.size.x/4,UiBridge.y(49),"#000000");
        menu_bg2.alpha(150);
        gameWorld.add(MenuScene.Layer.bg.ordinal(),menu_bg2);
        TextObject menu_text=new TextObject("메뉴",UiBridge.x(60),UiBridge.y(27),60,"#FFFFFF",true);
        gameWorld.add(MenuScene.Layer.bg.ordinal(),menu_text);



        selectButton1=new SelectButton(UiBridge.x(50),UiBridge.y(90),UiBridge.x(300),UiBridge.y(40),200,ch_button[0],45,R.mipmap.menu_button_idle,R.mipmap.menu_button);
        selectButton1.setTx(UiBridge.x(70));
        selectButton1.setOnClickRunnable(new Runnable() {
                                                @Override
                                                public void run() {

                                                }
                                            }
        );
        gameWorld.add(MenuScene.Layer.ui.ordinal(),selectButton1);

        selectButton2=new SelectButton(UiBridge.x(50),UiBridge.y(140),UiBridge.x(300),UiBridge.y(40),200,ch_button[1],45,R.mipmap.menu_button_idle,R.mipmap.menu_button);
        selectButton2.setTx(UiBridge.x(77));
        selectButton2.setOnClickRunnable(new Runnable() {
                                             @Override
                                             public void run() {

                                             }
                                         }
        );
        gameWorld.add(MenuScene.Layer.ui.ordinal(),selectButton2);
        selectButton3=new SelectButton(UiBridge.x(50),UiBridge.y(190),UiBridge.x(300),UiBridge.y(40),200,ch_button[2],45,R.mipmap.menu_button_idle,R.mipmap.menu_button);
        selectButton3.setTx(UiBridge.x(61));
        selectButton3.setOnClickRunnable(new Runnable() {
                                             @Override
                                             public void run() {

                                             }
                                         }
        );
        gameWorld.add(MenuScene.Layer.ui.ordinal(),selectButton3);
        selectButton4=new SelectButton(UiBridge.x(50),UiBridge.y(240),UiBridge.x(300),UiBridge.y(40),200,ch_button[3],45,R.mipmap.menu_button_idle,R.mipmap.menu_button);
        selectButton4.setTx(UiBridge.x(55));
        selectButton4.setOnClickRunnable(new Runnable() {
                                             @Override
                                             public void run() {

                                             }
                                         }
        );
        gameWorld.add(MenuScene.Layer.ui.ordinal(),selectButton4);
        selectButton5=new SelectButton(UiBridge.x(50),UiBridge.y(290),UiBridge.x(300),UiBridge.y(40),200,ch_button[4],45,R.mipmap.menu_button_idle,R.mipmap.menu_button);
        selectButton5.setTx(UiBridge.x(70));
        selectButton5.setOnClickRunnable(new Runnable() {
                                             @Override
                                             public void run() {

                                             }
                                         }
        );
        gameWorld.add(MenuScene.Layer.ui.ordinal(),selectButton5);
        selectButton6=new SelectButton(UiBridge.x(50),UiBridge.y(340),UiBridge.x(300),UiBridge.y(40),200,ch_button[5],45,R.mipmap.menu_button_idle,R.mipmap.menu_button);
        selectButton6.setTx(UiBridge.x(71));
        selectButton6.setOnClickRunnable(new Runnable() {
                                             @Override
                                             public void run() {

                                             }
                                         }
        );
        gameWorld.add(MenuScene.Layer.ui.ordinal(),selectButton6);

    }






    public static MenuScene get() {
        return instance;
    }



}
