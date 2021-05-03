package SpellCorrector;

import java.util.ArrayList;

/**
 * A class to hold data for wrongly spelled words. Contains the wrongly spelled form of the word, possible correct forms, beginning index, ending index and a boolean whether
 * the word is capitalized or not.
 * @author Kaan Berk Kabadayı
 * @version 30.04.2021
 */
public class WrongWordResponse {
    String wrongForm;
    ArrayList<String> possibleCorrectForms;
    int beginIndex;
    int endIndex;
    boolean isCapitalized;

    /**
     * Constructor for the WrongWordResponse object. Initializes the properties. If the isCapitalized boolean is true, turns capitalizes the possibleCorrectWords ArrayList.
     * @param wrongForm
     * @param possibleCorrectWords
     * @param beginIndex
     * @param endIndex
     * @param isCapitalized
     */
    public WrongWordResponse(String wrongForm, ArrayList<String> possibleCorrectWords, int beginIndex, int endIndex, boolean isCapitalized) {
        this.wrongForm = wrongForm;
        if (isCapitalized) {
            possibleCorrectForms = new ArrayList<>();
            for (String s : possibleCorrectWords) {
               
                possibleCorrectForms.add(trueForm(s));
            }
            this.wrongForm = trueForm(this.wrongForm);
        }
        else {
            this.possibleCorrectForms = possibleCorrectWords;
        }
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
        this.isCapitalized = isCapitalized;
    }
    
    private String trueForm( String s) {
        String word = "";
        if (s.charAt(0) == 'i') {
            word = "I" + s.substring(1);
        }
        else {
            word = Character.toUpperCase(s.charAt(0)) + s.substring(1);
        }  
        return word; 
    }
    /**
     * Getter method for the ArrayList of possible words.
     * @return possibleCorrectForms
     */
    public ArrayList<String> getPossibleCorrectForms() {
        return possibleCorrectForms;   
    }

    /**
     * Getter method for the wrong form.
     * @return wrongForm
     */
    public String getWrongForm() {
        return this.wrongForm;
    }

    /**
     * Getter method for the begin index.
     * @return beginIndex
     */
    public int getBeginIndex() {
        return this.beginIndex;
    }

    /**
     * Getter method for the end index.
     * @return endIndex
     */
    public int getEndIndex() {
        return this.endIndex;
    }
    
    /**
     * Setter method for the begin index.
     * @param newBegin
     */
    public void setBeginIndex(int newBegin) {
        this.beginIndex = newBegin;
    }

    /**
     * Setter method for the end index.
     * @param newEnd
     */
    public void setEndIndex(int newEnd) {
        this.endIndex = newEnd;
    }
    
    /**
     * String presentation of the WrongWordResponse object.
     * @return String presentation of the object, contains the data of the object
     */
    public String toString() {
        return "Wrong: " + wrongForm + " Possible words: " + possibleCorrectForms  + " Beginning: " + beginIndex + " End: " + endIndex + " Is capitalized? " + isCapitalized + "\n";
    }
}
