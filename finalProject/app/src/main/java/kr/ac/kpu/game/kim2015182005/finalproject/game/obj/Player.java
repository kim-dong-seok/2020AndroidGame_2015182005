package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.BoxCollidable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.Touchable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameTimer;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.AnimObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.res.bitmap.FrameAnimationBitmap;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.util.CollisionHelper;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.MainScene;

public class Player extends AnimObject implements Touchable, BoxCollidable {
    private static final String TAG = Player.class.getSimpleName();
    private static final float JUMP_POWER = -1500;
    private static final float GRAVITY_SPEED = 4500;
    private final FrameAnimationBitmap fabNormal;
    private final FrameAnimationBitmap fabJump;
    private final FrameAnimationBitmap fabLA;
    private final FrameAnimationBitmap fabSA;
    private int jumpCount = 10;
    private int saReload = 5;
    private int laReload = 10;
    private float base;
    private float speed;
    private int state = 0;
    private int jumpState;
    private int befState;
    private int hp;
    private float jumpY, originY, originX;

    public Player(float x, float y) {
        super(x, y, UiBridge.x(60), UiBridge.y(102), R.mipmap.tressa_right_move, 6, 6);
        base = y;
        originX=x;
        fabNormal = fab;
        fabJump = new FrameAnimationBitmap(R.mipmap.tressa_right_jump, 9, 9);
        fabSA = new FrameAnimationBitmap(R.mipmap.tressa_right_short_attack, 4, 4);
        fabLA = new FrameAnimationBitmap(R.mipmap.tressa_right_long_attack, 7, 7);
    }

    public enum AnimState {
        normal, jump, djump, sattack, lattack
    }
    public void setAnimState(AnimState state) {
        if (state == AnimState.normal) {
            fab = fabNormal;
        } else if (state == AnimState.sattack) {
            fab = fabSA;
        } else if (state == AnimState.lattack) {

            fab = fabLA;
        } else {
            fab = fabJump;
        }
    }


    @Override
    public void update() {
        if (fab.done() && (state == 2 || state == 3)) {
            setAnimState(AnimState.normal);
            state = 0;
            width=UiBridge.x(60);
            x=originX;
        }


        if (jumpCount > 0) {
            float timeDiffSeconds = GameTimer.getTimeDiffSeconds();
            y += speed * timeDiffSeconds;
            speed += GRAVITY_SPEED * timeDiffSeconds;
//            if (y >= base) {
//                Log.d(TAG, "Jumping Done");
//                jumpCount = 0;
//                speed = 0;
//                setAnimState(AnimState.normal);
//                y = base;
//            }
        }
        MainScene scene = MainScene.get();
        float footY = y + height / 2;
        Platform platform = scene.getPlatformAt(x, footY);
        if (platform != null) {

            RectF rect = new RectF();
            platform.getBox(rect);
//            Log.d(TAG, "Platform box = " + rect);
            float ptop = platform.getTop();
            if (jumpCount > 0) {
//                Log.d(TAG, "Platform box = " + rect + " footY = " + footY + " ptop=" + ptop);
                if (speed > 0 && footY >= ptop) {
//                    Log.d(TAG, " Stopping at the platform");
                    y = ptop - height / 2;
                    jumpCount = 0;
                    speed = 0;
                    if((state != 2 && state != 3))
                    setAnimState(AnimState.normal);
                }
            } else {
                if (footY < ptop) {
//                    Log.d(TAG, " Start to fall down");
                    jumpCount = 10; // falling down
                }
            }
        } else {
//            Log.d(TAG, " No platform. Falling down");
            jumpCount = 10;
        }


        saReload-=1;
        laReload-=1;

        checkItemCollision();

    }

    private void checkItemCollision() {

        ArrayList<GameObject> items = MainScene.get().getGameWorld().objectsAtLayer(MainScene.Layer.item.ordinal());
        for (GameObject obj : items) {
            if (!(obj instanceof CandyItem)) {
                continue;
            }
            CandyItem candy = (CandyItem) obj;
            if (CollisionHelper.collides(this, candy)) {
                candy.remove();
                MainScene.get().addScore(candy.getScore());
            }
        }
    }

    public void jump(){

    }
    @Override
    public boolean onTouchEvent(MotionEvent e) {
//        if (e.getAction() != MotionEvent.ACTION_DOWN) {
//            return false;
//        }
//        float tx = e.getX();
////        Log.d(TAG, "TouchEvent:" + e.getAction() + " - " + tx + "/" + UiBridge.metrics.center.x);
//        if (tx < UiBridge.metrics.center.x) {
//            // jump
//            if (jumpCount < 2) {
////                Log.d(TAG, "Jumping");
//                jumpCount++;
//                speed += JUMP_POWER;
//                if (speed > JUMP_POWER) {
//                    speed = JUMP_POWER;
//                }
//                setAnimState(jumpCount == 1 ? AnimState.jump : AnimState.djump);
//            }
//        } else {
//            // slide
//        }
         return false;
    }
    public void touchEvent(int c) {
//        Log.d(TAG, "TouchEvent:" + e.getAction() + " - " + tx + "/" + UiBridge.metrics.center.x);
        if(state!=2&&state!=3){
        if (c==1) {
            // jump
            if (jumpCount < 2) {
//                Log.d(TAG, "Jumping");
                jumpCount++;
                speed += JUMP_POWER;
                if (speed > JUMP_POWER) {
                    speed = JUMP_POWER;
                }
                setAnimState(jumpCount == 1 ? AnimState.jump : AnimState.djump);
                state=1;
            }
        } else if(c==2){
            if(saReload<=0){
            setAnimState(AnimState.sattack);
            x+=UiBridge.x(30);
            width=UiBridge.x(174);
            saReload=100;
            fab.reset();
            state=2;
            }

        }
        else if(c==3){
            if(laReload<=0){
            setAnimState(AnimState.lattack);
            laReload=200;
            fab.reset();
            state=3;
            }
        }
        }
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
