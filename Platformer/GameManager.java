package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import static java.awt.event.KeyEvent.*;

public class GameManager extends JFrame {

    private static GameManager gm = null;
    int up = VK_UP;
    int left = VK_LEFT;
    int right = VK_RIGHT;
    protected static int lives = 3;
    protected static int coins = 0;
    protected static Player player;
    // Whether or not game has ended
    protected static boolean ended = false;
    // Starting pos of player
    protected final static int PLAYER_X = 200, PLAYER_Y = 621;
    protected static int currentLevel = 1;
    // Stores all level sprites (all sprites other than Player)
    public static ArrayList<Sprite> sprites = new ArrayList<>();


    private GameManager() {
        // Makes main game panel
        MainPanel mainPanel = new MainPanel();

        add(mainPanel);
        setLayout(new GridLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(900, 750);
        // Spawns player
        player = new Player(PLAYER_X, PLAYER_Y, 20, 20);
        loadLevel(currentLevel);
    }

    public static void main(String[] args) {
        gm = GameManager.getInstance();
        gm.setVisible(true);
    }

    public static GameManager getInstance() {
        if (gm == null) {
            gm = new GameManager();
        }
        return gm;
    }

    protected static void endGame() { ended = true; }

    protected static void endLevel() {
        player.resetPos();
        sprites.clear();
        currentLevel++;
        loadLevel(currentLevel);
    }

    protected static void loadLevel(int level) {
        try {
            CsvReader.ConstructLevel(level);
        } catch (Exception e) {
            e.printStackTrace();
        }
        generateRandomSpikes();
    }

    protected static void generateRandomSpikes() {
        // Gets the chance that a spike will be generated, anywhere from 25% to 50%
        double chanceOfSpikes = (Math.random() + 1) / 4;
        ArrayList<Block> floors = new ArrayList<>();
        // Set to true if a spike was just placed to avoid adjacent spikes, adjacent spikes can make a platform unreachable
        boolean justPlacedSpike = false;
        // Gets all floors from sprites
        for (Sprite s : sprites) {
            if (s.type == Sprite.Type.FLOOR)
                floors.add((Block)s);
        }
        for (Block s : floors) {
            // Checks if a spike was just placed, if so iterate to next step of loop
            if (justPlacedSpike) {
                justPlacedSpike = false;
                continue;
            }
            // Checks if random is above chance and makes sure it isnt the spawn or goal platform
            if (Math.random() <= chanceOfSpikes && !s.specialPlatform) {
                new Spike(s.x, s.y - 20, s.width/2, 20);
                justPlacedSpike = true;
            }
        }
    }

    private static class MainPanel extends JPanel implements ActionListener, KeyListener {
        boolean imagesLoaded = false;
        BufferedImage heart = null;
        BufferedImage coin = null;

        // Milliseconds
        final protected double frameTime = 5;
        private MainPanel() {
            // Starts our timer
            new Timer((int) frameTime, this).start();

            setFocusable(true);
            addKeyListener(this);
        }

        // Tries to get heart image
        private static BufferedImage getHeart() {
            BufferedImage heart = null;
            try {
                heart = ImageIO.read(new File("src/com/company/Sprites/heart.png"));
            } catch (IOException e) {
                System.out.println("This shouldn't print ever");
            }
            return heart;
        }

        // Tries to get coin image
        private static BufferedImage getCoin() {
            BufferedImage coin = null;
            try {
                coin = ImageIO.read(new File("src/com/company/Sprites/coin.png"));
            } catch (IOException e) {
                System.out.println("This shouldn't print ever either");
            }
            return coin;
        }


        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            // If images aren't loaded load images
            if (!imagesLoaded) {
                heart = getHeart();
                coin = getCoin();
                imagesLoaded = true;
            }

            // Draws hearts
            if (lives > 0)
                g.drawImage(heart, 5, 5, 26, 26, this);
            if (lives > 1)
                g.drawImage(heart, 30, 5, 26, 26, this);
            if (lives > 2)
                g.drawImage(heart, 55, 5, 26, 26, this);

            // Draws coin
            g.drawImage(coin, getWidth() - 125, 5, 26, 26, this);

            // Draws coin text
            g.setFont(new Font("TimesRoman", Font.PLAIN, 27));
            g.drawString("x" + coins, getWidth() - 100, 25);

            // Draws level text
            g.drawString(Integer.toString(currentLevel), getWidth() / 2, 25);

            // Handles player wrapping around screen
            if (player.x > getWidth())
                player.x = 1;
            if (player.x < 0)
                player.x = getWidth() - 1;
            // If player falls below screen, kill player
            if (player.y >= getHeight())
                player.kill();

            player.doPhysics();
            player.draw((Graphics2D)g);

            // easter egg
            g.drawString("Make the window smaller!", 900, 400);

            // Draws all sprites
            sprites.forEach(sprite -> sprite.draw((Graphics2D)g));

            // If you won
            if (GameManager.ended) {
                // Draws black screen saying you won
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, 2000, 2000);
                g.setColor(Color.RED);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
                g.drawString("You won!", (getWidth() / 2) - 100, getHeight() / 2);
            }

            if (GameManager.lives < 0) {
                // Draws black screen saying you lost
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, 2000, 2000);
                g.setColor(Color.RED);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
                g.drawString("You Lost...", (getWidth() / 2) - 100, getHeight() / 2);
            }
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            repaint();
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent event) {
            // Makes sure you cant move after the game is over
            if (GameManager.ended)
                return;
            if (event.getKeyCode() == gm.up)
                player.move("up");
            else if (event.getKeyCode() == gm.left)
                player.move("left");
            else if (event.getKeyCode() == gm.right)
                player.move("right");
        }

        @Override
        public void keyReleased(KeyEvent event) {
            if (event.getKeyCode() == gm.left)
                player.move("stop_left");
            else if (event.getKeyCode() == gm.right)
                player.move("stop_right");
        }
    }

}
