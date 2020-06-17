package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.AnimObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.TextObject;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.FirstScene;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.MainScene;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.SecondScene;



public class MainCharacterInfo extends GameObject {
    private static final String TAG = MainCharacterInfo.class.getSimpleName();
    private Player player;
    private TextObject name,lv,hp,sp,totalHp,totalSp;
    private BitmapObject profile,bar,hp_icon,sp_icon;
    private BGBlack bg,hp_bar,sp_bar,hp_bar_bg,sp_bar_bg;
    private BGRhombus hit_bg;
    private float preHp;
    private float tphp,php,tpsp,psp;
    public MainCharacterInfo() {
        tphp=MainScene.get().getPlayer().getTotalHp();
        php=MainScene.get().getPlayer().getHp();
        preHp=php;
        bg=new BGBlack(0,0,UiBridge.metrics.size.x/3,UiBridge.y(100),"#000000");
        bg.alpha(50);
        MainScene.get().getGameWorld().add( MainScene.Layer.bg.ordinal(),bg);

        bar=new BitmapObject(UiBridge.x(145),UiBridge.y(15),UiBridge.x(155),UiBridge.y(10),R.mipmap.info_bar);
        MainScene.get().getGameWorld().add( MainScene.Layer.bg.ordinal(),bar);

        hp_bar_bg=new BGBlack(UiBridge.x(85),UiBridge.y(45),UiBridge.x(217),UiBridge.y(54),"#9f9e9c");
        hp_bar_bg.alpha(200);
        hp_bar_bg.setStroke();
        MainScene.get().getGameWorld().add( MainScene.Layer.bg.ordinal(),hp_bar_bg);

        hp_bar=new BGBlack(UiBridge.x(86),UiBridge.y(46),UiBridge.x(86)+(int)(UiBridge.x(130)*(php/tphp)),UiBridge.y(54),"#166c17");
        MainScene.get().getGameWorld().add( MainScene.Layer.bg.ordinal(),hp_bar);

        hp_icon=new BitmapObject(UiBridge.x(98),UiBridge.y(35),UiBridge.x(31),UiBridge.y(12),R.mipmap.hp_icon);
        MainScene.get().getGameWorld().add( MainScene.Layer.bg.ordinal(),hp_icon);

        totalHp=new TextObject("/"+(int)tphp,UiBridge.x(192),UiBridge.y(33),40,"#9f9e9c",true);
        MainScene.get().getGameWorld().add( MainScene.Layer.bg.ordinal(),totalHp);

        hp=new TextObject((int)php+"",UiBridge.x(147),UiBridge.y(31),44,"#FFFFFF",true);
        MainScene.get().getGameWorld().add( MainScene.Layer.bg.ordinal(),hp);






        sp_bar_bg=new BGBlack(UiBridge.x(72),UiBridge.y(78),UiBridge.x(204),UiBridge.y(88),"#000000");
        sp_bar_bg.alpha(200);
        MainScene.get().getGameWorld().add( MainScene.Layer.bg.ordinal(),sp_bar_bg);

        sp_bar=new BGBlack(UiBridge.x(73),UiBridge.y(79),UiBridge.x(203),UiBridge.y(87),"#005887");
        MainScene.get().getGameWorld().add( MainScene.Layer.bg.ordinal(),sp_bar);

        sp_icon=new BitmapObject(UiBridge.x(88),UiBridge.y(67),UiBridge.x(29),UiBridge.y(14),R.mipmap.sp_icon);
        MainScene.get().getGameWorld().add( MainScene.Layer.bg.ordinal(),sp_icon);

        totalSp=new TextObject("/1,000",UiBridge.x(180),UiBridge.y(66),40,"#9f9e9c",true);
        MainScene.get().getGameWorld().add( MainScene.Layer.bg.ordinal(), totalSp);

        sp=new TextObject("1,000",UiBridge.x(135),UiBridge.y(64),44,"#FFFFFF",true);
        MainScene.get().getGameWorld().add( MainScene.Layer.bg.ordinal(),sp);

        lv=new TextObject("LV. 1",UiBridge.x(40),UiBridge.y(90),40,"#FFFFFF",true);
        MainScene.get().getGameWorld().add( MainScene.Layer.bg.ordinal(),lv);


        profile=new BitmapObject(UiBridge.y(40),UiBridge.y(45),UiBridge.y(70),UiBridge.y(70),R.mipmap.tressa_state);
        MainScene.get().getGameWorld().add( MainScene.Layer.bg.ordinal(),profile);

        hit_bg=new BGRhombus(UiBridge.y(5),UiBridge.y(10),UiBridge.y(75),UiBridge.y(80),"#FF0000");
        hit_bg.alpha(0);
        hit_bg.setFlashSpeed(5);
        MainScene.get().getGameWorld().add( MainScene.Layer.bg.ordinal(),hit_bg);
    }

    @Override
    public void update() {
        super.update();

        tphp=MainScene.get().getPlayer().getTotalHp();
        php=MainScene.get().getPlayer().getHp();
        if(php!=preHp){
        preHp=php;
        hit_bg.flash(150);
        }
        if(hit_bg.isFlashDone()&&hit_bg.isFlashOn()){
            hit_bg.flash(150);
        }
        totalHp.setText("/"+(int)tphp);
        hp.setText((int)php+"");
        //Log.d(TAG,"asdasdsafgawqaw"+php/tphp);
        hp_bar.setR(UiBridge.x(86)+(int)(UiBridge.x(130)*(php/tphp)));
    }


}

