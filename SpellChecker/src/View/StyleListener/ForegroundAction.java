package View.StyleListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;

public class ForegroundAction extends StyledEditorKit.StyledTextAction {
    // Constants
    private static final long serialVersionUID = 6384632651737400352L;

    // Properties
    private JColorChooser colorChooser;
    private JDialog dialog;
    private boolean noChange; // if there is a change ?
    private boolean cancelled; // if it is cancelled ?

    /**
     * Constructor for ForeGround and uses the super class's constructor
     */ 
    public ForegroundAction() 
    {
        super("foreground");
        colorChooser = new JColorChooser();
        dialog = new JDialog();
        noChange = false;
        cancelled = false;
    }
    
    // program code
    
    /**
     * Action performed method
     * @param event (ActionEvent)
     */ 
    public void actionPerformed(ActionEvent event) {
        // create new JTextPane editor
        JTextPane editor = (JTextPane) getEditor(event);

        
        if (editor == null) 
        {
            // if editor is null show error message
            JOptionPane.showMessageDialog(null,
            "You need to select the editor pane before changing the color.", "Error!",
            JOptionPane.ERROR_MESSAGE);
        }
        
        int p0 = editor.getSelectionStart(); // return the the selected text's start position
        StyledDocument doc = getStyledDocument(editor); // return the documnet associated with an editor pane
        Element paragraph = doc.getCharacterElement(p0); // get the element that represents the char that is at the given offset within the document 
        AttributeSet as = paragraph.getAttributes(); // return Attribute value as a string
        fg = StyleConstants.getForeground(as); // get the foreground color of attribute
        
        
        // set color to foreground color
        colorChooser.setColor(fg);

        // create accept button
        JButton accept = new JButton("OK");
        accept.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) 
            {
                fg = colorChooser.getColor();
                dialog.dispose();
            }
        });

        // create cancel button
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) 
            {
                cancelled = true;
                dialog.dispose();
            }
        });

        // create non button
        JButton none = new JButton("None");
        none.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                noChange = true; // if non button is clicked there is no change
                dialog.dispose();
            }
        });

        // add these buttons to panel
        JPanel buttons = new JPanel();
        buttons.add(accept);
        buttons.add(none);
        buttons.add(cancel);

        // arrange the places oof buttons
        dialog.getContentPane().setLayout(new BorderLayout());
        dialog.getContentPane().add(colorChooser, BorderLayout.CENTER);
        dialog.getContentPane().add(buttons, BorderLayout.SOUTH);
        dialog.setModal(true);
        dialog.pack();
        dialog.setVisible(true);

        // if it is cancelled 
        if (!cancelled) 
        {
            // make attribution sets null
            MutableAttributeSet attr = null;
            if (editor != null) 
            {
                if (fg != null && !noChange) // if foreground and there is no change there is no change in char attributes
                {
                    attr = new SimpleAttributeSet();
                    StyleConstants.setForeground(attr, fg);
                    setCharacterAttributes(editor, attr, false);
                }
            }
        }
        // there is no change and it is not cancelled either
        noChange = false;
        cancelled = false;
    }

    private Color fg;
} // end class ForeGroundAction
