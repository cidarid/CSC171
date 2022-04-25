package com.company;

public class CollisionEvent {

    // Sprite collided with
    Sprite collidedSprite;
    // If collision happened with passed sprite
    boolean collision;
    public CollisionEvent(Sprite s2) {
        if (GameManager.player.intersects(s2)) {
            collision = true;
        }
        this.collidedSprite = s2;
    }

    public boolean hasCollision(){
        return collision;
    }

}
