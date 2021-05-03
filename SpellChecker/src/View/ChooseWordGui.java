package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class ChooseWordGui {

    private JFrame frame;
	private String difficulty;
	private String difficultyPage;
    /**
	 * Create the application.
	 * @throws IOException
	 */
	public ChooseWordGui( String difficulty, JFrame frame ) throws IOException {
		this.frame = frame;
        frame.getContentPane().setVisible(false);
        frame.getContentPane().removeAll();
		this.difficulty = difficulty;
		difficultyPage = this.difficulty;
		if ( this.difficulty.equals("Easy")) {
			this.difficulty = "easy.txt";
		}
		if ( this.difficulty.equals("Medium")) {
			this.difficulty = "medium.txt";
		}
		if ( this.difficulty.equals("Hard")) {
			this.difficulty = "hard.txt";
		}
		initialize();
        frame.getContentPane().setVisible(true);
	}
    
    /**
	 * Initialize the contents of the frame.
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		frame.setTitle("ChooseWordForGame");
        DefaultListModel<String> wordList = new DefaultListModel<>();
        File txtFile = new File( "src/"  + difficulty );
        Scanner inputFile = new Scanner (txtFile);
        while ( inputFile.hasNext() ) {
            wordList.addElement(inputFile.next());
        }
        inputFile.close();        

        frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        JList<String> list_1 = new JList<>(wordList);
		list_1.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		scrollPane.setViewportView(list_1);
		
		// South Panel
		JPanel southPanel = new JPanel();
		frame.getContentPane().add(southPanel, BorderLayout.SOUTH);
		
		JButton play = new JButton("Play");
		play.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String) list_1.getSelectedValue();
				new GameGui(s, frame, difficultyPage);
			}
		});
		southPanel.setLayout(new FlowLayout());
		southPanel.add(play);
		
		
		// North panel
		JPanel northPanel = new JPanel();
		frame.getContentPane().add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new GridLayout(0, 3, 0, 0));

		JPanel nortWestAbsolutePanel = new JPanel();
		northPanel.add(nortWestAbsolutePanel);
		nortWestAbsolutePanel.setLayout(null);
		nortWestAbsolutePanel.setBounds(0, 0, 15, 33);
		nortWestAbsolutePanel.setPreferredSize( new Dimension( 0, 33 ) );
		
		JButton back = new JButton("Back");
		back.setBounds(0, 0, 89, 33);
		nortWestAbsolutePanel.add(back);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ChooseDifficultyGui( frame);
			}
		});
		
		JLabel title = new JLabel("Difficulty: " + difficultyPage.toUpperCase());
		title.setHorizontalAlignment(SwingConstants.CENTER);
		northPanel.add(title);
	   
	}
	

	
}
 