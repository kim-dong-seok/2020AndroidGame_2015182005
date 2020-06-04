package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import android.content.res.Resources;
import android.util.Log;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.Recyclable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameScene;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameTimer;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameWorld;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.AnimObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.res.bitmap.SharedBitmap;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.MainScene;

import static kr.ac.kpu.game.kim2015182005.finalproject.game.scene.MainScene.*;

public class Arrow extends BitmapObject implements Recyclable  {
    private static final String TAG = Arrow.class.getSimpleName();
    private float dx;
    public Arrow(float x, float y) {
        super(x, y, 50, 50, R.mipmap.arrow);
        this.dx = 500;

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
        b.dx=500;
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

        //Log.d(TAG,"dx="+ dx * seconds);
        boolean toBeDeleted=false;

        if(!toBeDeleted){
            if(x>2000){
                toBeDeleted=true;
            }
        }
        if(toBeDeleted){
            gs.getGameWorld().remove(this);
        }
    }
    public void recycle() {

    }
}
