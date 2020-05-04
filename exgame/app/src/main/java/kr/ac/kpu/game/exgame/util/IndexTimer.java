package kr.ac.kpu.game.exgame.util;

import kr.ac.kpu.game.exgame.game.framework.GameWorld;

public class IndexTimer {
    private static final String TAG = IndexTimer.class.getSimpleName();
    private final int count;
    private long time;
    private final int fps;

    public IndexTimer(int count, int framesPerSecond) {
        this.count=count;
        this.fps= framesPerSecond;
        this.time= GameWorld.get().getCurrentTimeNanos();
        
    }
    public int getIndex(){
        long elapsed=  GameWorld.get().getCurrentTimeNanos()-time;
        int index=(int)((elapsed*fps+500000000)/1000000000);
        //Log.d(TAG,"e*f="+(elapsed*fps)+"index="+index);
        return index %count;
    }

    public void reset() {
        this.time= GameWorld.get().getCurrentTimeNanos();
    }

    public boolean done() {
        long elapsed= GameWorld.get().getCurrentTimeNanos()-time;
        int index=(int)((elapsed*fps+500000000)/1000000000);
        return index>=count;
    }
}
