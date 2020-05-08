package kr.ac.kpu.game.exgame.game.framework;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.nfc.Tag;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import kr.ac.kpu.game.exgame.game.iface.GameObject;
import kr.ac.kpu.game.exgame.game.iface.Recyclable;
import kr.ac.kpu.game.exgame.game.world.MainWorld;

public abstract class GameWorld {
    private static final String TAG = GameWorld.class.getSimpleName();
    protected View view;
    protected long frameTimeNanos;
    protected long timeDiffNanos;
    protected RecyclePool recyclePool=new RecyclePool();


    public  static  GameWorld get(){
        if(singleton==null){
//            singleton=new GameWorld();
            Log.e(TAG,"gameWorld not created");
        }
        return singleton;
    }
    protected  static  GameWorld singleton;
    //private ArrayList<GameObject> objects;
    protected Rect rect;

    protected GameWorld(){
    }
    public RecyclePool getRecyclePool(){
        return this.recyclePool;
    }
    public ArrayList<GameObject> objectsAt(int index) {
        return layers.get(index);
    }

//    HashMap<Class,ArrayList<GameObject>> recyclePool=new

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    protected void initLayers() {
        layers=new ArrayList<>();
        int layerCount=getLayerCount();
        for(int i = 0; i< layerCount; i++){
            ArrayList<GameObject> layer = new ArrayList<>();
            layers.add(layer);
        }
    }

    abstract protected int getLayerCount();

    public void initObjects() {

    }



    public void initResources(View view){
        this.view=view;
        initLayers();
        initObjects();
    }

    protected ArrayList<ArrayList<GameObject>> layers;

    public void draw(Canvas canvas) {
        for(ArrayList<GameObject> objects:layers){
            for(GameObject o: objects){
                o.draw(canvas);
            }
        }

    }
    public  long getTimeDiffNanos(){
        return timeDiffNanos;
    }
    public  float getTimeDiffInSecond(){
        return (float)(timeDiffNanos/1000000000.0);
    }

    public long getCurrentTimeNanos() {
        return frameTimeNanos;
    }

    public void update(long frameTimeNanos) {
        this.timeDiffNanos=frameTimeNanos-this.frameTimeNanos;
        this.frameTimeNanos=frameTimeNanos;
        if(rect==null){
            return;
        }

        for(ArrayList<GameObject> objects:layers) {

            for (GameObject o : objects) {
                o.update();
            }
        }

        if(trash.size()>0) {
            removeTrashObjects();
        }
        trash.clear();
    }

    protected void removeTrashObjects() {
        for(int tIndex=trash.size()-1;tIndex>=0;tIndex--){
            GameObject tobj=trash.get(tIndex);
            for(ArrayList<GameObject> objects:layers) {
                int index=objects.indexOf(tobj);
                if(index>=0) {
                    objects.remove(index);
                    break;
                }
            }
            trash.remove(tIndex);
            if(tobj instanceof Recyclable){
                ((Recyclable) tobj).recycle();
                getRecyclePool().add(tobj);
            }
        }
    }

    public void setRect(Rect rect) {
        //boolean first=this.rect==null;
        this.rect=rect;
//        if(first){
//            initObjects(view);
//        }
    }
    public int getLeft(){
        return rect.left;
    }
    public int getRight(){
        return rect.right;
    }
    public int getTop(){
        return rect.top;
    }
    public int getBottom(){
        return rect.bottom;
    }



    public Resources getResources() {
        return view.getResources();
    }

    public void add(final int index, final GameObject obj) {
        view.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<GameObject> objects=layers.get(index);
                objects.add(obj);
            }
        });
    }

    protected ArrayList<GameObject>trash=new ArrayList<>();
    public void remove(GameObject obj) {
        trash.add(obj);
    }

    public Context getContext() {
        return view.getContext();
    }

    public void pause() {
    }

    public void resume() {

    }
}
