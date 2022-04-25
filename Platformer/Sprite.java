package com.company;

import java.awt.*;

public class Sprite extends Rectangle {

    Color color;
    enum Type {
        FLOOR,
        COIN,
        GOAL,
        PLAYER,
        SPIKE
    }
    Type type;
    public Sprite(int x, int y, int width, int height, Type type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        if (type != Type.PLAYER) {
            GameManager.sprites.add(this);
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }
}
