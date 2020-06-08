package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.BoxCollidable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.Recyclable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameScene;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameTimer;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.RecyclePool;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.AnimObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.res.bitmap.FrameAnimationBitmap;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.res.bitmap.SharedBitmap;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.MainScene;

public class Enemy extends AnimObject implements Recyclable, BoxCollidable {
    private static final int[] RES_IDS = {
            R.mipmap.mikk_left_move,
    };
    public static final int ITEM_TYPE_COUNT = RES_IDS.length;
    private static final String TAG = CandyItem.class.getSimpleName();
    public static final int SCORE_MULTIPLIER = 10;
    private int atk=20;

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


    protected Enemy(float x, float y, int width, int height) {
        super(x, y,width, height, R.mipmap.mikk_left_move, 6, 6);
        Log.d(TAG, "Creating CandyItem instance");
    }
    public static Enemy get(float x, float y, int width, int height) {
        RecyclePool rpool = GameScene.getTop().getGameWorld().getRecyclePool();
        Enemy enemy = (Enemy) rpool.get(Enemy.class);
        if (enemy == null) {
            enemy = new Enemy(x, y, width, height);
            enemy.atk=20;
            enemy.hp=100;
        } else {
            enemy.x = x;
            enemy.y = y;
            enemy.atk=20;
            enemy.hp=100;
            enemy.width = width;
            enemy.height = height;
            enemy.fab = new FrameAnimationBitmap(R.mipmap.mikk_left_move, 6, 6);
        }
        enemy.score = SCORE_MULTIPLIER ;
        return enemy;
    }

    @Override
    public void update() {
        super.update();
        x-=UiBridge.x(1);;
        if (x < -width) {
            remove();
        }


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
