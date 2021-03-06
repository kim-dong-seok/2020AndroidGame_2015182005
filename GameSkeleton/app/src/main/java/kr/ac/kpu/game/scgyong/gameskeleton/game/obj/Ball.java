package kr.ac.kpu.game.scgyong.gameskeleton.game.obj;

import kr.ac.kpu.game.scgyong.gameskeleton.R;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.GameTimer;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.UiBridge;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.obj.AnimObject;

public class Ball extends AnimObject {
    private float dx, dy;
    public Ball(float x, float y, float dx, float dy) {
        super(x, y, 0, 0, R.mipmap.fireball_128_24f, 10, 0);
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public float getRadius() {
        return this.width / 4;
    }

    public void update() {
        float seconds = GameTimer.getTimeDiffSeconds();
        x += dx * seconds;
        float radius = getRadius();
        int screenWidth = UiBridge.metrics.size.x;
        int screenHeight = UiBridge.metrics.size.y;
//        Log.d(TAG, "dx=" + dx + " nanos=" + nanos + " x=" + x);
        if (dx > 0 && x > screenWidth - radius || dx < 0 && x < radius) {
            dx *= -1;
        }
        y += dy * seconds;
        if (dy > 0 && y > screenHeight - radius || dy < 0 && y < radius) {
            dy *= -1;
        }
    }
}
