package kr.ac.kpu.game.kim2015182005.finalproject.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameScene;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.view.GameView;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.MainScene;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.TitleScene;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        UiBridge.setActivity(this);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new GameView(this));
        new TitleScene().run();
        //new MainScene().run();
        //new InGameStoryScene().run();
        //new MenuScene().run();
        //new SecondScene().run();
    }
    @Override
    public void onBackPressed() {
        handleBackPressed();
    }
    public void handleBackPressed() {
        GameScene.getTop().onBackPressed();}

}
