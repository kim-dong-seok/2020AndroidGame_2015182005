package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.FloatProperty;
import android.util.Log;
import android.util.LogPrinter;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.BoxCollidable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.Touchable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameTimer;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.AnimObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.res.bitmap.FrameAnimationBitmap;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.res.sound.SoundEffects;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.util.CollisionHelper;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.FirstScene;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.MainScene;

public class Player extends AnimObject implements BoxCollidable {
    private static final String TAG = Player.class.getSimpleName();
    private static final float JUMP_POWER = -1500;
    private static final float GRAVITY_SPEED = 4500;
    private static final float HIT_TIME = 0.3f;
    private static final float LATTACK_TIME = 0.3f;
    private static final float SATTACK_TIME = 0.3f;
    private final FrameAnimationBitmap fabNormal;
    private final FrameAnimationBitmap fabJump;
    private final FrameAnimationBitmap fabLA;
    private final FrameAnimationBitmap fabSA;
    private final FrameAnimationBitmap fabHit;
    private final FrameAnimationBitmap fabDJump;
    private final FrameAnimationBitmap hitEffect;
    private final FrameAnimationBitmap shortAttackEffect;

    private int jumpCount = 10;
    private int saReload = 5;
    private int laReload = 10;
    private float base;
    private float speed;
    private int jumpState;
    private int befState;
    private int sAtk;
    private int lAtk;
    private int ATB;
    private int totalATB;
    private float jumpY, originY, originX;
    protected int totalHp=200;

    float hitTime;
    private AnimState state;
    public Player(float x, float y) {
        super(x, y, UiBridge.x(60), UiBridge.y(102), R.mipmap.tressa_right_run, 12, 6);
        base = y;
        originX=x;
        fabNormal = fab;
        hp=totalHp;
        sAtk=50;
        totalATB=300;
        ATB=100;
        hitEffect= new FrameAnimationBitmap(R.mipmap.hit_sprite, 16, 8);
        fabJump = new FrameAnimationBitmap(R.mipmap.tressa_right_jump4, 1, 1);
        fabDJump = new FrameAnimationBitmap(R.mipmap.tressa_right_djump, 1, 1);
        fabSA = new FrameAnimationBitmap(R.mipmap.tressa_right_short_attack2, 10, 5);
        fabLA = new FrameAnimationBitmap(R.mipmap.tressa_right_long_attack, 14, 7);
        fabHit = new FrameAnimationBitmap(R.mipmap.tressa_hit, 1, 1);
        shortAttackEffect=new FrameAnimationBitmap(R.mipmap.spear_slash3, 20, 5);
        hitEffect.setAlpha(200);
        shortAttackEffect.setAlpha(200);
        setAnimState(AnimState.normal);
    }

    public enum AnimState {
        normal, jump, djump, sattack, lattack,hit
    }

    public int getTotalHp() {
        return totalHp;
    }

    public int getATB() {
        return ATB;
    }

    public int getTotalATB() {
        return totalATB;
    }

    public void setATB(int ATB) {
        this.ATB = ATB;
    }

    public void setAnimState(AnimState state) {
        this.state = state;

        switch (state) {
            case normal: fab = fabNormal; break;
            case jump:   fab = fabJump;   break;
            case djump:  fab = fabDJump;  break;
            case sattack:  fab = fabSA;  break;
            case lattack:  fab = fabLA;  break;
            case hit:    fab = fabHit;    break;
        }
    }

    public AnimState getState() {
        return state;
    }

    @Override
    public void update() {
        if (fab.done() && (state == AnimState.sattack || state == AnimState.lattack)) {
            width=UiBridge.x(60);
            x=originX;
            setAnimState(AnimState.normal);
        }
        //Log.d(TAG,"state"+state);


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
                    if((state != AnimState.sattack && state!= AnimState.lattack))
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
        if(hitTime>0&&state==AnimState.hit){
            hitTime-= GameTimer.getTimeDiffSeconds();
            if (hitTime < 0) {
                hitTime = 0;
                setAnimState(AnimState.normal);
            }
        }

        saReload-=1;
        laReload-=1;

        checkItemCollision();
        checkEnemyCollision();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(hitTime>0&&!hitEffect.done()) {
            hitEffect.draw(canvas, x, y);
        }
        if(!shortAttackEffect.done()) {
            RectF rect=new RectF(x+UiBridge.x(50),y-UiBridge.y(20),x+UiBridge.x(150),y+UiBridge.y(20));
            shortAttackEffect.draw(canvas,rect,null);
        }
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

    private void checkEnemyCollision() {

        ArrayList<GameObject> enemys = MainScene.get().getGameWorld().objectsAtLayer(MainScene.Layer.enemy.ordinal());
        for (GameObject obj : enemys) {
            if (!(obj instanceof Enemy)) {
                continue;
            }
            Enemy enemy = (Enemy) obj;
            if(enemy.isColidable()){
            if (CollisionHelper.collides(this, enemy)&&state==AnimState.sattack) {
                int ehp=enemy.getHp();
                ehp-=sAtk;
                shortAttackEffect.reset();
                if(ehp<=0){
                    enemy.remove();
                    MainScene.get().addScore(enemy.getScore());
                }else{

                    enemy.setHp(ehp);
                    enemy.setX(enemy.getX()+UiBridge.x(100));
                }
            }else if(CollisionHelper.collides(this, enemy)&&state!=AnimState.sattack){
                int eAtk=enemy.getAtk();
                this.hp-=eAtk;

                //Log.d(TAG,"hp"+this.hp);
                enemy.setColidable(false);
                ATB+=eAtk;
                setAnimState(AnimState.hit);
                hitTime=HIT_TIME;
                hitEffect.reset();
                FirstScene.get().soundPlay(3,6,1.0f);
            }
            }
        }
    }



    public void jump() {
        if (state != AnimState.lattack&&state != AnimState.sattack){
            if (jumpCount < 2) {
//
            jumpCount++;
            speed += JUMP_POWER;
            if (speed > JUMP_POWER) {
                speed = JUMP_POWER;
            }
            FirstScene.get().soundPlay(R.raw.tressa_attack2,1.0f);
            setAnimState(jumpCount == 1 ? AnimState.jump : AnimState.djump);
        }}
    }
    public void shortAttack() {
        if (state != AnimState.lattack&&state != AnimState.sattack){
        if(saReload<=0){
            FirstScene.get().soundPlay(6,1,1.0f);
            setAnimState(AnimState.sattack);
            x+=UiBridge.x(30);
            width=UiBridge.x(160);
            saReload=60;
            fab.reset();
            FirstScene.get().soundPlay(R.raw.spear_attack,0.5f);
        }}
    }
    public void longAttack() {
        if (state != AnimState.lattack&&state != AnimState.sattack){
        if(laReload<=0){
            FirstScene.get().soundPlay(6,0,1.0f);
            setAnimState(AnimState.lattack);
            laReload=100;
            fab.reset();
            MainScene scene = MainScene.get();
            scene.getGameWorld().add(MainScene.Layer.arrow.ordinal(), new Arrow(x,y));
            FirstScene.get().soundPlay(R.raw.bow_attack,0.5f);
        }}
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
