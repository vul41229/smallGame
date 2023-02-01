package cn.tedu.subMarine;

import javax.swing.*;

/**
 * 魚雷潛艇類
 * */
public class TorpedoSubmarine extends SeaObject implements EnemyScore{
//    TorpedoSubmarine(){
//        width = 64;
//        height = 20;
//        x = -width;
//        y =(int) (Math.random()*(479-height-150)+150);
//        speed = (int) (Math.random()*(3-1)+1);
//    }
    public TorpedoSubmarine(){
    super(64,20);
  }
    @Override
    public void step() {
        x+=speed;
    }

    @Override
    public ImageIcon getImage() {
        if(this.isLive()){
            return Images.torpesubmarine;
        }
        return null;
    }

    @Override
    public int getScore() {
        return 40;
    }
}
