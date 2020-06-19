package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;

import static android.graphics.Paint.Style.STROKE;

public class BGBlack extends GameObject {
    private static final String TAG = BGBlack.class.getSimpleName();
    private int l,t,r,b;
    private String color;
    public  BGBlack(int l,int t,int r,int b,String color) {
        paint=new Paint();
        this.l=l;
        this.t=t;
        this.r=r;
        this.b=b;
        this.color=color;
        paint.setColor(Color.parseColor(color));
    }

    public void setR(int r) {
        this.r = r;
    }
    public void setStroke(){
        paint.setStyle(STROKE);
        paint.setStrokeWidth(UiBridge.x(1));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(l,t,r,b,paint);
    }
}
