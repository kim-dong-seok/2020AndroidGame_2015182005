package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
            R.mipmap.prt1,R.mipmap.prt2,R.mipmap.prt3,R.mipmap.prt4,
            R.mipmap.prt5,R.mipmap.prt6,R.mipmap.prt7

    };
    private static int[] Eattack = {
            10,15,20,25,30,40,50
    };
    private static int[] Ehp = {
            40,60,80,100,120,160,200
    };
    private static final float GRAVITY_SPEED = 4500;
    public static final int ITEM_TYPE_COUNT = RES_IDS.length;
    private static final String TAG = Enemy.class.getSimpleName();
    public static final int SCORE_MULTIPLIER = 10;
    private int atk;
    private int speed=0;
    private int jumpCount = 10;
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
    private int totalHp;
    private Paint hpPaint;
    private boolean hit;
    public void setHit(boolean hit){
        this.setColor("#9E0200");
    }

    protected Enemy(float x, float y, int width, int height,int typeIndex) {
        super(x, y,width, height, RES_IDS[typeIndex], 4, 4);
        Log.d(TAG, "Creating CandyItem instance");
    }
    public static Enemy get(float x, float y, int width, int height,int typeIndex) {
        RecyclePool rpool = GameScene.getTop().getGameWorld().getRecyclePool();
        Enemy enemy = (Enemy) rpool.get(Enemy.class);
        if (enemy == null) {
            enemy = new Enemy(x, y, width, height, typeIndex);
            enemy.atk=Eattack[typeIndex];
            enemy.totalHp=Ehp[typeIndex];
            enemy.hp=enemy.totalHp;
            enemy.hpPaint=new Paint();

        } else {
            enemy.x = x;
            enemy.y = y;
            enemy.atk=Eattack[typeIndex];
            enemy.totalHp=Ehp[typeIndex];
            enemy.hp=enemy.totalHp;
            enemy.width = width;
            enemy.height = height;
            enemy.fab = new FrameAnimationBitmap(RES_IDS[typeIndex], 4, 4);
        }
        enemy.score = SCORE_MULTIPLIER ;
        enemy.colidable=true;
        return enemy;
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
        x-=UiBridge.x(1);;
        if (x < -width) {
            remove();
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
//
            float ptop = platform.getTop();
            if (jumpCount > 0) {
//                Log.d(TAG, "Platform box = " + rect + " footY = " + footY + " ptop=" + ptop);
                if (speed > 0 && footY >= ptop) {
//                    Log.d(TAG, " Stopping at the platform");
                    y = ptop - height / 2;
                    jumpCount = 0;
                    speed = 0;
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
//
    }
    public int ColiH(){
        int hw = width / 20;
        int hh = height / 20;
        return (int) (y- hh*4);

    }
    @Override
    public void draw(Canvas canvas) {
//        RectF rect1 = new RectF();
//        getBox(rect1);
//        canvas.drawRect(rect1.left, rect1.top ,rect1.right,rect1.bottom,paint);
        super.draw(canvas);
        if(hp<totalHp){
            hpPaint.setColor(Color.BLACK);
            canvas.drawRect(x-UiBridge.x(30),y-UiBridge.y(20),x+UiBridge.x(30),y-UiBridge.y(10),hpPaint);
            hpPaint.setColor(Color.RED);
            canvas.drawRect(x-UiBridge.x(29),y-UiBridge.y(19),x-UiBridge.x(29)+(x+UiBridge.x(29)-(x-UiBridge.x(29)))*hp/totalHp,y-UiBridge.y(11),hpPaint);
        }

    }

    @Override
    public void recycle() {
    }
    @Override
    public void getBox(RectF rect) {
        int hw = width / 20;
        int hh = height / 20;
        rect.left = x - hw*4;
        rect.top = y+ hh*3 ;
        rect.right = x + hw*4;
        rect.bottom = y + hh*12;
    }
}
