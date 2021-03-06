package kr.ac.kpu.game.kim2015182005.finalproject.framework.main;

import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.ListIterator;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.Recyclable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.Touchable;

public class GameWorld {
    private static final String TAG = GameWorld.class.getSimpleName();
    protected RecyclePool recyclePool = new RecyclePool();
    protected ArrayList<ArrayList<GameObject>> layers;
    protected ArrayList<GameObject> trash = new ArrayList<>();
    protected Touchable capturingObject;


    public GameWorld(int layerCount) {
        layers = new ArrayList<>(layerCount);
        for (int i = 0; i < layerCount; i++) {
            Log.d(TAG, "Adding layer " + i);
            layers.add(new ArrayList<GameObject>());
        }

    }

    public ArrayList<GameObject> objectsAtLayer(int layer) {
        return layers.get(layer);
    }

    public void draw(Canvas canvas) {
        for (ArrayList<GameObject> objects: layers) {
            for (GameObject o : objects) {
                o.draw(canvas);
            }
        }
    }

    public void update() {
        for (ArrayList<GameObject> objects: layers) {
            for (GameObject o : objects) {
                o.update();
            }
        }
        if (trash.size() > 0) {
            clearTrash();
        }
    }

    public void add(final int layerIndex, final GameObject obj) {
        ArrayList<GameObject> objects = layers.get(layerIndex);
        int index = objects.indexOf(obj);
        if (index >= 0) {
            Log.e(TAG, "Duplicated: " + index + " / " + objects.size() + " : " + obj);
            return;
        }

        UiBridge.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<GameObject> objects = layers.get(layerIndex);
                objects.add(obj);
            }
        });
    }
    private void clearTrash() {
//        UiBridge.post(new Runnable() {
//            @Override
//            public void run() {
        for (int ti = trash.size() - 1; ti >= 0; ti--) {
            GameObject o = trash.get(ti);
            for (ArrayList<GameObject> objects: layers) {
                int i = objects.indexOf(o);
                if (i >= 0) {
                    objects.remove((i));
                    break;
                }
            }
            trash.remove(ti);
            if (o instanceof Recyclable) {
                ((Recyclable) o).recycle();
                recyclePool.add(o);
            }
        }
//            }
//        });
    }

    public RecyclePool getRecyclePool() {
        return recyclePool;
    }

    public void captureTouch(Touchable obj) {
        Log.d(TAG, "Capture: " + obj);
        capturingObject = obj;
    }
    public void releaseTouch() {
        Log.d(TAG, "Release: " + capturingObject);
        capturingObject = null;
    }
    public boolean onTouchEvent(MotionEvent event) {
        if (capturingObject != null) {
            return capturingObject.onTouchEvent(event);
        }
        ListIterator li=layers.listIterator(layers.size());
        for (ArrayList<GameObject> objects: layers) {
            for (GameObject o : objects) {
                if (o instanceof Touchable) {
                    boolean ret = ((Touchable) o).onTouchEvent(event);
                    if (ret) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void removeObject(GameObject gameObject) {
        trash.add(gameObject);
    }
}
