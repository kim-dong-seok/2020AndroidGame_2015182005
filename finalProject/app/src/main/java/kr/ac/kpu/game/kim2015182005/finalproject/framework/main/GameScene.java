package kr.ac.kpu.game.kim2015182005.finalproject.framework.main;

import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

public abstract class GameScene {
    private static final String TAG = GameScene.class.getSimpleName();
    private static ArrayList<GameScene> sceneStack = new ArrayList<>();

    protected static GameScene topGameScene;

    public static void drawScenes(Canvas canvas) {

        int topIndex = sceneStack.size() - 1;

       // Log.d(TAG,"topindex"+sceneStack.size());
        if (topIndex < 0) {
            return;
        }
        drawSceneAt(topIndex, canvas);
       // getTop().draw(canvas);
       // Log.d(TAG,"gettop"+getTop());
    }

    protected static void drawSceneAt(int stackIndex, Canvas canvas) {
        GameScene scene = sceneStack.get(stackIndex);
        if (scene.isTransparent() && stackIndex > 0) {

            drawSceneAt(stackIndex - 1, canvas);
        }
       // Log.d(TAG,"tackIndex"+stackIndex);
        scene.draw(canvas);
       // Log.d(TAG,"tackIndex"+stackIndex);
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    protected GameWorld gameWorld;

    public boolean isTransparent() {
        return transparent;
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    protected boolean transparent;

    abstract protected int getLayerCount();
    protected GameScene() {
        int layerCount = getLayerCount();
        gameWorld = new GameWorld(layerCount);
    }

    public static GameScene getTop() {
//        if (topGameScene == null) {
//            Log.e(TAG, "topGameScene is null on getTop(). Maybe a bug?");
//            return null;
//        }
        return topGameScene;
    }
    public void run() {
        if (sceneStack.size() != 0) {
            Log.e(TAG, "sceneStack size is not 0 on run(). Maybe a bug?");
        }
        sceneStack.add(this);
        topGameScene = this;

        this.enter();
    }
    public void push() {
        int size = sceneStack.size();
        if (size > 0) {
            GameScene gw = sceneStack.get(size - 1);
            gw.pause();
        }
        sceneStack.add(this);
        topGameScene = this;
        this.enter();
    }
    public static void pop() {
        int size = sceneStack.size();
        if (size == 0) return;
        GameScene top = sceneStack.get(size - 1);
        top.exit();
        sceneStack.remove(size - 1);
        if (size == 1) {
            UiBridge.exit();
            return;
        }
        topGameScene = sceneStack.get(size - 2);
        topGameScene.resume();
    }
    public void replace() {
        int size = sceneStack.size();
        if (size == 0) {
            Log.e(TAG, "sceneStack size is 0 on replace(). Maybe a bug?");
        }
        GameScene top = sceneStack.get(size - 1);
        top.exit();
        sceneStack.remove(size - 1);
        sceneStack.add(this);
        topGameScene = this;
        this.enter();
    }

    public void update() { gameWorld.update(); }
    public void draw(Canvas canvas) { gameWorld.draw(canvas); }
    public void enter() { Log.v(TAG, "enter() - " + getClass().getSimpleName()); }
    public void exit() { Log.v(TAG, "exit() - " + getClass().getSimpleName()); }
    public void pause() { Log.v(TAG, "pause() - " + getClass().getSimpleName()); }
    public void resume() { Log.v(TAG, "resume() - " + getClass().getSimpleName()); }

    public boolean onTouchEvent(MotionEvent event) {
        return gameWorld.onTouchEvent(event);
    }

    public void onBackPressed() {
        pop();
    }
}
