package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.BoxCollidable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameTimer;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.AnimObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.res.bitmap.FrameAnimationBitmap;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.res.bitmap.SharedBitmap;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.util.CollisionHelper;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.TitleScene;
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
    private final FrameAnimationBitmap fabDie;

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
    protected int totalHp=100;
    private boolean check=false;
    private boolean player_cheat=false;
    private int hp_portion=3;
    float hitTime;
    private AnimState state;
    public Player(float x, float y) {
        super(x, y, UiBridge.x(54), UiBridge.y(92), R.mipmap.tressa_right_run, 12, 6);
        base = y;
        originX=x;
        fabNormal = fab;
        hp=totalHp;
        sAtk=50;
        totalATB=300;
        ATB=0;
        fabJump = new FrameAnimationBitmap(R.mipmap.tressa_right_jump4, 1, 1);
        fabDJump = new FrameAnimationBitmap(R.mipmap.tressa_right_djump, 1, 1);
        fabSA = new FrameAnimationBitmap(R.mipmap.tressa_right_short_attack2, 10, 5);
        fabLA = new FrameAnimationBitmap(R.mipmap.tressa_right_long_attack, 14, 7);
        fabHit = new FrameAnimationBitmap(R.mipmap.tressa_hit, 1, 1);
        fabDie=new FrameAnimationBitmap(R.mipmap.tressa_die, 1, 1);

        setAnimState(AnimState.normal);
    }

    public int getHp_portion() {
        return hp_portion;
    }


    public void useHpPortion() {
        if(hp_portion>0) {
            this.hp_portion -= 1;
            if(getHp()+50>totalHp){
                setHp(totalHp);
            }else{
                setHp(getHp()+50);
            }
        }
    }


    public enum AnimState {
        normal, jump, djump, sattack, lattack,hit,die
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
            case die:    fab = fabDie;    break;
        }
    }

    public AnimState getState() {
        return state;
    }

    public boolean isCheck() {
        return check;
    }

    public void setTotalHp(int totalHp) {
        this.totalHp = totalHp;
    }

    @Override
    public void update() {
        super.update();
        if(MainScene.get().isCheat()&&!player_cheat){

            y-=UiBridge.y(30);
            width=UiBridge.x(60);
            height=UiBridge.x(102);
            setAnimState(AnimState.normal);
            setTotalHp(500);
            setHp(500);
            player_cheat=true;
        }
        if (state != AnimState.hit){alpha(255);}
        if(state != AnimState.die){
        if (fab.done() && (state == AnimState.sattack || state == AnimState.lattack)) {
            width=UiBridge.x(60);
            x=originX;
            setAnimState(AnimState.normal);

        }
        //Log.d(TAG,"check,check,check"+check);


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
//
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

        checkBoxCollision();
        checkEnemyCollision();
        checkPointCollision();
    }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
//        RectF rect1 = new RectF();
//        getBox(rect1);
//        int hw = width / 2;
//        int hh = height / 2;
//        canvas.drawRect(rect1.left, rect1.top ,rect1.right,rect1.bottom,paint);

    }

    private void checkBoxCollision() {

        ArrayList<GameObject> Box = MainScene.get().getGameWorld().objectsAtLayer(MainScene.Layer.box.ordinal());
        for (GameObject obj : Box) {
            if (!(obj instanceof BoxObject)) {
                continue;
            }
            BoxObject box = (BoxObject) obj;
            if(box.isColidable()){
                if (CollisionHelper.collides(this, box)&&state==AnimState.sattack) {
                     TitleScene.get().soundPlay(R.raw.box_open,1.0f);
                    box.setColidable(false);
                    box.setSbmp(SharedBitmap.load(R.mipmap.box_open));
                    hp_portion+=1;
                }
        }
    }
    }
    private void checkPointCollision() {

        ArrayList<GameObject> checkPointObject = MainScene.get().getGameWorld().objectsAtLayer(MainScene.Layer.checkPoint.ordinal());
        for (GameObject obj :checkPointObject) {
            if (!(obj instanceof CheckPointObject)) {
                continue;
            }
            CheckPointObject checkPoint = (CheckPointObject) obj;
            if( checkPoint.isColidable()){
                if (CollisionHelper.collides(this,  checkPoint)) {
                    checkPoint.setColidable(false);
                    if(!check) {
                        this.check = true;
                    }else{
                        MainScene.get().setStoryOn(true);
                        MainScene.get().setStory( MainScene.get().getStory()+1);
                    }
                }
            }
        }
    }

    public void setCheck(boolean check) {
        this.check = check;
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
                TitleScene.get().soundPlay(R.raw.spear_hit,0.5f);
                if(ehp<=0){
                    ATB+=30;
                    enemy.remove();
                    enemy.setHit(true);
                    MainScene.get().addKillcount();
                }else{
                    enemy.setHp(ehp);
                    enemy.setHit(true);
                    enemy.setX(enemy.getX()+UiBridge.x(400));
                    ATB+=10;
                }
            }else if(CollisionHelper.collides(this, enemy)&&state!=AnimState.sattack){
                //Log.d(TAG,"player 하단"+(this.y+height/2)+"적 상단"+enemy.ColiH());
                int eAtk=enemy.getAtk();
                this.hp-=eAtk;

                enemy.setColidable(false);
                TitleScene.get().soundPlay(3,6,1.0f);
                if(this.hp>0){
                ATB+=((float)eAtk/totalHp)*100;
                setAnimState(AnimState.hit);
                alpha(150);
                hitTime=HIT_TIME;
                }else{
                    setAnimState(AnimState.die);
                    y+=UiBridge.y(30);
                    width=UiBridge.x(94);
                    height=UiBridge.x(51);
                    TitleScene.get().soundPlay(R.raw.tressa_die,1.0f);
                }
            }
            }
        }
    }

    public void djump() {
        if (y+UiBridge.y(200)<=UiBridge.metrics.size.y&&jumpCount==0) {

            y+=UiBridge.y(30);
//                    Log.d(TAG, " Start to fall down");
            jumpCount = 10; // falling down
            if((state != AnimState.sattack && state!= AnimState.lattack))
                setAnimState(AnimState.djump);
        }
    }


    public void jump() {
        if (state != AnimState.lattack&&state != AnimState.sattack&&!check){
            if (jumpCount < 2) {
//
            jumpCount++;
            speed += JUMP_POWER;
            if (speed > JUMP_POWER) {
                speed = JUMP_POWER;
            }
            TitleScene.get().soundPlay(R.raw.tressa_attack2,1.0f);
            setAnimState(jumpCount == 1 ? AnimState.jump : AnimState.djump);
        }}
    }
    public void shortAttack() {
        if (state != AnimState.lattack&&state != AnimState.sattack&&!check){
        if(saReload<=0){
            TitleScene.get().soundPlay(6,1,1.0f);
            setAnimState(AnimState.sattack);
            x+=UiBridge.x(30);
            width=UiBridge.x(160);
            saReload=100;
            fab.reset();
            TitleScene.get().soundPlay(R.raw.spear_air_attack,1.0f);
        }}
    }
    public void longAttack() {
        if (state != AnimState.lattack&&state != AnimState.sattack&&!check){
        if(laReload<=0){
            TitleScene.get().soundPlay(6,0,1.0f);
            setAnimState(AnimState.lattack);
            laReload=30;
            fab.reset();
            MainScene scene = MainScene.get();
            scene.getGameWorld().add(MainScene.Layer.arrow.ordinal(), new Arrow(x,y));
            TitleScene.get().soundPlay(R.raw.bow_attack,0.6f);
        }}
    }



    @Override
    public void getBox(RectF rect) {
        int hw = width / 10;
        int hh = height / 10;
        rect.left = x - hw*4;
        rect.top = y - hh*4;
        rect.right = x + hw*4;
        rect.bottom = y + hh*4;
    }
}
