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
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import kr.ac.kpu.game.exgame.R;
import kr.ac.kpu.game.exgame.gameobj.Ball;
import kr.ac.kpu.game.exgame.gameobj.GameObject;
import kr.ac.kpu.game.exgame.gameobj.GameWorld;
import kr.ac.kpu.game.exgame.gameobj.Plane;

public class GameView extends View {
    private static final String TAG = GameView.class.getSimpleName();
    private Rect rect;
    private Paint mainPaint;

    private boolean moves;

    private GameWorld gameWorld;


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
        gameWorld.initResources(getResources());

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

    public void update() {
        if(moves){
            gameWorld.update();
        }
    }

    public void doAction() {
        moves=!moves;
        Log.d(TAG,"Now moves:"+moves);
    }
}
