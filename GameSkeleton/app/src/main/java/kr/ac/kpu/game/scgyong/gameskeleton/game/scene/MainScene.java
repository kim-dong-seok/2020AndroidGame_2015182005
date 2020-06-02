package kr.ac.kpu.game.scgyong.gameskeleton.game.scene;

import android.graphics.RectF;

import kr.ac.kpu.game.scgyong.gameskeleton.R;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.GameScene;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.GameTimer;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.UiBridge;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.obj.BitmapObject;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.obj.ScoreObject;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.obj.ui.Button;
import kr.ac.kpu.game.scgyong.gameskeleton.game.obj.Arrow;
import kr.ac.kpu.game.scgyong.gameskeleton.game.obj.CityBackground;
import kr.ac.kpu.game.scgyong.gameskeleton.game.obj.Player;

public class MainScene extends GameScene {
    private static final String TAG = MainScene.class.getSimpleName();

    public enum Layer {
        bg, enemy, player,arrow, ui, COUNT
    }

    private Player player;
    private ScoreObject scoreObject;
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
        if (timer.done()) {
            scoreObject.add(100);
            timer.reset();
        }
        if(jumpButton.isPressed()&&player.getState()==0){
            player.jump();
        }
        if(sAttackButton.isPressed()&&player.getState()==0){
            player.shortAttack();
        }
        if(lAttackButton.isPressed()&&player.getState()==0){
            player.longAttack();
            gameWorld.add(Layer.arrow.ordinal(), new Arrow(player.getX(),player.getY()));

        }
    }

    @Override
    public void enter() {
        initObjects();
    }

    private void initObjects() {
        int mdpi_100 = UiBridge.x(100);

        player = new Player(mdpi_100, mdpi_100*3, 100, 100);
        gameWorld.add(Layer.player.ordinal(), player);
        gameWorld.add(Layer.bg.ordinal(), new CityBackground());
        int screenWidth = UiBridge.metrics.size.x;
        RectF rbox = new RectF(UiBridge.x(-52), UiBridge.y(20), UiBridge.x(-20), UiBridge.y(62));
        scoreObject = new ScoreObject(R.mipmap.number_64x84, rbox);
        gameWorld.add(Layer.ui.ordinal(), scoreObject);
        BitmapObject title = new BitmapObject(UiBridge.metrics.center.x, UiBridge.y(160), -150, -150, R.mipmap.slap_fight_title);
        gameWorld.add(Layer.ui.ordinal(), title);
        timer = new GameTimer(2, 1);

        int cx = UiBridge.metrics.center.x;
        int y = UiBridge.metrics.center.y;
//        y += UiBridge.y(100);
        jumpButton=new Button(100, 900, R.mipmap.jump_button, R.mipmap.blue_round_btn, R.mipmap.red_round_btn);
        gameWorld.add(Layer.ui.ordinal(),jumpButton );
        sAttackButton=new Button(1000, 900, R.mipmap.jump_button, R.mipmap.blue_round_btn, R.mipmap.red_round_btn);
        gameWorld.add(Layer.ui.ordinal(),sAttackButton );
        lAttackButton=new Button(1200, 900, R.mipmap.jump_button, R.mipmap.blue_round_btn, R.mipmap.red_round_btn);
        gameWorld.add(Layer.ui.ordinal(),lAttackButton );
        y += UiBridge.y(100);
    }
}
