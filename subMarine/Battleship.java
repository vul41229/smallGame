package cn.tedu.subMarine;

import javax.swing.*;

/**
 * 戰艦類
 * */
public class Battleship extends SeaObject{
    private int life;

    public void subtractLife(){
        life--;
    }

    public void addLife(int life){
        this.life += life;
    }
    public int getLife() {
        return life;
    }
    public Battleship() { //无参数构造方法
        super(270,124,66,26,20);
        life = 5;//初始生命
    }

    @Override
    public void step() {
        System.out.println("戰艦通過鍵盤來移動...");
    }

    public void moveLeft(){
        x -= speed;
    }
    public void moveRight(){
        x += speed;
    }

    @Override
    public ImageIcon getImage() {
        return Images.battleShip;
    }

    public Bomb shootBomb(){
        return new Bomb(x,y);
    }
}
