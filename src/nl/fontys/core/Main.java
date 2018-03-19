package nl.fontys.core;

import nl.fontys.algorithms.HuffmanCompression;

import java.util.Scanner;

public class Main {

    private static HuffmanCompression huffmanCompression;

    public static void main(String[] args) {
        huffmanCompression = new HuffmanCompression();
        decideActionToPerform();
    }

    private static void decideActionToPerform() {
        final Scanner scanner = new Scanner(System.in);

        System.out.println("Which action would you like to perform?");
        System.out.println("[1] Encode text");
        final int inputChoice = Integer.parseInt(scanner.nextLine().trim());

        switch (inputChoice) {
            case 1:
                System.out.println("Write the text you want to encode.");
                encode(scanner.nextLine());
                break;
        }
    }

    private static void encode(final String text) {
        huffmanCompression.encode(text);
    }
}
