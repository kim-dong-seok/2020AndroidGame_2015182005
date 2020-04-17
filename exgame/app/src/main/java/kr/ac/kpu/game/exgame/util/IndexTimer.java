package kr.ac.kpu.game.exgame.util;

import android.util.Log;

public class IndexTimer {
    private static final String TAG = IndexTimer.class.getSimpleName();
    private final int count;
    private long time;
    private final int fps;

    public IndexTimer(int count, int framesPerSecond) {
        this.count=count;
        this.fps= framesPerSecond;
        this.time=System.currentTimeMillis();
        
    }
    public int getIndex(){
        long elapsed= System.currentTimeMillis()-time;
        int index=(int)((elapsed*fps+500)/1000);
        //Log.d(TAG,"e*f="+(elapsed*fps)+"index="+index);
        return index %count;
    }

    public void reset() {
        this.time=System.currentTimeMillis();
    }

    public boolean done() {
        long elapsed= System.currentTimeMillis()-time;
        int index=(int)((elapsed*fps+500)/1000);
        return index>=count;
    }
}
