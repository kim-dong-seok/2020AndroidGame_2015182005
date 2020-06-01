package kr.ac.kpu.game.scgyong.gameskeleton.game.obj;

import kr.ac.kpu.game.scgyong.gameskeleton.R;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.GameTimer;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.main.UiBridge;
import kr.ac.kpu.game.scgyong.gameskeleton.framework.obj.AnimObject;

public class Player extends AnimObject {
    private float dx, dy;
    private int state;
    private int hp;

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
    public void shortAttack(){

    }
    public void longAttack(){

    }
    public void jump(){

    }
    public void update() {

    }
}
