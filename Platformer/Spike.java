package com.company;

import java.awt.*;

public class Spike extends Sprite {
    public Spike(int x, int y, int width, int height) {
        super(x, y, width, height, Type.SPIKE);
        this.color = Color.magenta;
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        int[] xPoints = {x, x + width, x + width/2};
        int[] yPoints = {y + height, y + height, y};
        Polygon spike = new Polygon(xPoints, yPoints, 3);
        g.fillPolygon(spike);
    }
}
