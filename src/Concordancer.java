import java.io.*;
import java.util.*;

public class Concordancer {
    public static void main(String[] args) {
        String fileName = "c:\\mobydick.txt";
        List<String> tokens = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(fileName));
            scanner.useDelimiter("\\s+|(?=\\p{Punct})|(?<=\\p{Punct})");
            while (scanner.hasNext()) {
                tokens.add(scanner.next());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            return;
        }

        Scanner input = new Scanner(System.in);
        System.out.println("Enter a search phrase please (type .stop to quit)");
        String phrase = input.nextLine();

        while (!phrase.equals(".stop")) {
            List<Context> contexts = findContexts(tokens, phrase);
            System.out.println(contexts.size() + "individual contexts found in total");
            System.out.println("<<<<<<<<<< Left contexts <<<<<");
            for (Context context : contexts) {
                System.out.println(context.left);
            }
            System.out.println(">>>>> Right contexts >>>>>>>>>>");
            for (Context context : contexts) {
                System.out.println(context.right);
            }
            System.out.println("<<<<<<< Bidirectional contexts >>>>>>>");
            for (Context context : contexts) {
                System.out.println(context.getBidirectional());
            }
            System.out.println("Please enter a search phrase (.stop to end the program):");
            phrase = input.nextLine();
        }
        input.close();
    }
    static class Context {
        String left;
        String right;
        String phrase;

        public Context(String left, String right, String phrase) {
            this.left = left;
            this.right = right;
            this.phrase = phrase;
        }
        public String getBidirectional() {
            return left + phrase + right;
        }
    }
    public static List<Context> findContexts(List<String> tokens, String phrase) {
        List<Context> contexts = new ArrayList<>();
        String[] words = phrase.split("\\s+");
        for (int i = 0; i < tokens.size() - words.length + 1; i++) {
            boolean match = true;
            for (int j = 0; j < words.length; j++) {
                if (!tokens.get(i + j).equals(words[j])) {
                    match = false;
                    break;
                }
            }
            if (match) {
                String left = "";
                for (int k = Math.max(0, i - 5); k < i; k++) {
                    left += tokens.get(k) + " ";
                }
                String right = "";
                for (int k = i + words.length; k < Math.min(tokens.size(), i + words.length + 5); k++) {
                    right += tokens.get(k) + " ";
                }
                contexts.add(new Context(left, right, phrase));
            }
        }
        return contexts;
    }
}