package View;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Class to save MyList to .txt file
 * @author Ahmet Sahin
 * @version 19.04.2021
*/ 
public class FileOperatorForTheme {
    
    // Properties
    private File myObj;
    private Scanner reader;
    private FileWriter writer;
    private String mode;
    
    // Constructors
    
    /**
     * Construe of class that read from and write to file.
     */
    public FileOperatorForTheme() throws IOException {
        
        myObj = new File("src/savedThemeMode.txt");
        readFile();
    }
    
    // Methods
    
    /**
     * Method that reads words from savedMyListWords.txt 
    */ 
    private void readFile() throws FileNotFoundException {
        
        reader = new Scanner(myObj); 
        mode = reader.nextLine();
    }
    
    public String getMode() {
        return mode;   
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
     * Method that empty list
    */ 
    public void clearList() throws IOException {
        
        FileWriter writer = new FileWriter(myObj);
        writer.close();
    }
    
}

