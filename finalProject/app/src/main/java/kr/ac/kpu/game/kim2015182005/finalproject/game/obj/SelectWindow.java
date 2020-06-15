package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.AnimObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.TextObject;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.FirstScene;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.LoadingScene;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.SecondScene;

public class SelectWindow extends GameObject {
    private static final String TAG = SelectWindow.class.getSimpleName();
    private int x,y;
    private String text,answer1,answer2;

    public SelectWindow(int x,int y,String text,String answer1,String answer2) {
        this.x=x;
        this.y=y;
        this.text=text;
        this.answer1=answer1;
        this.answer2=answer2;
        BGBlack bg=new BGBlack(0,0, UiBridge.metrics.size.x, UiBridge.metrics.size.y,"#000000");
        bg.alpha(100);
        SecondScene.get().getGameWorld().add(SecondScene.Layer.ui2.ordinal(),bg);
        SelectButton YButton = new SelectButton(x-UiBridge.x(100),y+UiBridge.y(20),UiBridge.x(100), UiBridge.y(40),answer1,R.mipmap.btn_idle,R.mipmap.btn_select);
        YButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {

            }

        });
        SecondScene.get().getGameWorld().add(SecondScene.Layer.ui2.ordinal(), YButton);
        SelectButton NButton = new SelectButton(x+UiBridge.x(100),y+UiBridge.y(20),UiBridge.x(100), UiBridge.y(40),answer2,R.mipmap.btn_idle,R.mipmap.btn_select);
        NButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {

            }

        });
        SecondScene.get().getGameWorld().add(SecondScene.Layer.ui2.ordinal(), NButton);
    }





}
