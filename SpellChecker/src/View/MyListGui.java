package View;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Dimension;
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
import MyList.*;

/**
 * GUI for MyList
 * @author Ahmet Sahin
 * @version 1.05.2021
*/ 
public class MyListGui {
	
	// Properties
	private JFrame frame;
	private JList<String> list_1;
	private JScrollPane scrollPane;
	private MyList l;
	private DefaultListModel<String> l1;
	
	// Constructors

	/**
	 * Create the application.
	 * @param frame main frame
	 * @throws IOException
	 */
	public MyListGui(JFrame frame) throws IOException {
		this.frame = frame;
        frame.getContentPane().setVisible(false);
        frame.getContentPane().removeAll();
        initialize();
        frame.getContentPane().setVisible(true);
	}
	
	// Methods
	
	/**
	 * Initialize the contents of the frame.
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		frame.setTitle("MyList");
		l = new MyList();
	    l1 = l.getMyList();
	    
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		list_1 = new JList<>(l1);
		list_1.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		scrollPane.setViewportView(list_1);
		
		// South Panel
		createSouthPanel();
		
		// North panel
		createNorthPanel();
	}
	
	private void createNorthPanel() {
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
        back.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				new StartSpellCheckerGui(frame);
			} 
        });
		
		JLabel title = new JLabel("MyList");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		northPanel.add(title);
		
		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {					
					String newWord = JOptionPane.showInputDialog(frame, "Write the word that you want to add.", null);
					if ( newWord != null )
					{
						l.addWord(newWord);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		northPanel.add(add);    	
	}
	
	private void createSouthPanel() {
		JPanel southPanel = new JPanel();
		frame.getContentPane().add(southPanel, BorderLayout.SOUTH);
		
		JButton play = new JButton("Play");
		play.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String) list_1.getSelectedValue();
				new GameGui(s, frame, "first");
			}
		});
		southPanel.setLayout(new GridLayout(0, 2, 0, 0));
		southPanel.add(play);
		
		JButton delete = new JButton("Delete");
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = list_1.getSelectedIndex();
				try {
					l.removeWord( selectedIndex);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		southPanel.add(delete);	
	}
}
