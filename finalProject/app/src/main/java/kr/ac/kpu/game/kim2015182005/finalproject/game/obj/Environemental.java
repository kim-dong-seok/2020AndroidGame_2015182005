package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import android.util.Log;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.BoxCollidable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.Recyclable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameScene;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.RecyclePool;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.res.bitmap.SharedBitmap;

public class Environemental extends BitmapObject implements Recyclable, BoxCollidable {
    private static final int[] RES_IDS = {
            R.mipmap.store1,
            R.mipmap.store2,
            R.mipmap.store3,
            R.mipmap.store4,
    };
    public static final int ITEM_TYPE_COUNT = RES_IDS.length;
    private static final String TAG = Environemental.class.getSimpleName();
    public static final int SCORE_MULTIPLIER = 10;

    public int getScore() {
        return score;
    }

    protected int score;


    protected Environemental(float x, float y, int width, int height, int typeIndex) {
        super(x, y, width, height, RES_IDS[typeIndex]);
        Log.d(TAG, "Creating CandyItem instance");
    }
    public static Environemental get(float x, float y, int width, int height, int typeIndex) {
        RecyclePool rpool = GameScene.getTop().getGameWorld().getRecyclePool();
        Environemental environemental = (Environemental) rpool.get(Environemental.class);
        if (environemental == null) {
            environemental = new Environemental(x, y, width, height, typeIndex);
        } else {
            environemental.x = x;
            environemental.y = y;
            environemental.width = width;
            environemental.height = height;
            environemental.sbmp = SharedBitmap.load(RES_IDS[typeIndex]);
        }
        environemental.score = SCORE_MULTIPLIER * (typeIndex + 1);
        return environemental;
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
