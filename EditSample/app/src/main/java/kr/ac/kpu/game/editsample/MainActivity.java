package kr.ac.kpu.game.editsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PieView pieView = new PieView(this);
        PieView pieView2 = new PieView(this);
        //setContentView(pieView);
        FrameLayout parentView=findViewById(R.id.pieParent);
        parentView.addView(pieView);

        FrameLayout parentView2=findViewById(R.id.pieParent2);
        parentView2.addView(pieView2);

        editText=findViewById(R.id.editText);
        textView=findViewById(R.id.textView);

        editText.setOnEditorActionListener(editorActionListener);
    }
    private TextView.OnEditorActionListener editorActionListener=new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            String text = editText.getText().toString();
            textView.setText("You entered: "+text);
            return false;
        }
    };

    public void onBtnPush(View view) {
        String text = editText.getText().toString();
        textView.setText("You entered: "+text);
    }
}
