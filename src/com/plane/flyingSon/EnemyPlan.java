package com.plane.flyingSon;

import com.plane.basic.Score;
import com.plane.control.PlaneWar;
import com.plane.flyingPar.Enemy;
import java.util.Random;

public class EnemyPlan extends Enemy implements Score {
    //敌机的速度
    int speed=2;

    //敌机构造方法 初始化敌机位置
    public EnemyPlan(){
        int w=new Random().nextInt(6)+2;
        int h=new Random().nextInt(3)+15;
        this.setImage(PlaneWar.airplane);
        this.setWidth(this.getImage().getWidth());
        this.setHeight(this.getImage().getHeight());
        this.setX(PlaneWar.WIDTH-this.getWidth()*w);    //*5 (2--8)
        this.setY(PlaneWar.HEIGHT-this.getHeight()*h);  //*18  (15--18)
    }

    //射击敌机 得分
    @Override
    public int getScore() {
         return  5;
    }

    @Override
    public boolean outOfBounds() {
        return getY()>PlaneWar.HEIGHT;
    }
    public void step(){
        this.setY(getY()+speed);
    }
}
