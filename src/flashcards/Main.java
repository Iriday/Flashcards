package flashcards;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    private enum KeyOrVal {KEY, VALUE}

    private static Map<String, String> cards = new LinkedHashMap<>();
    //private static ArrayList<String> keys = new ArrayList<>();
    //private static ArrayList<String> values = new ArrayList<>();
    private static Scanner scn = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {

        boolean on = true;
        while (on) {
            System.out.println("Input the action (add, remove, import, export, ask, exit):");
            switch (scn.nextLine().toLowerCase(Locale.ENGLISH)) {
                case "add":
                    addCard();
                    break;
                case "remove":
                    removeCard();
                    break;
                case "import":
                    loadFromFile();
                    break;
                case "export":
                    saveToFile();
                    break;
                case "ask":
                    ask();
                    break;
                case "exit":
                    System.out.println("Bye bye!");
                    on = false;
            }
        }
    }

    //get key by value
    public static String getTermByDefinition(String val) {
        for (Map.Entry<String, String> pair : cards.entrySet()) {
            if (pair.getValue().equals(val)) {
                return pair.getKey();
            }
        }
        return null;
    }

    public static boolean checkIfPresent(String data, KeyOrVal keyOrVal) {
        switch (keyOrVal) {
            case KEY:
                return cards.keySet().contains(data);
            case VALUE:
                return cards.values().contains(data);
            default:
                return false;
        }
    }

    private static ArrayList<String> keys;

    public static void ask() {

        System.out.println("How many times to ask?");
        int num = Integer.parseInt(scn.nextLine());
        String answer;
        String randTerm;
        String value;
        int size = cards.size();
        keys = new ArrayList<>(cards.keySet());

        for (int i = 0; i < num; i++) {
            randTerm = keys.get(random.nextInt(size));
            value = cards.get(randTerm);
            System.out.printf("Print the definition of \"%s\":\n", randTerm);//"black"
            answer = scn.nextLine();
            if (value.equals(answer)) {
                System.out.println("Correct answer.");
                continue;
            }
            String s = getTermByDefinition(answer);
            if (s == null) {
                System.out.printf("Wrong answer. The correct one is \"%s\".\n", value);//"white"
            } else {
                System.out.printf("Wrong answer. The correct one is \"%s\", you've just written the definition of \"%s\".\n", value, s);
            }
        }
    }

    public static void addCard() {
        String term;
        String definition;

        System.out.println("The card:");
        // while (true) {
        term = scn.nextLine();
        if (checkIfPresent(term, KeyOrVal.KEY)) {
            System.out.printf("The card \"%s\" already exists.\n", term);
            return;
        } //else {
        //break;
        //}
        //}

        System.out.println("The definition of the card:");
        // while (true) {
        definition = scn.nextLine();
        if (checkIfPresent(definition, KeyOrVal.VALUE)) {
            System.out.printf("The definition \"%s\" already exists.\n", definition);
            return;
        }  //else {
        //break;
        // }
        //  }
        cards.put(term, definition);
        System.out.printf("The pair (\"%s\":\"%s\") has been added.\n", term, definition);
    }

    public static void removeCard() {
        System.out.println("The card:");
        String term = scn.nextLine();
        if (checkIfPresent(term, KeyOrVal.KEY)) {
            //System.out.printf("The pair (\"%s\":\"%s\") has been removed.\n", term, cards.get(term));
            System.out.println("The card has been removed.");
            cards.remove(term);
        } else {
            System.out.printf("Can't remove \"%s\": there is no such card.\n", term);
        }
    }

    public static void loadFromFile() {
        int counter = 0;
        System.out.println("File name:");
        try {
            Scanner fileScanner = new Scanner(new File(scn.nextLine()));
            while (fileScanner.hasNext()) {
                cards.put(fileScanner.nextLine(), fileScanner.nextLine());
                counter++;
            }
            System.out.println(counter + " cards have been loaded.");
        } catch (Exception e) {
            //String message = e.getMessage();
            //System.out.println(message != null ? message : "Error while reading file.");
            System.out.println("File not found.");
        }
    }

    public static void saveToFile() {
        int counter = 0;
        System.out.println("File name:");
        try {
            PrintWriter writer = new PrintWriter(scn.nextLine());
            for (Map.Entry<String, String> card : cards.entrySet()) {
                writer.println(card.getKey());
                writer.println(card.getValue());
                counter++;
            }
            writer.flush();
            writer.close();
            System.out.println(counter + " cards have been saved.");
        } catch (Exception e) {
            System.out.println("Error while wring to file.");
        }
    }
}
