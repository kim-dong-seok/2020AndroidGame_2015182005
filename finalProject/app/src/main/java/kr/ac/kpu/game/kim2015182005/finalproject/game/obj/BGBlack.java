package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.AnimObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.FirstScene;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.SecondScene;

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

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRect(l,t,r,b,paint);
    }
}
