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
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.TextObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.res.bitmap.FrameAnimationBitmap;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.util.CollisionHelper;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.LoadingScene;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.MainScene;

public class JumpText extends TextObject{
    private static final String TAG = JumpText.class.getSimpleName();
    private static final float JUMP_POWER = -1000;
    private static final float GRAVITY_SPEED = 4500;
    private int jumpCount = 10;
    private int saReload = 5;
    private int laReload = 10;
    private float base;
    private float speed;
    private int state = 0;
    private int jumpState;
    private int befState;
    private int sAtk;
    private int lAtk;
    private float jumpY, originY, originX;

    public JumpText(String text,float x, float y,int size,String color,boolean bold) {
        super(text,x, y,size,color,bold);
        base = y;
        originX=x;
    }

    public enum AnimState {
        normal, jump, djump, sattack, lattack
    }
    public void jump() {
        // jump
        if (jumpCount < 2) {
//                Log.d(TAG, "Jumping");
            jumpCount++;
            speed += JUMP_POWER;
            if (speed > JUMP_POWER) {
                speed = JUMP_POWER;
            }
        }
    }

    @Override
    public void update() {
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
        LoadingScene scene =  LoadingScene.get();
        float footY = y;

            float ptop = base;
            if (jumpCount > 0) {
//                Log.d(TAG, "Platform box = " + rect + " footY = " + footY + " ptop=" + ptop);
                if (speed > 0 && footY >= ptop) {
//                    Log.d(TAG, " Stopping at the platform");
                    y=base;
                    jumpCount = 0;
                    speed = 0;
                }
            } else {
                if (footY < ptop) {
//                    Log.d(TAG, " Start to fall down");
                    jumpCount = 10; // falling down
                }
            }
    }





}
