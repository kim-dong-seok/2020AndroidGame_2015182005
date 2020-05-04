package kr.ac.kpu.game.exgame.game.obj;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.kpu.game.exgame.R;
import kr.ac.kpu.game.exgame.game.framework.GameWorld;
import kr.ac.kpu.game.exgame.game.iface.BoxCollidable;
import kr.ac.kpu.game.exgame.game.iface.GameObject;
import kr.ac.kpu.game.exgame.game.iface.Recyclable;
import kr.ac.kpu.game.exgame.game.world.MainWorld;
import kr.ac.kpu.game.exgame.util.CollisionHelper;
import kr.ac.kpu.game.exgame.game.res.bitmap.FrameAnimationBitmap;

public class Bullet implements GameObject, BoxCollidable, Recyclable {

    private static final String TAG = Bullet.class.getSimpleName();
    public static final int FRAMES_PER_SECOND = 6;
    public static final int FRAME_COUNT = 13;
    private static float BULLET_SPEED=1500;
    private FrameAnimationBitmap fab;
    private int halfSize;

    private float x;
    private float y;
    private int power;

    private Bullet(){
        Log.d(TAG,"new"+this);
    }
    public static Bullet get( float x, float y){
        GameWorld gw=GameWorld.get();
        Resources res=gw.getResources();
        Bullet b = (Bullet) gw.getRecyclePool().get(Bullet.class);
        if(b==null) {
            b = new Bullet();
        }
        b.fab= new FrameAnimationBitmap(R.mipmap.metal_slug_missile,FRAMES_PER_SECOND, FRAME_COUNT);
        b.halfSize=b.fab.getHeight()/2;
        b.x=x;
        b.y=y;
        b.power=100;
        return b;
    }

    public void update(){
        //GameWorld gw=GameWorld.get();
        MainWorld gw = MainWorld.get();

        y-=BULLET_SPEED*gw.getTimeDiffInSecond();

        boolean toBeDeleted=false;
        ArrayList<GameObject> enemies=gw.objectsAt(MainWorld.Layer.enemy);
        for (GameObject e:enemies){
            if(!(e instanceof Enemy)) {
                Log.e(TAG,"Object at Layer.enemy is: "+e);
                continue;
            }
            Enemy enemy= (Enemy) e;
            if(CollisionHelper.collides(enemy,this)){
                enemy.decreaseLife(this.power);
                toBeDeleted=true;
                break;
            }
        }
        if(!toBeDeleted){
            if(y<gw.getTop()-halfSize){
                toBeDeleted=true;
            }
        }
        if(toBeDeleted){
            gw.remove(this);
        }
        //Log.d(TAG,"index="+index);
    }
    public void draw(Canvas canvas){
        fab.draw(canvas,x,y);
    }

    @Override
    public void getBox(RectF rect) {
        int hw=fab.getWidth()/2;
        int hh=fab.getHeight()/2;
        rect.left=x-hw;
        rect.right=x+hw;
        rect.top=y-hh;
        rect.bottom=y+hh;
    }

    @Override
    public void recycle() {

    }
}
