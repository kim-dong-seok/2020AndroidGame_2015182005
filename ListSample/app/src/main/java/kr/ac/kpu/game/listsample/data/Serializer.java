package kr.ac.kpu.game.listsample.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Serializer {

    public static final String PREFS_NAME = "Highscore";
    public static final String PREFS_KEY="scores";
    private static final String TAG = Serializer.class.getSimpleName();

    public static void save(Context context, ArrayList<HighScoreItem> items){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String jsonString=convertJson(items);
        Log.d(TAG,"JSON="+jsonString);
        editor.putString(PREFS_KEY,jsonString);
        editor.commit();
    }
    private static String convertJson(ArrayList<HighScoreItem> items) {
        JSONArray jarr=new JSONArray();
        for(HighScoreItem item:items){
            jarr.put(item.toJsonObect());
        }
        return jarr.toString();
    }
    private static String convertJson_old(ArrayList<HighScoreItem> items) {
        StringBuilder sb= new StringBuilder();
        sb.append("[");
        String comma="";
        for(HighScoreItem item:items){
            sb.append(comma);
            sb.append(item.toJsonString());
            comma=",";
        }
        sb.append("]");
        return sb.toString();
    }

    public static ArrayList<HighScoreItem> load(Context context){
        ArrayList<HighScoreItem> items=new ArrayList<>();

        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String jsonString=prefs.getString(PREFS_KEY,"[]");
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            int count=jsonArray.length();
            for(int i=0;i<count;i++){
                JSONObject s=jsonArray.getJSONObject(i);

                HighScoreItem item=new HighScoreItem(s);
                items.add(item);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        return items;
    }
}
