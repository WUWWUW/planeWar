package com.plane.flyingPar;
/*
  飞行类 FlyingObject
  战斗机类 Hero，敌人类 Enemy，子弹类 Bullet 的父类
 */
import com.plane.flyingSon.Bullet;

import java.awt.image.BufferedImage;

public abstract class FlyingObject {
    //飞行物的图片
    private BufferedImage image;

    //飞行物图片的宽，高：width，height
    private int width;
    private int height;

    //飞行物的坐标:x,y
    private int x;
    private int y;

    //所有属性的getter和setter
    public BufferedImage getImage(){
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    //抽象方法 检查是否出界
    public abstract boolean outOfBounds();

    //飞行物移动一步
    public abstract void step();

    //检查当前飞行物是否被子弹击中
    public boolean shootBy(Bullet bullet){
        int x=bullet.getX();
        int y=bullet.getY();
        return this.x<x&&x<this.x+width&&this.y<y&&y<this.y+height;
    }

}
