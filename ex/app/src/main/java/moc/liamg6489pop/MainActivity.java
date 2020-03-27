package moc.liamg6489pop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {//상속

    @Override   //재정의
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv =findViewById(R.id.textView6);   //컨트롤+p 파라미터
        tv.setText("Launched");

    }

    public void onBtnFirst(View v){
        TextView tv = findViewById(R.id.textView4);
        tv.setText("First Button Pressed");
        ImageView iv=findViewById(R.id.dogImageView);
        iv.setImageResource(R.mipmap.dog1);
    }
    public void onBtnsecond(View view) {
        ImageView iv=findViewById(R.id.dogImageView);
        iv.setImageResource(R.mipmap.dog2);
        Random random=new Random();
        final int value = random.nextInt(100) + 1;
        final TextView tv=findViewById(R.id.textView5);
        tv.setText("ramdom number:"+value);
        Handler h =new Handler();
        h.postDelayed(new Runnable(){
            @Override
            public void run(){
                tv.setText("Timer has changed: "+(value+100));
            }
        },1000);
    }
    public void onBtnThird(View view) {
        TextView tv= findViewById(R.id.textView6);
        int count =0;
        try{
            count=Integer.parseInt((String) tv.getText());
        }catch(Exception e){
        }
        count++;

        tv.setText(String.valueOf(count));
        new AlertDialog.Builder(this).setTitle("Hello").setMessage("World").setPositiveButton("hahaha", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();

    }

}
