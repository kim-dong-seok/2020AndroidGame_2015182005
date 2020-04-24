package kr.ac.kpu.game.exgame.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import kr.ac.kpu.game.exgame.R;
import kr.ac.kpu.game.exgame.gameobj.Ball;
import kr.ac.kpu.game.exgame.gameobj.GameObject;
import kr.ac.kpu.game.exgame.gameobj.GameWorld;
import kr.ac.kpu.game.exgame.gameobj.Plane;
import kr.ac.kpu.game.exgame.util.IndexTimer;

public class GameView extends View {
    private static final String TAG = GameView.class.getSimpleName();
    public static final int FRAME_RATE_SECONDS = 10;
    private Rect rect;
    private Paint mainPaint;

    private GameWorld gameWorld;
    private IndexTimer timer;


    public GameView(Context context) {
        super(context);
        initResources();
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initResources();
    }

    private void initResources() {
        mainPaint=new Paint();
        mainPaint.setColor(0xFFFFEEEE);

        rect=new Rect();
        gameWorld=GameWorld.get();
        gameWorld.initResources(this);


        timer=new IndexTimer(FRAME_RATE_SECONDS,1);
        postFrameCallback();

    }

    private void postFrameCallback() {
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                update(frameTimeNanos);
                invalidate();
                postFrameCallback();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rect.left=getPaddingLeft();
        rect.top=getPaddingTop();
        rect.right=getWidth()-getPaddingRight();
        rect.bottom=getHeight()-getPaddingBottom();
        gameWorld.setRect(rect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(rect,mainPaint);
        gameWorld.draw(canvas);
    }

    private int count;
    public void update(long frameTimeNanos) {
        gameWorld.update(frameTimeNanos);
        count++;
        if(timer.done()){
                Log.d(TAG,"Frame Count="+((float)count/FRAME_RATE_SECONDS));
                count=0;
                timer.reset();
        }
    }

    public void doAction() {
        gameWorld.doAction();
    }
}
