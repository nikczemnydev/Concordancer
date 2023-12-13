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
}