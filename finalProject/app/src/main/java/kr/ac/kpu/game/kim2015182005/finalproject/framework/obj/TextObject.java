package kr.ac.kpu.game.kim2015182005.finalproject.framework.obj;

import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;

import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.res.bitmap.SharedBitmap;

public class TextObject extends GameObject {

    private static final String TAG = TextObject.class.getSimpleName();
    private String text;
    protected float x,y,w;
    public TextObject(String text, float x,float y,int size,String color,boolean bold) {
        this.text=text;
        this.x=x;
        this.y=y;
        paint=new Paint();
        if(bold){
        setBold();}
        setSize(size);
        setColor(color);
       // scoreAnimator.setDuration(SCORE_INCREASE_DURATION);
    }



    @Override
    public void draw(Canvas canvas) {
        w=paint.measureText(text);
        canvas.drawText(text,x-w/2,y,paint);
    }
    public void setSize(int size) {
        paint.setTextSize(size);
    }
    public void setColor(String color) {
        paint.setColor(Color.parseColor(color));
    }
    public float getMeasure() {
        return paint.measureText(text);
    }


    public void setBold() {
        paint.setTypeface(Typeface.DEFAULT_BOLD);
    }
}
