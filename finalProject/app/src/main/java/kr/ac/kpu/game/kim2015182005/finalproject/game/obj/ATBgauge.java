package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import android.util.Log;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.TextObject;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.MainScene;


public class ATBgauge extends GameObject {
    private static final String TAG = ATBgauge.class.getSimpleName();
    private int x,y,w,totalAtb;
    private float atb;
    private ArcObject gauge_bg,gauge1,gauge2,gauge3;
    public ATBgauge(int x,int y,int w) {
        this.x=x;
        this.y=y;
        this.w=w;

        gauge_bg = new ArcObject(UiBridge.metrics.size.x-UiBridge.x(150), UiBridge.metrics.size.y-UiBridge.y(50), UiBridge.x(50),UiBridge.y(10),0,360,"#9f9e9c",UiBridge.y(3));
        MainScene.get().getGameWorld().add( MainScene.Layer.ui.ordinal(),gauge_bg);

        gauge1 = new ArcObject(UiBridge.metrics.size.x-UiBridge.x(150), UiBridge.metrics.size.y-UiBridge.y(50), UiBridge.x(48),UiBridge.y(8),95,0,"#06454F",0);
        MainScene.get().getGameWorld().add( MainScene.Layer.ui.ordinal(),gauge1);
        gauge2 = new ArcObject(UiBridge.metrics.size.x-UiBridge.x(150), UiBridge.metrics.size.y-UiBridge.y(50), UiBridge.x(48),UiBridge.y(8),215,0,"#06454F",0);
        MainScene.get().getGameWorld().add( MainScene.Layer.ui.ordinal(),gauge2);
        gauge3 = new ArcObject(UiBridge.metrics.size.x-UiBridge.x(150), UiBridge.metrics.size.y-UiBridge.y(50), UiBridge.x(48),UiBridge.y(8),335,0,"#06454F",0);
        MainScene.get().getGameWorld().add( MainScene.Layer.ui.ordinal(),gauge3);

    }

    @Override
    public void update() {
        super.update();

        totalAtb=MainScene.get().getPlayer().getTotalATB();
        atb=MainScene.get().getPlayer().getATB();
        //Log.d(TAG,"ATB"+atb);
        if(0<=(atb-100)) {
            gauge1.setEndAngle((int)110);
            if(0<=(atb-200)){
                gauge2.setEndAngle((int)110);
                if(totalAtb<=atb){
                    gauge3.setEndAngle((int)110);
                }else{
                    gauge3.setEndAngle((int)110*((atb-200)/100));
                }
            }else{
                gauge2.setEndAngle((int)110*((atb-100)/100));
            }

        }else{
            //Log.d(TAG,"ATB"+atb/100);
            gauge1.setEndAngle((int)110*(atb/100));
        }

    }

}

