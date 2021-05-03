package SpellCorrector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Dictionary.*;


/**
 * A class to contain all the spellchecking methods. 
 * @author Kaan Berk Kabadayı
 * @version 30.04.2021
 */
public class SpellCorrector {
    private Dictionary dict;
    private ArrayList<WrongWordResponse> wrongWordResponses;

    /**
     * Constructor for the SpellCorrector object.
     * @throws IOException
     */
    public SpellCorrector() throws IOException {
        dict = new Dictionary();
    }

    /**
     * Finds the most possible candidates for the word, sorts their probability of appearing in the dictionary in ascending order and return the first 3 elements max.
     * @param word
     * @return the 3 most likely candidate of the given word
     */
    public ArrayList<String> correction(String word) {
        ArrayList<String> possibleWords = new ArrayList<>();

        if (word.equals("ı") || word.equals("i")) {
            possibleWords.add("I");
            return possibleWords;
        }

        HashSet<String> possibleCandidates = possibleCandidates(word);
        ArrayList<Double> probabilityList = new ArrayList<>();
        HashMap<Double, String> wordtoProb = new HashMap<>();

        double probability = 0;
        for (String s : possibleCandidates) {
            probability = dict.probability(s);
            wordtoProb.put(probability, s);
            probabilityList.add(probability);
        }
        Collections.sort(probabilityList, Collections.reverseOrder());
        for (int i = 0; i < probabilityList.size(); i++) {
            possibleWords.add(wordtoProb.get(probabilityList.get(i)));
            if (i == 2) {
                break;
            }
        }
        return possibleWords;
    }
    
    /**
     * Possible candidates of the word. If the word is already meaningful then the only candidate is the word itself. If not, the candidates are edits with one distance max. 
     * If they are not found as well, then the candidates are edits with two distances. If it also fails and there is no possible return an empty set.
     * @param word
     * @return possible candidates of the word with the above algorithm
     */
    public HashSet<String> possibleCandidates(String word) {
        HashSet<String> possibleCandidates = new HashSet<>();

        if (meaningfulWord(word)) {
            possibleCandidates.add(word);
        }
        
        if (possibleCandidates.isEmpty()) {
            possibleCandidates.addAll(meaningfulWordsInSet(editsOneDistanceAway(word)));
        }

        if (possibleCandidates.isEmpty()) {
            possibleCandidates.addAll(meaningfulWordsInSet(editsTwoDistanceAway(word)));
        }
        return possibleCandidates;
    }

    /**
     * Finds the most likely correction in the possible candidates list in one distance max. Useful for corrections like messa gf -> message
     * @param word
     * @return the most likely correction one distance max
     */
    public String correctionOneDistanceMax(String word) {
        double maxProbability = Integer.MIN_VALUE;
        String correction = "";
    
        for (String candidate : possibleCandidatesOneDistanceMax(word)) {
            if (dict.probability(candidate) > maxProbability) {
                maxProbability = dict.probability(candidate);
                correction = candidate;
            }
        }
        return correction;
    }

    /**
     * Possible candidates of the word. If the word is already meaningful then the only candidate is the word itself. If not, the candidates are edits with one distance max.
     * If it fails return an empty set.
     * @param word
     * @return the possible candidates with the above algorithm
     */
    public HashSet<String> possibleCandidatesOneDistanceMax(String word) {
        HashSet<String> possibleCandidates = new HashSet<>();

        if (meaningfulWord(word)) {
            possibleCandidates.add(word);
        }
        
        if (possibleCandidates.isEmpty()) {
            possibleCandidates.addAll(meaningfulWordsInSet(editsOneDistanceAway(word)));
        }
        return possibleCandidates;
    }

    /**
     * Finds all edits of the word one distance away. These edits include: Insertions, deletions, Transpositions, Replacements.
     * @param word
     * @return set of all edits one distance away from the word
     */
    private HashSet<String> editsOneDistanceAway(String word) {
        String letters = "abcdefghijklmnopqrstuvwxyz'";
        HashSet<String> operations = new HashSet<>();

        String first = "";
        String second = "";

        // Find all splits of the word
        HashSet<Pair> splits = new HashSet<>();
        for (int i = 0; i <= word.length(); i++) {
            splits.add(new Pair(word.substring(0, i), word.substring(i, word.length())));
        }
        
        // Find deletions by deleting one character in splits
        HashSet<String> deletions = new HashSet<>();
        for (Pair pair : splits) {
            if (pair.second.length() > 0) {
                first = pair.first;
                second = pair.second;
                deletions.add(first + second.substring(1, second.length()));
            }
        }
        operations.addAll(deletions);
        
        // Find transposes by swapping two characters in splits
        HashSet<String> transposes = new HashSet<>();
        for (Pair pair : splits) {
            if (pair.second.length() > 1) {
                first = pair.first;
                second = pair.second;
                transposes.add(first + second.charAt(1) + second.charAt(0) + second.substring(2, second.length())); 
            }
        }
        operations.addAll(transposes);

        // Find replaces by replacing one charater in splits
        HashSet<String> replaces = new HashSet<>();
        for (Pair pair : splits) {
            if (pair.second.length() > 0) {
                first = pair.first;
                second = pair.second;
                for (char c : letters.toCharArray()) {
                    replaces.add(first + c + second.substring(1, second.length()));
                }
            }
        }
        operations.addAll(replaces);

        // Find insertions by inserting one character to splits
        HashSet<String> inserts = new HashSet<>();
        for (Pair pair : splits) {
            first = pair.first;
            second = pair.second;
            for (char c : letters.toCharArray()) {
                inserts.add(first + c + second);
            }
        }
        operations.addAll(inserts);

        return operations;  
    }

    /**
     * Finds the set of the edits two distance away of the given word.
     * @param word
     * @return set of words with two edits
     */
    private HashSet<String> editsTwoDistanceAway(String word) {
        HashSet<String> oneDistance = editsOneDistanceAway(word);
        HashSet<String> twoEditDistance = new HashSet<>();

        for (String s : oneDistance) {
            twoEditDistance.addAll(editsOneDistanceAway(s));
        }
        

        return twoEditDistance;
    }

    /**
     * The main functional method for SpellCorrector object. Takes the text to be processed and splits the words using a regular expression. After the text is split into words 
     * and put into an ArrayList, algorithm for spellchecking now consists of 6 cases. After the method scans the words in text and finds possible corrections, puts them into an
     * ArrayList of WrongeWordResponse object.
     * @param mainText
     * @return ArrayList of WrongWordResponses for each wrongly spelled word
     */ 
    public ArrayList<WrongWordResponse> correctAll(String mainText) {
        HashMap<String, Boolean> wordToCapitalBoolean = new HashMap<>();
        String stringPattern = "[\\w'-]+";
        String firstWord;
        String secondWord;
        String wrongForm;
        String correctForm;
        String thisWord; 
        int beginIndex  = 0; 
        int endIndex = 0;

        wrongWordResponses = new ArrayList<>();
        ArrayList<String> wordsInText = new ArrayList<>();
        
        Pattern pattern = Pattern.compile(stringPattern);
        Matcher matcher = pattern.matcher(mainText);

        while (matcher.find()) {
            String word = matcher.group();

            if (word.charAt(0) >= 'A' && word.charAt(0) <= 'Z') {
                if (word.charAt(0) == 'I') {
                    word = "i" + word.substring(1);
                }
                else {
                    word = toLowerCaseWithI(word);
                }
                wordToCapitalBoolean.put(word, true);
            }
            else {
                wordToCapitalBoolean.put(word, false);
            }
            wordsInText.add(word);
        }
        mainText = toLowerCaseWithI(mainText);
        System.out.println(mainText);

        // Case 1: Both meaningful words that have meaning when whitespace is deleted like soft ware
        for (int i = 0; i < wordsInText.size() - 1; i++) {
            ArrayList<String> case1List = new ArrayList<>();
            firstWord = wordsInText.get(i);
            secondWord = wordsInText.get(i + 1);
            if (bothMeaningfulWithSpace(firstWord, secondWord)) {
                wrongForm = firstWord + " " + secondWord; 
                correctForm = firstWord + secondWord;
                beginIndex = mainText.indexOf(toLowerCaseWithI(firstWord));
                endIndex = beginIndex + wrongForm.length();
                case1List.add(correctForm);
                wordsInText.remove(firstWord);
                wordsInText.remove(secondWord);
                wordsInText.add(i, correctForm);
                wrongWordResponses.add(new WrongWordResponse(wrongForm, case1List, beginIndex, endIndex, wordToCapitalBoolean.get(firstWord)));
            }
        }
        System.out.println("After Case 1: " + wordsInText);

        // Case 2: Check if two meaningless words mean something when put together like messa gf
        for (int i = 0; i < wordsInText.size() - 1; i++) {
            ArrayList<String> case2List = new ArrayList<>(); 
            firstWord = wordsInText.get(i);
            secondWord = wordsInText.get(i + 1);
            if (neitherMeaningful(firstWord, secondWord)) {
                String withoutSpace = firstWord + secondWord;
                correctForm = correctionOneDistanceMax(withoutSpace);
                if (!correctForm.equals("")) {
                    wrongForm = firstWord + " " + secondWord;
                    beginIndex = mainText.indexOf(toLowerCaseWithI(firstWord));
                    endIndex = beginIndex + wrongForm.length();
                    case2List.add(correctForm);
                    wordsInText.remove(firstWord);
                    wordsInText.remove(secondWord);
                    wordsInText.add(i, correctForm);
                    wrongWordResponses.add(new WrongWordResponse(wrongForm, case2List, beginIndex, endIndex, wordToCapitalBoolean.get(firstWord)));
                } 
            }
        }
        System.out.println("After Case 2: " + wordsInText);
         
        // Case 3: Check all possible compound words by adding spaces and checking if they exist in dictionary: thereis
        for (int i = 0; i < wordsInText.size(); i++) {
            ArrayList<String> case3List = new ArrayList<>();
            thisWord = wordsInText.get(i);
            if (!meaningfulWord(thisWord)) {
                HashSet<Pair> splits = new HashSet<>();
                for (int j = 0; j <= thisWord.length(); j++) {
                    splits.add(new Pair(thisWord.substring(0, j), thisWord.substring(j, thisWord.length())));
                }
                for (Pair pair : splits) {
                    firstWord = pair.first;
                    secondWord = pair.second;
                    if (meaningfulWord(firstWord) && meaningfulWord(secondWord)) {
                        wrongForm = thisWord;
                        correctForm = firstWord + " " + secondWord;
                        beginIndex = mainText.indexOf(toLowerCaseWithI(wrongForm));
                        endIndex = beginIndex + wrongForm.length();
                        case3List.add(correctForm);
                        wordsInText.remove(wrongForm);
                        wordsInText.add(i, firstWord);
                        wordsInText.add(i + 1, secondWord);
                        wrongWordResponses.add(new WrongWordResponse(wrongForm, case3List, beginIndex, endIndex, wordToCapitalBoolean.get(thisWord)));
                        break;
                    }   
                }
            }
        }
        System.out.println("After Case 3: " + wordsInText);

        // Case 4: Check all single words and correct if you can
        for (int i = 0; i < wordsInText.size(); i++) {
            ArrayList<String> case4List = new ArrayList<>();
            thisWord = wordsInText.get(i);
            if (!meaningfulWord(thisWord)) {
                case4List = correction(thisWord);
                if (!case4List.isEmpty()) {
                    wrongForm = thisWord;
                    correctForm = case4List.get(0);
                    wordsInText.remove(thisWord);
                    wordsInText.add(i, correctForm);
                    beginIndex = mainText.indexOf(toLowerCaseWithI(thisWord));
                    endIndex = beginIndex + thisWord.length();
                    wrongWordResponses.add(new WrongWordResponse(thisWord, case4List, beginIndex, endIndex, wordToCapitalBoolean.get(thisWord)));
                }
            }
        }
        System.out.println("After Case 4: " + wordsInText);

        // Case 5: Bruteforce and add spaces to words which still dont have a correction
        for (int i = 0; i < wordsInText.size(); i++) {
            HashMap<Double, String> probToWord = new HashMap<>();
            ArrayList<Double> probabilityList = new ArrayList<>();
            ArrayList<String> case5List = new ArrayList<>();
            String firstCorrect = "";
            String secondCorect = "";
            correctForm = "";
            thisWord = wordsInText.get(i);
            if (!meaningfulWord(thisWord)) {
                HashSet<Pair> splits = new HashSet<>();
                for (int j = 0; j <= thisWord.length(); j++) {
                    splits.add(new Pair(thisWord.substring(0, j), thisWord.substring(j, thisWord.length())));
                }
                for (Pair pair : splits) {
                    firstCorrect = correctionOneDistanceMax(pair.first);
                    secondCorect = correctionOneDistanceMax(pair.second);
                    if (meaningfulWord(firstCorrect) && meaningfulWord(secondCorect)) {
                        double probabilitySum = dict.probability(firstCorrect) + dict.probability(secondCorect); 
                        correctForm = firstCorrect + " " + secondCorect;
                        probToWord.put(probabilitySum, correctForm);
                    }
                }
                for (Double d : probToWord.keySet()) {
                    probabilityList.add(d);
                }
                Collections.sort(probabilityList, Collections.reverseOrder());
                for (int j = 0; j < probabilityList.size(); j++) {
                    case5List.add(probToWord.get(probabilityList.get(j)));
                    if (j == 2) {
                        break;
                    }
                }
                if (!probabilityList.isEmpty()) {
                    System.out.println(toLowerCaseWithI(thisWord));
                    beginIndex = mainText.indexOf(toLowerCaseWithI(thisWord));
                    endIndex = beginIndex + thisWord.length();
                    wrongForm = thisWord;
                    correctForm = case5List.get(0);
                    wordsInText.remove(thisWord);
                    wrongWordResponses.add(new WrongWordResponse(thisWord, case5List, beginIndex, endIndex, wordToCapitalBoolean.get(thisWord)));
                }
            } 
        }
        System.out.println("After Case 5: " + wordsInText);

        // Lastly, flag each word that still can't be found in the dictionary
        for (int i = 0; i < wordsInText.size(); i++) {
            thisWord = wordsInText.get(i);
            if (!meaningfulWord(thisWord)) {
                beginIndex = mainText.indexOf(toLowerCaseWithI(thisWord));
                endIndex = beginIndex + thisWord.length();  
                wrongWordResponses.add(new WrongWordResponse(thisWord, null, beginIndex, endIndex, false));
            }
        }

        return wrongWordResponses;

    }

    /**
     * Turns the given text into lowercase characters. This method is useful because toLowerCase() method turned uppercase I to lowercase ı which broke the program.
     * @param text
     * @return lowercase form of the given text
     */
    private String toLowerCaseWithI(String text) {
        StringBuilder sb = new StringBuilder(text);

        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) >= 'A' && sb.charAt(i) <= 'Z') {
                if (sb.charAt(i) == 'I') {
                    sb.replace(i, i+1, "i");
                }
                else {
                    sb.replace(i, i+1, Character.toLowerCase(sb.charAt(i)) + "");
                }
            }
        }
        return sb.toString();
    }

    /**
     * Checks if the given two words are both meaningful and they are meaningful when they are put together i.e. soft ware.
     * @param word1
     * @param word2
     * @return true if both are meaningful and are meaningful when they are put together
     */
    public boolean bothMeaningfulWithSpace(String word1, String word2) {
        return meaningfulWord(word1) && meaningfulWord(word2) && meaningfulWord(word1 + word2);
    }

    /**
     * Checks if given two words are both meaningless. Useful for compound words.
     * @param word1
     * @param word2
     * @return true if neither meaningful
     */
    public boolean neitherMeaningful(String word1, String word2) {
        return !meaningfulWord(word1) && !meaningfulWord(word2);
    }

    /**
     * Given a set of words, finds the meaningful words among them.
     * @param words set of words
     * @return the meaningful set of words
     */
    private HashSet<String> meaningfulWordsInSet(HashSet<String> words) {
        HashSet<String> meaningfulWords = new HashSet<>();
        for (String word : words) {
            if (meaningfulWord(word)) {
                meaningfulWords.add(word);
            }
        }
        return meaningfulWords;
    }

    /**
     * Checks if the given word exists in the dictionary.
     * @param word
     * @return true if meaningful
     */
    private boolean meaningfulWord(String word) {
        return dict.existsInDict(word);
    }

    /**
     * Turns the word with space into two seperate Strings.
     * @param wordWithSpace
     * @return a Pair object containing two seperate words
     */
    public Pair toTwoDifferentWords(String wordWithSpace) {
        Pair pair = new Pair();
        int pointer = 0;

        for (int i = 0; i < wordWithSpace.length(); i++) {
            if (wordWithSpace.charAt(i) == ' ')  {
                pair.first = wordWithSpace.substring(pointer, i);
                pointer = i + 1;
            }
        }
        pair.second = wordWithSpace.substring(pointer, wordWithSpace.length());
        return pair;
    }
    
    /**
     * A class to hold a pair of strings. Useful for the compound words.
     */
    public static class Pair {
        String first;
        String second;

        public Pair(String first, String second) {
            this.first = first;
            this.second = second;
        }
        public Pair() {

        }

        @Override
        public String toString() {
            return first + " " + second;
        }
    }
}