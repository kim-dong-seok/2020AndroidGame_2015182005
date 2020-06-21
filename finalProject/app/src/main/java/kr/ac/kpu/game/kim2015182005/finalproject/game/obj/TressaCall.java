package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import android.content.res.Resources;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.BoxCollidable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.Recyclable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameScene;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameTimer;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.res.bitmap.SharedBitmap;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.util.CollisionHelper;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.MainScene;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.TitleScene;

import static kr.ac.kpu.game.kim2015182005.finalproject.game.scene.MainScene.Layer;

public class TressaCall extends BitmapObject implements Recyclable, BoxCollidable {
    private static final String TAG = TressaCall.class.getSimpleName();
    private float dx;
    private int num;
    private int atk;
    private static int[] CallAttack = {
            10,15,20,25,30,40,50
    };
    private static final int[] RES_IDS = {
            R.mipmap.call_priest

    };

    public TressaCall(float x, float y,int num) {
        super(x, y, 100, 20,RES_IDS[num] );
        sbmp= SharedBitmap.load(RES_IDS[num]);
        atk=CallAttack[num];
        dx=700;
        Log.d(TAG,"new"+this);
    }

    public static TressaCall get(float x, float y,int num) {
        GameScene gs=GameScene.getTop();
        Resources res=UiBridge.getResources();
        TressaCall c = (TressaCall) gs.getGameWorld().getRecyclePool().get(TressaCall.class);
        if(c==null) {
            c = new TressaCall(x, y,num);
        }
        c.sbmp= SharedBitmap.load(RES_IDS[num]);
        c.atk=CallAttack[num];
        c.x=x;
        c.y=y;
        c.dx=700;
        return c;
    }

    @Override
    public float getRadius() {
        return this.width / 4;
    }

    public void update() {
        GameScene gs=MainScene.getTop();

        float seconds = GameTimer.getTimeDiffSeconds();
        x += dx * seconds;

        Log.d(TAG,"GameTimer.getTimeDiffSeconds()"+GameTimer.getTimeDiffSeconds());
        super.update();
        //
        if (x > UiBridge.metrics.size.x) {
            remove();
        }
        checkEnemyCollision();
    }

    private void checkEnemyCollision() {

        ArrayList<GameObject> enemys = MainScene.get().getGameWorld().objectsAtLayer(Layer.enemy.ordinal());
        for (GameObject obj : enemys) {
            if (!(obj instanceof Enemy)) {
                continue;
            }
            Enemy enemy = (Enemy) obj;
            if (CollisionHelper.collides(this, enemy)&&enemy.isColidable()) {
                Log.d(TAG,"arrow collides");
                TitleScene.get().soundPlay(R.raw.bow_hit,0.5f);
                int ehp=enemy.getHp();
                ehp-=atk;
                if(ehp<=0){
                    MainScene.get().getPlayer().setATB(MainScene.get().getPlayer().getATB()+20);
                    enemy.remove();
                    MainScene.get().addKillcount();
                }else{
                    MainScene.get().getPlayer().setATB(MainScene.get().getPlayer().getATB()+10);
                    enemy.setHp(ehp);
                    enemy.setX(enemy.getX()+UiBridge.x(100));
                }
                this.remove();
            }
        }
    }


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
