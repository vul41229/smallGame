package cn.tedu.subMarine;

import javax.swing.*;

/**
 * 水雷潛艇類
 * */
public class MineSubmarine extends SeaObject implements EnemyLife{
//    MineSubmarine(){
//        width = 63;
//        height = 19;
//        x = -width;
//        y =(int) (Math.random()*(479-height-150)+150);
//        speed = (int) (Math.random()*(3-1)+1);
//    }
     public MineSubmarine(){ //无参构造方法
        super(63,19); //复用父类的构造方法
}
    @Override
    public void step() {
        x+=speed;
    }

    @Override
    public ImageIcon getImage() {
        if(this.isLive()){
            return Images.minesubmarine;
        }
        return null;
    }

    @Override
    public int getLife() {
        return 1;
    }
}
