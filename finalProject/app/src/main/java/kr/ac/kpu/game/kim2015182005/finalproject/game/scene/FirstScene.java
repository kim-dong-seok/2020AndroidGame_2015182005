package kr.ac.kpu.game.kim2015182005.finalproject.game.scene;

import android.graphics.RectF;
import android.media.MediaPlayer;
import android.media.SoundPool;

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
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.Ball;

public class FirstScene extends GameScene {
    private static final String TAG = FirstScene.class.getSimpleName();

    public enum Layer {
        bg, enemy, player, ui, COUNT
    }
    private Ball ball;
    private ScoreObject scoreObject;
    private GameTimer timer;
    private Button startButton;
    private SoundPool pool;
    private int start_bgm;
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

    @Override
    public void enter() {
        super.enter();
        initObjects();
    }

    private void initObjects() {
//        pool=new SoundPool(1, AudioManager.STREAM_MUSIC,0);
//        start_bgm=pool.load(,R.raw.bg_bgm,1);
//        pool.play(start_bgm,1,1,0,0,1);

        //mediaPlayer = MediaPlayer.create(UiBridge.getView().getContext() , R.raw.main );
        //mediaPlayer.start();
        int mdpi_100 = UiBridge.x(100);
        gameWorld.add(Layer.bg.ordinal(),new BitmapObject(UiBridge.metrics.center.x,UiBridge.metrics.center.y,UiBridge.metrics.size.x,UiBridge.metrics.size.y,R.mipmap.main));
        int screenWidth = UiBridge.metrics.size.x;
        RectF rbox = new RectF(UiBridge.x(-52), UiBridge.y(20), UiBridge.x(-20), UiBridge.y(62));
        timer = new GameTimer(2, 1);

        int cx = UiBridge.metrics.center.x;
        int y = UiBridge.metrics.center.y;
//        y += UiBridge.y(100);
        y += UiBridge.y(100);
        FlashTextObject fto = new FlashTextObject("Touch To Start", UiBridge.metrics.center.x,y);
        fto.setBold();
        gameWorld.add(Layer.bg.ordinal(), fto);
        TouchManager tm = new TouchManager(0, 0,UiBridge.metrics.size.x,UiBridge.metrics.size.y);
        tm.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                //mediaPlayer.release();
                SecondScene scene = new SecondScene();
                scene.push();

            }
        });
        gameWorld.add(Layer.ui.ordinal(), tm);

    }
}
