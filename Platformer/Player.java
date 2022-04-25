package com.company;

import java.awt.*;
import java.util.ArrayList;

public class Player extends Sprite {

    protected double dx = 0, dy = 0;
    final double GRAVITY = 0.1;
    final double JUMP_FORCE = 3.4;
    final double MOVEMENT_FORCE = 2.4;
    final double TERMINAL_VELOCITY = 5;
    protected boolean onGround = false;
    protected boolean noMoreJumps = false;

    public Player(int x, int y, int width, int height) {
        super(x, y, width, height, Sprite.Type.PLAYER);
    }

    public void doPhysics() {
        // If player is not touching ground and has not reached terminal velocity
        if (!onGround && dy < TERMINAL_VELOCITY) {
            dy += GRAVITY;
        }
        y += dy;
        x += dx;
        ArrayList<CollisionEvent> collisions = new ArrayList<>();
        for (Sprite sprite : GameManager.sprites) {
            CollisionEvent collision = new CollisionEvent(sprite); // Create a collision event for every sprite
            if (collision.hasCollision()) {
                collisions.add(collision); // Check if there's actually a collision occuring for each event
            }
        }
        for (CollisionEvent collision : collisions) {
            if (collision.collidedSprite.type == Type.COIN) {
                ((Coin)collision.collidedSprite).collect(); // This removes the coin and gives the player a coin

            }
            if (collision.collidedSprite.type == Type.GOAL) {
                switch (GameManager.currentLevel) {
                    case 1 -> {
                        GameManager.endLevel(); // Starts level 2
                        return;
                    }
                    case 2 -> {
                        GameManager.endGame();
                        return;
                    }
                }
            }
            else if (collision.collidedSprite.type == Type.FLOOR) {
                if (this.dy > 0 && this.bottomIntersects(collision.collidedSprite)) {
                    this.y = collision.collidedSprite.y - this.height + 1;
                    dy = 0;
                    onGround = true;
                    noMoreJumps = false; // Give the player all jumps back
                }
            }
            else if (collision.collidedSprite.type == Type.SPIKE) {
                kill();
            }
        }
        if (collisions.isEmpty()) {
            onGround = false; // If the player isn't colliding with anything, they're in the air
        }
    }

    public void kill() {
        resetPos();
        GameManager.lives--;
    }

    public void resetPos() {
        dx = 0;
        dy = 0;
        x = GameManager.PLAYER_X;
        y = GameManager.PLAYER_Y;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, width, height);
    }

    private void jump() {
        if (onGround) {
            onGround = false;
            dy -= JUMP_FORCE;
            noMoreJumps = false;
        }
        else if (!noMoreJumps) {
            dy = 0;
            dy -= JUMP_FORCE;
            noMoreJumps = true;
        }
    }

    public void move(String movement) {
        switch (movement) {
            case("left") -> dx = -MOVEMENT_FORCE;
            case("right") -> dx = MOVEMENT_FORCE;
            case("up") -> jump();
            // If moving left, then stop movement left
            case ("stop_left") -> dx = (dx <= 0) ? 0 : dx;
            // If moving right, then stop movement right
            case("stop_right") -> dx = (dx >= 0) ? 0 : dx;
            default -> System.out.println("Invalid string passed to move().");
        }
    }


    // This method is so that the player sits properly on top of the floor.
    boolean bottomIntersects(Sprite s) {
        Rectangle bottomHitbox = new Rectangle(this.x, this.y + this.height - 1, this.width, 1);
        return bottomHitbox.intersects(s);
    }
}