package kr.ac.kpu.game.kim2015182005.finalproject.game.obj.ui;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.AnimObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.TextObject;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.BGBlack;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.TitleScene;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.CharacterSelectScene;

public class SelectWindow extends GameObject {
    private static final String TAG = SelectWindow.class.getSimpleName();
    private int x,y;
    private String text,answer1,answer2;
    private BGBlack SWbg;
    private BitmapObject SWaw;
    private TextObject SWto;
    private SelectButton YButton,NButton;
    private AnimObject op;
    public SelectWindow(int x,int y,String text,String answer1,String answer2) {
        this.x=x;
        this.y=y;
        this.text=text;
        this.answer1=answer1;
        this.answer2=answer2;
        SWbg=new BGBlack(0,0, UiBridge.metrics.size.x, UiBridge.metrics.size.y,"#000000");
        SWbg.alpha(80);
        CharacterSelectScene.get().getGameWorld().add(CharacterSelectScene.Layer.ui2.ordinal(),SWbg);
        SWaw=new BitmapObject(UiBridge.metrics.center.x,UiBridge.metrics.center.y, UiBridge.metrics.center.x+UiBridge.x(60), UiBridge.metrics.center.y,R.mipmap.select_window);
        SWaw.alpha(230);
        CharacterSelectScene.get().getGameWorld().add(CharacterSelectScene.Layer.ui2.ordinal(),SWaw);
        SWto=new TextObject(text,UiBridge.metrics.center.x,UiBridge.metrics.center.y-UiBridge.y(40),50,"#FFFFFF",true);
        CharacterSelectScene.get().getGameWorld().add(CharacterSelectScene.Layer.ui2.ordinal(),SWto);
        YButton = new SelectButton(x-UiBridge.x(80),y+UiBridge.y(30),UiBridge.x(120), UiBridge.y(40),100,answer1,50,R.mipmap.btn_idle,R.mipmap.btn_select);
        YButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                if(isSWFlashDone()&& CharacterSelectScene.get().getCharacterSelect()==0&&YButton.isFlashOn()) {
//                    TitleScene.get().getSoundEffects().play(R.raw.character_select_tressa,1.0f);
//                    CharacterSelectScene.get().getCharacterBackgrounds()[0].getOp().alpha(0);
//                    op=new AnimObject(UiBridge.metrics.center.x+UiBridge.x(10),UiBridge.metrics.center.y+UiBridge.y(10),UiBridge.x(172),UiBridge.y(183),R.mipmap.tressa_select,4,9);
//                    CharacterSelectScene.get().getGameWorld().add(CharacterSelectScene.Layer.ui.ordinal(),op);
//                    while(!op.getFab().done()){
//
//                    }
//                    TitleScene.get().getBgmPlayer().stopBGM();
//
//                    MainScene scene = new MainScene();
//                    scene.push();
                    CharacterSelectScene.get().setStart(true);
                    flashSW();
                    //CharacterSelectScene.get().getCharacterBackgrounds()[0].getOp().alpha(0);
                    TitleScene.get().getSoundEffects().play(R.raw.ch_select_effect,1.0f);
                }
            }

        });
        CharacterSelectScene.get().getGameWorld().add(CharacterSelectScene.Layer.ui2.ordinal(), YButton);
        NButton = new SelectButton(x+UiBridge.x(80),y+UiBridge.y(30),UiBridge.x(120), UiBridge.y(40),100,answer2,50,R.mipmap.btn_idle,R.mipmap.btn_select);
        NButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                if(isSWFlashDone()&& CharacterSelectScene.get().getCharacterSelect()==0&&NButton.isFlashOn()) {
                flashSW();
                CharacterSelectScene.get().setTouchable(true);
                TitleScene.get().getSoundEffects().play(R.raw.back_button,1.0f);
            }
            }

        });
        CharacterSelectScene.get().getGameWorld().add(CharacterSelectScene.Layer.ui2.ordinal(), NButton);
        setSWFlashSpeed();
    }

    public SelectButton getNButton() {
        return NButton;
    }

    public SelectButton getYButton() {
        return YButton;
    }

    public boolean isSWFlashDone() {
       // Log.d(TAG,"bg.isFlashDone()"+bg.isFlashDone()+"to.isFlashDone()"+to.isFlashDone()+"w.isFlashDone()"+aw.isFlashDone()+"NButton.isFlashDone()"+NButton.isFlashDone());
        return (SWbg.isFlashDone()&&SWto.isFlashDone()&&SWaw.isFlashDone()&&NButton.isFlashDone()&&YButton.isFlashDone());

    }

    public void flashSW() {
        SWbg.flash(80);
        SWto.flash(255);
        SWaw.flash(230);
        NButton.buttonFlash(255);
        YButton.buttonFlash(255);
    }
    public void setAlpha(int alpha) {
        SWbg.alpha(alpha);
        SWto.alpha(alpha);
        SWaw.alpha(alpha);
        NButton.setAlpha(alpha);
        YButton.setAlpha(alpha);
    }
    public void setSWFlashSpeed() {
        SWbg.setFlashSpeed(3);
        SWto.setFlashSpeed(10);
        SWaw.setFlashSpeed(9);
        NButton.setFlashSpeed(10);
        YButton.setFlashSpeed(10);
    }
}
