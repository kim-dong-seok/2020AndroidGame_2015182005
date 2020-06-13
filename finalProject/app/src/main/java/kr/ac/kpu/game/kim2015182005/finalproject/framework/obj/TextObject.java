package kr.ac.kpu.game.kim2015182005.finalproject.framework.obj;

import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.res.bitmap.SharedBitmap;

public class TextObject extends GameObject {

    private static final String TAG = TextObject.class.getSimpleName();
    private ObjectAnimator scoreAnimator = ObjectAnimator.ofInt(this, "displayedScore", 0, 1);
    private String text;
    private float x,y,w;
    private Paint paint;
    public TextObject(String text, float x,float y) {
        this.text=text;
        this.x=x;
        this.y=y;
        paint=new Paint();
        paint.setTextSize(UiBridge.x(30));
       // scoreAnimator.setDuration(SCORE_INCREASE_DURATION);
    }

    @Override
    public void update() {

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
