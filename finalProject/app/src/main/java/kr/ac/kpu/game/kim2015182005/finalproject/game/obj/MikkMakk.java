package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.BoxCollidable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.Recyclable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameScene;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.RecyclePool;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.AnimObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.res.bitmap.FrameAnimationBitmap;

public class MikkMakk extends AnimObject implements Recyclable, BoxCollidable {
    private static final int[] RES_IDS = {
            R.mipmap.mikk_left_move,
    };
    public static final int ITEM_TYPE_COUNT = RES_IDS.length;
    private static final String TAG = CandyItem.class.getSimpleName();
    public static final int SCORE_MULTIPLIER = 10;
    private int atk;
    private boolean colidable;
    public int getScore() {
        return score;
    }
    public void setHp(int hp) {
        this.hp=hp;
    }
    public int getHp() {
        return hp;
    }
    public void setX(float x) {
        this.x=x;
    }
    public float getX() {
        return x;
    }
    public int getAtk() {
        return atk;
    }
    protected int score;
    private int phase;

    protected MikkMakk(float x, float y, int width, int height) {
        super(x, y,width, height, R.mipmap.mikk_left_move, 6, 6);
        this.x = x;
        this.y = y;
        this.atk=50;
        this.hp=2000;
        this.phase=0;
        this.width = width;
        this.height = height;
        this.fab = new FrameAnimationBitmap(R.mipmap.mikk_left_move, 6, 6);
        this.score = SCORE_MULTIPLIER ;
        this.colidable=true;
    }


    public void setColidable(boolean colidable) {
        this.colidable = colidable;
    }

    public boolean isColidable() {
        return colidable;
    }

    @Override
    public void update() {
        super.update();



    }

    @Override
    public void recycle() {
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
