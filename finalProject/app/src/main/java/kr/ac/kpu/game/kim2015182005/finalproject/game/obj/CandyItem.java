package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import android.util.Log;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.BoxCollidable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.Recyclable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameScene;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.RecyclePool;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.res.bitmap.SharedBitmap;

public class CandyItem extends BitmapObject implements Recyclable, BoxCollidable {
    private static final int[] RES_IDS = {
            R.mipmap.cookie_item_001,
            R.mipmap.cookie_item_002,
            R.mipmap.cookie_item_003,
            R.mipmap.cookie_item_004,
    };
    public static final int ITEM_TYPE_COUNT = RES_IDS.length;
    private static final String TAG = CandyItem.class.getSimpleName();
    public static final int SCORE_MULTIPLIER = 10;

    public int getScore() {
        return score;
    }

    protected int score;


    protected CandyItem(float x, float y, int width, int height, int typeIndex) {
        super(x, y, width, height, RES_IDS[typeIndex]);
        Log.d(TAG, "Creating CandyItem instance");
    }
    public static CandyItem get(float x, float y, int width, int height, int typeIndex) {
        RecyclePool rpool = GameScene.getTop().getGameWorld().getRecyclePool();
        CandyItem item = (CandyItem) rpool.get(CandyItem.class);
        if (item == null) {
            item = new CandyItem(x, y, width, height, typeIndex);
        } else {
            item.x = x;
            item.y = y;
            item.width = width;
            item.height = height;
            item.sbmp = SharedBitmap.load(RES_IDS[typeIndex]);
        }
        item.score = SCORE_MULTIPLIER * (typeIndex + 1);
        return item;
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

}
