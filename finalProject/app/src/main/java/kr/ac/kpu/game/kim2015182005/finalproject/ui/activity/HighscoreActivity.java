package kr.ac.kpu.game.kim2015182005.finalproject.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.game.world.MainWorld;

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
