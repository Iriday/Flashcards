package flashcards;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scn = new Scanner(System.in);

        System.out.println("Input the number of cards:");
        int numOfCards = Integer.valueOf(scn.nextLine());
        String[][] cards = new String[numOfCards][2];

        for (int i = 0; i < numOfCards; i++) {
            System.out.printf("The card #%d:\n", i);
            cards[i][0] = scn.nextLine(); //term

            System.out.printf("The definition of the card #%d:\n", i);
            cards[i][1] = scn.nextLine(); //definition
        }

        String answer;
        for (String[] card : cards) {
            System.out.printf("Type the definition of \"%s\":\n", card[0]);//"black"
            answer = scn.nextLine();
            System.out.printf(card[1].equals(answer) ? "Correct answer.\n" : "Wrong answer. The correct one is \"%s\".\n", card[1]);//"white"
        }
    }

}
