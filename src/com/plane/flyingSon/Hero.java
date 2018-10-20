package com.plane.flyingSon;

import com.plane.control.PlaneWar;
import com.plane.flyingPar.FlyingObject;

import java.awt.image.BufferedImage;

public class Hero extends FlyingObject {
    //英雄机图片
    BufferedImage[] images;

    //英雄机图片切换索引
    private int index=0;

    //生命数 初始为3
    private int life=3;

    //火力值 初始为0
    private int fire=100;

    public Hero(){
        this.setImage(PlaneWar.hero0);
        this.setWidth(this.getImage().getWidth());
        this.setHeight(this.getImage().getHeight());
        this.setX(PlaneWar.WIDTH-this.getWidth()*4);
        this.setY(PlaneWar.HEIGHT-this.getHeight()*2);
        images=new BufferedImage[]{PlaneWar.hero0,PlaneWar.hero1};
    }
    public int getFire(){
        return fire;
    }

    public void setFire(int fire) {
        this.fire = fire;
    }

    public void addFire(){
        fire+=100;
    }

    public void addLife(){
        life++;
    }
    public void subLise(){
        life--;
    }
    public int getLife(){
        return life;
    }
    //当前物体移动一下，相对距离，x,y鼠标位置
    public void  moveTo(int x,int y){
        this.setX(x-PlaneWar.hero0.getWidth()/2);
        this.setY(y-PlaneWar.hero0.getHeight()/2);
    }
    //越界处理
    @Override
    public boolean outOfBounds() {
        return false;
    }
    //发射子弹
    public Bullet[] shoot(){
        int xStep=getWidth()/4;
        int yStep=20;
       if(fire==200){
            Bullet[] bullets=new Bullet[2];
            bullets[0]=new Bullet(getX()+xStep,getY()-yStep);
            bullets[1]=new Bullet(getX()+3*xStep,getY()-yStep);
            return bullets;
        }else if(fire>=300){
            Bullet[] bullets=new Bullet[3];
            bullets[0]=new Bullet(getX()+xStep,getY()-yStep);
            bullets[1]=new Bullet(getX()+3*xStep,getY()-yStep);
            bullets[2]=new Bullet(getX()+2*xStep,getY()-yStep);
            return bullets;
        }else {
            Bullet[] bullets=new Bullet[1];
            bullets[0]=new Bullet(getX()+xStep,getY()-yStep);
            return bullets;
        }
    }
    //移动
    public void step(){
        if(images.length>0){
            this.setImage(images[index++/10%images.length]);
        }
    }
    //碰撞算法
    public boolean hit(FlyingObject object){
        int x1=object.getX()-this.getWidth()/2;
        int x2=object.getX()+this.getWidth()/2+object.getWidth();
        int y1=object.getY()-this.getHeight()/2;
        int y2=object.getY()+this.getHeight()/2+object.getHeight();
        int herox=this.getX()+this.getWidth()/2;
        int heroy=this.getY()+this.getHeight()/2;
        return herox>x1&&herox<x2&&heroy>y1&&heroy<y2;
    }
}
