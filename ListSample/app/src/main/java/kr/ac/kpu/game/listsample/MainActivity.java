package kr.ac.kpu.game.listsample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import kr.ac.kpu.game.listsample.data.HighScoreItem;
import kr.ac.kpu.game.listsample.data.Serializer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView listView;
    private ArrayList<HighScoreItem> scores=new ArrayList<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        scores.add(new HighScoreItem("Hello",new Date(),20000));
//        scores.add(new HighScoreItem("Hello",new Date(),20000));
//        scores.add(new HighScoreItem("Hello",new Date(),20000));
        scores=Serializer.load(this);

        listView=findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
    BaseAdapter adapter=new BaseAdapter(){
        @Override
        public int getCount() {
            return scores.size();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //Log.d(TAG,"Pos:"+ position+"convertView:"+convertView);
            View view =convertView;
            if(view==null){
                Log.d(TAG,"Loading from XML"+ position);
                LayoutInflater inflater = getLayoutInflater();
                view=inflater.inflate(R.layout.score_item,null);
            }
            TextView tv_name= view.findViewById(R.id.scoreItemTextView_name);
            TextView tv_date= view.findViewById(R.id.scoreItemTextView_date);
            TextView tv_score= view.findViewById(R.id.scoreItemTextView_score);
            HighScoreItem s=scores.get(position);
            tv_name.setText("플레이어: "+s.name);
            tv_score.setText("점수: "+s.score);
            tv_date.setText("기록 시간: "+dateFormat.format(s.date));
            ImageView iv=view.findViewById(R.id.scoreItemImageView);
            switch (s.country){
                case 0:
                    iv.setImageResource(R.mipmap.south_korea);
                    break;
                case 1:
                    iv.setImageResource(R.mipmap.japan);
                    break;
                case 2:
                    iv.setImageResource(R.mipmap.china);
                    break;
                case 3:
                    iv.setImageResource(R.mipmap.usa);
                    break;
            }

            return view;
        }
        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    };

    public void onBtnAdd(View view) {
        final EditText et=new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle(R.string.highscore)
                .setMessage(R.string.add_highscore_message)
                .setView(et)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name=et.getText().toString();
                        int score=new Random().nextInt(100000);
                        addHighscore(name,score);
                    }
                })
                .setNegativeButton(R.string.cancel,null)
                .create()
                .show();
    }

    private void addHighscore(String name, int score) {
        scores.add(new HighScoreItem(name,new Date(),score));
        Serializer.save(this, scores);
        //listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void onBtnDelete(View view) {
    }
}
