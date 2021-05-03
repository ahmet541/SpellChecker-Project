package View;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;

/**
 * GUI for StartSpellChecker
 * @author Ahmet Sahin
 * @version 1.05.2021
*/ 
public class StartSpellCheckerGui {
	
	// Properties
	private JFrame frame;

	// Constructors
	
	/**
	 * Create the application.
	 * @param frame main frame
	 */
	public StartSpellCheckerGui(JFrame frame) {
        this.frame = frame;
        frame.getContentPane().setVisible(false);
        frame.getContentPane().removeAll();
        initialize();
        frame.getContentPane().setVisible(true);

	}
	
	// Methods 
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame.setTitle("StartSpellChecker");
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		// Create panel with box layout and place it to center of frame
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// Create & replace buttons and its action listener
        JButton freeWM = new JButton("Free Writing Mode");
        freeWM.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				try {
					new freeWritingMode(frame);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} 
        });
		freeWM.setAlignmentX(Component.CENTER_ALIGNMENT);
		freeWM.setMaximumSize(new Dimension(200, 50));
		panel.add(freeWM);
		
		panel.add(Box.createRigidArea(new Dimension(450, 15)));

        JButton practiceGame = new JButton("PracticeGame");
        practiceGame.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				new ChooseDifficultyGui(frame);
			} 
        });
		practiceGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		practiceGame.setMaximumSize(new Dimension(200, 50));
		panel.add(practiceGame);
		
		panel.add(Box.createRigidArea(new Dimension(450, 15)));

        JButton myList = new JButton("My List");
        myList.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				try {
					new MyListGui(frame);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} 
        });
		myList.setAlignmentX(Component.CENTER_ALIGNMENT);
		myList.setMaximumSize(new Dimension(200, 50));
		panel.add(myList);
		
		JPanel dummy1 = new JPanel(); // Dummy panel to arrange frame
		frame.getContentPane().add(dummy1, BorderLayout.NORTH);
		dummy1.setPreferredSize(new Dimension(450, 50));
		dummy1.setLayout(null);
		
        JButton back = new JButton("Back");
        back.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				try {
					new MainPageGui(frame);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} 
        });
		back.setBounds(0, 0, 89, 33);
		dummy1.add(back);

		JPanel dummy2 = new JPanel(); // Dummy panel to arrange frame
		frame.getContentPane().add(dummy2, BorderLayout.SOUTH);
		dummy2.setPreferredSize(new Dimension(450, 50));
	}

}
