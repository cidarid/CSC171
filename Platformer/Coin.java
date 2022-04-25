package com.company;

import java.awt.*;

public class Coin extends Sprite {

    public Coin (int x, int y, int width, int height) {
        super(x, y, width, height, Type.COIN);
        this.color = Color.YELLOW;
    }

    public void collect() {
        GameManager.coins++;
        GameManager.sprites.remove(this);
    }

    public void draw(Graphics2D g) {
        // Base coin
        g.setColor(color);
        g.fillOval(x, y, width, height);
        // Black oval inset in coin
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(4));
        g.drawLine(x + width/2, y + height/2 - 5, x + width/2, y + height/2 + 5);
    }


}
