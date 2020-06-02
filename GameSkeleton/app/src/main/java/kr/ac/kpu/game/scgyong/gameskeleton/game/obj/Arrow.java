package kr.ac.kpu.game.scgyong.gameskeleton.game.obj;

import android.util.Log;

import kr.ac.kpu.game.scgyong.gameskeleton.R;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.GameTimer;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.UiBridge;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.obj.AnimObject;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.obj.BitmapObject;

public class Arrow extends BitmapObject {
    private static final String TAG = Arrow.class.getSimpleName();
    private float dx, dy;
    public Arrow(float x, float y) {
        super(x, y, 50, 50, R.mipmap.arrow);
        this.dx = 500;

        Log.d(TAG,"new"+this);
    }

    @Override
    public float getRadius() {
        return this.width / 4;
    }

    public void update() {
        float seconds = GameTimer.getTimeDiffSeconds();
        x += dx * seconds;


    }
}
