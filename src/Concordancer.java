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

    }

}
