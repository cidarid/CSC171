package com.company;

import java.awt.*;

public class Block extends Sprite {

    // Either goal platform or starting platform, stops from generating spikes
    boolean specialPlatform = false;
    public Block(int x, int y, int width, int height) {
        super(x, y, width, height, Type.FLOOR);
        this.color = Color.BLACK;
    }

    public Block(int x, int y, int width, int height, boolean specialPlatform) {
        super(x, y, width, height, Type.FLOOR);
        this.color = Color.BLACK;
        this.specialPlatform = specialPlatform;
    }



}
