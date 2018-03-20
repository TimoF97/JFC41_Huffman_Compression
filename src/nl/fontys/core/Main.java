package nl.fontys.core;

import nl.fontys.algorithms.HuffmanCompression;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static HuffmanCompression huffmanCompression;
    private static final Logger LOGGER = Logger.getLogger(HuffmanCompression.class.getName());

    public static void main(String[] args) {
        huffmanCompression = new HuffmanCompression();
        decideActionToPerform();
    }

    private static void decideActionToPerform() {
        final Scanner scanner = new Scanner(System.in);

        LOGGER.log(Level.INFO, "Which action would you like to perform?");
        LOGGER.log(Level.INFO, "[1] Encode text");
        LOGGER.log(Level.INFO, "[2] Decode text");
        final int inputChoice = Integer.parseInt(scanner.nextLine().trim());

        if (inputChoice == 1) {
            LOGGER.log(Level.INFO, "Write the text you want to encode.");
            encode(scanner.nextLine());

        }
        else if (inputChoice == 2) {
            decode();
        }
    }

    private static void encode(final String text) {
        huffmanCompression.encode(text);
    }

    private static void decode() {
        huffmanCompression.decode();
    }
}
