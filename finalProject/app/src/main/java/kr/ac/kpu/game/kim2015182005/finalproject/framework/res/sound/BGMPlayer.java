package kr.ac.kpu.game.kim2015182005.finalproject.framework.res.sound;

import android.media.MediaPlayer;

import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;

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
