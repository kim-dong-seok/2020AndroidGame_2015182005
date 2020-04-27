package kr.ac.kpu.game.exgame.gameobj;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import kr.ac.kpu.game.exgame.R;

public class Plane implements GameObject{

    public static final int BULLET_FIRE_INTERVAL_NSEC = 100_000_000;
    private static Bitmap bitmap;
    private static int halfSize;
    private final float dy;
    private final float dx;
    //private final Matrix matrix;

    private float x;
    private float y;
    private long lastFire;


    public Plane(Resources res, float x, float y, float dx, float dy){
        if(bitmap==null){
            bitmap= BitmapFactory.decodeResource(res, R.mipmap.plane_240);
            halfSize=bitmap.getHeight()/2;
        }
        this.x=x;
        this.y=y;
        this.dy=dy;
        this.dx=dx;
//        this.matrix=new Matrix();
//        matrix.preTranslate(x-halfSize,y-halfSize);
    }

    public void update(){
//        x+=dx;
//        y+=dy;
//        matrix.postRotate(5.0f,x,y);
        GameWorld gw=GameWorld.get();
        long now=gw.getCurrentTimeNanos();
        long elapsed=now-lastFire;
        if(elapsed> BULLET_FIRE_INTERVAL_NSEC){
            fire();
            lastFire=now;
        }
    }

    private void fire() {
        Bullet bullet=Bullet.get(x,y-halfSize);
        GameWorld.get().add(GameWorld.Layer.missile,bullet);
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap,x-halfSize,y-halfSize,null);
    }

    public void head(float x, float y) {
        this.x=x;
    }
}
