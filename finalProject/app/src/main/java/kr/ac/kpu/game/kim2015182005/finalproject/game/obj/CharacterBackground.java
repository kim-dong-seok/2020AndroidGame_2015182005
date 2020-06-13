package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import android.util.Log;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.AnimObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.bg.ImageScrollBackground;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.bg.MapBackground;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.FirstScene;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.SecondScene;

public class CharacterBackground extends GameObject {
    private static final String TAG = CharacterBackground.class.getSimpleName();
    private static final int[] profile_IDS = {
        R.mipmap.tressa_profile,R.mipmap.olberic_profile,R.mipmap.primrose_profile,
            R.mipmap.alfyn_profile,R.mipmap.therion_profile,R.mipmap.haanit_profile,
            R.mipmap.olberic_profile,R.mipmap.cyrus_profile
    };
    private static final int[] op_IDS = {
            R.mipmap.op_tressa,R.mipmap.op_olberic,R.mipmap.op_primrose,
            R.mipmap.op_alfyn,R.mipmap.op_therion,R.mipmap.op_haanit,
            R.mipmap.op_olberic,R.mipmap.op_cyrus
    };
    public CharacterBackground(int n,BitmapObject[] profile,AnimObject[] op) {
        profile[n]=new BitmapObject(UiBridge.metrics.size.x-UiBridge.x(150),UiBridge.metrics.center.y,UiBridge.metrics.size.x/2,UiBridge.metrics.size.y,profile_IDS[0]);
        SecondScene.get().getGameWorld().add(FirstScene.Layer.bg.ordinal(),profile[n]);
        op[n]=new AnimObject(UiBridge.metrics.center.x+UiBridge.x(10),UiBridge.metrics.center.y+UiBridge.x(30),UiBridge.x(82),UiBridge.y(132),op_IDS[0],4,24);
        SecondScene.get().getGameWorld().add(FirstScene.Layer.bg.ordinal(),op[n]);

    }

    @Override
    public void update() {
        super.update();

    }

    @Override
    public void remove() {
        super.remove();
    }

}
