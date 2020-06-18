package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.TextObject;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.FirstScene;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.MainScene;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.SecondScene;

public class SelectWindowOneAnswer extends GameObject {
    private static final String TAG = SelectWindowOneAnswer.class.getSimpleName();
    private int x,y;
    private String text,answer1;
    private BGBlack OAbg;
    private BitmapObject OAaw;
    private TextObject OAto;
    private SelectButton OAButton;
    public SelectWindowOneAnswer(int x, int y, String text, String answer1) {
        this.x=x;
        this.y=y;
        this.text=text;
        this.answer1=answer1;
        OAbg=new BGBlack(0,0, UiBridge.metrics.size.x, UiBridge.metrics.size.y,"#000000");
        OAbg.alpha(80);
        SecondScene.get().getGameWorld().add(SecondScene.Layer.ui2.ordinal(),OAbg);
        OAaw=new BitmapObject(UiBridge.metrics.center.x,UiBridge.metrics.center.y, UiBridge.metrics.center.x+UiBridge.x(60), UiBridge.metrics.center.y,R.mipmap.select_window);
        OAaw.alpha(230);
        SecondScene.get().getGameWorld().add(SecondScene.Layer.ui2.ordinal(),OAaw);
        OAto=new TextObject(text,UiBridge.metrics.center.x,UiBridge.metrics.center.y-UiBridge.y(40),50,"#FFFFFF",true);
        SecondScene.get().getGameWorld().add(SecondScene.Layer.ui2.ordinal(),OAto);
        OAButton = new SelectButton(x,y+UiBridge.y(30),UiBridge.x(120), UiBridge.y(40),100,answer1,50,R.mipmap.btn_idle,R.mipmap.btn_select);
        OAButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                if(isSWFlashDoneA1()&&SecondScene.get().getCharacterSelect()!=0)
                flashSW1A();
                SecondScene.get().setTouchable(true);
                FirstScene.get().getSoundEffects().play(R.raw.back_button,1.0f);
            }

        });
        SecondScene.get().getGameWorld().add(SecondScene.Layer.ui2.ordinal(), OAButton);
        setSW1AFlashSpeed();
    }

    public boolean isSWFlashDoneA1() {
        // Log.d(TAG,"bg.isFlashDone()"+bg.isFlashDone()+"to.isFlashDone()"+to.isFlashDone()+"w.isFlashDone()"+aw.isFlashDone()+"NButton.isFlashDone()"+NButton.isFlashDone());
        return (OAbg.isFlashDone()&&OAto.isFlashDone()&&OAaw.isFlashDone()&&OAButton.isFlashDone());

    }
    public void flashSW1A() {
        OAbg.flash(80);
        OAto.flash(255);
        OAaw.flash(230);
        OAButton.buttonFlash(255);

    }
    public void setAlpha(int alpha) {
        OAbg.alpha(alpha);
        OAto.alpha(alpha);
        OAaw.alpha(alpha);
        OAButton.setAlpha(alpha);
    }
    public void setSW1AFlashSpeed() {
        OAbg.setFlashSpeed(3);
        OAto.setFlashSpeed(10);
        OAaw.setFlashSpeed(9);
        OAButton.setFlashSpeed(10);
    }
}
