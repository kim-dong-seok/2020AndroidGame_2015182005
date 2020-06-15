package kr.ac.kpu.game.kim2015182005.finalproject.framework.obj;

import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;

public class FlashTextObject extends TextObject {
    private static final String TAG = FlashTextObject.class.getSimpleName();
    private String text;
    private float x,y,w;
    private int alpha=0;
    private int speed;
    private boolean textOn=true;
    public FlashTextObject(String text, float x, float y,int size,String color,boolean bold,int speed) {
        super(text,x, y,size,color,bold);
        this.speed=speed;
    }
    @Override
    public void update() {
        if(textOn){
            alpha+=speed;
            if(alpha>=255){
                alpha=255;
                textOn=false;
            }
        }
        else{
            alpha-=speed;
            if(alpha<=0){
                alpha=0;
                textOn=true;
            }
        }
        alpha(alpha);
    }




}
