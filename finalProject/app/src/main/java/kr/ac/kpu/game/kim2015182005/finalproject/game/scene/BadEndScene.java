package kr.ac.kpu.game.kim2015182005.finalproject.game.scene;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameScene;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.TextObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.ui.TouchManager;

public class BadEndScene extends GameScene {
        private static final String TAG = BadEndScene.class.getSimpleName();
        private static BadEndScene instance;

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
            TitleScene.get().getBgmPlayer().setBGM(R.raw.game_over);
            TitleScene.get().getBgmPlayer().setloop(false);
            TitleScene.get().getBgmPlayer().startBGM();
            this.setTransparent(true);
            initObjects();

        }


    private void initObjects() {
        int screenWidth = UiBridge.metrics.size.x;
        int screenHeight = UiBridge.metrics.size.y;

        int cx = UiBridge.metrics.center.x;
        int cy = UiBridge.metrics.center.y;
        BitmapObject endBg=new BitmapObject(cx, cy, screenWidth, screenHeight,R.mipmap.gameover_bg);
        gameWorld.add(Layer.bg.ordinal(), endBg);
        TextObject endAt = new TextObject("Touch to title", cx, cy + UiBridge.y(75), 60, "#d8cfcc", true);
        gameWorld.add( Layer.bg.ordinal(), endAt);


        TouchManager tm = new TouchManager(UiBridge.metrics.size.x/4, UiBridge.metrics.size.y/4,UiBridge.metrics.size.x/4*3,UiBridge.metrics.size.y/4*3);
        tm.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                TitleScene.get().getBgmPlayer().stopBGM();
                TitleScene.get().getSoundEffects().play(R.raw.select_button,1.0f);
//                TitleScene.get().getBgmPlayer().setBGM(R.raw.main);
//                TitleScene.get().getBgmPlayer().setloop(true);
//                TitleScene.get().getBgmPlayer().startBGM();
                TitleScene.get().getBgmPlayer().setBGM(R.raw.battle);
                TitleScene.get().getBgmPlayer().setloop(true);
                TitleScene.get().getBgmPlayer().startBGM();
                MainScene.get().setBad_end(false);
                MainScene.get().setCheat(true);
                MainScene.get().getEnd_bg().alpha(0);

                pop();
//                pop();
//                pop();
            }
        });
        gameWorld.add(Layer.ui.ordinal(), tm);


    }

    @Override
    public void onBackPressed() {

    }

    public static BadEndScene get() {return instance; }


 }

