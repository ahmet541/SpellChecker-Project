package View;

import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JMenuBar;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.JTextPane;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.text.StyledDocument;
import java.awt.Component;
import SpellCorrector.*;
import MyList.*;

/**
 * GUI for freeWriting page
 * @author Ahmet Sahin
 * @version 25.04.2021
*/ 
public class freeWritingMode {
	
	// Constants
	final private int WORD_LIMIT = 450;
	
	// Properties
	private JFrame frame;
	private JTextPane textPane;
	private MyList myList;
	private JLabel wordCountLabel;
	private JScrollPane scrollPane;
	private ArrayList<WrongWordResponse> wrongWords;

	// Constructors
	
	/**
	 * Create the application.
	 * @param frame main frame
	 * @throws IOException
	 */
	public freeWritingMode(JFrame frame) throws IOException {
		this.frame = frame;
        frame.getContentPane().setVisible(false);
        frame.getContentPane().removeAll();
		myList = new MyList();
		wordCountLabel = new JLabel("0 / " + WORD_LIMIT);
		initialize();
		frame.getContentPane().setVisible(true);
	}
	
	// Methods
	
	/**
	 * Initialize the contents of the frame.
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		frame.setTitle("FreeWriting");
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		addNorthPanel();
		addScrollPaneToCenter();
		addSouthPanel();
	}
	
	/**
	 * Adding north panel with its components
	*/ 
	private void addNorthPanel() {
		// Firstly create north panel that includes more panels
		JPanel nPanel = new JPanel();
		frame.getContentPane().add(nPanel, BorderLayout.NORTH);
		nPanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		// Create north west panel and add Back button to it
		JPanel nWestPanel = new JPanel();
		nPanel.add(nWestPanel);
		nWestPanel.setLayout(null);
		nWestPanel.setBounds(0, 0, 15, 33);

		JButton backButton = new JButton("Back");
        backButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				new StartSpellCheckerGui(frame);
			} 
		});
		backButton.setBounds(0, 0, 89, 33);
		nWestPanel.add(backButton);
		
		// Create north center panel and add Check button to it
		JPanel nCenterPanel = new JPanel();
		nPanel.add(nCenterPanel);
		
		JButton checkButton = new JButton("Check");
		checkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkButAction();
			}
		});
		nCenterPanel.add(checkButton);
		
		// Create north east Panel and add JMenuBar which is style manager 
		JPanel nEastPanel = new JPanel();
		nPanel.add(nEastPanel);
		nEastPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JMenuBar style = new JMenuBar();
		StyleButtonCreator newBut = new StyleButtonCreator();
		style = newBut.getStyleMenu();
		style.setAlignmentX(Component.CENTER_ALIGNMENT);
		nEastPanel.add(style);
	}
	
	/**
	 * Add scrol panel with JTextPane to center of frame
	 * Also add key listener to textPane
	 * @param
	 * @return
	*/ 
	private void addScrollPaneToCenter() {
		scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		textPane = new JTextPane();
		scrollPane.setColumnHeaderView(wordCountLabel);
		scrollPane.setViewportView(textPane);
		
		textPane.addKeyListener(new java.awt.event.KeyAdapter() 
		{
			public void keyTyped(java.awt.event.KeyEvent evt) 
			{
				keyTypedAction(evt);
			}
			public void keyReleased(java.awt.event.KeyEvent evt) {
				keyReleasedAction(evt);
			}
		});
	}
	
	/**
	 * Adding south panel to frame and add its componenets to it
	 * Also add action listeer to correctAllBut and ignoreAllBut
	*/ 
	private void addSouthPanel() {
		JPanel sPanel = new JPanel();
		frame.getContentPane().add(sPanel, BorderLayout.SOUTH);
		
		JButton correctAllBut = new JButton("Correct all");
		correctAllBut.addActionListener(e -> {
			try {
				correctAllAction();
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		});
		sPanel.add(correctAllBut);
		
		JButton ignoreAllBut = new JButton("Ignore all");
		ignoreAllBut.addActionListener(e -> {
			ignoreAllAction();		
		});
		sPanel.add(ignoreAllBut);
	}        
	
	/**
	 * Method that implements Action of checkAllBut
	*/ 
	private void checkButAction() {
		
		try {
			String text = textPane.getText();
			frame.getContentPane().remove(scrollPane);
			addScrollPaneToCenter();
			textPane.setText( text);
			frame.revalidate(); // in- and validate in one !! 
			SpellCorrector corrector = new SpellCorrector();
			textPane.getDocument().putProperty(DefaultEditorKit.EndOfLineStringProperty, "\n");
			wrongWords = corrector.correctAll( textPane.getText() );
			textPane.addMouseListener( new MouseAdapterForPopup( textPane, wrongWords, myList)); 
			new Highlight( textPane, wrongWords);

		} catch (IOException | BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}
	
	/**
	 * keyTypedAction method that if user exceeds word limits change wordCountLabel to red
	 * @param evt key event
	*/ 
	private void keyTypedAction(java.awt.event.KeyEvent evt) {
		if( textPane.getText().length() >= WORD_LIMIT )
		{
			evt.consume();
			wordCountLabel.setForeground(Color.RED);
		}
	}
	
	/**
	 * keyTypedAction method that if user deletes some word after exceed limits change wordCountLabel to black
	 * @param evt keyevent
	*/ 
	private void keyReleasedAction(java.awt.event.KeyEvent evt ) {
		int count = textPane.getText().length();
				if ( count <= WORD_LIMIT ) {
					wordCountLabel.setForeground(Color.BLACK);	
				}
				wordCountLabel.setText( count + " / " + WORD_LIMIT);		
	}
	
	/**
	 * Method that implements action of ignoreAllBut
	*/ 
	private void ignoreAllAction() {
		while ( wrongWords.size() != 0  ) {
			WrongWordResponse word = wrongWords.get(0);
			int start = word.getBeginIndex();
			int end = word.getEndIndex();
			String wrongForm = word.getWrongForm();
			StyledDocument doc = textPane.getStyledDocument();

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
			wrongWords.remove( 0);
			
			//Prepare caret position, so the user can keep on writing:
			textPane.setCaretPosition(start + newWord.length());
		}
	}	
	
	/**
	 * Method that implements action of correctAllBut
	*/ 
	private void correctAllAction() throws BadLocationException {

		while ( wrongWords != null && wrongWords.size() != 0 ) { // while there are some objects in wrongWords<>
			WrongWordResponse word = wrongWords.get(0);
			int start = word.getBeginIndex();
			int end = word.getEndIndex();
			ArrayList<String> correctForms = word.getPossibleCorrectForms();
			String wrongForm = word.getWrongForm();
			StyledDocument doc = textPane.getStyledDocument();
			
			 //Get the text of that button (it is going to be the new word):
			 String newWord = correctForms.get(0);
            
			 //Replace the old text with the new one:
			 doc.remove(start, end - start);
			 doc.insertString(start, newWord, null);
			 
			 // Delete WrongWordResponse object from arrayList
			 wrongWords.remove( 0);
			 updateIndexOfWrongWords(newWord.length() - wrongForm.length(), start); // We also should update ındex of other wrong words our text can grow longer so their index change
		}	
	}
	
	/**
	 * Method that updates begin and end index of objects type WrongWordResponse
	 * Since when we correct words and if its size increase or decrease it affects other words
	 * @param change it is change of size of word
	 * @param start is begin index of word
	*/ 
	private void updateIndexOfWrongWords( int change, int start) {
		
        for ( int i = 0; i < wrongWords.size(); i++ ) {
            WrongWordResponse word = wrongWords.get(i);
            
            if ( word.getBeginIndex() >= start  ) {
                word.setBeginIndex(word.getBeginIndex() + change);
                word.setEndIndex(word.getEndIndex() + change);
            }
        }
    }
							  
		
}


