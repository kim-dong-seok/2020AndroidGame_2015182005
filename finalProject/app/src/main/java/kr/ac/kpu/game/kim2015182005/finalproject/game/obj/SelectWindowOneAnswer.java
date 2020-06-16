package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.TextObject;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.MainScene;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.SecondScene;

public class SelectWindowOneAnswer extends GameObject {
    private static final String TAG = SelectWindowOneAnswer.class.getSimpleName();
    private int x,y;
    private String text,answer1;
    private BGBlack bg;
    private BitmapObject aw;
    private TextObject to;
    private SelectButton Button;
    public SelectWindowOneAnswer(int x, int y, String text, String answer1) {
        this.x=x;
        this.y=y;
        this.text=text;
        this.answer1=answer1;
        bg=new BGBlack(0,0, UiBridge.metrics.size.x, UiBridge.metrics.size.y,"#000000");
        bg.alpha(80);
        SecondScene.get().getGameWorld().add(SecondScene.Layer.ui2.ordinal(),bg);
        aw=new BitmapObject(UiBridge.metrics.center.x,UiBridge.metrics.center.y, UiBridge.metrics.center.x+UiBridge.x(60), UiBridge.metrics.center.y,R.mipmap.select_window);
        aw.alpha(230);
        SecondScene.get().getGameWorld().add(SecondScene.Layer.ui2.ordinal(),aw);
        to=new TextObject(text,UiBridge.metrics.center.x,UiBridge.metrics.center.y-UiBridge.y(40),50,"#FFFFFF",true);
        SecondScene.get().getGameWorld().add(SecondScene.Layer.ui2.ordinal(),to);
        Button = new SelectButton(x,y+UiBridge.y(30),UiBridge.x(120), UiBridge.y(40),100,answer1,50,R.mipmap.btn_idle,R.mipmap.btn_select);
        Button.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                flash();
                SecondScene.get().setTouchable(true);
            }

        });
        SecondScene.get().getGameWorld().add(SecondScene.Layer.ui2.ordinal(), Button);
    }


    public void flash() {
        bg.flash(80);
        to.flash(255);
        aw.flash(230);
        Button.flash(255);

    }
    public void setAlpha(int alpha) {
        bg.alpha(alpha);
        to.alpha(alpha);
        aw.alpha(alpha);
        Button.setAlpha(alpha);
    }
}
