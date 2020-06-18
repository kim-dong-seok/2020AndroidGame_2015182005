package kr.ac.kpu.game.kim2015182005.finalproject.game.scene;

import android.animation.ValueAnimator;
import android.view.animation.OvershootInterpolator;

import java.util.ArrayList;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameScene;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameTimer;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.RotateBitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.ui.Button;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.BGBlack;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.JumpText;

public class LoadingScene extends GameScene {
    private static final String TAG = LoadingScene.class.getSimpleName();
    private GameTimer timer;
    private GameTimer jumpTimer;
    private JumpText[] jt;
    private String[] s={
            "N","o","w"," ","L","o","a","d","n","i","g",".",".","."
    };
    public enum Layer {
        bg, enemy, player, ui, COUNT
    }
    private int jumpCount=0;
    private static LoadingScene instance;

    @Override
    protected int getLayerCount() {
        return Layer.COUNT.ordinal();
    }

    @Override
    public void enter() {
        super.enter();
        instance=this;
        setTransparent(true);
        initObjects();

        int mdpi_100 = UiBridge.y(100);
        //ValueAnimator animator =
        ValueAnimator anim = ValueAnimator.ofFloat(UiBridge.metrics.size.y, mdpi_100);
        anim.setDuration(500);
        anim.setInterpolator(new OvershootInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                scrollTo(value);
            }
        });
        anim.start();
    }

    private void scrollTo(float y) {
        int mdpi_100 = UiBridge.y(100);
        ArrayList<GameObject> objs = gameWorld.objectsAtLayer(Layer.ui.ordinal());
        int count = objs.size();
        for (int i = 0; i < count; i++) {
            Button btn = (Button)objs.get(i);
            float diff = y - btn.getY();
            btn.move(0, diff);

            y += mdpi_100;
        }
    }

    @Override
    public void update() {
        super.update();
        if(timer.done()){
            pop();
            FirstScene.get().getBgmPlayer().startBGM();
        }
        if(jumpTimer.done()){
            jt[jumpCount].jump();
            jumpTimer.reset();
            jumpCount+=1;
            if(jumpCount>=s.length){
                jumpCount=0;
            }
        }
    }

    private void initObjects() {
        int screenWidth = UiBridge.metrics.size.x;
        int screenHeight = UiBridge.metrics.size.y;

        int cx = UiBridge.metrics.center.x;
        int y = UiBridge.metrics.center.y;
        timer=new GameTimer(3,2);
        jumpTimer=new GameTimer(1,5);
        gameWorld.add(Layer.bg.ordinal(), new BGBlack(0,0, UiBridge.metrics.size.x, UiBridge.metrics.size.y,"#000000"));
//        y += UiBridge.y(100);

        gameWorld.add(Layer.bg.ordinal(), new RotateBitmapObject(UiBridge.metrics.size.x/10*6, UiBridge.metrics.size.y/10*8+UiBridge.y(30),UiBridge.x(100),UiBridge.y(100),R.mipmap.compass1,0,4,true));
        gameWorld.add(Layer.enemy.ordinal(), new RotateBitmapObject(UiBridge.metrics.size.x/10*6, UiBridge.metrics.size.y/10*8+UiBridge.y(30),UiBridge.x(90),UiBridge.y(90),R.mipmap.compass2,180,4,false));
        jt=new JumpText[s.length];
        for(int i=0;i<s.length;++i){
            jt[i]=new JumpText(s[i],UiBridge.metrics.size.x/10*7+i*40, UiBridge.metrics.size.y/10*9,70,"#FFFFFF",true);
            gameWorld.add(Layer.enemy.ordinal(),jt[i]);
        }


    }
    public static LoadingScene get(){
        return instance;
    }
}
