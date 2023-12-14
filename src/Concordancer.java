import java.io.*;
import java.util.*;

public class Concordancer {
    public static void main(String[] args) {
        /** Loading the corpus, in this case any file called book.txt stored directly on C drive **/
        String fileName = "c:\\book.txt";
        List<String> tokens = new ArrayList<>();
        /** Tokenizing the corpus i.e. splitting the body of text into separate 'regions', in this
         case separate words. I decided to include punctuation as tokens in this version too. **/
        try {
            Scanner scanner = new Scanner(new File(fileName));
            /** Single words are extracted using the separators expressed by regex **/
            scanner.useDelimiter("\\s+|(?=\\p{Punct})|(?<=\\p{Punct})");
            while (scanner.hasNext()) {
                tokens.add(scanner.next());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            return;
        }
        /** Asking for a search phrase, looped so user can conduct multiple searches
         without having to reload the program. Printing the contexts as 3 lists.  **/
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
            /** Asking for another search phrase or .stop command to terminate **/
            System.out.println("Please enter a search phrase (.stop to end the program):");
            phrase = input.nextLine();
        }
        input.close();
    }
    /** Helper class to store the contexts of given phrase **/
    static class Context {
        String left;
        String right;
        String phrase;
        /** Constructor to initialize the fields **/
        public Context(String left, String right, String phrase) {
            this.left = left;
            this.right = right;
            this.phrase = phrase;
        }
        /** Simple method to make code for returning bidirectional contexts cleaner **/
        public String getBidirectional() {
            return left + phrase + right;
        }
    }
    /** Context finder method - it searches for the search phrase in already tokenized corpus, if
     a match is found, 5 tokens on each side get stored as contexts. **/
    public static List<Context> findContexts(List<String> tokens, String phrase) {
        List<Context> contexts = new ArrayList<>();
        /** Splitting phrase into words, using whitespace as separator **/
        String[] words = phrase.split("\\s+");
        /** Loop through the tokens **/
        for (int i = 0; i < tokens.size() - words.length + 1; i++) {
            /** Check if tokens match the words **/
            boolean match = true;
            for (int j = 0; j < words.length; j++) {
                if (!tokens.get(i + j).equals(words[j])) {
                    match = false;
                    break;
                }
            }
            /** On match create context object 5 tokens to either or both sides. **/
            if (match) {
                /** Get left context starting from 5 tokens before the matched search phrase. **/
                String left = "";
                for (int k = Math.max(0, i - 5); k < i; k++) {
                    left += tokens.get(k) + " ";
                }
                /** Get right context 5 tokens after the search phrase. **/
                String right = "";
                for (int k = i + words.length; k < Math.min(tokens.size(), i + words.length + 5); k++) {
                    right += tokens.get(k) + " ";
                }
                /** Store contexts in the list. **/
                contexts.add(new Context(left, right, phrase));
            }
        }
        /** Return the list. **/
        return contexts;
    }
}