package com.company;
import com.company.Puzzle;

import java.util.Arrays;

public class Interact extends Puzzle {
    public static void interact() {
        System.out.print("""
                    1: Easy
                    2: Medium
                    3: Hard
                    """);
        int selectedDifficulty;
        do {
            System.out.print("Select a difficulty: ");
            selectedDifficulty = Integer.parseInt(scanner.nextLine());
        } while (selectedDifficulty > 3 || selectedDifficulty < 1);
        int numberOfMoves = selectedDifficulty * 8;
        setZeroPos(activeGrid);
        generateGrid(numberOfMoves);
        printGrid(activeGrid);
        while (!Arrays.deepEquals(initialSetup, activeGrid) && !Arrays.deepEquals(altInitialSetup, activeGrid)) {
            System.out.print("Make a move (U: Up, R: Right, D: Down, L: Left): ");
            String direction = scanner.nextLine();
            moveInDirection(direction, true);
            printGrid(activeGrid);
        }
        System.out.println("Congrats! You solved the puzzle. ");
    }
}
