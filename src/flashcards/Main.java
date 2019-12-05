package flashcards;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
  /*      System.out.println("Card:\n" + "black");
        System.out.println("Definition:\n" + "white");*/

        Scanner scn = new Scanner(System.in);

        String term = scn.nextLine();
        String definition = scn.nextLine();
        String answer = scn.nextLine();

        String[] card = {term, definition};
        System.out.println(card[1].equals(answer) ? "Your answer is right!" : "Your answer is wrong...");
    }
}
