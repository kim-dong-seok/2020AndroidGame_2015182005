package kr.ac.kpu.game.exgame.gameobj;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclePool {
    private HashMap<Class, ArrayList<Object>> map=new HashMap<>();
    public  RecyclePool(){

    }

    public void add(Object obj){
        Class clazz=obj.getClass();
        ArrayList<Object> list=map.get(clazz);
        if(list==null){
            list=new ArrayList<>();
            map.put(clazz,list);
        }
        list.add(obj);
    }
    public Object get(Class clazz){
        ArrayList<Object> list=map.get(clazz);
        if(list==null){
            return null;
        }
        if(list.size()==0){
            return null;
        }
        return list.remove(0);
    }
}
