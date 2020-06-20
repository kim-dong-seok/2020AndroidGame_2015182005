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
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.ScoreObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.TextObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.bg.ImageScrollBackground;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.ui.TouchManager;
import kr.ac.kpu.game.kim2015182005.finalproject.game.map.TextMap;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.ui.ATBgauge;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.BGBlack;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.ui.MainCharacterInfo;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.Platform;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.Player;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.ui.SelectButton;

public class MainScene extends GameScene {
    private static final String TAG = MainScene.class.getSimpleName();
    private TextMap map;
    private int mdpi_100;
    private RectF rect = new RectF();
    private ScoreObject scoreObject;
    private MainCharacterInfo playerInfo;
    private ATBgauge atBgauge;
    private boolean atbWindowOn;
    private BGBlack end_bg;
    private String[] player_story ={
            "이런 상상이나 하고 있을 때가 아니야","우리 가게는 조그마하고...","내가 더 열심히 해야지!"
            ,"심부름 받은 포도주를 받으러 가자"
    };
    private String[] story_name={
            "트레사"
    };

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
        bg, platform, item,box,environemental,npc, obstacle,enemy,checkPoint, arrow,player, ui,story, COUNT
    }

    private Player player;
    private GameTimer timer;
    private SelectButton jumpButton;
    private SelectButton SAButton;
    private SelectButton LAButton;
    private SelectButton ATBButton;
    private boolean game_puase,bad_end;
    private  static  MainScene instance;
    private boolean storyOn=true;
    private int story=0;
    private BitmapObject speech;
    private TextObject speech_text;
    private TextObject speech_name;
    @Override
    protected int getLayerCount() {
        return Layer.COUNT.ordinal();
    }


    @Override
    public void update() {
        //Log.d(TAG,"map.getRows()"+map.getRows()+",      map.getRows()"+map.getColumns());

        if(storyOn){
            for (GameObject o :getGameWorld().objectsAtLayer(Layer.story.ordinal())) {
                o.update();
            }
            switch (story){
                case 0:
                    game_puase=true;
                    break;
                case 1:
                    speech_text.setText(player_story[1]);
                    speech_text.setCut(6,1);
                    break;
                case 2:
                    speech_text.setText(player_story[2]);
                    speech_text.setCut(8,1);
                    break;
                case 3:
                    speech_text.setText(player_story[3]);
                    speech_text.setCut(11,1);
                    break;
                case 4:
                    speech_text.alpha(0);
                    speech.alpha(0);
                    speech_name.alpha(0);
                    storyOn=false;
                    game_puase=false;
                    break;
            }
        }else{
        if(!bad_end){
        if(!game_puase){
            super.update();
        float dx = -2 * mdpi_100 * GameTimer.getTimeDiffSeconds();
        map.update(dx);
        for (int layer = Layer.platform.ordinal(); layer <= Layer.checkPoint.ordinal(); layer++) {
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
            super.update();
            if(end_bg.getAlpha()==0) {
                end_bg.setFlashSpeed(1);
                end_bg.flash(255);
            }
            if(end_bg.getAlpha()==255){
                TitleScene.get().getBgmPlayer().stopBGM();
                BadEndScene scene = new  BadEndScene();
                scene.push();
            }
        }}
    }

    public void setStory(int story) {
        this.story = story;
    }

    public int getStory() {
        return story;
    }

    @Override
    public void enter() {
        super.enter();
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

    public void setStoryOn(boolean storyOn) {
        this.storyOn = storyOn;
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
        player = new Player(UiBridge.x(100), UiBridge.y(320));
        gameWorld.add(Layer.player.ordinal(),player);
        end_bg=new BGBlack(0,0, UiBridge.metrics.size.x, UiBridge.metrics.size.y,"#000000");
        end_bg.alpha(0);
        gameWorld.add(Layer.ui.ordinal(), end_bg);

        speech=new BitmapObject(UiBridge.x(110), UiBridge.y(200),UiBridge.y(250),UiBridge.y(130),R.mipmap.speech);
        gameWorld.add(Layer.story.ordinal(), speech);

        speech_name=new TextObject(story_name[0],UiBridge.x(32), UiBridge.y(145),40,"#3d331d",true);
        gameWorld.add(Layer.story.ordinal(), speech_name);
        speech_text=new TextObject(player_story[0],UiBridge.x(180), UiBridge.y(170),50,"#3d331d",true);
        speech_text.setCut(10,1);
        gameWorld.add(Layer.story.ordinal(), speech_text);


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
                if(!bad_end&&!storyOn){
                player.jump();}
            }
        });
        gameWorld.add(Layer.ui.ordinal(), jumpButton);
        SAButton = new SelectButton(UiBridge.metrics.size.x-UiBridge.x(50), UiBridge.metrics.size.y-UiBridge.y(150), UiBridge.x(90), UiBridge.y(90),150,"",10,R.mipmap.spear_btn60, R.mipmap.spear_btn100);
        SAButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                if(!bad_end&&!storyOn){
                player.shortAttack();}
            }
        });
        gameWorld.add(Layer.ui.ordinal(), SAButton);
        LAButton = new SelectButton(UiBridge.metrics.size.x-UiBridge.x(50), UiBridge.metrics.size.y-UiBridge.y(50), UiBridge.x(110), UiBridge.y(110),150,"",10,R.mipmap.bow_btn60,R.mipmap.bow_btn100);
        LAButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {if(!bad_end){
                player.longAttack();}
            }
        });
        gameWorld.add(Layer.ui.ordinal(), LAButton);
        ATBButton = new SelectButton(UiBridge.metrics.size.x-UiBridge.x(155), UiBridge.metrics.size.y-UiBridge.y(50), UiBridge.x(90), UiBridge.y(90),150,"",10,R.mipmap.atb_btn60,R.mipmap.atb_btn100);
        ATBButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {if(!bad_end&&!storyOn&&!player.isCheck()){
                if(player.getATB()>=100){
                ATBScene atbScene = new ATBScene();
                atbScene.push();
               // TitleScene.get().soundPlay(5,11,1.0f);
                }}
            }
        });
        gameWorld.add(Layer.ui.ordinal(), ATBButton);
        atBgauge = new ATBgauge(UiBridge.metrics.size.x-UiBridge.x(155), UiBridge.metrics.size.y-UiBridge.y(50), UiBridge.x(90));
        gameWorld.add(Layer.ui.ordinal(), atBgauge);

        playerInfo = new MainCharacterInfo();
        gameWorld.add(Layer.ui.ordinal(), playerInfo);

        AnimObject bit = new AnimObject(UiBridge.metrics.center.x,UiBridge.metrics.center.y,UiBridge.x(100),UiBridge.y(20),R.mipmap.spear_slash3,12,5);
        gameWorld.add(Layer.ui.ordinal(),bit);

        SelectButton menuButton = new SelectButton(UiBridge.metrics.size.x-UiBridge.x(50), UiBridge.y(35), UiBridge.x(90), UiBridge.y(60),250,"",10,R.mipmap.menu_icon3,R.mipmap.menu_icon3);
        menuButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {if(!bad_end&&!storyOn&&!player.isCheck()){
               MenuScene scene=new MenuScene();
               scene.push();

            }
            }
        });
        gameWorld.add(Layer.ui.ordinal(),menuButton);

        TouchManager tm = new TouchManager(UiBridge.metrics.size.x/4, UiBridge.metrics.size.y/4,UiBridge.metrics.size.x/4*3,UiBridge.metrics.size.y/4*3);
        tm.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                if(storyOn) {
                    story+=1;
                }
            }
        });
        gameWorld.add(Layer.story.ordinal(),tm);


    }

    @Override
    public void onBackPressed() {
        if(!bad_end&&!game_puase){
            TitleScene.get().getSoundEffects().play(R.raw.menu_window,1.0f);
        MenuScene scene = new MenuScene();
        scene.push();
        }
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
