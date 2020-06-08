package kr.ac.kpu.game.kim2015182005.finalproject.game.obj;

import kr.ac.kpu.game.kim2015182005.finalproject.R;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.iface.Recyclable;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.main.GameTimer;
import kr.ac.kpu.game.kim2015182005.finalproject.framework.obj.AnimObject;

public class Enemy extends AnimObject implements Recyclable {
    private static final String TAG = Enemy.class.getSimpleName();
    private float dx, dy;
    private int state;
    private int jumpState;
    private int befState;
    private int hp;
    private float jumpY,originY,originX;

    public Enemy(float x, float y, float dx, float dy, int resId,int fps,int count) {
        super(x, y, 95, 180, resId, fps, count);
        this.dx = dx;
        this.dy = dy;
        this.hp=20;
        this.state=0;
        this.jumpState=0;
    }

    @Override
    public float getRadius() {
        return this.width / 4;
    }

    public int getHp(){
        return hp;
    }
    public int getState(){
        return state;
    }
    public int getJumpState(){
        return jumpState;
    }


    public void jump(){
        jumpState=1;
        originY=y;
        jumpY=y-dy*3;
    }
    public void update() {
        float seconds = GameTimer.getTimeDiffSeconds();
        if (jumpState==1) {
            y -= dy *4* seconds;
            if(y<jumpY) {
                y=jumpY;
                jumpState=2;
            }
        }
        if(jumpState==2){
            y += dy *5* seconds;
            if(y>originY) {
                y=originY;
                jumpState=0;
            }
        }


    }
    public void recycle() {

    }
}
