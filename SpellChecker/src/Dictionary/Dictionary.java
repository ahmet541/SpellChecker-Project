package Dictionary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * A class to represent a dictionary. It contains data of the words and their frequencies. Reads directly from the file.
 * @author Kaan Berk Kabadayı
 * @version 30.04.2021
 */
public class Dictionary {
    private final String path = "src/freq.txt";

    private HashMap<String, Long> counter;
    private int wordCount;
    private Scanner scan; 

    /**
     * Constructor for the Dictionary object. 
     * @throws FileNotFoundException
     */
    public Dictionary() throws FileNotFoundException {
        calculateFrequency();
    }

    /**
     * Calculates the frequencies of each word. Initializes the HashMap object counter
     * @return
     * @throws FileNotFoundException
     */
    public HashMap<String, Long> calculateFrequency() throws FileNotFoundException {
        counter = new HashMap<>();
        String thisLine = "";
        String beforeSpace = "";
        String afterSpace = "";
        int pointer;
        scan = new Scanner(new File(path));
        while (scan.hasNextLine()) {
            thisLine = scan.nextLine();
            pointer = 0;
            for (int i = 0; i < thisLine.length(); i++) {
                if (thisLine.charAt(i) == ' ') {
                    beforeSpace = thisLine.substring(0, i);
                    pointer = i + 1;
                }
            }
            wordCount++;
            afterSpace = thisLine.substring(pointer, thisLine.length());
            counter.put(beforeSpace, Long.parseLong(afterSpace));
        }
        return counter;
    }
    
    /**
     * Getter method for probability in the dictionary
     * @param word
     * @return
     */
    public double probability(String word) {
        return counter.get(word) / (double) wordCount;
    }

    /**
     * Getter method for the HashMap object counter
     * @return
     */
    public HashMap<String, Long> getCounter() {
        return counter;
    }

    /**
     * Returns true if the given word exists in the dictionary
     * @param word
     * @return true if meaningful
     */
    public boolean existsInDict(String word) {
        return counter.containsKey(word);
    }
}
