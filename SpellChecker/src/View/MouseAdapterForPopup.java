package View;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.text.Utilities;
import java.awt.GridLayout;
import SpellCorrector.*;
import MyList.*;

/**
 * Popup creator class
 * @author Ahmet Sahin
 * @version 25.04.2021
*/ 
public class MouseAdapterForPopup extends MouseAdapter
{
    // Properties
    private JTextPane textPane;
    private int start;
    private int end;
    private int indexInArray;
    private String wrongForm;
    private ArrayList<String> correctForms;
    private ArrayList<WrongWordResponse> wrongWordList;
    private MyList myList;
    private boolean pendingPopUp;
    private StyledDocument doc;
    private Popup popup;
    
    // Constructors
    
    /**
     * Construcure of class that creates popup if word is wrong and implements MouseAdapter
     * @param pane JTextPane that user write
     * @param wrongWords list of wrong words objects of type WrongWordResponse
     * @param list myList that storage added words
    */ 
    public MouseAdapterForPopup( JTextPane pane, ArrayList<WrongWordResponse> wrongWords, MyList list) throws IOException {
        pendingPopUp = false; //Indicates whether we have already a popup popped up...
        this.textPane = pane;
        wrongWordList = wrongWords;
        start = -1;
        end = -1;
        correctForms= new ArrayList<String>();
        myList = list;
        wrongForm = "";
        System.out.println(wrongWordList);
    }
    
    // Methods   
       
    /**
     * Method that checks whether clicked word is wrong or not
     * @return true if clicked word is wrong , or return false
    */ 
    private boolean isWrongWord() {
        for ( int i = 0; i < wrongWordList.size(); i++ ) {
            WrongWordResponse word = wrongWordList.get(i);
            if ( start >= word.getBeginIndex() && end <= word.getEndIndex() ) {
                start = word.getBeginIndex();
                end = word.getEndIndex();
                correctForms = word.getPossibleCorrectForms();
                indexInArray = i;
                wrongForm = word.getWrongForm();
                return true;
            }
        }
        return false;
    }
    
    /**
     * Method that creates popUp
     * if user clicks 1 times then it shows popup for wrong words
     * if user clicks 2 times then it shows pop up for adding words to myList
     * @param mevt mouse event 
    */ 
    private void pop(final MouseEvent mevt) {
        if ( SwingUtilities.isLeftMouseButton(mevt) ) {
            if ( mevt.getClickCount() == 1 ) {
                popForWrongWords(mevt);
            }
            else if ( mevt.getClickCount() == 2 ) {
                popForAddingToList(mevt);
            }
        }  
    }
    
    /**
     * Methodd that shows popup by pop method if there is no current open popup
     * @param mevt mouse event
    */ 
    private void maybePop(final MouseEvent mevt) {
        if (mevt.getButton() == MouseEvent.BUTTON1) {
            if (pendingPopUp)
                System.err.println("A popup is already popped. Close it to pop a new one.");
            else
                pop(mevt);
        }
    }
    
    @Override
    /**
     * Override method that checks whether mouse clicked or not
     * @param mevt mouse event
    */ 
    public void mouseClicked(final MouseEvent mevt) {
        maybePop(mevt);
    }
    
    @Override
    /**
     * Override method that checks whether mouse pressed or not
     * @param mevt mouse event
    */ 
    public void mousePressed(final MouseEvent mevt) {
        maybePop(mevt);
    }
    
    @Override
    /**
     * Override method that checks whether mouse released or not
     * @param mevt mouse event
    */ 
    public void mouseReleased(final MouseEvent mevt) {
        maybePop(mevt);
    }
    
    /**
     * Method that shows popup for adding word to list
     * Adds its componenets to popUpPanel
     * @param mevt mouse event
    */ 
    private void popForAddingToList( MouseEvent mevt) {
        
        try {
            doc = textPane.getStyledDocument();

            //Find out the location of the document the user clicked on:
            int offset = textPane.viewToModel2D(mevt.getPoint());
            if ( offset != 0 && !(offset >= textPane.getText().length() - 1) ) {
                
                //Find out what word is in the position of the document the user clicked on:
                start = Utilities.getWordStart(textPane, offset);
                end = Utilities.getWordEnd(textPane, offset);

                //Set the selection to be that word:
                textPane.setSelectionStart(start);
                textPane.setSelectionEnd(end);
                
                //Get the selected word:
                String word = doc.getText(start, end - start);
                                
                if (  !Character.isWhitespace(word.charAt(0))) {
                
                    //Create the contents of the popup:   
                    final JPanel popupPanel = new JPanel();
                    
                    //Add addbutton:
                    final JButton addButton = new JButton("Add To MyList");
                    popupPanel.add(addButton);
                    
                    final JButton cancel = new JButton("Cancel");
                    popupPanel.add(cancel);

                    //Create the popup itself:
                    popup = PopupFactory.getSharedInstance().getPopup(textPane, popupPanel, mevt.getXOnScreen(), mevt.getYOnScreen());

                    //Hook action listenere to the word and cancel buttons:
                    addButton.addActionListener(e -> {
                        try {
                            //Add the selected word to myList:
                            myList.addWord( word);
                        }
                        catch (final RuntimeException | IOException x) {

                        }
                        popup.hide();
                        pendingPopUp = false;
                        
                    });

                    //Click cancel to deselect selected word and close popup:
                    cancel.addActionListener(e -> {
                        popup.hide();
                        textPane.setSelectionStart(offset);
                        textPane.setSelectionEnd(offset);
                        pendingPopUp = false;
                    });
                    
                    pendingPopUp = true;
                    popup.show();                    
                }
            }
        }
        catch (final BadLocationException | RuntimeException x) {
                        
        }
    }
    
    /**
     * Method that shows popup for wrong words
     * Adds its componenets to popUpPanel
     * @param mevt mouse event
    */ 
    private void popForWrongWords( MouseEvent mevt) {
     
        try {
            doc = textPane.getStyledDocument();

            //Find out the location of the document the user clicked on:
            int offset = textPane.viewToModel2D(mevt.getPoint());
            if ( offset != 0 && !(offset >= textPane.getText().length() - 1) ) {
                //Find out what word is in the position of the document the user clicked on:
                start = Utilities.getWordStart(textPane, offset);
                end = Utilities.getWordEnd(textPane, offset) - 1;
                
                //Get the selected word:
                if ( isWrongWord() ) {
                    //Set the selection to be that word:
                    textPane.setSelectionStart(start);
                    textPane.setSelectionEnd(end);                                    
                    
                    //Create the contents of the popup: 
                      
                    // Create popupPanel
                    final JPanel popupPanel = new JPanel();
                    popupPanel.setLayout(new GridLayout(2, 0, 0, 0));
                    
                    //Add possible correct words:
                    // Create first row of popUpPanel
                    JPanel popupComponentPanel1 = new JPanel();
                    popupPanel.add(popupComponentPanel1);
                    popupComponentPanel1.setLayout(new GridLayout(1, 0, 0, 0));
                    
                    JLabel labelForPossibleAnswers = new JLabel();
                    popupComponentPanel1.add(labelForPossibleAnswers);
                    
                    JComboBox<String> possibleComboBox = new JComboBox<>();
                    if ( correctForms != null ) {
                        for ( int i = 0; i < correctForms.size(); i++ ) { // Add possbile words to combobox
                            possibleComboBox.addItem( correctForms.get(i));
                        }
                        labelForPossibleAnswers.setText("Possible answers are:");
                    }   
                    else {
                        labelForPossibleAnswers.setText("There is no possible answer");
                    }
                    
                    popupPanel.add(possibleComboBox);

                    // Create second row of popUpPanel
                    JPanel popupComponentPanel2 = new JPanel();
                    popupPanel.add(popupComponentPanel2);
                    
                    final JButton correct = new JButton("Correct");
                    popupPanel.add(correct);
                    
                    final JButton ignore = new JButton("Ignore");
                    popupPanel.add(ignore);
                    
                    final JButton cancel = new JButton("Cancel");
                    popupPanel.add(cancel);
                    
                    //Create the popup itself:
                    popup = PopupFactory.getSharedInstance().getPopup(textPane, popupPanel, mevt.getXOnScreen(), mevt.getYOnScreen());
                    
                    //Hook action listenere to the word and cancel buttons:
                    if ( correctForms != null ) {
                        correct.addActionListener(e -> {
                        correctButAction(possibleComboBox);
                        popup.hide();
                        pendingPopUp = false;
                        
                        });
                    }

                    // Click ignore
                    ignore.addActionListener(e -> {
                        
                        ignoreButAction();
                        // Close popup
                        popup.hide();
                        pendingPopUp = false;
                    });
                    
                    //Click cancel to deselect selected word and close popup:
                    cancel.addActionListener(e -> {
                        popup.hide();
                        textPane.setSelectionStart(offset);
                        textPane.setSelectionEnd(offset);
                        pendingPopUp = false;
                    });

                    pendingPopUp = true;
                    popup.show();                    
                }
            }
        }
        catch (final BadLocationException | RuntimeException x) {
        }
    }
    
    /**
	 * Method that updates begin and end index of objects type WrongWordResponse
	 * Since when we correct words and if its size increase or decrease it affects other words
	 * @param change it is change of size of word
	 * @param start is begin index of word
	*/ 
    private void updateIndexOfWrongWords( int change) {
        for ( int i = 0; i < wrongWordList.size(); i++ ) {
            WrongWordResponse word = wrongWordList.get(i);
            
            if ( word.getBeginIndex() >= start  ) {
                word.setBeginIndex(word.getBeginIndex() + change);
                word.setEndIndex(word.getEndIndex() + change);
            }
        }
    }
    
    /**
     * Method that implemets action of correct button
     * @param possibleComboBox combobox that includes possible answers
    */ 
    private void correctButAction(JComboBox<String> possibleComboBox) {
        try {
            //Get the text of that button (it is going to be the new word):
            String newWord = (String) possibleComboBox.getSelectedItem();
            
            //Replace the old text with the new one:
            doc.remove(start, end - start);
            doc.insertString(start, newWord, null);
            
            // Delete WrongWordResponse object from arrayList
            wrongWordList.remove( indexInArray);
            updateIndexOfWrongWords(newWord.length() - wrongForm.length()); // We also should update ındex of other wrong words our text can grow longer so their index change
            
            //Prepare caret position, so the user can keep on writing:
            textPane.setCaretPosition(start + newWord.length());
        }
        catch (final BadLocationException | RuntimeException x) {
        }   
    }
    
    /**
     * Method that implemets action of ignore button
    */ 
    private void ignoreButAction() {
        // Get wrong form and replace it with current text to delete highlight
        String newWord = wrongForm;
                        
        //Replace the old text with the new one:
        try {
            doc.remove(start, end - start);
            doc.insertString(start, newWord, null);
        } catch (BadLocationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        // Delete WrongWordResponse object from arrayList
        wrongWordList.remove( indexInArray);
        
        //Prepare caret position, so the user can keep on writing:
        textPane.setCaretPosition(start + newWord.length());
           
    }
}
