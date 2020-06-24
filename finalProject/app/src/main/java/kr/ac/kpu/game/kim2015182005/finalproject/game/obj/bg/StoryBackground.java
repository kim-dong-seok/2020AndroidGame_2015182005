package kr.ac.kpu.game.kim2015182005.finalproject.game.obj.bg;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.res.bitmap.SharedBitmap;

public class StoryBackground extends GameObject {
    private static final String TAG = BitmapObject.class.getSimpleName();
    protected SharedBitmap sbmp;
    protected final RectF dstRect;
    private  Paint tPaint;
    private Rect srcRect;
    private float ny;
    private float nx;
    private float my;
    private float mx;
    protected int width;
    protected int height;
    private int chNum;

    public StoryBackground(float x, float y, int width, int height, int resId,int num) {
        this.paint=new Paint();
        tPaint=new Paint();
        sbmp = SharedBitmap.load(resId);
        this.x = x;
        this.y = y;
        this.nx = x;
        this.ny = y;
        this.chNum=num;
        this.dstRect = new RectF();
        this.srcRect=new Rect();
        if (width == 0) {
            width = UiBridge.x(sbmp.getWidth());
        } else if (width < 0) {
            width = UiBridge.x(sbmp.getWidth()) * -width / 100;
        }
        this.width = width;
        if (height == 0) {
            height = UiBridge.x(sbmp.getHeight());
        } else if (height < 0) {
            height = UiBridge.x(sbmp.getHeight()) * -height / 100;
        }
        this.height = height;


    }
    public void update(){
        if(x>nx){
            x-=mx/20;
            if(x<=nx){
                x=nx;
            }
        }
        if(x<nx){
            x+=-mx/20;
            if(x>=nx){
                x=nx;
            }
        }
        if(y>ny){
            y-=my/20;
            if(y<=ny){
                y=ny;
            }
        }
        if(y<ny){
            y+=-my/20;
            if(y>=ny){
                y=ny;
            }
        }
    }
    public void draw(Canvas canvas) {
        int halfWidth = width / 2;
        int halfHeight = height / 2;
        dstRect.left = x - halfWidth;
        dstRect.top = y - halfHeight;
        dstRect.right = x + halfWidth;
        dstRect.bottom = y + halfHeight;
        canvas.drawBitmap(sbmp.getBitmap(),null, dstRect, paint);
        tPaint.setColor(Color.WHITE);
        tPaint.setTextSize(50);
        for(int i=0;i<10;++i){
            int id = UiBridge.getResources().getIdentifier("profile_story_"+chNum+i, "string", UiBridge.getView().getContext().getPackageName());
            canvas.drawText(UiBridge.getResources().getString(id),x- halfWidth/2-UiBridge.x(10),y- halfHeight/3*2+(i*60)-UiBridge.y(10),tPaint);
        }
    }
    public boolean posCheck(){
        return (this.x==nx&&this.y==ny);
    }
    public void setNewPos(float nx ,float ny){
        this.nx=nx;
        this.ny = ny;
        mx=x-nx;
        my=y-ny;
    }
    public void fastSetNewPos(float nx ,float ny){
        this.x=nx;
        this.y = ny;
        mx=0;
        my=0;
    }
}
