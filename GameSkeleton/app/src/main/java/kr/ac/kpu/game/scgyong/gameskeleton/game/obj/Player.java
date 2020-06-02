package kr.ac.kpu.game.scgyong.gameskeleton.game.obj;

import kr.ac.kpu.game.scgyong.gameskeleton.R;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.GameTimer;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.obj.AnimObject;
import kr.ac.kpu.game.scgyong.gameskeleton.game.scene.MainScene;

public class Player extends AnimObject {
    private static final String TAG = Player.class.getSimpleName();
    private float dx, dy;
    private int state;
    private int jumpState;
    private int befState;
    private int hp;
    private float jumpY,originY,originX;

    public Player(float x, float y, float dx, float dy) {
        super(x, y, 95, 180, R.mipmap.tressa_right_move, 7, 6);
        this.dx = dx;
        this.dy = dy;
        this.hp=100;
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

    public void shortAttack(){
        befState=this.state;
        originX=this.x;
        this.x+=50;
        state=3;
        changeBitmap(290,170,R.mipmap.tressa_right_short_attack,10,4);
    }
    public void longAttack(){
        befState=this.state;
        originX=this.x;
        state=4;
        changeBitmap(111,165,R.mipmap.tressa_right_long_attack,15,7);
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
        if(fab.done()&&(state==3||state==4)){
            this.state=0;
            this.x=originX;
            changeBitmap(95, 180, R.mipmap.tressa_right_move, 7, 6);
        }

    }
}
