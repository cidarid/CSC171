package com.company;

import java.awt.*;

public class Goal extends Sprite {
    public Goal (int x, int y, int width, int height) {
        super(x, y, width, height, Sprite.Type.GOAL);
        this.color = Color.GREEN;
    }


}
