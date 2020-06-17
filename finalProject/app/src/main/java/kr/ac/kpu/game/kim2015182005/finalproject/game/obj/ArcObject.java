package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;

import static android.graphics.Paint.Style.STROKE;

public class ArcObject extends GameObject {
    private static final String TAG = ArcObject.class.getSimpleName();
    private int x,y,r,w;
    private String color;
    int radius = 40;
    private Canvas arc;
    float startAngle;
    float endAngle;
    private Bitmap bit;
    private Paint arcPaint;
    private RectF oval,oval2;
    private int stroke;
    public ArcObject(int x, int y, int r,int w,float start,float end, String color,int stroke) {
        paint=new Paint();
        bit = Bitmap.createBitmap(r*2, r*2, Bitmap.Config.ARGB_8888);
        arc=new Canvas(bit);
        arcPaint=new Paint();
        oval= new RectF();
        this.x=x;
        this.y=y;
        this.r=r;
        this.w=w;
        this.color=color;
        this.endAngle=end;
        this.startAngle=start;
        this.stroke=stroke;
        arcPaint.setAntiAlias(true);
        paint.setColor(Color.parseColor(this.color));
        arcPaint.setColor(Color.parseColor(this.color));
        arcPaint.setStyle(Paint.Style.FILL);
    }

    public void setEndAngle(float end) {
        this.endAngle = end;
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


        if(stroke>0){
            //바깥 테두리
            oval.set(r-r, r-r, r+r, r+r);
            // Log.d(TAG,"큰원~~~~~~~~~~~~~"+oval.left+","+oval.top+","+oval.right+","+oval.bottom);
            arcPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
            arc.drawArc(oval, startAngle, endAngle, true, arcPaint);
            //빈공간
            arcPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
            oval.set(r-r+stroke, r-r+stroke, r+r-stroke, r+r-stroke);
            // Log.d(TAG,"큰원~~~~~~~~~~~~~"+oval.left+","+oval.top+","+oval.right+","+oval.bottom);
            arc.drawArc(oval, startAngle, endAngle, true, arcPaint);
            //안쪽 테두리
            arcPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
            oval.set(r-r+w, r-r+w, r+r-w, r+r-w);
             //Log.d(TAG,"큰원~~~~~~~~~~~~~"+oval.left+","+oval.top+","+oval.right+","+oval.bottom);
            arc.drawArc(oval, startAngle, endAngle, true, arcPaint);

            //3등분
            oval.set(r-r, r-r, r+r, r+r);
            // Log.d(TAG,"큰원~~~~~~~~~~~~~"+oval.left+","+oval.top+","+oval.right+","+oval.bottom);
            arcPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
            arc.drawArc(oval, 90, 5, true, arcPaint);

            oval.set(r-r, r-r, r+r, r+r);
            // Log.d(TAG,"큰원~~~~~~~~~~~~~"+oval.left+","+oval.top+","+oval.right+","+oval.bottom);
            arcPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
            arc.drawArc(oval, 210, 5, true, arcPaint);
            oval.set(r-r, r-r, r+r, r+r);
            // Log.d(TAG,"큰원~~~~~~~~~~~~~"+oval.left+","+oval.top+","+oval.right+","+oval.bottom);
            arcPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
            arc.drawArc(oval, 330, 5, true, arcPaint);

            //빈공간
            arcPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
            oval.set(r-r+w+stroke, r-r+w+stroke, r+r-w-stroke, r+r-w-stroke);
            arc.drawArc(oval, startAngle, endAngle, true, arcPaint);
             //Log.d(TAG,"작은원~~~~~~~~~~~~~"+oval.left+","+oval.top+","+oval.right+","+oval.bottom);
        }
        else{
            oval.set(r-r, r-r, r+r, r+r);
            // Log.d(TAG,"큰원~~~~~~~~~~~~~"+oval.left+","+oval.top+","+oval.right+","+oval.bottom);
            arcPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
            arc.drawArc(oval, startAngle, endAngle, true, arcPaint);

            arcPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
            oval.set(r-r+w, r-r+w, r+r-w, r+r-w);
            // Log.d(TAG,"작은원~~~~~~~~~~~~~"+oval.left+","+oval.top+","+oval.right+","+oval.bottom);
            arc.drawArc(oval, startAngle, endAngle, true, arcPaint);
        }



        oval.set(x-r, y-r, x+r, y+r);
        canvas.drawBitmap(bit, null, oval, paint);
    }
}
