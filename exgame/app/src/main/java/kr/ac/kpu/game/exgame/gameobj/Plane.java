package kr.ac.kpu.game.exgame.gameobj;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import kr.ac.kpu.game.exgame.R;

public class Plane implements GameObject{

    private static Bitmap bitmap;
    private final float dy;
    private final float dx;
    private final Matrix matrix;

    private float x;
    private float y;


    public Plane(Resources res, float x, float y, float dx, float dy){
        if(bitmap==null){
            bitmap= BitmapFactory.decodeResource(res, R.mipmap.plane_240);
        }
        this.x=x;
        this.y=y;
        this.dy=dy;
        this.dx=dx;
        this.matrix=new Matrix();
        matrix.preTranslate(x,y);
    }

    public void update(){
//        x+=dx;
//        y+=dy;
        matrix.postRotate(5.0f,x,y);
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap,matrix,null);
    }

}
