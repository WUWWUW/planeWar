package com.plane.flyingPar;
/*
  敌人类 Enemy 继承Flying 飞行类
  蜜蜂类 Bee，敌机类 EnemyPlane 的父类
 */
public class Enemy extends FlyingObject {
    @Override
    public boolean outOfBounds() {
        return false;
    }

    @Override
    public void step() {

    }
}
