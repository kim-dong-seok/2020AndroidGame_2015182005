package kr.ac.kpu.game.kim2015182005.finalproject.game.scene;

import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameScene;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameTimer;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameWorld;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.ScoreObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.bg.ImageScrollBackground;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.ui.Button;
import kr.ac.kpu.game.kim2015182005.finalproject.game.map.TextMap;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.Arrow;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.CityBackground;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.Enemy;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.MainCharacterInfo;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.Platform;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.Player;

public class MainScene extends GameScene {
    private static final String TAG = MainScene.class.getSimpleName();
    private TextMap map;
    private int mdpi_100;

    private RectF rect = new RectF();
    private ScoreObject scoreObject;
    private MainCharacterInfo playerInfo;
    public Platform getPlatformAt(float x, float y) {
        Platform platform = null;
        ArrayList<GameObject> objects = gameWorld.objectsAtLayer(Layer.platform.ordinal());
        for (GameObject obj : objects) {
            if (!(obj instanceof Platform)) {
                continue;
            }
            ((Platform) obj).getBox(rect);
            if (rect.left > x || rect.right < x) {
                continue;
            }
            if (rect.top < y - rect.height() / 2) {
                continue;
            }
            if (platform == null) {
                platform = (Platform) obj;
            } else {
                if (platform.getY() > obj.getY()) {
                    platform = (Platform) obj;
                }
            }
        }
        return platform;
    }


    public enum Layer {
        bg, platform, item, obstacle,arrow,enemy, player, ui, COUNT
    }

    private Player player;
    private GameTimer timer;
    private Button jumpButton;
    private Button sAttackButton;
    private Button lAttackButton;
    @Override
    protected int getLayerCount() {
        return Layer.COUNT.ordinal();
    }


    @Override
    public void update() {
        super.update();
//        Log.d(TAG, "Score: " + timer.getRawIndex());
//        if (timer.done()) {
//            pop();
//        }
        float dx = -2 * mdpi_100 * GameTimer.getTimeDiffSeconds();
        map.update(dx);
        for (int layer = Layer.platform.ordinal(); layer <= Layer.obstacle.ordinal(); layer++) {
            ArrayList<GameObject> objects = gameWorld.objectsAtLayer(layer);
            for (GameObject obj : objects) {
                obj.move(dx, 0);
            }
        }
    }

    @Override
    public void enter() {
        super.enter();
//        GyroSensor.get();
        initObjects();
        map = new TextMap("stage_01.txt", gameWorld);
    }
    @Override
    public void exit() {
//        GyroSensor.get().destroy();
        super.exit();

    }

    private void initObjects() {
        timer = new GameTimer(60, 1);
        Random rand = new Random();
        mdpi_100 = UiBridge.x(100);
        Log.d(TAG, "mdpi_100: " + mdpi_100);
        int sw = UiBridge.metrics.size.x;
        int sh = UiBridge.metrics.size.y;
        int cx = UiBridge.metrics.center.x;
        int cy = UiBridge.metrics.center.y;
        player = new Player(mdpi_100, mdpi_100);
        gameWorld.add(Layer.player.ordinal(),player);
        gameWorld.add(Layer.bg.ordinal(), new ImageScrollBackground(R.mipmap.cave_bg2, ImageScrollBackground.Orientation.horizontal, -100));
        gameWorld.add(Layer.bg.ordinal(), new ImageScrollBackground(R.mipmap.cave_bg12, ImageScrollBackground.Orientation.horizontal, -200));
        gameWorld.add(Layer.bg.ordinal(), new ImageScrollBackground(R.mipmap.cave_bg13, ImageScrollBackground.Orientation.horizontal, -300));

        RectF rbox = new RectF(UiBridge.x(-52), UiBridge.y(20), UiBridge.x(-20), UiBridge.y(62));
        scoreObject = new ScoreObject(R.mipmap.number_64x84, rbox);
        gameWorld.add(MainScene.Layer.ui.ordinal(), scoreObject);


        Button jumpButton = new Button(cx-UiBridge.x(300), cy+UiBridge.y(150), R.mipmap.btn_jump, R.mipmap.blue_round_btn, R.mipmap.red_round_btn);
        jumpButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                player.touchEvent(1);
            }
        });
        gameWorld.add(Layer.ui.ordinal(), jumpButton);
        Button SAButton = new Button(cx+UiBridge.x(250), cy+UiBridge.y(150), R.mipmap.btn_sa, R.mipmap.blue_round_btn, R.mipmap.red_round_btn);
        SAButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                player.touchEvent(2);
            }
        });
        gameWorld.add(Layer.ui.ordinal(), SAButton);
        Button LAButton = new Button(cx+UiBridge.x(300), cy+UiBridge.y(150), R.mipmap.btn_la, R.mipmap.blue_round_btn, R.mipmap.red_round_btn);
        LAButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                player.touchEvent(3);
            }
        });
        gameWorld.add(Layer.ui.ordinal(), LAButton);

        playerInfo = new MainCharacterInfo();
        gameWorld.add(Layer.ui.ordinal(), playerInfo);
    }

    public Player getPlayer() {
        return player;
    }

    public void addScore(int amount) {
        scoreObject.add(amount);
    }

    public static MainScene get() {
        return (MainScene) GameScene.getTop();
    }
}
