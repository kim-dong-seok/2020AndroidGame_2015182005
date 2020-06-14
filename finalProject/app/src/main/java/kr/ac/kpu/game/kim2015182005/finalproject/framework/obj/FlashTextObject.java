package kr.ac.kpu.game.kim2015182005.finalproject.framework.obj;

import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;

public class FlashTextObject extends GameObject {
    private static final String TAG = FlashTextObject.class.getSimpleName();
    private String text;
    private float x,y,w;
    private Paint paint;
    private int alpha=0;
    private boolean textOn=true;
    public FlashTextObject(String text, float x, float y) {
        this.text=text;
        this.x=x;
        this.y=y;
        paint=new Paint();
        paint.setTextSize(UiBridge.x(30));
    }
    @Override
    public void update() {
        if(textOn){
            alpha+=2;
            if(alpha>=255){
                alpha=255;
                textOn=false;
            }
        }
        else{
            alpha-=2;
            if(alpha<=0){
                alpha=0;
                textOn=true;
            }
        }

        paint.setAlpha(alpha);
    }
    public void setBold() {
        paint.setTypeface(Typeface.DEFAULT_BOLD);
    }
    @Override
    public void draw(Canvas canvas) {
        w=paint.measureText(text);
        canvas.drawText(text,x-w/2,y,paint);
    }
    public void setSize(int size) {
        paint.setTextSize(size);
    }


}
