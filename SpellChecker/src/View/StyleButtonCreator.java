package View;

import View.StyleListener.*;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**
 * This class creates style editor for the text has been entered by the user (bold, italic, etc.)
 * @version 29.04.2021
 */ 

public class StyleButtonCreator 
{
    // Properties
    private JMenuBar menu;
    
    // Constructors
    
    /**
     * Construct a menu bar
     */ 
    public StyleButtonCreator() 
    {
        menu = new JMenuBar();
        initialize();
    } 
    
    // Methods
    
    /**
     * This method initializes the window that we first see
     */ 
    private void initialize() {
        
        // create style button in the menu
        JMenu styleMenu = new JMenu();
        styleMenu.setText("Style");

        // create bold button in the style menu and make the text bold when it is clicked
        Action boldAction = new BoldAction();
        boldAction.putValue(Action.NAME, "Bold");
        styleMenu.add(boldAction);

        // create italic button in the style menu and make the text italic when it is clicked
        Action italicAction = new ItalicAction();
        italicAction.putValue(Action.NAME, "Italic");
        styleMenu.add(italicAction);

        // create color menu in the style menu
        Action foregroundAction = new ForegroundAction();
        foregroundAction.putValue(Action.NAME, "Color");
        styleMenu.add(foregroundAction);

        // create font and size menu in the style menu
        Action formatTextAction = new FontAndSizeAction();
        formatTextAction.putValue(Action.NAME, "Font and Size");
        styleMenu.add(formatTextAction);

        menu.add(styleMenu);
    }
    
    /**
     * Getter method for menu
     */ 
    public JMenuBar getStyleMenu() {
        return menu;   
    }
} // end class StyleButton Creator