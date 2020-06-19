package kr.ac.kpu.game.kim2015182005.finalproject.game.scene;

import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameScene;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameTimer;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.AnimObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.ScoreObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.bg.ImageScrollBackground;
import kr.ac.kpu.game.kim2015182005.finalproject.game.map.TextMap;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.ATBgauge;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.BGBlack;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.MainCharacterInfo;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.Platform;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.Player;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.SelectButton;

public class MainScene extends GameScene {
    private static final String TAG = MainScene.class.getSimpleName();
    private TextMap map;
    private int mdpi_100;
    private int story=0;
    private RectF rect = new RectF();
    private ScoreObject scoreObject;
    private MainCharacterInfo playerInfo;
    private ATBgauge atBgauge;
    private boolean atbWindowOn;
    private BGBlack end_bg;


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
    private SelectButton jumpButton;
    private SelectButton SAButton;
    private SelectButton LAButton;
    private SelectButton ATBButton;
    private boolean game_puase,bad_end;
    private  static  MainScene instance;
    @Override
    protected int getLayerCount() {
        return Layer.COUNT.ordinal();
    }


    @Override
    public void update() {

        super.update();
        if(!bad_end){
        if(!game_puase){
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
        if(player.getHp()<=0){
            bad_end=true;
        }
        }else{
            if(end_bg.getAlpha()==0) {
                end_bg.setFlashSpeed(1);
                end_bg.flash(255);
            }
            if(end_bg.getAlpha()==255){
                TitleScene.get().getBgmPlayer().stopBGM();
                BadEndScene scene = new  BadEndScene();
                scene.push();
            }
        }
    }

    @Override
    public void enter() {
        super.enter();
        LoadingScene lscene = new LoadingScene();
        lscene.push();
        TitleScene.get().getBgmPlayer().setBGM(R.raw.coast_lands);
        TitleScene.get().getBgmPlayer().startBGM();
//        GyroSensor.get();
        instance=this;
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
        bad_end=false;
        game_puase=false;

        mdpi_100 = UiBridge.x(100);
        Log.d(TAG, "mdpi_100: " + mdpi_100);
        int sw = UiBridge.metrics.size.x;
        int sh = UiBridge.metrics.size.y;
        int cx = UiBridge.metrics.center.x;
        int cy = UiBridge.metrics.center.y;
        player = new Player(mdpi_100, mdpi_100);
        gameWorld.add(Layer.player.ordinal(),player);
        end_bg=new BGBlack(0,0, UiBridge.metrics.size.x, UiBridge.metrics.size.y,"#000000");
        end_bg.alpha(0);
        gameWorld.add(Layer.ui.ordinal(), end_bg);


        gameWorld.add(Layer.bg.ordinal(), new ImageScrollBackground(R.mipmap.town_bg, ImageScrollBackground.Orientation.horizontal, -100));
        gameWorld.add(Layer.bg.ordinal(), new ImageScrollBackground(R.mipmap.town_bg_2, ImageScrollBackground.Orientation.horizontal, -200));
        gameWorld.add(Layer.bg.ordinal(), new ImageScrollBackground(R.mipmap.town_bg_3, ImageScrollBackground.Orientation.horizontal, -300));

        RectF rbox = new RectF(UiBridge.x(-52), UiBridge.y(20), UiBridge.x(-20), UiBridge.y(62));
        scoreObject = new ScoreObject(R.mipmap.number_64x84, rbox);
        gameWorld.add(MainScene.Layer.ui.ordinal(), scoreObject);


        jumpButton = new SelectButton(UiBridge.x(60), UiBridge.metrics.size.y-UiBridge.y(60),UiBridge.x(100), UiBridge.y(100),150,"",10,R.mipmap.jump_btn60, R.mipmap.jump_btn100);
        jumpButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                game_puase=false;
                player.jump();
            }
        });
        gameWorld.add(Layer.ui.ordinal(), jumpButton);
        SAButton = new SelectButton(UiBridge.metrics.size.x-UiBridge.x(50), UiBridge.metrics.size.y-UiBridge.y(150), UiBridge.x(90), UiBridge.y(90),150,"",10,R.mipmap.spear_btn60, R.mipmap.spear_btn100);
        SAButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                game_puase=true;
                player.shortAttack();
            }
        });
        gameWorld.add(Layer.ui.ordinal(), SAButton);
        LAButton = new SelectButton(UiBridge.metrics.size.x-UiBridge.x(50), UiBridge.metrics.size.y-UiBridge.y(50), UiBridge.x(110), UiBridge.y(110),150,"",10,R.mipmap.bow_btn60,R.mipmap.bow_btn100);
        LAButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                player.longAttack();
            }
        });
        gameWorld.add(Layer.ui.ordinal(), LAButton);
        ATBButton = new SelectButton(UiBridge.metrics.size.x-UiBridge.x(150), UiBridge.metrics.size.y-UiBridge.y(50), UiBridge.x(90), UiBridge.y(90),150,"",10,R.mipmap.atb_btn60,R.mipmap.atb_btn100);
        ATBButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                if(player.getATB()>=100){
                ATBScene atbScene = new ATBScene();
                atbScene.push();
                TitleScene.get().soundPlay(5,11,1.0f);
                }
            }
        });
        gameWorld.add(Layer.ui.ordinal(), ATBButton);
        atBgauge = new ATBgauge(UiBridge.metrics.size.x-UiBridge.x(150), UiBridge.metrics.size.y-UiBridge.y(50), UiBridge.x(90));
        gameWorld.add(Layer.ui.ordinal(), atBgauge);

        playerInfo = new MainCharacterInfo();
        gameWorld.add(Layer.ui.ordinal(), playerInfo);

        AnimObject bit = new AnimObject(UiBridge.metrics.center.x,UiBridge.metrics.center.y,UiBridge.x(100),UiBridge.y(20),R.mipmap.spear_slash3,12,5);
        gameWorld.add(Layer.ui.ordinal(),bit);

    }

    @Override
    public void onBackPressed() {
        MenuScene scene = new MenuScene();
        scene.push();
    }

    public Player getPlayer() {
        return player;
    }

    public void addScore(int amount) {
        scoreObject.add(amount);
    }

    public static MainScene get() {
        return instance;
    }
}
