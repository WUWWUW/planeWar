package com.plane.basic;
/*
   获得奖励的接口Award
 */
public interface Award {
    //活力值FIRE，默认为0
    int FIRE=100;
    //生命值LIFE，默认为1
    int LIFE=1;
    //获得奖励getAward()，返回0或者1
    int getAward();
}
