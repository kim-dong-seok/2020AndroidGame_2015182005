package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.BoxCollidable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.Recyclable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameScene;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.RecyclePool;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.res.bitmap.SharedBitmap;

public class CheckPointObject extends GameObject implements Recyclable, BoxCollidable {
    private static final int[] RES_IDS = {
            R.mipmap.box,
            R.mipmap.box_open,
    };
    public static final int ITEM_TYPE_COUNT = RES_IDS.length;
    private static final String TAG = CheckPointObject.class.getSimpleName();

    public int getScore() {
        return score;
    }
    private boolean colidable;
    protected int score;

    private int width,height;


    protected CheckPointObject(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.paint=new Paint();

    }
    public static CheckPointObject get(float x, float y, int width, int height) {
        RecyclePool rpool = GameScene.getTop().getGameWorld().getRecyclePool();
        CheckPointObject checkPointObject = (CheckPointObject) rpool.get(CheckPointObject.class);
        if (checkPointObject == null) {
            checkPointObject = new CheckPointObject(x, y, width, height);
        } else {
            checkPointObject.x = x;
            checkPointObject.y = y;
            checkPointObject.width = width;
            checkPointObject.height = height;

        }
        checkPointObject.colidable=true;
        return  checkPointObject;

    }

    public void setColidable(boolean colidable) {
        this.colidable = colidable;
    }

    @Override
    public void update() {
        super.update();
        //Log.d(TAG,"asdasdsada"+x);
        if (x < -width) {
            remove();
        }
    }

//    @Override
//    public void draw(Canvas canvas) {
//        int hw = width / 2;
//        int hh = height / 2;
//        paint.setColor(Color.RED);
//        canvas.drawRect(x - hw,y - hh,x + hw, y + hh,paint);
//    }

    @Override
    public void recycle() {
    }

    public boolean isColidable() {
        return colidable;
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
