package kr.ac.kpu.game.kim2015182005.finalproject.game.scene;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameScene;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.BitmapObject;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.TextObject;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.BGBlack;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.SelectButton;

public class CharacterSelectBackPressScene extends GameScene {
        private static final String TAG = CharacterSelectBackPressScene.class.getSimpleName();
        private static CharacterSelectBackPressScene instance;
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
            this.setTransparent(true);
            initObjects();

        }

    private void initObjects() {
        int screenWidth = UiBridge.metrics.size.x;
        int screenHeight = UiBridge.metrics.size.y;

        int cx = UiBridge.metrics.center.x;
        int cy = UiBridge.metrics.center.y;
        BGBlack tbBg = new BGBlack(0, 0, screenWidth, screenHeight, "#000000");
        tbBg.alpha(100);
        gameWorld.add(Layer.bg.ordinal(), tbBg);


        BitmapObject tbAw = new BitmapObject(cx, cy, cx + UiBridge.x(60),cy, R.mipmap.select_window);
        tbAw.alpha(200);
        gameWorld.add( Layer.bg.ordinal(), tbAw);
        TextObject tbAt = new TextObject("타이틀 화면으로 돌아가시겠습니까?", cx, cy - UiBridge.y(40), 50, "#FFFFFF", true);
        gameWorld.add( Layer.bg.ordinal(), tbAt);
        SelectButton tbYButton = new SelectButton(cx - UiBridge.x(80), cy + UiBridge.y(30), UiBridge.x(120), UiBridge.y(40), 200,"예", 50, R.mipmap.btn_idle, R.mipmap.btn_select);
        tbYButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                TitleScene.get().getSoundEffects().play(R.raw.select_button,1.0f);
                pop();
                pop();
            }

        });
        gameWorld.add(Layer.ui.ordinal(), tbYButton);
        SelectButton tbNButton = new SelectButton(cx + UiBridge.x(80), cy + UiBridge.y(30), UiBridge.x(120), UiBridge.y(40), 200, "아니요", 50, R.mipmap.btn_select, R.mipmap.btn_idle);
        tbNButton.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                TitleScene.get().getSoundEffects().play(R.raw.select_button,1.0f);
                pop();
            }

        });
        gameWorld.add(Layer.ui.ordinal(), tbNButton);
    }


    public static CharacterSelectBackPressScene get() {return instance; }


 }

