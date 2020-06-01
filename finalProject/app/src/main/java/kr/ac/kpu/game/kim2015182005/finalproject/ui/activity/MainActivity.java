package kr.ac.kpu.game.kim2015182005.finalproject.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import kr.ac.kpu.game.kim2015182005.finalproject.game.res.sound.SoundEffects;
import kr.ac.kpu.game.kim2015182005.finalproject.game.world.MainWorld;
import kr.ac.kpu.game.kim2015182005.finalproject.ui.view.GameView;

public class MainActivity extends AppCompatActivity {

    private static final long GAMEVIEW_UPDATE_INTERVAL_MSEC = 30;
    private static final String TAG = MainActivity.class.getSimpleName();
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        MainWorld.create();
        gameView=new GameView(this);
        setContentView(gameView);

        SoundEffects se= SoundEffects.get();
        se.init(this);
        se.loadAll();
        //gameView=findViewById(R.id.gameView);
        //postUpdate();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        Log.d(TAG,"new Configuration"+newConfig);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPause() {
        gameView.pause();
        //       GameWorld.get().pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        gameView.resume();
        //       GameWorld.get().resume();
        super.onResume();
    }

    //    private void postUpdate() {
//        gameView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                gameView.update();
//                gameView.invalidate();
//                postUpdate();
//            }
//        },GAMEVIEW_UPDATE_INTERVAL_MSEC);
//    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if(event.getAction()==MotionEvent.ACTION_DOWN){
//            gameView.doAction();
//        }
//        return true;
//    }
}