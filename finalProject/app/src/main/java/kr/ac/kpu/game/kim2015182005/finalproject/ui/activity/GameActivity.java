package kr.ac.kpu.game.kim2015182005.finalproject.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.view.GameView;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.FirstScene;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UiBridge.setActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));

        new FirstScene().run();
    }
}
