package kr.ac.kpu.game.exgame.gameobj;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import kr.ac.kpu.game.exgame.R;

public class Ball implements GameObject{

    private static Bitmap bitmap;
    private float dy;
    private float dx;

    private float x;
    private float y;


    public Ball(Resources res, float x, float y, float dx, float dy){
        if(bitmap==null){
            bitmap= BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);
        }
        this.x=x;
        this.y=y;
        this.dy=dy;
        this.dx=dx;
    }

    public void update(){
        GameWorld gw=GameWorld.get();
        x+=dx;
        if(dx>0&&x>gw.getRight()||dx<0&&x<gw.getLeft()){
            dx*=-1;
        }
        y+=dy;
        if(dy>0&&y>gw.getBottom()||dy<0&&y<gw.getTop()){
            dy*=-1;
        }
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap,x,y,null);
    }

}
