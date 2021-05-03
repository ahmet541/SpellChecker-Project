package MyList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.DefaultListModel;

/**
 * Class to save MyList to .txt file
 * @author Ahmet Sahin
 * @version 19.04.2021
*/ 
public class FileOperator {
    final String textPath = "src/savedMyListWords.txt";
    
    // Properties
    private File myObj;
    private Scanner reader;
    private FileWriter writer;
    private DefaultListModel<String> savedList;
    private int index;

    // Constructors
    /**
     * Construe of class that read from and write to file.
     */
    public FileOperator() throws IOException {
        
        myObj = new File(textPath);
        savedList = new DefaultListModel<>();
        readFile();
    }
    
    // Methods
    
    /**
     * Method that reads words from savedMyListWords.txt 
    */ 
    private void readFile() throws FileNotFoundException {
        
        reader = new Scanner(myObj); 
        index = 0;
        DefaultListModel<String> temp = new DefaultListModel<>();;
        while ( reader.hasNext() ) {
            
            String currentWord = reader.nextLine();
            temp.add(index, currentWord);
            index++;
        }
        savedList = temp;
    }
    
    /**
     * Method that writes given word to savedMyListWords.txt 
     * @param word is word that user want to add
    */ 
    public void writeOnFile( String word) throws IOException {
        
        writer = new FileWriter( myObj, true);
        if ( myObj.length() != 0 ) {
            
            word = "\n" + word;
        }
        writer.write(word);
        writer.close();
    }
    
    /**
     * Method that writes given word to savedMyListWords.txt with given index
     * @param index is index where we want to add the word
     * @param word is word that user want to add
    */ 
    public void writeOnFile( int index, String word) throws IOException {
        
        Path path = Paths.get(textPath);
        List<String> lines = Files.readAllLines( path, StandardCharsets.UTF_8);
        lines.add(index, word);
        Files.write(path, lines, StandardCharsets.UTF_8);
    }
    
    /**
     * Method to delete word from savedMyListWords.txt
     * @param index is index of word that we want to delete
    */ 
    public void deleteFromFile(int index) throws IOException {
        
        Path path = Paths.get(textPath);
        List<String> lines = Files.readAllLines( path, StandardCharsets.UTF_8);
        lines.remove(index);
        Files.write(path, lines, StandardCharsets.UTF_8);
    }
    
    /**
     * Getter method to get getSavedList
     * @return getSavedList<String>
    */ 
    public DefaultListModel<String> getSavedList() throws FileNotFoundException {
        
        readFile();
        return savedList;
    }
    
    /**
     * Method that empty list
    */ 
    public void clearList() throws IOException {
        
        FileWriter writer = new FileWriter(myObj);
        writer.close();
    }
    
}

