package cn.tedu.subMarine;

import javax.swing.*;

/**
 * 水雷類
 * */
public class Mine extends SeaObject{
    public Mine(int x,int y){
        super(x,y,11,11,2);
    }
    public @Override
    void step() {
        y-=speed;
    }

    @Override
    public ImageIcon getImage() {
        if(this.isLive()){
            return Images.mine;
        }
        return null;
    }

    @Override
    public boolean isOutBounds() {
        return this.y <= 150 - height;
    }
}
