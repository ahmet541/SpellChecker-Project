package View.StyleListener;

import java.awt.event.ActionEvent;
import javax.swing.JEditorPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;

public class BoldAction extends StyledEditorKit.StyledTextAction 
{
    // Constants
    private static final long serialVersionUID = 9174670038684056758L;

    /**
     * Constructor for BoldAction and uses super class's constructor
     */ 
    public BoldAction() 
    {
        super("font-bold");
    }

    /**
     * Represent name of bold button
     */ 
    public String toString() {
        return "Bold";
    }
    
    /**
     * This method makes the text italic when bold action is performed
     * @param event (ActionEvent)
     */ 
    public void actionPerformed(ActionEvent event) {    
        JEditorPane editor = getEditor(event);
        if (editor != null) 
        {   
            StyledEditorKit kit = getStyledEditorKit(editor);
            MutableAttributeSet attr = kit.getInputAttributes();
            boolean bold = (StyleConstants.isBold(attr)) ? false : true; // look at whether style is bold or not
            SimpleAttributeSet sas = new SimpleAttributeSet();
            StyleConstants.setBold(sas, bold); // set style to bold
            setCharacterAttributes(editor, sas, false);
        }
    }    
} // end class BoldAction
