package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Flashcards {

    private enum KeyOrVal {KEY, VALUE}

    // term, definition, hardestCard
    private static Map<String, Map.Entry<String, Integer>> cards = new LinkedHashMap<>();
    private static ArrayList<String> log = new ArrayList<>();
    //private static ArrayList<String> keys = new ArrayList<>();
    //private static ArrayList<String> values = new ArrayList<>();
    private static Scanner scn = new Scanner(System.in);
    private static Random random = new Random();
    private static String pathImport = "";
    private static String pathExport = "";
    private static boolean importInput = false;
    private static boolean exportInput = false;

    public static void main(String[] args) {

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("-import")) {
                pathImport = args[1];
                loadFromFile();
            } else if (args[0].equalsIgnoreCase("-export")) {
                pathExport = args[1];
            }
        }
        if (args.length == 4) {
            if (args[0].equalsIgnoreCase("-import")) {
                pathImport = args[1];
                loadFromFile();
            } else if (args[0].equalsIgnoreCase("-export")) {
                pathExport = args[1];
            }
            if (args[2].equalsIgnoreCase("-import")) {
                pathImport = args[3];
                loadFromFile();
            } else if (args[2].equalsIgnoreCase("-export")) {
                pathExport = args[3];
            }
        }
       /* if (pathImport.equals("")) {
            System.out.println("0 cards have been loaded.");
        }
*/
        boolean on = true;
        while (on) {
            System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            switch (scn.nextLine().toLowerCase(Locale.ENGLISH)) {
                case "add":
                    addCard();
                    break;
                case "remove":
                    removeCard();
                    break;
                case "import":
                    importInput = true;
                    loadFromFile();
                    break;
                case "export":
                    exportInput = true;
                    saveToFile();
                    break;
                case "ask":
                    ask();
                    break;
                case "exit":
                    if (!pathExport.equals("")) {
                        System.out.println("Bye bye!");
                    }
                    saveToFile();

                    on = false;
                    break;
                case "log":
                    log();
                    break;
                case "hardest card":
                    hardestCard();
                    break;
                case "reset stats":
                    resetStats();
                    break;
            }
        }
    }

    //get key by value
    public static String getTermByDefinition(String val) {
        for (Map.Entry<String, Map.Entry<String, Integer>> pair : cards.entrySet()) {
            if (pair.getValue().getKey().equals(val)) {
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
                return cards.values().contains(new Definition(data, 0));
            default:
                return false;
        }
    }

    private static ArrayList<String> keys;

    private static void ask() {
        if (cards.isEmpty()) {
            System.out.println("You have to add or import some cards before choosing this action.");
            return;
        }
        System.out.println("How many times to ask?");
        int num = Integer.parseInt(scn.nextLine());
        String answer;
        String randTerm;
        Map.Entry<String, Integer> value;
        int size = cards.size();
        keys = new ArrayList<>(cards.keySet());

        for (int i = 0; i < num; i++) {
            randTerm = keys.get(random.nextInt(size));
            value = cards.get(randTerm);
            System.out.printf("Print the definition of \"%s\":\n", randTerm);//"black"
            answer = scn.nextLine();
            if (value.getKey().equals(answer)) {
                System.out.println("Correct answer.");
                continue;
            }
            String s = getTermByDefinition(answer);
            if (s == null) {
                System.out.printf("Wrong answer. The correct one is \"%s\".\n", value.getKey());//"white"
                value.setValue(value.getValue() + 1);
            } else {
                System.out.printf("Wrong answer. The correct one is \"%s\", you've just written the definition of \"%s\".\n", value.getKey(), s);
                value.setValue(value.getValue() + 1);
            }
        }
    }

    private static void addCard() {
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
        cards.put(term, new Definition<>(definition, 0));
        log.add(term); //log.add(---addCard()---);
        log.add(definition);
        //log.add("0");
        System.out.printf("The pair (\"%s\":\"%s\") has been added.\n", term, definition);
    }

    private static void removeCard() {
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

    private static void loadFromFile() {
        int counter = 0;
        String filePath;
        if (importInput) {
            System.out.println("File name:");
            filePath = scn.nextLine();
            importInput = false;
        } else {
            filePath = pathImport;
        }

        try {
            Scanner fileScanner = new Scanner(new File(filePath));
            while (fileScanner.hasNext()) {
                String term = fileScanner.nextLine();
                String definition = fileScanner.nextLine();
                Integer errCount = Integer.parseInt(fileScanner.nextLine());
                cards.put(term, new Definition<>(definition, errCount));
                log.add(term);//log.add("---loadFromFile()---");
                log.add(definition);
                log.add(errCount.toString());
                counter++;
            }
            System.out.println(counter + " cards have been loaded.");
        } catch (Exception e) {
            //String message = e.getMessage();
            //System.out.println(message != null ? message : "Error while reading file.");
            System.out.println("File not found.");
        }
    }

    private static void saveToFile() {
        int counter = 0;
        String filePath;
        if (exportInput) {
            System.out.println("File name:");
            filePath = scn.nextLine();
            exportInput = false;
        } else {
            if (pathExport.equals("")) {
                System.out.println("0 cards have been saved.");
                return;
            }
            filePath = pathExport;
        }

        try {
            PrintWriter writer = new PrintWriter(filePath);
            for (Map.Entry<String, Map.Entry<String, Integer>> card : cards.entrySet()) {
                writer.println(card.getKey());
                writer.println(card.getValue().getKey());
                writer.println(card.getValue().getValue());
                counter++;
            }
            writer.flush();
            writer.close();
            System.out.println(counter + " cards have been saved.");
        } catch (Exception e) {
            System.out.println("Error while wring to file.");
        }
    }

    private static void log() {
        System.out.println("File name:");
        try {
            PrintWriter writer = new PrintWriter(scn.nextLine());
            for (String line : log) {
                writer.println(line);
            }
            String output = "The log has been saved.";
            System.out.println(output);
//            log.add(output);
//            writer.println(output);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error");
        }
    }

    private static void hardestCard() {
        Map<String, Integer> cardsWithMostMistakes = new HashMap<>(); // ArrayList?
        int maxErr = -1;

        for (Map.Entry<String, Map.Entry<String, Integer>> card : cards.entrySet()) {
            int errCounter = card.getValue().getValue();
            if (errCounter != 0 && errCounter > maxErr) {
                cardsWithMostMistakes.clear();
                cardsWithMostMistakes.put(card.getKey(), card.getValue().getValue());
                maxErr = errCounter;
            } else if (errCounter == maxErr) {
                cardsWithMostMistakes.put(card.getKey(), card.getValue().getValue());
            }
        }

        if (!cardsWithMostMistakes.isEmpty()) {
            String space = " ";
            String quote = "\"";
            String comma = ",";
            int cardsSize = cardsWithMostMistakes.size();
            StringBuilder builder = new StringBuilder("The hardest card" + (cardsSize == 1 ? " is" : "s are"));
            String output1;

            for (Map.Entry<String, Integer> pair : cardsWithMostMistakes.entrySet()) {
                builder.append(space);
                builder.append(quote);
                builder.append(pair.getKey());
                builder.append(quote);
                builder.append(comma);
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append(String.format(". You have %d errors answering %s.", maxErr, cardsSize == 1 ? "it" : "them"));
            output1 = builder.toString();
            System.out.println(output1);
            log.add(output1);
        } else {
            String output2 = "There are no cards with errors.";
            System.out.println(output2);
            log.add(output2);
        }
    }

    private static void resetStats() {
        boolean cardsWithErrors = false;

        for (Map.Entry<String, Map.Entry<String, Integer>> card : cards.entrySet()) {
            if (card.getValue().getValue() != 0) {
                cardsWithErrors = true;
            }
            card.getValue().setValue(0);
        }
        if (!cardsWithErrors) {
            String output2 = "There are no cards with errors.";
            System.out.println(output2);
            log.add(output2);
        } else {
            String output1 = "Card statistics has been reset.";
            System.out.println(output1);
            log.add(output1);
        }
    }
}
