package kr.ac.kpu.game.kim2015182005.finalproject.game.scene;

import android.graphics.RectF;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameScene;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.ScoreObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.TextObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.ui.TouchManager;

public class WinScene extends GameScene {
        private static final String TAG = WinScene.class.getSimpleName();
        private static WinScene instance;
        private ScoreObject expScore,jpScore,goldScore;
        private int kill_count=0;
        private int exp_score,jp_score,gold_score;
        public enum Layer {
            bg, enemy, player, ui, COUNT
        }

        @Override
        protected int getLayerCount() {
            return Layer.COUNT.ordinal();
        }



        @Override
        public void enter() {
            super.enter();
            instance=this;
            TitleScene.get().getBgmPlayer().setBGM(R.raw.win);
            TitleScene.get().getBgmPlayer().setVolume(0.5f);
            TitleScene.get().getBgmPlayer().startBGM();
            TitleScene.get().soundPlay(R.raw.tressa_win3,1.0f);
            this.setTransparent(true);
            initObjects();

        }


    private void initObjects() {
        int screenWidth = UiBridge.metrics.size.x;
        int screenHeight = UiBridge.metrics.size.y;
        exp_score=200;
        jp_score=100;
        gold_score=300;
        kill_count=MainScene.get().getKill_count();
        int cx = UiBridge.metrics.center.x;
        int cy = UiBridge.metrics.center.y;
        BitmapObject winBg=new BitmapObject(cx, cy, screenWidth, screenHeight,R.mipmap.cave_win_bg);
        gameWorld.add(Layer.bg.ordinal(), winBg);
        BitmapObject winBg2=new BitmapObject(cx, cy, screenWidth, screenHeight,R.mipmap.result);
        gameWorld.add(Layer.bg.ordinal(), winBg2);

        RectF rbox = new RectF(UiBridge.x(-410), UiBridge.y(137), UiBridge.x(-395), UiBridge.y(157));
        expScore = new ScoreObject(R.mipmap.numbers, rbox);
        expScore.add(exp_score*kill_count);
        gameWorld.add(Layer.ui.ordinal(), expScore);
        rbox.set(UiBridge.x(-410), UiBridge.y(173), UiBridge.x(-395), UiBridge.y(193));
        jpScore = new ScoreObject(R.mipmap.numbers, rbox);
        jpScore.add(jp_score*kill_count);
        gameWorld.add(Layer.ui.ordinal(), jpScore);
        rbox.set(UiBridge.x(-410), UiBridge.y(211), UiBridge.x(-395), UiBridge.y(231));
        goldScore = new ScoreObject(R.mipmap.numbers, rbox);
        goldScore.add(gold_score*kill_count);
        gameWorld.add(Layer.ui.ordinal(), goldScore);


        TextObject expAt = new TextObject(exp_score+" X 적 처치 "+kill_count, cx+ UiBridge.x(80), cy - UiBridge.y(62), 55, "#000000", true);
        gameWorld.add( Layer.bg.ordinal(), expAt);
        TextObject jpAt = new TextObject(jp_score+" X 적 처치 "+kill_count, cx+ UiBridge.x(80), cy - UiBridge.y(22), 55, "#000000", true);
        gameWorld.add( Layer.bg.ordinal(), jpAt);
        TextObject goldAt = new TextObject(gold_score+" X 적 처치 "+kill_count, cx+ UiBridge.x(80), cy + UiBridge.y(16), 55, "#000000", true);
        gameWorld.add( Layer.bg.ordinal(), goldAt);

        TextObject endAt = new TextObject("Touch to title", cx, cy + UiBridge.y(105), 70, "#000000", true);
        gameWorld.add( Layer.bg.ordinal(), endAt);


        TouchManager tm = new TouchManager(UiBridge.metrics.size.x/4, UiBridge.metrics.size.y/4,UiBridge.metrics.size.x/4*3,UiBridge.metrics.size.y/4*3);
        tm.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                TitleScene.get().getBgmPlayer().stopBGM();
                TitleScene.get().getSoundEffects().play(R.raw.select_button,1.0f);
                TitleScene.get().getBgmPlayer().setBGM(R.raw.main);
                TitleScene.get().getBgmPlayer().setloop(true);
                TitleScene.get().getBgmPlayer().startBGM();
                pop();
                pop();
                pop();
                pop();
//                LoadingScene scene1 = new LoadingScene();
//                scene1.push();
 //               LoadingScene.get().setFinish(false);

            }
        });
        gameWorld.add(Layer.ui.ordinal(), tm);


    }

    @Override
    public void onBackPressed() {

    }

    public static WinScene get() {return instance; }


 }

