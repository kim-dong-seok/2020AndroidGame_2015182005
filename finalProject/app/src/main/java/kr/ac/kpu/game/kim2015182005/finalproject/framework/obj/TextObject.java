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
    protected float x,y,w,h;
    private Rect rect;
    private int row;
    private int col;
    public TextObject(String text, float x,float y,int size,String color,boolean bold) {
        this.text=text;
        this.x=x;
        this.y=y;
        paint=new Paint();
        this.rect=new Rect();
        if(bold){
        setBold();}
        setSize(size);
        setColor(color);
        setHW();
        row=0;
        col=0;
       // scoreAnimator.setDuration(SCORE_INCREASE_DURATION);
    }

    public void setCut(int row,int col) {
        this.row = row;
        this.col=col;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAlign(Paint.Align align){
        paint.setTextAlign(align);
    }
    public void setHW(){
        w=paint.measureText(text);
        paint.getTextBounds(text,0,text.length(),rect);
        h=rect.height();
    }

    @Override
    public void draw(Canvas canvas) {
        if(col==1) {
            canvas.drawText(text.substring(0,row), x - w / 2, y + h / 2, paint);
            canvas.drawText(text.substring(row+1), x - w / 2, y + h / 2+h/10*12, paint);
        }else{
            canvas.drawText(text, x - w / 2, y + h / 2, paint);
        }

    }
    public void setSize(int size) {
        paint.setTextSize(size);
        setHW();
    }
    public void setColor(String color) {
        paint.setColor(Color.parseColor(color));
    }
    public float getMeasure() {
        return paint.measureText(text);
    }


    public void setBold() {
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        setHW();
    }
}
