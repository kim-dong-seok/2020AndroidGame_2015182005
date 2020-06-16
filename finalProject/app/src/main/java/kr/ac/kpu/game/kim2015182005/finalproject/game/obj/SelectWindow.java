package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import android.util.Log;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.AnimObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.TextObject;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.FirstScene;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.LoadingScene;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.MainScene;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.SecondScene;

public class SelectWindow extends GameObject {
    private static final String TAG = SelectWindow.class.getSimpleName();
    private int x,y;
    private String text,answer1,answer2;
    private BGBlack bg;
    private BitmapObject aw;
    private TextObject to;
    private SelectButton YButton,NButton;
    public SelectWindow(int x,int y,String text,String answer1,String answer2) {
        this.x=x;
        this.y=y;
        this.text=text;
        this.answer1=answer1;
        this.answer2=answer2;
        bg=new BGBlack(0,0, UiBridge.metrics.size.x, UiBridge.metrics.size.y,"#000000");
        bg.alpha(80);
        SecondScene.get().getGameWorld().add(SecondScene.Layer.ui2.ordinal(),bg);
        aw=new BitmapObject(UiBridge.metrics.center.x,UiBridge.metrics.center.y, UiBridge.metrics.center.x+UiBridge.x(60), UiBridge.metrics.center.y,R.mipmap.select_window);
        aw.alpha(230);
        SecondScene.get().getGameWorld().add(SecondScene.Layer.ui2.ordinal(),aw);
        to=new TextObject(text,UiBridge.metrics.center.x,UiBridge.metrics.center.y-UiBridge.y(40),50,"#FFFFFF",true);
        SecondScene.get().getGameWorld().add(SecondScene.Layer.ui2.ordinal(),to);
        YButton = new SelectButton(x-UiBridge.x(80),y+UiBridge.y(30),UiBridge.x(120), UiBridge.y(40),100,answer1,50,R.mipmap.btn_idle,R.mipmap.btn_select);
        YButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {

                MainScene scene = new MainScene();
                scene.push();
            }

        });
        SecondScene.get().getGameWorld().add(SecondScene.Layer.ui2.ordinal(), YButton);
        NButton = new SelectButton(x+UiBridge.x(80),y+UiBridge.y(30),UiBridge.x(120), UiBridge.y(40),100,answer2,50,R.mipmap.btn_idle,R.mipmap.btn_select);
        NButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                flash();
                SecondScene.get().setTouchable(true);
            }

        });
        SecondScene.get().getGameWorld().add(SecondScene.Layer.ui2.ordinal(), NButton);
    }


    public void flash() {
        bg.flash(80);
        to.flash(255);
        aw.flash(230);
        NButton.flash(255);
        YButton.flash(255);
    }
    public void setAlpha(int alpha) {
        bg.alpha(alpha);
        to.alpha(alpha);
        aw.alpha(alpha);
        NButton.setAlpha(alpha);
        YButton.setAlpha(alpha);
    }
}
