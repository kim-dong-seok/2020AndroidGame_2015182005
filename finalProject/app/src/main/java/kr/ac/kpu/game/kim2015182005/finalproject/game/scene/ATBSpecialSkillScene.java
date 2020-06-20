package kr.ac.kpu.game.kim2015182005.finalproject.game.scene;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameScene;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.UiBridge;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.BGBlack;
import kr.ac.kpu.game.kim2015182005.finalproject.game.obj.ui.SelectButton;

public class ATBSpecialSkillScene extends GameScene {
        private static final String TAG = ATBSpecialSkillScene.class.getSimpleName();
        private BGBlack atbBG;
        private static ATBSpecialSkillScene instance;
        private boolean clickOn;
    private SelectButton atbSSbutton1,atbSSbutton2,atbSSbutton3,atbSSbutton4;
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

//            int mdpi_100 = UiBridge.y(100);
//            //ValueAnimator animator =
//            ValueAnimator anim = ValueAnimator.ofFloat(UiBridge.metrics.size.y, mdpi_100);
//            anim.setDuration(500);
//            anim.setInterpolator(new OvershootInterpolator());
//            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    Float value = (Float) animation.getAnimatedValue();
//                    scrollTo(value);
//                }
//            });
//            anim.start();
        }

//        private void scrollTo(float y) {
//            int mdpi_100 = UiBridge.y(100);
//            ArrayList<GameObject> objs = gameWorld.objectsAtLayer(Layer.ui.ordinal());
//            int count = objs.size();
//            for (int i = 0; i < count; i++) {
//                ATBWindow atbWindow1 = (ATBWindow )objs.get(i);
//                float diff = y - atbWindow1.getY();
//                atbWindow1.move(0, diff);
//
//                y += mdpi_100;
//            }
//        }
        public void setClickOn(boolean clickOn) {
            this.clickOn = clickOn;
        }

    public boolean isClickOn() {
        return clickOn;
    }



    private void initObjects() {
            int screenWidth = UiBridge.metrics.size.x;
            int screenHeight = UiBridge.metrics.size.y;

            int cx = UiBridge.metrics.center.x;
            int y = UiBridge.metrics.center.y;
            atbBG=new BGBlack(0, 0, screenWidth, screenHeight,"#000000");
            atbBG.alpha(100);
            gameWorld.add(Layer.ui.ordinal(),atbBG );

        atbSSbutton1 = new SelectButton(cx-UiBridge.x(100),y-UiBridge.y(100),UiBridge.x(200), UiBridge.y(50),200,"",50,R.mipmap.atb_window,R.mipmap.atb_window);
        atbSSbutton1.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {

            }

        });
        ATBSpecialSkillScene.get().getGameWorld().add(Layer.ui.ordinal(), atbSSbutton1);

        atbSSbutton2 = new SelectButton(UiBridge.metrics.size.x-UiBridge.x(100),y-UiBridge.y(50),UiBridge.x(200), UiBridge.y(50),200,"",50,R.mipmap.atb_window,R.mipmap.atb_window);
        atbSSbutton2.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {

            }

        });
        gameWorld.add(Layer.ui.ordinal(), atbSSbutton2);
        atbSSbutton3 = new SelectButton(UiBridge.metrics.size.x-UiBridge.x(100),y+UiBridge.y(0),UiBridge.x(200), UiBridge.y(50),200,"",50,R.mipmap.atb_window,R.mipmap.atb_window);
        atbSSbutton3.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {

            }

        });
        gameWorld.add(Layer.ui.ordinal(), atbSSbutton3);
        atbSSbutton4 = new SelectButton(cx-UiBridge.x(100),y+UiBridge.y(50),UiBridge.x(200), UiBridge.y(50),200,"취소",50,R.mipmap.atb_window,R.mipmap.atb_window);
        atbSSbutton4.setOnClickRunnable(new Runnable() {
            @Override
            public void run() {
                if(! ATBSpecialSkillScene.get().isClickOn()){
                    ATBSpecialSkillScene.pop();
                }
            }

        });
       gameWorld.add(Layer.ui.ordinal(), atbSSbutton4);
        }



    public static ATBSpecialSkillScene get() {return instance; }


 }

