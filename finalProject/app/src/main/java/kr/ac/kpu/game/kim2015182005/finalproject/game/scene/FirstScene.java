package kr.ac.kpu.game.kim2015182005.finalproject.game.scene;

import android.graphics.RectF;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.Random;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameScene;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameTimer;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.FlashTextObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.ScoreObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.TextObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.ui.Button;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.ui.TouchManager;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.res.sound.BGMPlayer;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.res.sound.SoundEffects;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.Ball;

public class FirstScene extends GameScene {
    private static final String TAG = FirstScene.class.getSimpleName();
    private BGMPlayer bgmPlayer;

    public enum Layer {
        bg, enemy, player, ui, COUNT
    }

    private ScoreObject scoreObject;
    private GameTimer timer;
    private Button startButton;
    private SoundPool pool;
    private int start_bgm;
    private static FirstScene instance;
    private SoundEffects soundEffects;
    private int[] PLAYER_SOUND_IDS;
    private Random random;
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
    }
    public void soundPlay(int resId,float volume){
        soundEffects.play(resId,volume);
    }
    public void soundPlay(int start,int bound,float volume){
        soundEffects.play(PLAYER_SOUND_IDS[random.nextInt(start)+bound],volume);
    }
    public SoundEffects getSoundEffects() {
        return soundEffects;
    }
    @Override
    public void enter() {
        super.enter();
        instance=this;
        initObjects();
    }
    private void initObjects() {
        random=new Random();
        bgmPlayer=new BGMPlayer();
        bgmPlayer.setBGM(R.raw.main);

        bgmPlayer.setloop(true);
        bgmPlayer.startBGM();


        PLAYER_SOUND_IDS=soundEffects.getSoundIds();
        soundEffects= SoundEffects.get();
        soundEffects.loadAll(UiBridge.getView().getContext());


        int mdpi_100 = UiBridge.x(100);
        gameWorld.add(Layer.bg.ordinal(),new BitmapObject(UiBridge.metrics.center.x,UiBridge.metrics.center.y,UiBridge.metrics.size.x,UiBridge.metrics.size.y,R.mipmap.main));
        int screenWidth = UiBridge.metrics.size.x;
        RectF rbox = new RectF(UiBridge.x(-52), UiBridge.y(20), UiBridge.x(-20), UiBridge.y(62));
        timer = new GameTimer(2, 1);

        int cx = UiBridge.metrics.center.x;
        int y = UiBridge.metrics.center.y;
//        y += UiBridge.y(100);
        y += UiBridge.y(100);
        gameWorld.add(Layer.bg.ordinal(),new FlashTextObject("Touch To Start", UiBridge.metrics.center.x,y,85,"#FFFFFF",true,2));
        gameWorld.add(Layer.bg.ordinal(),new FlashTextObject("Touch To Start", UiBridge.metrics.center.x,y,83,"#000000",true,2));
        TouchManager tm = new TouchManager(0, 0,UiBridge.metrics.size.x,UiBridge.metrics.size.y);
        tm.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                //mediaPlayer.release();
                SecondScene scene = new SecondScene();
                scene.push();

                soundEffects.play(R.raw.select_button,1.0f);
                bgmPlayer.pauseBGM();

            }
        });
        gameWorld.add(Layer.ui.ordinal(), tm);

    }
    public static FirstScene get() {
        return instance;
    }

    public BGMPlayer getBgmPlayer() {
        return bgmPlayer;
    }
}
