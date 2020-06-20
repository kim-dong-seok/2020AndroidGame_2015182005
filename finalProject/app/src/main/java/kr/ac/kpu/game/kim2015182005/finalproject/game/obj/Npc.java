package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import android.util.Log;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.BoxCollidable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.Recyclable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameScene;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.RecyclePool;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.res.bitmap.SharedBitmap;

public class Npc extends BitmapObject implements Recyclable, BoxCollidable {
    private static final int[] RES_IDS = {
            R.mipmap.npc1,
            R.mipmap.npc2,
            R.mipmap.npc3,
            R.mipmap.npc4,
    };
    public static final int ITEM_TYPE_COUNT = RES_IDS.length;
    private static final String TAG = Npc.class.getSimpleName();
    public static final int SCORE_MULTIPLIER = 10;

    public int getScore() {
        return score;
    }

    protected int score;


    protected Npc(float x, float y, int width, int height, int typeIndex) {
        super(x, y, width, height, RES_IDS[typeIndex]);
        Log.d(TAG, "Creating CandyItem instance");
    }
    public static Npc get(float x, float y, int width, int height, int typeIndex) {
        RecyclePool rpool = GameScene.getTop().getGameWorld().getRecyclePool();
        Npc npc = (Npc) rpool.get(Npc.class);
        if (npc == null) {
            npc = new Npc(x, y, width, height, typeIndex);
        } else {
            npc.x = x;
            npc.y = y;
            npc.width = width;
            npc.height = height;
            npc.sbmp = SharedBitmap.load(RES_IDS[typeIndex]);
        }
        npc.score = SCORE_MULTIPLIER * (typeIndex + 1);
        return npc;
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
