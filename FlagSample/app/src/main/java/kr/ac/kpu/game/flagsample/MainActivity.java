package kr.ac.kpu.game.flagsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private JSONObject json;
//    private ImageView imageView;
    private HashMap<String,Bitmap> imageCache=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startJsonDownloadThead();
        //imageView=findViewById(R.id.imageView);
        // startImageDownloadThread();
//        Bitmap bitmap=loadBitmapFromNetwork();
//        imageView.setImageBitmap(bitmap);
        listView=findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    private void startJsonDownloadThead() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final JSONObject json=loadJsonFromNetwork();
                listView.post(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.this.json=json;
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    private void startImageDownloadThread(final String strUrl, final ImageView imageView, final int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap=loadBitmapFromNetwork(strUrl);
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageCache.put(strUrl,bitmap);
                        int first=listView.getFirstVisiblePosition();
                        int last=listView.getLastVisiblePosition();
                        if(position<first||position>last) {
                            return;
                        }
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
    }

    private JSONObject loadJsonFromNetwork() {
        try {
            String strUrl="http://scgyong.net/thumbs/";
            URL url = new URL(strUrl);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            InputStream is=conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader streamReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                sb.append(inputStr);

            JSONObject jobj=new JSONObject(sb.toString());
            return jobj;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Bitmap loadBitmapFromNetwork(String strUrl) {
        try {
            //String strUrl="http://scgyong.net/thumbs/slow.php/204_192131.jpg";
            URL url = new URL(strUrl);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            InputStream is=conn.getInputStream();
            Bitmap bitmap=BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;

    }

//    private void loadCountries() {
//        AssetManager assets=getAssets();
//        try {
//            InputStream is =assets.open("nations.js");
//            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
//            BufferedReader streamReader = new BufferedReader(isr);
//            StringBuilder sb = new StringBuilder();
//
//            String inputStr;
//            while ((inputStr = streamReader.readLine()) != null)
//                sb.append(inputStr);
//
//            JSONArray jarr=new JSONArray(sb.toString());
//            this.continents=jarr;
//
//            adapter.notifyDataSetChanged();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    private BaseAdapter adapter=new BaseAdapter() {
        @Override
        public int getCount() {
            try {
                JSONArray albums = json.getJSONArray("albums");
                return albums.length();
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (NullPointerException e){
                e.printStackTrace();
            }
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //재활용
            View view=convertView;
            if(view==null){
                LayoutInflater inflater=getLayoutInflater();
                view=inflater.inflate(R.layout.country_item,null);
            }
            String artistName="",albumTitle="";
            //포지션 데이터 가져오기
            try {
                JSONArray albums = json.getJSONArray("albums");
                JSONObject album=albums.getJSONObject(position);

                artistName=album.getString("artistName");
                albumTitle=album.getString("albumTitle");

                ImageView iv=view.findViewById(R.id.imageView);
                String imageUrl=album.getString("image");
                Bitmap bitmap=imageCache.get(imageUrl);
                if (bitmap != null) {
                    iv.setImageBitmap(bitmap);
                } else {
                    iv.setImageResource(R.mipmap.note);
                    startImageDownloadThread(imageUrl, iv, position);
                }
            } catch (JSONException e) {
            }

            //뷰에 연결
            TextView attv=view.findViewById(R.id.albumTitleTextView);
 //           ImageView iv=view.findViewById(R.id.imageView);
            attv.setText(albumTitle);
            TextView antv=view.findViewById(R.id.artistNameTextView);
            antv.setText(artistName);

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
}
