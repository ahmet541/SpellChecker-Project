package View.StyleListener;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;


/**
 * This class creates methods in order to arrange text's font and size
 */ 
public class FontAndSizeAction extends StyledEditorKit.StyledTextAction 
{
    // Constants
    private static final long serialVersionUID = 584531387732416339L;

    // Properties
    private String family;
    private float fontSize;
    private JDialog formatText;
    private boolean accept;
    private JComboBox<String> fontFamilyChooser;
    private JComboBox<Float> fontSizeChooser;

    /**
     * Constructor for FontAndSizeAction and uses super class's constructor
     */ 
    public FontAndSizeAction() 
    {
        super("Font and Size");
        accept = false;
    }

    @Override
    /**
     * Represent the text "Font and Size"
     */ 
    public String toString() {
        return "Font and Size";
    }

    /**
     * Action performed method
     * @param event (ActionEvent)
     */ 
    public void actionPerformed(ActionEvent event) {
        // create new JTextPane editor
        JTextPane editor = (JTextPane) getEditor(event);
        
        int p0 = editor.getSelectionStart(); // return the the selected text's start position
        StyledDocument doc = getStyledDocument(editor); // return the documnet associated with an editor pane
        Element paragraph = doc.getCharacterElement(p0); // get the element that represents the char that is at the given offset within the document 
        AttributeSet as = paragraph.getAttributes(); // return Attribute value as a string

        family = StyleConstants.getFontFamily(as); // get font family
        fontSize = StyleConstants.getFontSize(as); // get font size

        // form the texts
        formatText = new JDialog(new JFrame(), "Font and Size", true);
        formatText.getContentPane().setLayout(new BorderLayout());

        // create a chooser panel
        JPanel choosers = new JPanel();
        choosers.setLayout(new GridLayout(2, 1));

        // create panel for fonts
        JPanel fontFamilyPanel = new JPanel();
        fontFamilyPanel.add(new JLabel("Font"));

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = ge.getAvailableFontFamilyNames(); // gather fonts in a string array

        // create font chooser
        fontFamilyChooser = new JComboBox<String>();
        for (int i = 0; i < fontNames.length; i++) 
        {
            fontFamilyChooser.addItem(fontNames[i]);
        }
        fontFamilyChooser.setSelectedItem(family); // set selected font
        fontFamilyPanel.add(fontFamilyChooser); 
        choosers.add(fontFamilyPanel);

        // create panel for size 
        JPanel fontSizePanel = new JPanel();
        fontSizePanel.add(new JLabel("Size")); // put JLabel which contains "Size"
        fontSizeChooser = new JComboBox<Float>(); // create size chooser
        fontSizeChooser.setEditable(true); // make it editable
        fontSizeChooser.addItem(Float.valueOf(4));
        fontSizeChooser.addItem(Float.valueOf(8));
        fontSizeChooser.addItem(Float.valueOf(12));
        fontSizeChooser.addItem(Float.valueOf(16));
        fontSizeChooser.addItem(Float.valueOf(20));
        fontSizeChooser.addItem(Float.valueOf(24));
        fontSizeChooser.setSelectedItem(Float.valueOf(fontSize));
        fontSizePanel.add(fontSizeChooser);
        choosers.add(fontSizePanel);

        // create "OK" button
        JButton ok = new JButton("OK");
        
        // if "OK" button is clicked implement selected size and font
        ok.addActionListener(new ActionListener() {
            // action performed for font and size
            public void actionPerformed(ActionEvent ae) {
                accept = true;
                formatText.dispose();
                family = (String) fontFamilyChooser.getSelectedItem();
                fontSize = Float.parseFloat(fontSizeChooser.getSelectedItem().toString());
            }
        });

        // create "Cancel" button
        JButton cancel = new JButton("Cancel");
        
        // if "Cancel" button is clicked nothing changes
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) 
            {
                formatText.dispose();
            }
        });

        // add buttons to panel
        JPanel buttons = new JPanel();
        buttons.add(ok);
        buttons.add(cancel);
        
        // arrange the button's places
        formatText.getContentPane().add(choosers, BorderLayout.CENTER);
        formatText.getContentPane().add(buttons, BorderLayout.SOUTH);
        formatText.pack();
        formatText.setVisible(true); // make it visible 

        MutableAttributeSet attr = null;
        if (editor != null && accept) 
        {
            attr = new SimpleAttributeSet();
            StyleConstants.setFontFamily(attr, family);
            StyleConstants.setFontSize(attr, (int) fontSize);
            setCharacterAttributes(editor, attr, false);
        }
    }
} // end class FontAndSizeAction