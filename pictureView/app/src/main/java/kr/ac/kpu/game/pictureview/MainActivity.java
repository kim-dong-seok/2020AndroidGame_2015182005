package kr.ac.kpu.game.pictureview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.CollationElementIterator;

public class MainActivity extends AppCompatActivity {

    private int pageNumber;
    private static int[] IMAGE_RES_IDS={
            R.mipmap.running1,R.mipmap.running2,R.mipmap.running3,
            R.mipmap.running4,R.mipmap.running5,R.mipmap.running6,
            R.mipmap.running7,R.mipmap.running8,R.mipmap.running9,
            R.mipmap.running10,R.mipmap.running11,R.mipmap.running12,
            R.mipmap.running13,
    };
    private ImageView mainImageView;
    private TextView headerTextView;
    private String headerFormatString;
    private ImageButton prevButton;
    private ImageButton nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainImageView = findViewById(R.id.mainImageView);
        headerTextView = findViewById(R.id.headerTextView);
        prevButton=findViewById(R.id.prevButton);
        nextButton=findViewById(R.id.nextButton);

        Resources res=getResources();
        headerFormatString=res.getString(R.string.header_title_fmt);

        pageNumber=1;
        showPage();
    }

    private void showPage() {

        prevButton.setEnabled(pageNumber>1);
        nextButton.setEnabled((pageNumber<IMAGE_RES_IDS.length));

        int resId = IMAGE_RES_IDS[pageNumber-1];
        mainImageView.setImageResource(resId);
        String text=String.format(headerFormatString,pageNumber,IMAGE_RES_IDS.length);
        headerTextView.setText(text);
    }

    public void onBtnPrev(View view) {
//        if(pageNumber<=1) return;;
        pageNumber--;
        if(pageNumber==1) {
            prevButton.setEnabled(false);
        }
        showPage();

    }

    public void onBtnNext(View view) {
//        if(pageNumber>=IMAGE_RES_IDS.length) return;
        pageNumber++;
        if(pageNumber==IMAGE_RES_IDS.length) {
            nextButton.setEnabled(false);
        }
        showPage();
    }
}
