package kr.ac.kpu.game.kim2015182005.finalproject.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.view.GameView;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.FirstScene;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.MainScene;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        UiBridge.setActivity(this);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new GameView(this));


        new FirstScene().run();
        //new MainScene().run();
    }
}
