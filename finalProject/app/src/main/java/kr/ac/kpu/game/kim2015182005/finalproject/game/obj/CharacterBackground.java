package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import android.graphics.Canvas;
import android.util.Log;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.AnimObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.TextObject;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.TitleScene;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.CharacterSelectScene;

public class CharacterBackground extends GameObject {
    private static final String TAG = CharacterBackground.class.getSimpleName();
    private final StoryBackground story_bg;
    private BitmapObject profile;
    private boolean storyOn;
    private AnimObject op;
    private TextObject name,name_bg;

    private static final int[] profile_IDS = {
        R.mipmap.tressa_profile,R.mipmap.olberic_profile,R.mipmap.primrose_profile,
            R.mipmap.alfyn_profile,R.mipmap.therion_profile,R.mipmap.haanit_profile,
            R.mipmap.ophilia_profile,R.mipmap.cyrus_profile
    };
    private static final int[] op_IDS = {
            R.mipmap.tressa_op24,R.mipmap.olberic_op21,R.mipmap.primrose_op13,
            R.mipmap.alfyn_op16,R.mipmap.therion_op23,R.mipmap.haanit_op23,
            R.mipmap.ophilia_op24,R.mipmap.cyrus_op21
    };
    private static final int[] op_count = {
            24,21,13,
            16,23,23,
            24,21
    };
    private static final String[] ch_name = {
            "Tressa","Olberic","Primrose",
            "Alfyn","Therion","H'aanit",
            "Ophilia","Cyrus"
    };
    public CharacterBackground(int n) {
        storyOn=false;

        story_bg=new StoryBackground(UiBridge.metrics.center.x/5*2-UiBridge.metrics.size.x/5*2,UiBridge.metrics.center.y-UiBridge.metrics.size.y/15,UiBridge.metrics.size.x/5*2,UiBridge.metrics.size.y-UiBridge.metrics.size.y/3,R.mipmap.story_bg, n);
        story_bg.alpha(150);
        CharacterSelectScene.get().getGameWorld().add(TitleScene.Layer.bg.ordinal(),story_bg);
        CharacterSelectScene.get().getGameWorld().add(CharacterSelectScene.Layer.bg.ordinal(),new BitmapObject(UiBridge.metrics.center.x,UiBridge.metrics.center.y-UiBridge.metrics.size.y/20,UiBridge.metrics.size.x,UiBridge.metrics.size.y-UiBridge.metrics.size.y/10,R.mipmap.map_frame));
        profile=new BitmapObject(UiBridge.metrics.size.x-UiBridge.x(150),UiBridge.metrics.center.y-UiBridge.metrics.size.y/20,UiBridge.metrics.size.x/2,UiBridge.metrics.size.y-UiBridge.metrics.size.y/10,profile_IDS[n]);
        CharacterSelectScene.get().getGameWorld().add(TitleScene.Layer.ui.ordinal(),profile);
        op=new AnimObject(UiBridge.metrics.center.x+UiBridge.x(10),UiBridge.metrics.center.y+UiBridge.y(10),UiBridge.x(172),UiBridge.y(183),op_IDS[n],4,op_count[n]);
        CharacterSelectScene.get().getGameWorld().add(TitleScene.Layer.ui.ordinal(),op);
        name_bg=new TextObject(ch_name[n],UiBridge.metrics.center.x+UiBridge.metrics.size.x/20*7,UiBridge.metrics.size.y-UiBridge.metrics.size.y/5,105,"#000000",true);
        CharacterSelectScene.get().getGameWorld().add(TitleScene.Layer.ui.ordinal(),name_bg);
        name=new TextObject(ch_name[n],UiBridge.metrics.center.x+UiBridge.metrics.size.x/20*7,UiBridge.metrics.size.y-UiBridge.metrics.size.y/5,100,"#FFFFFF",true);
        CharacterSelectScene.get().getGameWorld().add(TitleScene.Layer.ui.ordinal(),name);
        setCBFlashSpeed(5);
    }

    public AnimObject getOp() {
        return op;
    }

    @Override
    public void update() {
        super.update();
    }
    public boolean flashDone(){
        return profile.isFlashDone();
    }
    public void flash(){
        profile.flash(255);
        op.flash(255);
        name.flash(255);
        name_bg.flash(255);
        storyBgMove();

    }
    public void setCBAlpha(int x){
        profile.alpha(x);
        op.alpha(x);
        name.alpha(x);
        name_bg.alpha(x);
    }
    public void setCBFlashSpeed(int x){
        profile.setFlashSpeed(x);
        op.setFlashSpeed(x);
        name.setFlashSpeed(x);
        name_bg.setFlashSpeed(x);
    }
    public void storyBgMove(){
        if(storyOn) {
            story_bg.setNewPos(UiBridge.metrics.center.x/5*2-UiBridge.metrics.size.x/3, UiBridge.metrics.center.y - UiBridge.metrics.size.y / 15);
            storyOn=false;
        }else{
            story_bg.setNewPos(UiBridge.metrics.center.x / 5 * 2, UiBridge.metrics.center.y - UiBridge.metrics.size.y / 15);
            storyOn=true;
        }
    }


    @Override
    public void remove() {
        super.remove();
        profile.remove();
        op.remove();
        story_bg.remove();
        name.remove();
    }

}
