package kr.ac.kpu.game.kim2015182005.finalproject.framework.main;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.Touchable;

public class GameObject {
    private static final String TAG = GameObject.class.getSimpleName();
    protected float x, y;
    protected Paint paint;
    protected int alphaNum;
    protected int maxAlpha;
    protected int flashSpeed=3;
    protected boolean flashDone=true;
    protected boolean flashOn;
    protected boolean flash=false;
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() { return 0; }
    public void draw(Canvas canvas) {}

    public void captureTouch() {
        if (!(this instanceof Touchable)) {
            return;
        }
        GameScene.getTop().getGameWorld().captureTouch((Touchable) this);
    }
    public void releaseTouch() {
        GameScene.getTop().getGameWorld().releaseTouch();
    }

    public void move(float dx, float dy) {
        x += dx;
        y += dy;
    }

    public void setPos(float x,float y){
        this.x = x;
        this.y = y;
    }
    public void setX(float x) {
        this.x = x;
    }

    public void remove() {
        GameWorld gw = GameScene.getTop().getGameWorld();
        gw.removeObject(this);
    }

    public boolean isFlashOn() {
        return flashOn;
    }

    public boolean isFlashDone(){
        return flashDone;
    }
    public void flash(int maxAlpha){
        flash=true;
        this.maxAlpha=maxAlpha;
    }

    public void setFlashOn(boolean flashOn) {
        this.flashOn = flashOn;
    }

    public void setFlashSpeed(int speed){
        flashSpeed=speed;
    }
    public void update() {
        if(flash){
            if(flashDone) {
                flashDone = false;
            }else{
                if(!flashOn) {
                    alphaNum += flashSpeed;
                    if (alphaNum >= maxAlpha) {
                        alphaNum = maxAlpha;
                        flashOn = true;
                        flashDone=true;
                        flash=false;
                    }
                    alpha(alphaNum);
                }else{
                    //Log.d(TAG,"")
                    alphaNum -= flashSpeed;
                    if (alphaNum <= 0) {
                        alphaNum = 0;
                        flashOn = false;
                        flashDone=true;
                        flash=false;
                    }
                    alpha(alphaNum);
                }
            }
        }
    }
    public void alpha(int x){
        paint.setAlpha(x);
        alphaNum=x;
    }
    public int getAlpha(){
        return paint.getAlpha();
    }

}
