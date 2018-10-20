package com.plane.flyingSon;

import com.plane.control.PlaneWar;
import com.plane.flyingPar.FlyingObject;

public class Bullet extends FlyingObject {
    //子弹的速度
    int speed=3;

    //子弹的构造方法，初始化子弹的位置
    public Bullet(Hero hero){
        this.setImage(PlaneWar.bullet);
        this.setWidth(this.getImage().getWidth());
        this.setHeight(this.getImage().getHeight());
        this.setX(PlaneWar.WIDTH-hero.getWidth()*3-hero.getWidth()/2-3);
        this.setY(PlaneWar.HEIGHT-hero.getHeight()*2-10);
    }
    public Bullet(int x,int y){
        this.setX(x);
        this.setY(y);
        this.setImage(PlaneWar.bullet);
    }

    public void step(){
        this.setY(getY()-speed);
    }

    @Override
    public boolean outOfBounds() {
        return getY()<getHeight();
    }
}
