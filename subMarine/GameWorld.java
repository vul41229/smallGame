package cn.tedu.subMarine;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 遊戲世界類:
 *          主要是用來運行(測試)遊戲的類
 * */
public class GameWorld extends JPanel{
    public static final int WIDTH = 641;
    public static final int HEIGHT = 479;
    public static final int START = 0;
    public static final int RUNNING = 1;
    public static final int GAME_OVER = 2;
    public int currentGameState = START;
    private Battleship ship = new Battleship();
    private Bomb[] bombs = {};
    private SeaObject[] submarines = {};
    private SeaObject[] thunder = {};

    public SeaObject nextSubmarine(){
        int type = (int)(Math.random()*20);
        if(type<10){
            return new ObserverSubmarine();
        }
        else if(type < 15){
            return new TorpedoSubmarine();
        }
        else{
            return new MineSubmarine();
        }
    }
    private int subEnterIndex = 0;

    public void submarineEnterAction(){
        subEnterIndex++;
        if(subEnterIndex%40==0){
            SeaObject obj = nextSubmarine();
            submarines = Arrays.copyOf(submarines,submarines.length+1);
            submarines[submarines.length-1] = obj;
        }
    }
    private int thunderEnterIndex = 0;

    public void thunderEnterAction(){
        thunderEnterIndex++;
        if (thunderEnterIndex % 100 == 0) {
            for (int i = 0; i < submarines.length; i++) {
                SeaObject t = submarines[i].shootThunder();
                if(t != null){
                    thunder = Arrays.copyOf(thunder,thunder.length+1);
                    thunder[thunder.length-1] = t;
                }
            }
        }
    }
    public void stepAction(){
        for(int i = 0;i<submarines.length;i++){
            submarines[i].step();
        }
        for (int i = 0;i<thunder.length;i++){
            thunder[i].step();
        }
        for (int i = 0;i<bombs.length;i++){
            bombs[i].step();
        }
       
    }
    public void outOfBound(){
        for (int i = 0; i < submarines.length; i++){
            if(submarines[i].isOutBounds() || submarines[i].isDead()){
                submarines[i] = submarines[submarines.length-1];
                submarines = Arrays.copyOf(submarines,submarines.length-1);
            }
        }
        for (int i = 0; i < thunder.length; i++){
            if(thunder[i].isOutBounds() || thunder[i].isDead()){
                thunder[i] = thunder[thunder.length-1];
                thunder = Arrays.copyOf(thunder,thunder.length-1);
            }
        }
        for (int i = 0; i < bombs.length; i++){
            if(bombs[i].isOutBounds() || bombs[i].isDead()){
                bombs[i] = bombs[bombs.length-1];
                bombs = Arrays.copyOf(bombs,bombs.length-1);
            }
        }
    }
    private int score = 0;

    public void bombBangAction(){
        for(int i = 0; i < bombs.length; i++){
            Bomb b =bombs[i];
            for (int j = 0; j < submarines.length; j++){
                SeaObject s = submarines[j];
                if(b.isLive()&& s.isLive()&& s.isHit(b)){
                    System.out.println("碰到了");
                    b.goDead();
                    s.goDead();
                    if(s instanceof EnemyScore){
                        EnemyScore e = (EnemyScore) s;
                        e.getScore();
                        score += e.getScore();
                    }else if (s instanceof EnemyLife){
                        EnemyLife e = (EnemyLife) s;
                        ship.addLife(e.getLife());
                    }
                }
            }
        }
    }
    public void thunderBangAction(){
        for(int i = 0; i<thunder.length;i++){
            SeaObject t = thunder[i];
            if(t.isLive() && ship.isLive() && t.isHit(ship)){
                t.goDead();
                ship.subtractLife();
            }
        }

    }
    public void checkGameAction(){
        if(ship.getLife() <=0){
            currentGameState = GAME_OVER;
        }
    }
    private void action() {
        KeyAdapter k = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE){
                    if(currentGameState == START){
                       currentGameState = RUNNING;
                    }else {
                        bombEnterAction();
                    }
                }
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    ship.moveLeft();
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    ship.moveRight();
                }
            }
        };
        this.addKeyListener(k);

        Timer timers = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(currentGameState == RUNNING) {
                    submarineEnterAction();
                    thunderEnterAction();
                    stepAction();
                    outOfBound();
                    bombBangAction();
                    thunderBangAction();
                    checkGameAction();
//                  System.out.println("戰艦數量:"+submarines.length);
                    repaint();
                }
            }

        };
        timers.schedule(task, 5000, 10);

    }

    public void bombEnterAction() {
        Bomb b =ship.shootBomb();
        bombs = Arrays.copyOf(bombs,bombs.length+1);
        bombs[bombs.length-1]=b;
    }


    void  paintWorld(){
        JFrame frame = new JFrame();
        this.setFocusable(true);
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH+16,HEIGHT+39);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    @Override
    public void paint(Graphics g) {
        switch (currentGameState) {
            case START://开始状态
                Images.start.paintIcon(null, g, 0, 0);//绘制开始图片
                break;
            case RUNNING:
                Images.sea.paintIcon(null, g, 0, 0);//背景要先画
                ship.paintImage(g);//战舰对象打点调用绘制图片的方法
                //绘制所有的潜艇
                for (int i = 0; i < submarines.length; i++) {
                    submarines[i].paintImage(g);
                }
                //绘制所有的水雷和鱼雷
                for (int i = 0; i < thunder.length; i++) {
                    thunder[i].paintImage(g);
                }
                //绘制所有的深水炸弹
                for (int i = 0; i < bombs.length; i++) {
                    bombs[i].paintImage(g);
                }
                g.drawString("Score:" + score, 200, 50);
                g.drawString("Life:" + ship.getLife(), 400, 50);
                break;
            case GAME_OVER:
                Images.gameOver.paintIcon(null, g, 0, 0);
                break;
        }
    }

    public static void main(String[] args) {
        GameWorld world = new GameWorld();
        world.paintWorld();
        world.action();
    }
}
