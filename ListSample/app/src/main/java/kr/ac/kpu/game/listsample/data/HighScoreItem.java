package kr.ac.kpu.game.listsample.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Random;

public class HighScoreItem {
    public String name;
    public Date date;
    public int score;
    public int country;
    Random rnd=new Random();
    public HighScoreItem(String name, Date date, int score){
        this.name=name;
        this.date=date;
        this.score=score;
        this.country=rnd.nextInt(4);
    }
    public HighScoreItem(JSONObject s) throws JSONException {
        this.name=s.getString("name");
        long dateValue=s.getLong("date");
        this.date=new Date(dateValue);
        this.score=s.getInt("score");
        this.country=s.getInt("country");
    }
    public String toJsonString() {
        return "{\"name\":\""+name+"\",\"date\":"+date.getTime()+",\"score\":"+score+"\",\"country\":"+country+"}";

    }
    public JSONObject toJsonObect(){
        JSONObject s=new JSONObject();
        try{
            s.put("name",name);
            s.put("date",date.getTime());
            s.put("score",score);
            s.put("county",country);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return s;
    }
}
