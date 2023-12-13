import java.io.*;
import java.util.*;

public class Concordancer {

    public static void main (String[] args) {

        String fileName = "c:\\mobydick.txt";
        List<String> tokens = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(fileName));
            scanner.useDelimiter("\\s+|(?=\\p{Punct})|(?<=\\p{Punct})");
            while (scanner.hasNext()) {
                tokens.add(scanner.next());
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            return;
        }

        Scanner input = new Scanner(System.in);
        System.out.println("Enter a search phrase please (type .stop to quit)");
        String phrase = input.nextLine();
        /*
        Placeholder for print statements, looped so it can be run multiple times without restarting
         while (!phrase.equals(".stop")) {
             List<Context> contexts = findContexts(tokens, phrase);
             System.out.println("how many contexts found");
             System.out.println("left contexts");
             System.out.println("right contexts");
             System.out.println("bidirectional contexts");
        }
        */

        System.out.println("Please enter a search phrase (.stop to end the program):");
        phrase = input.nextLine();
    }
    input.close();
}
