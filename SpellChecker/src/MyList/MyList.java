package MyList;

import java.io.IOException;
import javax.swing.DefaultListModel;

/**
 * MyList Class that includes word that we selected
 * @author Ahmet Sahin
 * @version 14.04.2021
*/ 
public class MyList {
    
    // Properties
    private DefaultListModel<String> myList;
    private int valid;
    private FileOperator fileOperator;
    
    // Constructors
    /**
     * Constructure of Mylis class
     * Get myList by reading file
    */ 
    public MyList() throws IOException {
        
        myList = new DefaultListModel<String>();   
        fileOperator = new FileOperator();
        myList = fileOperator.getSavedList();
        valid = myList.size();
    }
    
    // Methods
    
    /**
     * Method that adds new words to Mylist
     * @param word is new word that user want to add
    */ 
    public void addWord( String word) throws IOException {
        
        int indexForAlphabeticOrder;
        if ( word.length() != 0 ) {
            
            if ( myList.size() == 0 ) { // if MyList is empty than set index to 0
                
                indexForAlphabeticOrder = 0;
            }
            else { // else find appropriate index for word
                
                indexForAlphabeticOrder = findIndexForAlphabeticOrder(word);
            }
            myList.add( indexForAlphabeticOrder, word);
            valid++;   
            fileOperator.writeOnFile(indexForAlphabeticOrder, word);
        }
    }
    
    /**
     * Method to find index to add new word to myList
     * @param word is new word that user want to add
     * @return wanted index for word to add myList
    */ 
    public int findIndexForAlphabeticOrder( String word) {
        
        return  researchPlaceForNewWord(word, 0, myList.size() - 1);
    }
    
    /**
     * Helper recursive method to findIndexForAlphabeticOrder
     * @param key a word that we search place for
     * @param start index of start
     * @param end index of end 
     * @return wanted index for word to add myList
    */ 
    public int researchPlaceForNewWord(String key, int start, int end) {
        
        int middle;
        middle = (start + end) / 2;
        boolean isLastTerm;

        isLastTerm = false;
        if ( start >= end ) { // Stop condition

            isLastTerm = true;  
        }
        if ( key.equals( myList.get(middle))  ) { // if key and word in MyList with index of middle is same set index middle
            
            return middle;
        }   
            
        if ( key.compareTo( myList.get(middle)) < 0 ) {
            
            if ( isLastTerm ) { // if we look at last term and if our word is alphabetically ahead then set index start
                
                return start;
            }
            end = middle;
        }
        else if ( key.compareTo( myList.get(middle)) > 0 ) {

            if ( isLastTerm ) { // if we look at last term and if our word is alphabetically on the back then set index (start + 1), so we add new word just after that word
                
                return start + 1;
            }
            start = middle + 1;
        }
        return researchPlaceForNewWord( key, start, end);
    }
    
    /**
     * Method that deletes word from list
     * @param word is a word that user want to delete
     * @return true if word is found and deleted, else return false
    */ 
    public boolean removeWord( String word) throws IOException {
        int index;
        
        index = myList.indexOf( word);
        if ( word.equals(myList.remove( index ))  ) {
            
            fileOperator.deleteFromFile(index);
            valid--;
            return true;
        }   
        return false;
    }

    /**
     * Method that deletes word with given index from list
     * @param index is index of word that user want to delete
     * @return true if word is found and deleted, else return false
    */     
    public boolean removeWord( int index) throws IOException {
        
        if ( myList.get(index).equals( myList.remove(index)) && index <= valid - 1) {
            
            fileOperator.deleteFromFile(index);
            valid--;
            return true;
        }   
        return false;
    }
    
    /**
     * Method to get MyList
     * @return MyList<String>
    */ 
    public DefaultListModel<String> getMyList() {
        
        return myList;   
    }
}
