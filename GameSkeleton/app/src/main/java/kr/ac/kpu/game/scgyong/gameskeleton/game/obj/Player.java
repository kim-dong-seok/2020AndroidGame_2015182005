package kr.ac.kpu.game.scgyong.gameskeleton.game.obj;

import kr.ac.kpu.game.scgyong.gameskeleton.R;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.GameTimer;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.UiBridge;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.obj.AnimObject;

public class Player extends AnimObject {
    private float dx, dy;
    private int state;
    private int hp;
    private float jumpY,originY;

    public Player(float x, float y, float dx, float dy) {
        super(x, y, 95, 180, R.mipmap.tressa_right_move, 7, 6);
        this.dx = dx;
        this.dy = dy;
        this.hp=100;
        this.state=0;
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
    public void shortAttack(){

    }
    public void longAttack(){

    }
    public void jump(){
        state=1;
        originY=y;
        jumpY=y-dy*3;
    }
    public void update() {
        float seconds = GameTimer.getTimeDiffSeconds();
        if (state==1) {
            y -= dy *4* seconds;
            if(y<jumpY) {
                y=jumpY;
                state=2;
            }
        }
        if(state==2){
            y += dy *5* seconds;
            if(y>originY) {
                y=originY;
                state=0;
            }
        }
    }
}
