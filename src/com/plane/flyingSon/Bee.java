package com.plane.flyingSon;

import com.plane.basic.Award;
import com.plane.control.PlaneWar;
import com.plane.flyingPar.Enemy;

import java.util.Random;

public class Bee extends Enemy implements Award {
    //移动的速度
    int xSpeed=1;
    int ySpeed=2;
    private int awardType;
    //射击蜜蜂得到奖励
    public int getAward(){
        return new Random().nextInt(2);
    }

    //蜜蜂构造方法 初始化蜜蜂的位置
    public Bee(){
        this.setImage(PlaneWar.bee);
        this.setWidth(this.getImage().getWidth());
        this.setHeight(this.getImage().getHeight());
        int w=new Random().nextInt(6);
        int h=new Random().nextInt(3)+11;
        this.setX(PlaneWar.WIDTH-this.getWidth()*w-this.getWidth()*2+20);
        this.setY(PlaneWar.HEIGHT-this.getHeight()*h);
        awardType=new Random().nextInt(2);
    }

    public int getAwardType(){
        return awardType;
    }

    @Override
    public boolean outOfBounds() {
        return getY()>PlaneWar.HEIGHT;
    }
    public  void step(){
        this.setX(getX()+xSpeed);
        this.setY(getY()+ySpeed);
        if(getX()>PlaneWar.WIDTH-getWidth());{
            xSpeed=-1;
        }
        if (getX()<0){
            xSpeed=-1;
        }
    }
}
