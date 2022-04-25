package com.company;

import java.util.Arrays;
import java.util.Scanner;

public class Validate extends Puzzle {
    public static void validate() {
        getTable();
        setZeroPos(activeGrid);
        printGrid(activeGrid);
        System.out.print("Enter your move sequence: ");
        String moveSequence = scanner.nextLine();
        for (int i = 0; i < moveSequence.length(); i++) {
            moveInDirection(String.valueOf(moveSequence.charAt(i)), false);
        }
        System.out.println("After moves: ");
        printGrid(activeGrid);
        if (Arrays.deepEquals(initialSetup, activeGrid) || Arrays.deepEquals(altInitialSetup, activeGrid)) {
            System.out.println("success");
        }
        else {
            System.out.println("failed");
        }
    }
}
