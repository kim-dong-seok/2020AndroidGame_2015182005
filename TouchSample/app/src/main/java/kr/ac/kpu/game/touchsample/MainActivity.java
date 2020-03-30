package kr.ac.kpu.game.touchsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getCanonicalName();
    private View mainView;
    private View movingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainView = findViewById(R.id.mainView);
        movingView=findViewById(R.id.movingView);

        mainView.setOnTouchListener(mainOnTouchListener);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Log.d(TAG,"Touch Event:"+event.getAction()+"-"+event.getX()+","+event.getY());
//
//        if(event.getAction()==MotionEvent.ACTION_DOWN){
//            moveView(event.getX(),event.getY());
//        }
//        return super.onTouchEvent(event);
//    }
    private View.OnTouchListener mainOnTouchListener=new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.d(TAG,"onTouch Event:"+event.getAction()+"-"+event.getX()+","+event.getY());
            if(event.getAction()==MotionEvent.ACTION_DOWN) {
                moveView(event.getX(), event.getY());
            }
            return true;
        }
    };
    private void moveView(float x, float y) {
//        ViewPropertyAnimator animator=movingView.animate();
//        animator.x(x);
//        animator.y(y);
//        animator.setDuration(0);
//        animator.start();

        int w= movingView.getWidth();
        int h= movingView.getHeight();
        movingView.animate()
                  .x(x-w/2)
                  .y(y-h/2)
                  .setDuration(100)
                  .start();
    }
}
