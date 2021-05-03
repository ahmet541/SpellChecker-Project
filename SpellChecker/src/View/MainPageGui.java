package View;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

/**
 * GUI for MainPage 
 * @author Ahmet Sahin
 * @version 30.04.2021
*/ 
public class MainPageGui {

	private JFrame frame;
	private FileOperatorForTheme fileOperator;
	private String nameOfButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPageGui window = new MainPageGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application when start program
	 * @throws IOException
	 */
	public MainPageGui() throws IOException {
		fileOperator = new FileOperatorForTheme();
		nameOfButton =fileOperator.getMode();
        frame = new JFrame();
		frame.setBounds(100, 100, 700, 450);
		startingMode(nameOfButton);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initialize();
    }
	
	/**
	 * Create class content when class opened from another class. For example if someone click back button to open this class 
	 * @param frame main frame
	*/ 
    public MainPageGui(JFrame frame) throws IOException {
		fileOperator = new FileOperatorForTheme();
		nameOfButton =fileOperator.getMode();

        this.frame = frame;
        frame.getContentPane().setVisible(false);
        frame.getContentPane().removeAll();
        initialize();
        frame.getContentPane().setVisible(true);
    }

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		frame.setTitle("MainPage");
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		// Create panel and place it at center of border layout
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel.setPreferredSize(new Dimension(300, 200));
		
		// Create button and add it to panel
		JButton openSCBut = new JButton("Open Spell Checker");
		openSCBut.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                new StartSpellCheckerGui(frame);
			}
		});
        openSCBut.setPreferredSize(new Dimension(300, 200));
        openSCBut.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(openSCBut);
		
		// Create dummby panels
		JPanel dummy1 = new JPanel();
		frame.getContentPane().add(dummy1, BorderLayout.NORTH);
		dummy1.setPreferredSize(new Dimension(450, 75));

		JPanel dummy2 = new JPanel();
		dummy2.setLayout( new FlowLayout(FlowLayout.CENTER));
		JButton changeThemeBut = new JButton(nameOfButton);
		
		// Override action listener of changeTheme button
		changeThemeBut.addActionListener( new ActionListener() { // Change theme with click
            @Override 
			public void actionPerformed(ActionEvent e) {
				try {
					actionOfChangeTheme(changeThemeBut);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		dummy2.add(changeThemeBut);
		frame.getContentPane().add(dummy2, BorderLayout.SOUTH);
		dummy2.setPreferredSize(new Dimension(450, 75));
	}
	
	/**
	 * Method that arranges colors of "Nimbus" type of theme in UIManager
	*/ 
	private void changeTheme() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.put( "control", new Color( 128, 128, 128) );
		UIManager.put( "info", new Color(128,128,128) );
		UIManager.put( "nimbusBase", new Color( 18, 30, 49) );
		UIManager.put( "nimbusAlertYellow", new Color( 248, 187, 0) );
		UIManager.put( "nimbusDisabledText", new Color( 128, 128, 128) );
		UIManager.put( "nimbusFocus", new Color(115,164,209) );
		UIManager.put( "nimbusGreen", new Color(176,179,50) );
		UIManager.put( "nimbusInfoBlue", new Color( 66, 139, 221) );
		UIManager.put( "nimbusLightBackground", new Color( 18, 30, 49) );
		UIManager.put( "nimbusOrange", new Color(191,98,4) );
		UIManager.put( "nimbusRed", new Color(169,46,34) );
		UIManager.put( "nimbusSelectedText", new Color( 255, 255, 255) );
		UIManager.put( "nimbusSelectionBackground", new Color( 104, 93, 156) );
		UIManager.put( "text", new Color( 230, 230, 230) );
		
		  for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(info.getName())) {
				javax.swing.UIManager.setLookAndFeel(info.getClassName());
			}
		}
	}
	
	/**
	 * Method that implements action of changeTheme button
	 * @param changeTheme is button
	*/ 
	private void actionOfChangeTheme(JButton changeTheme) throws IOException {
		if ( changeTheme.getText().equals("Open Dark Mode")) {
			try {
				changeTheme.setText("Open Light Mode");
				fileOperator.clearList();
				fileOperator.writeOnFile("Open Light Mode");
				changeTheme();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if (changeTheme.getText().equals("Open Light Mode")) {
			try {
				changeTheme.setText("Open Dark Mode");
				fileOperator.clearList();
				fileOperator.writeOnFile("Open Dark Mode");
				
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		new MainPageGui(frame);
	}
	
	/**
	 * According to informatin written in text we start our frame with it
	 * @param mode is mode of theme
	 * If nameOfButton is "Open Dark Mode" then create normal frame, if it is "Open Light Mode" open dark frame 
	*/ 
	private void startingMode( String buttonName) throws IOException {
		if ( nameOfButton.equals("Open Dark Mode")) {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if (nameOfButton.equals("Open Light Mode")) {
			try {
				changeTheme();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}


}
