package kr.ac.kpu.game.exgame.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import kr.ac.kpu.game.exgame.R;
import kr.ac.kpu.game.exgame.sound.SoundEffects;
import kr.ac.kpu.game.exgame.ui.view.GameView;

public class MainActivity extends AppCompatActivity {

    private static final long GAMEVIEW_UPDATE_INTERVAL_MSEC = 30;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SoundEffects se= SoundEffects.get();
        se.init(this);
        se.loadAll();
        gameView=findViewById(R.id.gameView);
        postUpdate();
    }

    private void postUpdate() {
        gameView.postDelayed(new Runnable() {
            @Override
            public void run() {
                gameView.update();
                gameView.invalidate();
                postUpdate();
            }
        },GAMEVIEW_UPDATE_INTERVAL_MSEC);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            gameView.doAction();
        }
        return true;
    }
}
