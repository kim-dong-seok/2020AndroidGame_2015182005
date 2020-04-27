package kr.ac.kpu.game.exgame.gameobj;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameWorld {
    private static final int BALL_COUNT = 10;
    private Fighter fighter;
    private View view;
    private EnemyGenerator enemyGenerator=new EnemyGenerator();
    private long frameTimeNanos;
    private long timeDiffNanos;
    private Plane plane;
    private RecyclePool recyclePool=new RecyclePool();

    public  static  GameWorld get(){
        if(singleton==null){
            singleton=new GameWorld();
        }
        return singleton;
    }
    public  static  GameWorld singleton;
    //private ArrayList<GameObject> objects;
    private Rect rect;

    private GameWorld(){
    }
    public RecyclePool getRecyclePool(){
        return this.recyclePool;
    }
    public ArrayList<GameObject> objectsAt(Layer layer) {
        return layers.get(layer.ordinal());
    }

//    HashMap<Class,ArrayList<GameObject>> recyclePool=new

    public boolean onTouchEvent(MotionEvent event) {
        int action=event.getAction();
        if(action==MotionEvent.ACTION_DOWN){
            doAction();
            plane.head(event.getX(),event.getY());
        }
        else if (action==MotionEvent.ACTION_MOVE){
            plane.head(event.getX(),event.getY());
        }
        return true;
    }

    public enum Layer{
        bg,missile,enemy,player,ui,COUNT,
    }
    public void initResources(View view){
        this.view=view;
        initLayers();

        //objects.add(fighter);
    }

    private void initObjects(View view) {
        Resources res = view.getResources();
        //objects=new ArrayList<>();

        Random rand= new Random();
        for(int i=0;i<BALL_COUNT;i++){
            float x= rand.nextFloat()*1000;
            float y= rand.nextFloat()*1000;
            float dx= (float) (rand.nextFloat()*50.0-25.0);
            float dy= (float)(rand.nextFloat()*50.0-25.0);
            add(Layer.missile,new Ball(res,x,y,dx,dy));
            //objects.add(new Ball(res,x,y,dx,dy));
        }

        float playerY=rect.bottom-100;
        plane=new Plane(res,500,playerY,0.0f,0.0f);
        add(Layer.player,plane);
        //objects.add(new Plane(res,500,500,0.0f,0.0f));
        fighter=new Fighter( 200, 700);
        add(Layer.player, fighter);
    }

    protected ArrayList<ArrayList<GameObject>> layers;
    private void initLayers() {
        layers=new ArrayList<>();
        for(int i=0;i<Layer.COUNT.ordinal();i++){
            ArrayList<GameObject> layer = new ArrayList<>();
            layers.add(layer);
        }
    }

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
        enemyGenerator.update();
        for(ArrayList<GameObject> objects:layers) {

            for (GameObject o : objects) {
                o.update();
            }
        }
//            for(GameObject o: objects){
//            o.update();
//        }
//        objects.removeAll(trash);
        if(trash.size()>0) {
            removeTrashObjects();
        }
        trash.clear();
    }

    private void removeTrashObjects() {
        //for(GameObject to:trash){
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
        boolean first=this.rect==null;
        this.rect=rect;
        if(first){
            initObjects(view);
        }
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

    public void doAction() {
        fighter.fire();
    }

    public Resources getResources() {
        return view.getResources();
    }

    public void add(final Layer layer, final GameObject obj) {
        view.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<GameObject> objects=layers.get(layer.ordinal());
                objects.add(obj);
            }
        });
    }

    private ArrayList<GameObject>trash=new ArrayList<>();
    public void remove(GameObject obj) {
        trash.add(obj);
    }

    public Context getContext() {
        return view.getContext();
    }
}
