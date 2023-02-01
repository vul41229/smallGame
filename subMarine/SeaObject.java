package cn.tedu.subMarine;

import javax.swing.*;
import java.awt.*;

public abstract class SeaObject {
    public static final int LIVE = 0;
    public static final int DEAD = 1;
    public int currentState = LIVE;


    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int speed;

    public SeaObject(int width, int height) {
        this.width = width;
        this.height = height;
        this.x = -width;
        this.y = (int) (Math.random() * (GameWorld.HEIGHT - height - 150) + 150);
        this.speed = (int) (Math.random() * (3 - 1) + 1);
    }

    public SeaObject(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    public abstract void step();


    public abstract ImageIcon getImage();

    public boolean isLive() {
        return currentState == LIVE;
    }

    public boolean isDead() {
        return currentState == DEAD;
    }

    public void paintImage(Graphics g) {
        if (this.getImage() != null) {

            ImageIcon icon = this.getImage();
            icon.paintIcon(null, g, this.x + 100, this.y);
        }
    }

    public SeaObject shootThunder() {
        int x = this.x + this.width;
        int y = this.y - 5;
        if (this instanceof TorpedoSubmarine) {
            return new Torpedo(x, y);
        } else if (this instanceof MineSubmarine) {
            return new Mine(x, y);
        } else {
            return null;
        }
    }

    public boolean isOutBounds() {
        return this.x >= GameWorld.WIDTH;
    }

    public boolean isHit(SeaObject other) {
        int x1 = this.x - other.width;
        int x2 = this.x + this.width;
        int y1 = this.y - other.width;
        int y2 = this.y + this.width;
        int x = other.x;
        int y = other.y;
        return x >= x1 && x <= x2 && y >= y1 && y <= y2;
    }
    public void goDead(){
        this.currentState =DEAD;
    }
}