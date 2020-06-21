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
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameWorld;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.AnimObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.res.bitmap.SharedBitmap;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.util.CollisionHelper;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.MainScene;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.TitleScene;

import static kr.ac.kpu.game.kim2015182005.finalproject.game.scene.MainScene.*;

public class Arrow extends BitmapObject implements Recyclable, BoxCollidable {
    private static final String TAG = Arrow.class.getSimpleName();
    private float dx;
    private int hp=40;
    public Arrow(float x, float y) {
        super(x, y, 100, 20, R.mipmap.arrow);
        this.dx =700;

        Log.d(TAG,"new"+this);
    }

    public static Arrow get(float x, float y) {
        GameScene gs=GameScene.getTop();
        Resources res=UiBridge.getResources();
        Arrow b = (Arrow) gs.getGameWorld().getRecyclePool().get(Arrow.class);
        if(b==null) {
            b = new Arrow(x, y);
        }
        b.sbmp= SharedBitmap.load(R.mipmap.arrow);

        b.x=x;
        b.y=y;
        b.dx=700;
        return b;
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

        ArrayList<GameObject> enemys = MainScene.get().getGameWorld().objectsAtLayer(MainScene.Layer.enemy.ordinal());
        for (GameObject obj : enemys) {
            if (!(obj instanceof Enemy)) {
                continue;
            }
            Enemy enemy = (Enemy) obj;
            if (CollisionHelper.collides(this, enemy)&&enemy.isColidable()) {
                Log.d(TAG,"arrow collides");
                TitleScene.get().soundPlay(R.raw.bow_hit,0.5f);
                int ehp=enemy.getHp();
                ehp-=hp;
                if(ehp<=0){
                    MainScene.get().getPlayer().setATB(MainScene.get().getPlayer().getATB()+40);
                    enemy.remove();
                    MainScene.get().addKillcount();
                }else{
                    MainScene.get().getPlayer().setATB(MainScene.get().getPlayer().getATB()+20);
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
