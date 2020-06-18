package kr.ac.kpu.game.kim2015182005.finalproject.framework.res.sound;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.io.IOException;
import java.util.HashMap;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameScene;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.view.GameView;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.FirstScene;
import kr.ac.kpu.game.kim2015182005.finalproject.game.scene.MainScene;

public class BGMPlayer {
    private MediaPlayer mediaPlayer;

    public BGMPlayer() {
        mediaPlayer = new MediaPlayer();
    }
    public void setBGM(int resId){
        mediaPlayer =MediaPlayer.create(UiBridge.getView().getContext(),resId);
    }
    public void setVolume(float volume){
        mediaPlayer.setVolume(volume,volume);
    }

    public void setloop(boolean loop) {
        mediaPlayer.setLooping(loop);
    }
    public void pauseBGM(){

        mediaPlayer.pause();
    }
    public void startBGM(){

        mediaPlayer.start();
    }
    public void stopBGM(){
        mediaPlayer.stop();
        mediaPlayer.release();
    }

}
