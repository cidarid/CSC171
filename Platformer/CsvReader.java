package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

/*
1 - Regular floor
2 - Coin
3 - Goal
4 - Special floor that doesn't get spikes on it
0 or empty space - Empty space
 */

public class CsvReader {
    // Base units of width and height for generation, "blocks"
    final static int WIDTH = 40;
    final static int HEIGHT = 40;

    public static void ConstructLevel(int level) throws Exception {
        // Gets the filepath of CSV file
        String filePath = System.getProperty("user.dir") + String.format("/src/com/company/levels/level%d.csv", level);
        File csvFile = new File(filePath);
        Scanner sc = new Scanner(csvFile);
        String line;
        int counter = 0;
        // While line remaining
        while (sc.hasNextLine()) {
            // Get line
            line = sc.nextLine();
            // Remove commas
            line = line.replace(",", "");
            // Iterate through line and get characters
            for (int i = 0; i < line.length(); i++) {
                switch (line.charAt(i)) {
                    case ('1') -> new Block(i*WIDTH, counter*HEIGHT, WIDTH, HEIGHT/8);
                    case ('2') -> new Coin(i*WIDTH, counter*HEIGHT, WIDTH/2, (int)(HEIGHT/1.5));
                    case ('3') -> new Goal(i*WIDTH, counter*HEIGHT, WIDTH, HEIGHT);
                    // Special case, either the beginning platform or goal platform
                    case ('4') -> new Block(i*WIDTH, counter*HEIGHT, WIDTH, HEIGHT/8, true);
                    case ('0'), (' ') -> {}
                    default -> System.out.println("Invalid character in CSV map file.");
                }
            }
            counter++;
        }
    }
}
