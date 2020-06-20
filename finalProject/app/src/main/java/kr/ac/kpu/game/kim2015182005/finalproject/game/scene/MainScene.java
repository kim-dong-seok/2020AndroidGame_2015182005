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
            ,"심부름 받은 포도주를 받으러 가자","무슨 일 있나요?","본거지가 마을 외곽 마미야 동굴이였던가요"
            ,"소중한 상품을 빼앗다니 용서 할 수 없어요","저에게도 상인으로서의 고집이 있어요!"
    };
    private String[] npc_story ={
            "~~웅성~~ ~~웅성~~ ~~시끌~~ ~~시끌~~","오! 트레사구나..","실은 해적에게 장사할 물건을 빼았겨 버렸단다"
            ,"너무 위험하단다. 물건은 다음주를 기약 하자구나"
    };
    private String[] story_name={
            "트레사","마을상인"
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
    private BitmapObject speech,npc_speech;
    private TextObject speech_text,npc_speech_text;
    private TextObject speech_name,npc_speech_name;
    private ImageScrollBackground bg1,bg2,bg3;
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
                    player_speechOn(false);
                    storyOn=false;
                    game_puase=false;
                    player.setCheck(false);
                    break;
                case 5:
                    npc_speechOn(true);
                    break;
                case 6:
                    npc_speechOn(false);
                    player_speechOn(true);
                    speech_text.setText(player_story[4]);
                    speech_text.setCut(0,0);
                    break;
                case 7:
                    npc_speechOn(true);
                    player_speechOn(false);
                    npc_speech_text.setText(npc_story[1]);
                    npc_speech_text.setCut(0,0);
                    break;
                case 8:
                    npc_speech_text.setText(npc_story[2]);
                    npc_speech_text.setCut(11,1);
                    break;
                case 9:
                    npc_speechOn(false);
                    player_speechOn(true);
                    speech_text.setText(player_story[5]);
                    speech_text.setCut(10,1);
                    break;
                case 10:
                    npc_speechOn(true);
                    player_speechOn(false);
                    npc_speech_text.setText(npc_story[3]);
                    npc_speech_text.setCut(13,1);
                    break;
                case 11:
                    npc_speechOn(false);
                    player_speechOn(true);
                    speech_text.setText(player_story[6]);
                    speech_text.setCut(12,1);
                    break;
                case 12:
                    speech_text.setText(player_story[7]);
                    speech_text.setCut(11,1);
                    break;
                case 13:
                    player_speechOn(false);
                    storyOn=false;
                    game_puase=false;
                    player.setCheck(false);
                    end_bg.setFlashSpeed(1);
                    end_bg.flash(255);
                    break;
            }
        }else{
        if(!bad_end){
        if(!game_puase){
            super.update();
            if(end_bg.getAlpha()==255){
                map = new TextMap("stage_02.txt", gameWorld);
                bg1.setImage(R.mipmap.cave_bg2);
                bg2.setImage(R.mipmap.cave_bg12);
                bg3.setImage(R.mipmap.cave_bg13);
                end_bg.flash(255);
            }
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
        }
        else{
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
    public void npc_speechOn(boolean on){
           if(on){
               npc_speech_text.alpha(255);
               npc_speech.alpha(255);
               npc_speech_name.alpha(255);
           }else{
               npc_speech_text.alpha(0);
               npc_speech.alpha(0);
               npc_speech_name.alpha(0);
           }
    }
    public void player_speechOn(boolean on){
        if(on){
            speech_text.alpha(255);
            speech.alpha(255);
            speech_name.alpha(255);
        }else{
            speech_text.alpha(0);
            speech.alpha(0);
            speech_name.alpha(0);
        }
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
        speech_text=new TextObject(player_story[0],UiBridge.x(155), UiBridge.y(170),45,"#3d331d",true);
        speech_text.setCut(10,1);
        gameWorld.add(Layer.story.ordinal(), speech_text);

        npc_speech=new BitmapObject(UiBridge.x(550), UiBridge.y(200),UiBridge.y(250),UiBridge.y(130),R.mipmap.speech);
        gameWorld.add(Layer.story.ordinal(), npc_speech);
        npc_speech_name=new TextObject(story_name[1],UiBridge.x(472), UiBridge.y(145),40,"#3d331d",true);
        gameWorld.add(Layer.story.ordinal(), npc_speech_name);
        npc_speech_text=new TextObject(npc_story[0],UiBridge.x(630), UiBridge.y(170),45,"#3d331d",true);
        npc_speech_text.setCut(13,1);
        gameWorld.add(Layer.story.ordinal(), npc_speech_text);
        npc_speechOn(false);
        bg1=new ImageScrollBackground(R.mipmap.town_bg, ImageScrollBackground.Orientation.horizontal, -100);
        gameWorld.add(Layer.bg.ordinal(), bg1);
        bg2=new ImageScrollBackground(R.mipmap.town_bg_2, ImageScrollBackground.Orientation.horizontal, -200);
        gameWorld.add(Layer.bg.ordinal(), bg2);
        bg3=new ImageScrollBackground(R.mipmap.town_bg_3, ImageScrollBackground.Orientation.horizontal, -300);
        gameWorld.add(Layer.bg.ordinal(), bg3);

        RectF rbox = new RectF(UiBridge.x(-52), UiBridge.y(20), UiBridge.x(-20), UiBridge.y(62));
        scoreObject = new ScoreObject(R.mipmap.number_64x84, rbox);
        gameWorld.add(MainScene.Layer.ui.ordinal(), scoreObject);

        end_bg.alpha(255);
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
