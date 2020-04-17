package kr.ac.kpu.game.exgame.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.HashMap;

public class FrameAnimationBitmap{
    private final Bitmap bitmap;
    private final int height;
    private final int frameCount;
    private final int frameWidth;
    private IndexTimer indexTimer;

    private FrameAnimationBitmap(Resources res, int resId, int frameCount){
        bitmap= BitmapFactory.decodeResource(res,resId);
        height=bitmap.getHeight();
        int width=bitmap.getWidth();
        if(frameCount==0) {
            this.frameCount = width / height;
            this.frameWidth=height;
        }
        else{
            this.frameCount=frameCount;
            this.frameWidth=width/frameCount;
        }

    }

    private static HashMap<Integer,FrameAnimationBitmap> map=new HashMap<>();

    public FrameAnimationBitmap(FrameAnimationBitmap stored) {
        bitmap=stored.bitmap;
        height=stored.height;
        frameCount=stored.frameCount;
        frameWidth=stored.frameWidth;
    }

    public static FrameAnimationBitmap load(Resources res,int resId,int framePerSecond,int frameCount){
        FrameAnimationBitmap fab;
        FrameAnimationBitmap stored=map.get(resId);
        if(stored==null){
            fab=new FrameAnimationBitmap(res,resId,frameCount);
            map.put(resId,fab);
        }
        else{
            fab=new FrameAnimationBitmap(stored);
        }
        fab.indexTimer=new IndexTimer(fab.frameCount,framePerSecond);
        return fab;
    }

    public void draw(Canvas canvas, float x,float y){
        int index=indexTimer.getIndex();
        int size=frameWidth;
        int halfWidth=size/2;
        int halfHeight=height/2;
        Rect rectSrc=new Rect(size*index,0,size*(index+1),size);
        RectF rectDsl=new RectF(x-halfWidth,y-halfHeight,x+halfWidth,y+halfHeight);
        canvas.drawBitmap(bitmap,rectSrc,rectDsl,null);
        //canvas.drawBitmap(bitmap,x-halfHeight,y-halfHeight,null);
    }

    public int getHeight() {
        return  height;
    }

    public boolean done() {
        return indexTimer.done();
}

    public void reset() {
        indexTimer.reset();
    }
}
