package kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.bg;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameTimer;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.res.bitmap.SharedBitmap;

public class MapBackground extends GameObject {
    private static final String TAG = BitmapObject.class.getSimpleName();
    protected SharedBitmap sbmp;
    protected final RectF dstRect;
    private Rect srcRect;
    private int oy;
    private int ox;
    private int ny;
    private int nx;
    private int range;
    protected int width;
    protected int height;

    public MapBackground(float x, float y, int width, int height,int nx,int ny, int resId,int range) {
        sbmp = SharedBitmap.load(resId);
        this.x = x;
        this.y = y;
        this.nx = nx;
        this.ny = ny;
        this.ox=nx;
        this.oy=ny;
        this.dstRect = new RectF();
        this.srcRect=new Rect();
        this.range=range;
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
        if(ox>nx){
            ox-=3;
            if(ox<=nx){
                ox=nx;
            }
        }
        if(ox<nx){
            ox+=3;
            if(ox>=nx){
                ox=nx;
            }
        }
        if(oy>ny){
            oy-=3;
            if(oy<=ny){
                oy=ny;
            }
        }
        if(oy<ny){
            oy+=3;
            if(oy>=ny){
                oy=ny;
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
        srcRect.left = ox - 160*range;
        srcRect.top = oy - 120*range;
        srcRect.right = ox + 160*range;
        srcRect.bottom = oy + 120*range;
        canvas.drawBitmap(sbmp.getBitmap(), srcRect, dstRect, null);
    }
    public boolean posCheck(){
        return (this.ox==nx&&this.oy==ny);
    }
    public void setNewPos(int nx ,int ny){
        this.nx=nx;
        this.ny = ny;
    }
}
