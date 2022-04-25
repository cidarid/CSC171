package com.company;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner mainScanner = new Scanner(System.in);
        final int MAX_LENGTH = mainScanner.nextInt();
        mainScanner.nextLine();
        String inputLine = mainScanner.nextLine();
        Scanner lineScanner;
        String currWord;
        String outputLine = "";
        String totalOutput = "";
        int outputLineLength, currWordLength;

        //Loops once every new line of user input until user inputs "Stop.\n"
        while (!inputLine.equals("Stop.")) {
            lineScanner = new Scanner(inputLine);
            //Loops through every new line of output
            while (lineScanner.hasNext()) {
                currWord = lineScanner.next();
                currWordLength = currWord.length();
                outputLineLength = outputLine.length();
                if (outputLineLength + currWordLength > MAX_LENGTH) {
                    totalOutput = totalOutput.concat(outputLine + System.lineSeparator());
                    outputLine = currWord + " ";
                } else {
                    outputLine = outputLine.concat(currWord + " ");
                }
            }
            if (inputLine.equals("")) {
                if (!outputLine.equals("")) {
                    totalOutput = totalOutput.concat(outputLine + System.lineSeparator());
                }
                outputLine = "";
                totalOutput = totalOutput.concat(System.lineSeparator());
            }

            lineScanner.close();
            inputLine = mainScanner.nextLine();
            if (inputLine.equals("Stop.")) {
                totalOutput = totalOutput.concat(outputLine + System.lineSeparator());
                break;
            }
        }
        System.out.print(totalOutput);
    }
}
