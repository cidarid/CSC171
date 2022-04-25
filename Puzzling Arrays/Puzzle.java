package com.company;
import java.util.Arrays;
import java.util.Scanner;

public class Puzzle {

    protected final static int[][] initialSetup = {
            {0, 1, 2, 3},
            {4, 5, 6, 7},
            {8, 9, 10, 11},
            {12, 13, 14, 15}
    };
    protected final static int[][] altInitialSetup = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };
    // activeTable is initialized to the same as initialSetup, will be randomized after difficulty is decided
    protected static int[][] activeGrid = {
            {0, 1, 2, 3},
            {4, 5, 6, 7},
            {8, 9, 10, 11},
            {12, 13, 14, 15}
    };
    // zeroPos[0] is row, zeroPos [1] is column
    static int[] zeroPos = new int[2];
    protected static Scanner scanner;

    /*
    Move in direction
    "U" - Up
    "R" - Right
    "L" - Left
    "D" - Down
    Anything else returns -1
    */
    protected static int moveInDirection(String dir, boolean printError) {
        int[] itemToSwap = new int[2];
        switch (dir) {
            // Move up
            case "U" -> {
                // If zero is already all the way up
                if (zeroPos[0] == 0) {
                    if (printError)
                        System.out.println("That is an invalid move.");
                    return -1;
                }
                // Item above current zeroPos
                itemToSwap[0] = zeroPos[0] - 1;
                itemToSwap[1] = zeroPos[1];
                swap(itemToSwap);
            }
            // Move right
            case "R" -> {
                // If zero is already all the way to the right
                if (zeroPos[1] == 3) {
                    if (printError)
                        System.out.println("That is an invalid move.");
                    return -1;
                }
                // Item below current zeroPos
                itemToSwap[1] = zeroPos[1] + 1;
                itemToSwap[0] = zeroPos[0];
                swap(itemToSwap);
            }
            // Move down
            case "D" -> {
                // If zero is already all the way down
                if (zeroPos[0] == 3) {
                    if (printError)
                        System.out.println("That is an invalid move.");
                    return -1;
                }
                // Item below current zeroPos
                itemToSwap[0] = zeroPos[0] + 1;
                itemToSwap[1] = zeroPos[1];
                swap(itemToSwap);
            }
            // Move left
            case "L" -> {
                // If zero is already all the way left
                if(zeroPos[1] == 0) {
                    if (printError)
                        System.out.println("That is an invalid move.");
                    return -1;
                }
                // Item to left of current zeroPos
                itemToSwap[1] = zeroPos[1] - 1;
                itemToSwap[0] = zeroPos[0];
                swap(itemToSwap);
            }
            // Invalid input (not U, R, L, or D)
            default -> {
                if (printError)
                    System.out.println("Invalid input");
                return -1;
            }
        }
        return 0;
    }

    // Swaps items in table
    public static void swap(int[] itemToSwap) {
        activeGrid[zeroPos[0]][zeroPos[1]] = activeGrid[itemToSwap[0]][itemToSwap[1]];
        activeGrid[itemToSwap[0]][itemToSwap[1]] = 0;
        zeroPos = itemToSwap;
    }

    // Randomly generates the grid by moving 0 in a random direction numberOfMoves times
    public static void generateGrid(int numberOfMoves) {
        for (int i = 0;  i < numberOfMoves; i++) {
            // Stores the return value of move()
            int result;
            // Keep trying to make a move until a valid move is made (i.e. move() does not return 1)
            do {
                int dirToMove = (int) (Math.random() * 10);
                String dirAsString = "I";
                switch (dirToMove) {
                    case 0 -> dirAsString = "U";
                    case 1 -> dirAsString = "R";
                    case 2 -> dirAsString = "D";
                    case 3 -> dirAsString = "L";
                }
                result = moveInDirection(dirAsString, false);
            } while (result == -1);
        }
    }

    // Outputs a 2D array in a user-readable format
    public static void printGrid(int[][] grid) {
        for (int[] ints : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }
    }

    public static void setZeroPos(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0) {
                    zeroPos[0] = i;
                    zeroPos[1] = j;
                }
            }
        }
    }

    public static void getTable() {
        Scanner inputScanner;
        System.out.println("Enter your table: ");
        for (int i = 0; i < 4; i++) {
            String userInput = scanner.nextLine();
            inputScanner = new Scanner(userInput);
            for (int j = 0; j < 4; j++) {
                activeGrid[i][j] = Integer.parseInt(inputScanner.next());
            }
        }
    }

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        String modeSelection;
        // Gets mode of input
        do {
            System.out.print("Input 'I' for interactive mode and 'V' for validation mode: ");
            modeSelection = scanner.nextLine();
        } while (!(modeSelection.equals("I")) && !(modeSelection.equals("V")));

        if (modeSelection.equals("I")) {
            Interact.interact();
        }
        else {
            Validate.validate();
        }
    }
}
