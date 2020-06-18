package kr.ac.kpu.game.kim2015182005.finalproject.framework.obj;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.BoxCollidable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.res.bitmap.SharedBitmap;

public class RotateBitmapObject extends GameObject implements BoxCollidable {
    private static final String TAG = RotateBitmapObject.class.getSimpleName();
    protected SharedBitmap sbmp;
    private Bitmap rotateBitmap;
    protected final RectF dstRect;
    protected int width;
    protected int height;
    private Matrix rotate;
    private int degree;
    private int count;
    private int speed;
    private int max;
    private int min;
    private boolean clock;
    private boolean reite,reite_flag;
    public RotateBitmapObject(float x, float y, int width, int height, int resId,int fitst_dgree,int speed,boolean clock) {
        sbmp = SharedBitmap.load(resId);
        rotate=new Matrix();
        this.paint=new Paint();
        this.x = x;
        this.y = y;
        this.count=0;
        this.speed=speed;
        this.degree=fitst_dgree;
        this.dstRect = new RectF();
        this.alphaNum=0;
        this.flashDone=true;
        this.flashOn=false;
        this.flash=false;
        this.clock=clock;
        this.max=360;
        this.reite=false;
        this.reite_flag=false;
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

    public void setMax(int max) {
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setReite(boolean reite) {
        this.reite = reite;
    }

    @Override
    public void update() {
        super.update();


        count+=1;
        if(count>=speed){

            count=0;
            if (!reite) {


            if(clock){
            degree+=1;
            if(degree>=max) {
                degree=0;
            }}
            else{
                degree-=1;
                if(degree<=-max){
                    degree=0;
                }
            }
            }else{
                if(clock){
                    if(!reite_flag){
                    degree+=1;
                    if(degree>=max) {
                        degree=max;
                        reite_flag=true;
                    }
                    }else{
                        degree-=1;
                        if(degree<=0) {
                            degree=0;
                            reite_flag=false;
                        }
                    }
                }else{
                    if(!reite_flag){
                        degree-=1;
                        if(degree<=-max) {
                            degree=-max;
                            reite_flag=true;
                        }
                    }else{
                        degree+=1;
                        if(degree>=0) {
                            degree=0;
                            reite_flag=false;
                        }
                    }
                }
            }
        }




    }

    @Override
    public float getRadius() {
        return this.width / 2;
    }

    public void draw(Canvas canvas) {
        int halfWidth = width / 2;
        int halfHeight = height / 2;
        dstRect.left = x - halfWidth;
        dstRect.top = y - halfHeight;
        dstRect.right = x + halfWidth;
        dstRect.bottom = y + halfHeight;
        rotateBitmap=rotateBitmap(sbmp.getBitmap(),degree);
        canvas.drawBitmap(rotateBitmap, null, dstRect, paint);

    }

    private Bitmap rotateBitmap(Bitmap bitmap, int rotationAngleDegree){

            int w = bitmap.getWidth();
            int h = bitmap.getHeight();

            int newW=w, newH=h;
            if (rotationAngleDegree==90 || rotationAngleDegree==270){
                newW = h;
                newH = w;
            }
            Bitmap rotatedBitmap = Bitmap.createBitmap(newW,newH, bitmap.getConfig());
            Canvas canvas = new Canvas(rotatedBitmap);

            Rect rect = new Rect(0,0,newW, newH);
            Matrix matrix = new Matrix();
            float px = rect.exactCenterX();
            float py = rect.exactCenterY();
            matrix.postTranslate(-bitmap.getWidth()/2, -bitmap.getHeight()/2);
            matrix.postRotate(rotationAngleDegree);
            matrix.postTranslate(px, py);
            canvas.drawBitmap(bitmap, matrix, new Paint( Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG ));
            matrix.reset();
            return rotatedBitmap;
    }


    @Override
    public void getBox(RectF rect) {
        int hw = width / 2;
        int hh = height / 2;
        rect.left = x - hw;
        rect.top = y - hh;
        rect.right = x + hw;
        rect.bottom = y + hh;
    }
}
