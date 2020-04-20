package kr.ac.kpu.game.exgame.gameobj;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class GameWorld {
    private static final int BALL_COUNT = 10;
    private Fighter fighter;
    private View view;

    public  static  GameWorld get(){
        if(singleton==null){
            singleton=new GameWorld();
        }
        return singleton;
    }
    public  static  GameWorld singleton;
    private ArrayList<GameObject> objects;
    private Rect rect;

    private GameWorld(){
    }

    public void initResources(View view){
        this.view=view;
        Resources res = view.getResources();
        objects=new ArrayList<>();
        Random rand= new Random();
        for(int i=0;i<BALL_COUNT;i++){
            float x= rand.nextFloat()*1000;
            float y= rand.nextFloat()*1000;
            float dx= (float) (rand.nextFloat()*50.0-25.0);
            float dy= (float)(rand.nextFloat()*50.0-25.0);
            objects.add(new Ball(res,x,y,dx,dy));
        }
        objects.add(new Plane(res,500,500,0.0f,0.0f));
        fighter = new Fighter( 200, 700);
        objects.add(fighter);
    }

    public void draw(Canvas canvas) {
        for(GameObject o: objects){
            o.draw(canvas);
        }
    }

    public void update() {
        for(GameObject o: objects){
            o.update();
        }
        objects.removeAll(trash);
        trash.clear();
    }

    public void setRect(Rect rect) {
        this.rect=rect;
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

    public void add(final GameObject obj) {
        view.post(new Runnable() {
            @Override
            public void run() {
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
