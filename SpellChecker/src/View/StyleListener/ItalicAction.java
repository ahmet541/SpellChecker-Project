package View.StyleListener;

import java.awt.event.ActionEvent;
import javax.swing.JEditorPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;

/**
 * This class makes the text italic when italic button is clicked
 */ 
public class ItalicAction extends StyledEditorKit.StyledTextAction 
{   
    // Constants
    private static final long serialVersionUID = -1428340091100055456L;

    // Constructors
    /**
     * Constructor for ItalicAction and use super class's constructor
     */ 
    public ItalicAction() 
    {
        super("font-italic");
    }

    /**
     * Represent name of italic button
     */ 
    @Override
    public String toString() {
        return "Italic";
    }
    
    /**
     * This method makes the text italic when italic action is performed
     * @param event (ActionEvent)
     */ 
    public void actionPerformed(ActionEvent event) {
        JEditorPane editor = getEditor(event);
        if (editor != null) 
        {
            StyledEditorKit kit = getStyledEditorKit(editor);
            MutableAttributeSet attr = kit.getInputAttributes();
            boolean italic = (StyleConstants.isItalic(attr)) ? false : true; // look at whether style is italic or not
            SimpleAttributeSet sas = new SimpleAttributeSet();
            StyleConstants.setItalic(sas, italic); // set style to italic
            setCharacterAttributes(editor, sas, false);
        }
    }
} // end class ItalicAction
