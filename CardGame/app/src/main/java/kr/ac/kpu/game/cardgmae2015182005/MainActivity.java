package kr.ac.kpu.game.cardgmae2015182005;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int[] BUTTON_IDS={
            R.id.b_00,R.id.b_01,R.id.b_02,R.id.b_03,
            R.id.b_10,R.id.b_11,R.id.b_12,R.id.b_13,
            R.id.b_20,R.id.b_21,R.id.b_22,R.id.b_23,
            R.id.b_30,R.id.b_31,R.id.b_32,R.id.b_33,
            R.id.b_40,R.id.b_41,R.id.b_42,R.id.b_43,
            R.id.b_50,R.id.b_51,R.id.b_52,R.id.b_53,
    };
    private static final int[] IMAGE_RES_BACK_IDS={
            R.mipmap.card_back1,R.mipmap.card_back2,R.mipmap.card_back3,R.mipmap.card_back4,
            R.mipmap.card_back5,R.mipmap.card_back6,R.mipmap.card_back7,R.mipmap.card_back8,
            R.mipmap.card_back9,R.mipmap.card_back10,R.mipmap.card_back11,R.mipmap.card_back12,
            R.mipmap.card_back13,R.mipmap.card_back14,R.mipmap.card_back15,R.mipmap.card_back16,
            R.mipmap.card_back17,R.mipmap.card_back18,R.mipmap.card_back19,R.mipmap.card_back20,
            R.mipmap.card_back21,R.mipmap.card_back22,R.mipmap.card_back23,R.mipmap.card_back24,
    };
    private static final int[] IMAGE_RES_FRONT_IDS={
            R.mipmap.card1,R.mipmap.card2,R.mipmap.card3,R.mipmap.card4,
            R.mipmap.card5,R.mipmap.card6,R.mipmap.card7,R.mipmap.card8,
            R.mipmap.card9,R.mipmap.card10,R.mipmap.card11,R.mipmap.card12,
    };
    private ImageButton lastButton;
    private int flips;
    private TextView scoreTextView;
    private String scoreFormatSting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectViews();

        startGame();
    }

    private void connectViews() {
        scoreTextView = findViewById(R.id.scoreTexView);
        Resources res = getResources();
        scoreFormatSting = res.getString(R.string.flips_fmt);
    }

    private void startGame() {
        int[] buttonIds=shuffleButtonIds();
        for (int i=0;i<BUTTON_IDS.length;i++){
            ImageButton btn=findViewById(buttonIds[i]);
            int resId=IMAGE_RES_FRONT_IDS[i/2];
            btn.setTag(resId);
            for (int j=0;j<BUTTON_IDS.length;j++) {
                if(buttonIds[i]==BUTTON_IDS[j]){
                btn.setImageResource(IMAGE_RES_BACK_IDS[j]);
                }
            }
            btn.setScaleType(ImageView.ScaleType.FIT_CENTER);
            btn.setEnabled(true);
        }
        lastButton=null;
        flips =0;
        sowScore();
    }

    private void sowScore() {
        String text = String.format(scoreFormatSting, flips);
        scoreTextView.setText(text);
    }

    private int[] shuffleButtonIds() {
        int[] buttonIds= Arrays.copyOf(BUTTON_IDS,BUTTON_IDS.length);
        Random rand=new Random();
        for(int i=0;i<BUTTON_IDS.length;i++){
            int r=rand.nextInt(buttonIds.length);
            int temp=buttonIds[i];
            buttonIds[i]=buttonIds[r];
            buttonIds[r]=temp;
        }
        return  buttonIds;
    }

    public void onBtnItem(View view) {
        Log.d(TAG,"Button ID = "+view.getId());
        Log.d(TAG,"Button Index = "+(view.getId()-R.id.b_00));

        ImageButton btn = (ImageButton) view;
        int resId= (int) btn.getTag();
        btn.setImageResource(resId);
        btn.setEnabled(false);

        if(lastButton==null){
            lastButton=btn;
            return;
        }
        if((int)lastButton.getTag()==(int)btn.getTag()){
            lastButton=null;
            return;
        }
        for (int j=0;j<BUTTON_IDS.length;j++) {
            if(lastButton.getId()==BUTTON_IDS[j]){
                lastButton.setImageResource(IMAGE_RES_BACK_IDS[j]);
            }
        }

        lastButton.setEnabled(true);
        lastButton=btn;

        flips++;
        sowScore();
    }

    public void onBtnRestart(View view) {
        Log.d(TAG,"onBtnRestart");
//        startGame();
        new AlertDialog.Builder(this)
                .setTitle(R.string.restrartTitle)
                .setMessage(R.string.restartMessage)
                .setNegativeButton(R.string.restartNo,null)
                .setPositiveButton(R.string.restartYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startGame();
                    }
                })
                .create()
                .show();
    }
}
