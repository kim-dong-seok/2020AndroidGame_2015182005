package kr.ac.kpu.game.kim2015182005.finalproject.game.obj.bg;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;

import static android.graphics.Paint.Style.STROKE;

public class closeBackground extends GameObject {
    private static final String TAG = closeBackground.class.getSimpleName();
    private boolean closeDone;
    public closeBackground() {
        paint=new Paint();
        paint.setColor(Color.parseColor("#000000"));
        paint.setAlpha(0);
        closeDone=false;
        this.flash(255);
    }

    @Override
    public void update() {
        super.update();
        if(paint.getAlpha()>=255){
            closeDone=true;
        }
    }

    public boolean isCloseDone() {
        return closeDone;
    }

    public void setStroke(){
        paint.setStyle(STROKE);
        paint.setStrokeWidth(UiBridge.x(1));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(0,0,UiBridge.metrics.size.x,UiBridge.metrics.size.y,paint);
    }
}
