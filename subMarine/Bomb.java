package cn.tedu.subMarine;

import javax.swing.*;

/**
 * 深水炸彈類
 * */
public class Bomb extends SeaObject{
    public Bomb(int x,int y){//有参构造方法
        super(x,y,9,12,3);
    }
    @Override
    public void step() {
        y += speed;
    }

    @Override
    public ImageIcon getImage() {
        if(this.isLive()){
            return Images.bomb;
        }
        return null;
    }

    @Override
    public boolean isOutBounds() {
        return this.y >= GameWorld.HEIGHT;
    }
}
