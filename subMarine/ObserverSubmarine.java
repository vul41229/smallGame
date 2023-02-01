package cn.tedu.subMarine;

import javax.swing.*;

/**
 * 偵查潛艇類
 * */
public class ObserverSubmarine extends SeaObject implements EnemyScore{
//    ObserverSubmarine(){
//        width = 63;
//        height = 19;
//        x = -width;
//        y =(int) (Math.random()*(479-height-150)+150);
//        speed = (int) (Math.random()*(3-1)+1);
//    }
      public ObserverSubmarine() {//无参构造方法
          super(63,19);
    }
    @Override
    public void step() {
        x+=speed;
    }

    @Override
    public ImageIcon getImage() {
        if(this.isLive()){
            return Images.obsersubmarine;
        }
        return null;
    }

    @Override
    public int getScore() {
        return 10;
    }
}
