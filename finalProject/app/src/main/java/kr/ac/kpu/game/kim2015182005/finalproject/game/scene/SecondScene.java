package kr.ac.kpu.game.kim2015182005.finalproject.game.scene;

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
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.bg.MapBackground;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.ui.Button;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.ui.TouchManager;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.Ball;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.CharacterBackground;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.Player;

public class SecondScene extends GameScene {
    private static final String TAG = SecondScene.class.getSimpleName();

    public enum Layer {
        bg, enemy, player, ui, COUNT
    }

    private Ball ball;
    private ScoreObject scoreObject;
    private GameTimer timer;
    private Button startButton;
    private SoundPool pool;
    private int start_bgm;
    private MapBackground map;
    private CharacterBackground[] characterBackgrounds;
    private BitmapObject profile;
    private AnimObject op;
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
        Log.d(TAG,"알파"+profile.getAlpha());
    }

    @Override
    public void enter() {
        super.enter();
        initObjects();
    }

    private void initObjects() {
        characterBackgrounds=new CharacterBackground[8];
        int sw = UiBridge.metrics.size.x;
        int sh = UiBridge.metrics.size.y;
        int cx = UiBridge.metrics.center.x;
        int cy = UiBridge.metrics.center.y;
        mediaPlayer = MediaPlayer.create(UiBridge.getView().getContext() , R.raw.main );
        mediaPlayer.start();

        int mdpi_100 = UiBridge.x(100);
        map=new MapBackground(UiBridge.metrics.center.x,UiBridge.metrics.center.y,UiBridge.metrics.size.x,UiBridge.metrics.size.y, 1650,1050,R.mipmap.world_map);
        SecondScene.get().getGameWorld().add(SecondScene.Layer.bg.ordinal(),map);
        SecondScene.get().getGameWorld().add(SecondScene.Layer.bg.ordinal(),new BitmapObject(UiBridge.metrics.center.x,UiBridge.metrics.center.y,UiBridge.metrics.size.x,UiBridge.metrics.size.y,R.mipmap.main_frame));
        profile=new BitmapObject(UiBridge.metrics.size.x-UiBridge.x(150),UiBridge.metrics.center.y,UiBridge.metrics.size.x/2,UiBridge.metrics.size.y,R.mipmap.tressa_profile);
        SecondScene.get().getGameWorld().add(FirstScene.Layer.bg.ordinal(),profile);
//        op=new AnimObject(UiBridge.metrics.center.x+UiBridge.x(10),UiBridge.metrics.center.y+UiBridge.x(30),UiBridge.x(82),UiBridge.y(132),R.mipmap.op_tressa,4,24);
//        SecondScene.get().getGameWorld().add(FirstScene.Layer.bg.ordinal(),op);

        int screenWidth = UiBridge.metrics.size.x;
        RectF rbox = new RectF(UiBridge.x(-52), UiBridge.y(20), UiBridge.x(-20), UiBridge.y(62));
        timer = new GameTimer(2, 1);

        int y = UiBridge.metrics.center.y;
//        y += UiBridge.y(100);
        y += UiBridge.y(100);
        TouchManager tm = new TouchManager(0, 0,UiBridge.metrics.size.x,UiBridge.metrics.size.y);
        tm.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {

            }
        });

        makeCB();


        Button jumpButton = new Button(cx-UiBridge.x(300), cy+UiBridge.y(150), R.mipmap.btn_jump, R.mipmap.blue_round_btn, R.mipmap.red_round_btn);
        jumpButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                profile.alpha(0);

            }
        });
        gameWorld.add(SecondScene.Layer.ui.ordinal(), jumpButton);
        Button SAButton = new Button(cx+UiBridge.x(250), cy+UiBridge.y(150), R.mipmap.btn_sa, R.mipmap.blue_round_btn, R.mipmap.red_round_btn);
        SAButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                profile.alpha(255);

            }
        });
        gameWorld.add(SecondScene.Layer.ui.ordinal(), SAButton);

    }
    public static SecondScene get() {
        return (SecondScene) GameScene.getTop();
    }
    private void makeCB(){
    }
}
