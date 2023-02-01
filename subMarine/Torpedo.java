package cn.tedu.subMarine;

import javax.swing.*;

/**
 * 魚雷類
 * */
public class Torpedo extends SeaObject {
    public Torpedo(int x, int y) {
        super(x,y,5,18,1);
    }
    @Override
    public void step() {
        y-=speed;
    }

    @Override
    public ImageIcon getImage() {
        if(this.isLive()){
            return Images.torpedo;
        }
        return null;
    }

    @Override
    public boolean isOutBounds() {
        return this.y <= -height;
    }
}
