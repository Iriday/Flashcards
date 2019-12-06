package flashcards;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private enum KeyOrVal {KEY, VALUE}

    private static Map<String, String> cards = new LinkedHashMap<>();
    //private static ArrayList<String> keys = new ArrayList<>();
    //private static ArrayList<String> values = new ArrayList<>();

    public static void main (String[]args){

        Scanner scn = new Scanner(System.in);

        System.out.println("Input the number of cards:");
        int numOfCards = Integer.parseInt(scn.nextLine());

        String term;
        String definition;

        for (int i = 0; i < numOfCards; i++) {

            System.out.printf("The card #%d:\n", i);
            while (true) {
                term = scn.nextLine();
                if (checkIfPresent(term, KeyOrVal.KEY)) {
                    System.out.printf("The card \"%s\" already exists. Tray again:\n", term);
                    continue;
                } else {
                    break;
                }
            }

            System.out.printf("The definition of the card #%d:\n", i);
            while (true) {
                definition = scn.nextLine();
                if (checkIfPresent(definition, KeyOrVal.VALUE)) {
                    System.out.printf("The definition \"%s\" already exists.Try again:\n", definition);
                    continue;
                } else {
                    break;
                }
            }
            cards.put(term, definition);
        }

        String answer;
        for (Map.Entry<String, String> card : cards.entrySet()) {
            System.out.printf("Type the definition of \"%s\":\n", card.getKey());//"black"
            answer = scn.nextLine();
            if (card.getValue().equals(answer)) {
                System.out.println("Correct answer.");
                continue;
            }
            String s = getTermByDefinition(answer);
            if (s == null) {
                System.out.printf("Wrong answer. The correct one is \"%s\".\n", card.getValue());//"white"
            } else {
                System.out.printf("Wrong answer. The correct one is \"%s\", you've just written the definition of \"%s\".\n", card.getValue(), s);
            }
        }
    }
    //get key by value
    public static String getTermByDefinition (String val){

        for (Map.Entry<String, String> pair : cards.entrySet()) {
            if (pair.getValue().equals(val)) {
                return pair.getKey();
            }
        }
        return null;
    }

    public static boolean checkIfPresent (String data, KeyOrVal keyOrVal){
        switch (keyOrVal) {
            case KEY:
                return cards.keySet().contains(data);
            case VALUE:
                return cards.values().contains(data);
            default:
                return false;
        }
    }
}
