package kr.ac.kpu.game.exgame.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import kr.ac.kpu.game.exgame.R;
import kr.ac.kpu.game.exgame.game.world.MainWorld;

public class HighscoreActivity extends AppCompatActivity {

    private TextView highscoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        highscoreTextView=findViewById(R.id.highscoreTextView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getSharedPreferences(MainWorld.PREFS_NAME, Context.MODE_PRIVATE);
        int highscore=prefs.getInt(MainWorld.PREF_KEY_HIGHSCORE,0);
//        highscoreTextView.setText(""+highscore);
        highscoreTextView.setText(String.valueOf(highscore));
    }
}
