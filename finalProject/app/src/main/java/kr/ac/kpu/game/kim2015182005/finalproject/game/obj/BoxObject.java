package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import android.util.Log;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.BoxCollidable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.Recyclable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameScene;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.RecyclePool;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.res.bitmap.SharedBitmap;

public class BoxObject extends BitmapObject implements Recyclable, BoxCollidable {
    private static final int[] RES_IDS = {
            R.mipmap.box,
            R.mipmap.box_open,
    };
    public static final int ITEM_TYPE_COUNT = RES_IDS.length;
    private static final String TAG = BoxObject.class.getSimpleName();

    public int getScore() {
        return score;
    }
    private boolean colidable;
    protected int score;



    protected BoxObject(float x, float y, int width, int height) {
        super(x, y, width, height,R.mipmap.box);
        Log.d(TAG, "Creating CandyItem instance");
    }
    public static BoxObject get(float x, float y, int width, int height) {
        RecyclePool rpool = GameScene.getTop().getGameWorld().getRecyclePool();
        BoxObject box = (BoxObject) rpool.get(BoxObject.class);
        if (box == null) {
            box = new BoxObject(x, y, width, height);
        } else {
            box.x = x;
            box.y = y;
            box.width = width;
            box.height = height;
            box.sbmp = SharedBitmap.load(R.mipmap.box);
        }
        box.colidable=true;
        return  box;

    }

    public void setColidable(boolean colidable) {
        this.colidable = colidable;
    }

    @Override
    public void update() {
        super.update();
        if (x < -width) {
            remove();
        }
    }

    @Override
    public void recycle() {
    }

    public boolean isColidable() {
        return colidable;
    }
}
