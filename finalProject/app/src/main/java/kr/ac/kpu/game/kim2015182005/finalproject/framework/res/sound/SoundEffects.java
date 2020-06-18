package kr.ac.kpu.game.kim2015182005.finalproject.framework.res.sound;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

import kr.ac.kpu.game.kim2015182005.finalproject.R;

public class SoundEffects {
    private static final String TAG = SoundEffects.class.getSimpleName();
    private static SoundEffects singleton;
    private SoundPool soundPool;
    private HashMap<Integer, Integer> soundIdMap = new HashMap<>();
    private static final int[] SOUND_IDS = {
            R.raw.tressa_attack2,R.raw.tressa_attack1,R.raw.tressa_attack3,R.raw.tressa_attack4,R.raw.tressa_attack5,R.raw.tressa_attack6
            ,R.raw.tressa_hit1,R.raw.tressa_hit2,R.raw.tressa_hit3,R.raw.bow_attack,R.raw.spear_attack
            ,R.raw.tressa_select,R.raw.tressa_select2,R.raw.tressa_select3,R.raw.tressa_select4,R.raw.tressa_select5
            ,R.raw.select_button,R.raw.back_button,R.raw.menu_move,R.raw.menu_window
    };

    public static SoundEffects get() {
        if (singleton == null) {
            singleton = new SoundEffects();
        }
        return singleton;
    }
    private SoundEffects() {
        AudioAttributes audioAttributes;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            this.soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(3)
                    .build();
        } else {
            this.soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        }
    }
    public void loadAll(Context context) {
        for (int resId: SOUND_IDS) {
            int soundId = soundPool.load(context, resId, 1);
            soundIdMap.put(resId, soundId);
        }
    }

    public static int[] getSoundIds() {
        return SOUND_IDS;
    }

    public int play(int resId,float volume) {
        int soundId = soundIdMap.get(resId);
        int streamId = soundPool.play(soundId, volume, volume, 1, 0, 1f);
        return streamId;
    }

}
