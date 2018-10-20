package com.plane.control;
/*
   main类
 */
import com.plane.flyingPar.Enemy;
import com.plane.flyingPar.FlyingObject;
import com.plane.flyingSon.Bee;
import com.plane.flyingSon.Bullet;
import com.plane.flyingSon.EnemyPlan;
import com.plane.flyingSon.Hero;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;

public class PlaneWar extends JPanel {
    //设置窗口大小
    public static final int WIDTH=415;
    public static final int HEIGHT=680;
    //声明静态资源
    public static BufferedImage airplane;
    public static BufferedImage background;
    public static BufferedImage bee;
    public static BufferedImage bullet;
    public static BufferedImage gameover;
    public static BufferedImage hero0;
    public static BufferedImage hero1;
    public static BufferedImage pause;
    public static BufferedImage start;
    private int state;
    private static final int START=0;
    private static final int RUNNING=1;
    private static final int PAUSE=2;
    private static final int GAME_OVER=3;
    private int score=0;
    private int intervel=1000/100;   //时间间隔
    //声明变量
    Enemy[] enemies;
    Hero hero;
    Bullet bullets[];

    //静态代码块，在类加载之前执行，初始化声明的静态资源
    static {
        try{
            airplane= ImageIO.read(PlaneWar.class.getResource("../img/airplane.png"));
            background=ImageIO.read(PlaneWar.class.getResource("../img/background.png"));
            bee=ImageIO.read(PlaneWar.class.getResource("../img/bee.png"));
            bullet=ImageIO.read(PlaneWar.class.getResource("../img/bullet.png"));
            gameover=ImageIO.read(PlaneWar.class.getResource("../img/gameover.png"));
            hero0=ImageIO.read(PlaneWar.class.getResource("../img/hero0.png"));
            hero1=ImageIO.read(PlaneWar.class.getResource("../img/hero1.png"));
            pause=ImageIO.read(PlaneWar.class.getResource("../img/pause.png"));
            start=ImageIO.read(PlaneWar.class.getResource("../img/start.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        //创建窗口
        JFrame jf=new JFrame("");
        //创建面板
        PlaneWar jp=new PlaneWar();
        //将面板添加到窗口上
        jf.add(jp);
        //默认关闭
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口大小
        jf.setSize(WIDTH,HEIGHT);
        //设置窗口居中
        jf.setLocationRelativeTo(null);
        //设置图标
        jf.setIconImage(bee);
        //窗口可见
        jf.setVisible(true);
        //设置不能改变窗口大小
        jf.setResizable(false);
        jp.action();
    }

    //构造方法，为变量创建对象
    public PlaneWar(){
        hero=new Hero();
        bullets=new Bullet[]{new Bullet(hero),new Bullet(hero)};
        enemies=new Enemy[]{new EnemyPlan(),new EnemyPlan(),new Bee()};
    }

    //paint方法，画出物体，窗口加载时直接调用paint()方法
    public void paint(Graphics g){
        paintBackground(g);
        paintHero(g);
        paintBullet(g);
        paintEnemy(g);
        paintScore(g);
        paintState(g);
    }

    //画背景
    public void paintBackground(Graphics g){
        g.drawImage(background,0,0,null);
    }
    //画出战斗机
    public void paintHero(Graphics g){
        g.drawImage(hero.getImage(),hero.getX(),hero.getY(),null);
    }
    //画出子弹，子弹随着战斗机移动
    public void paintBullet(Graphics g){
        for(int i=0;i<bullets.length;i++){
            g.drawImage(bullets[i].getImage(),bullets[i].getX(),bullets[i].getY(),null);
        }
    }
    //画出敌机
    public void paintEnemy(Graphics g){
        for(int i=0;i<enemies.length;i++){
            g.drawImage(enemies[i].getImage(),enemies[i].getX(),enemies[i].getY(),null);
        }
    }

    //画分数
    public void paintScore(Graphics g){
        int x=10;
        int y=25;
        int showfire;
        Font font=new Font(Font.SANS_SERIF,Font.BOLD,14);
        g.setColor(new Color(0x3A3B3B));
        g.setFont(font);
        g.drawString("分数："+score,x,y);
        y+=20;
        g.drawString("生命数："+hero.getLife(),x,y);
        y+=20;
        if (hero.getFire()>=300) {
            g.drawString("最大火力：" +300, x, y);
        }else {
            g.drawString("火力值：" + hero.getFire(), x, y);
        }
    }
    //画游戏状态
    public void paintState(Graphics g){
        switch (state){
            case START:
                g.drawImage(start,0,0,null);
                break;
            case PAUSE:
                g.drawImage(pause,0,0,null);
                break;
            case GAME_OVER:
                g.drawImage(gameover,0,0,null);
                break;
        }
    }
    public void action(){
        MouseAdapter l=new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if(state==RUNNING){
                    int x=e.getX();
                    int y=e.getY();
                    hero.moveTo(x,y);
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                if (state==PAUSE){
                    state=RUNNING;
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(state!=GAME_OVER&&state!=START){
                    state=PAUSE;
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                switch (state){
                    case START:
                        state=RUNNING;
                        break;
                    case GAME_OVER:
                        enemies=new Enemy[0];
                        bullets=new Bullet[0];
                        hero=new Hero();
                        score=0;
                        state=START;
                        break;
                }
            }
        };
        this.addMouseListener(l);
        this.addMouseMotionListener(l);
        Timer timer=new Timer();

        int intervel=10;
        timer.schedule(new TimerTask(){
            @Override
            public void run(){
                if (state==RUNNING){
                    enterAction();
                    stepAction();
                    flyingStepAction();
                    shootAction();
                    bangAction();
                    outOfBoundAction();
                    checkGameOverAction();
                }
                repaint();
            }
        },intervel,intervel);

    }
    int flyEnteredIndex=0;

    public void enterAction(){
        flyEnteredIndex++;
        if(flyEnteredIndex%40==0){
            Enemy object=nextOne();
            enemies=Arrays.copyOf(enemies,enemies.length+1);
            enemies[enemies.length-1]=object;
        }
    }
    //走一步
    public void stepAction(){
        for(int i=0;i<enemies.length;i++){
            FlyingObject f=enemies[i];
            f.step();
        }
        for(int i=0;i<bullets.length;i++){
            Bullet b=bullets[i];
            b.step();
        }
        hero.step();
    }

    //飞行物走一步
    public void flyingStepAction(){
        for(int i=0;i<enemies.length;i++){
            FlyingObject object=enemies[i];
            object.step();
        }
    }
    int shootIndex=0;
    //英雄机射击
    public void shootAction(){
        shootIndex++;
        if(shootIndex%30==0){
            Bullet[] bs=hero.shoot();
            bullets=Arrays.copyOf(bullets,bullets.length+bs.length);
            System.arraycopy(bs,0,bullets,bullets.length-bs.length,bs.length);

        }
    }
    //子弹射击飞行物
    public void bangAction(){
        for(int i=0;i<bullets.length;i++){
            Bullet b=bullets[i];
            bang(b);
        }
    }

    //检查是否越界
    public void outOfBoundAction(){
        int index=0;
        Enemy[] flyingLives=new Enemy[enemies.length];
        for(int i=0;i<enemies.length;i++){
            Enemy f=enemies[i];
            if(!f.outOfBounds()){
                flyingLives[index++]=f;
            }
        }
        enemies=Arrays.copyOf(flyingLives,index);
        index=0;
        Bullet[] bulletLives=new Bullet[bullets.length];
        for(int i=0;i<bullets.length;i++){
            Bullet b=bullets[i];
            if(!b.outOfBounds()){
                bulletLives[index++]=b;
            }
        }
        bullets=Arrays.copyOf(bulletLives,index);
    }
    public void checkGameOverAction(){
        if(isGameOver()){
            state=GAME_OVER;
        }
    }

    //检查游戏是否结束
    public boolean isGameOver(){
        for(int i=0;i<enemies.length;i++){
            int index=-1;
            Enemy enemy=enemies[i];
            if(hero.hit(enemy)){
                hero.subLise();
                hero.setFire(100);
                index=i;
            }
            if(index!=-1){
                Enemy enemy1=enemies[index];
                enemies[index]=enemies[enemies.length-1];
                enemies[enemies.length-1]=enemy1;
                enemies=Arrays.copyOf(enemies,enemies.length-1);
            }
        }
        return hero.getLife()<=0;
    }

    //子弹和飞行物之间的碰撞检查
    public void bang(Bullet bullet){
        int index=-1;
        for(int i=0;i<enemies.length;i++){
            Enemy enemy=enemies[i];
            if(enemy.shootBy(bullet)){
                index=i;
                break;
            }
        }
        if(index!=-1){
            Enemy enemy1=enemies[index];
            Enemy temp=enemies[index];
            enemies[index]=enemies[enemies.length-1];
            enemies[enemies.length-1]=temp;
            enemies=Arrays.copyOf(enemies,enemies.length-1);
            if(enemy1 instanceof EnemyPlan){
                EnemyPlan e=(EnemyPlan)enemy1;
                score+=e.getScore();
            }else if(enemy1 instanceof Bee) {
                Bee bee = (Bee) enemy1;
                int type = bee.getAwardType();
                switch (type) {
                    case 0:
                        hero.addFire();
                        break;
                    case 1:
                        hero.addLife();
                        break;
                }
            }

        }
    }

    //随机生成敌人（蜜蜂，敌机）
    public static Enemy nextOne(){
        Random random=new Random();
        int type=random.nextInt(20);
        if(type==0){
        return new Bee();
        }else {
            return new EnemyPlan();
        }
    }


}
