package View;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.JButton;

public class GameGui {
    // Properties
    private JFrame frame;
    private String chosenWord;
    private String answer;
    private JButton backButton;
    private JTextArea textArea;
    private JTextField answerTextField;
    private JButton checkButton;
    private JPanel inNorth;
    private JPanel innerCenter;
    private JPanel innerNorth;
    private JPanel inSouth;
    private JPanel eastPanel;
    private JPanel westPanel;
    private JPanel northPanel;
    private JPanel centerPanel;
    private String whichGame;
    
    /**
     * Creates new form MyListGameGUI
     */
    public GameGui (String chosenWord, JFrame frame, String whichGame) {
        this.frame = frame;
        frame.getContentPane().setVisible(false);
        frame.getContentPane().removeAll();
        initComponents();
        this.chosenWord = chosenWord;
        this.whichGame = whichGame;
        frame.getContentPane().setVisible(true);   
    }
    
    /**
     * Method that initializes components
     */
	public void initComponents() {
        frame.setTitle("Game");
		frame.setLayout(new BorderLayout());
		
		backButton = new JButton("Back");
        backButton.setBounds(0, 0, 89, 33);
        backButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
					backButtonActionPerformed(evt);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
        		
//		North Panel 
		northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(600, 150));
		northPanel.setLayout(null);
		northPanel.add(backButton);

		
//		Side Panels
		westPanel = new JPanel();
		westPanel.setPreferredSize(new Dimension(40, 400));
		
		eastPanel = new JPanel();
		eastPanel.setPreferredSize(new Dimension(40, 400));
		
		
//		Center Panel
		centerPanel = new JPanel();
		centerPanel.setBackground(Color.pink);
        centerPanel.setLayout(new BorderLayout());
		
		
//		Inner North
		innerNorth = new JPanel();
		innerNorth.setPreferredSize(new Dimension(520, 80));
		
		textArea = new JTextArea("Take a deep breath, think about the clouds\r\n"
                + "and try to write the word you choose correctly!\r\n");
        
        textArea.setFont(new Font("Arial", 1, 14));       
		innerNorth.add(textArea);
		
		centerPanel.add(innerNorth, BorderLayout.NORTH);
		
		
//		Inner Center
		innerCenter = new JPanel();
		innerCenter.setLayout(new BorderLayout());
		
		inNorth = new JPanel();
	    answerTextField = new JTextField();
        answerTextField.setPreferredSize(new Dimension(300, 30));
        answerTextField.setFont(new Font("Arial", 1, 14)); 
        inNorth.add(answerTextField);
		
		innerCenter.add(inNorth, BorderLayout.NORTH);
		
		inSouth = new JPanel();
		checkButton = new JButton("CHECK!");
        inSouth.add(checkButton);
        checkButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkButtonAction(evt);
            }
        });
        
		
		innerCenter.add(inSouth, BorderLayout.CENTER);
		
//		innerCenter.add(textField);
		
		centerPanel.add(innerCenter, BorderLayout.CENTER);
		
        inNorth.setBackground(new Color(204, 204, 255));
        innerCenter.setBackground(new Color(204, 204, 255));
        innerNorth.setBackground(new Color(204, 204, 255));
        inSouth.setBackground(new Color(204, 204, 255));
        eastPanel.setBackground(new Color(204, 204, 255));
        westPanel.setBackground(new Color(204, 204, 255));
        northPanel.setBackground(new Color(204, 204, 255));

		frame.add(northPanel, BorderLayout.NORTH);
		frame.add(eastPanel, BorderLayout.EAST);
		frame.add(westPanel, BorderLayout.WEST);
		frame.add(centerPanel, BorderLayout.CENTER);
    }
    
    private void checkButtonAction(java.awt.event.ActionEvent evt) {
        if(isRoundWon()){
            textArea.setText("CONGRATULATIONS!!!");
            textArea.setFont(new Font("Arial", 1, 20));
            inNorth.setBackground(new Color(8, 199, 145));
            innerCenter.setBackground(new Color(8, 199, 145));
            innerNorth.setBackground(new Color(8, 199, 145));
            inSouth.setBackground(new Color(8, 199, 145));
            eastPanel.setBackground(new Color(8, 199, 145));
            westPanel.setBackground(new Color(8, 199, 145));
            northPanel.setBackground(new Color(8, 199, 145));
        }
        else{
            textArea.setText("TRY AGAIN!!!");
            textArea.setFont(new Font("Arial", 1, 20));
            inNorth.setBackground(new Color(255, 99, 71));
            innerCenter.setBackground(new Color(255, 99, 71));
            innerNorth.setBackground(new Color(255, 99, 71));
            inSouth.setBackground(new Color(255, 99, 71));
            eastPanel.setBackground(new Color(255, 99, 71));
            westPanel.setBackground(new Color(255, 99, 71));
            northPanel.setBackground(new Color(255, 99, 71));
        }
        
    }
    
    /**
     * It chechks whether the game is won by the user.
     * @return true if the user win, otherwise false
     */
    private boolean isRoundWon(){
        answer = answerTextField.getText().toLowerCase();;
        if( answer.equals( chosenWord ) ) {
            return true;
        }
        
        return false;
    }
        
    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) throws IOException {                                           
        // TODO add your handling code here:
        if ( whichGame.equals("first")) {
            new MyListGui(frame);
        }
        else {
            new ChooseWordGui(whichGame, frame);
        }   
    }  
	
}