package MyWork.ExtendStandart;
/**
 * Extended JTextField which provides cut/copy/paste/delete/selectAll actions
 * via a popup menu. This provides similar operations to the windows system.
 *
 * @author steve.webb
 */
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.Document;

import MyWork.Tools.TextPopUpMenu;

import static MyWork.Tools.UndoRedoText.undoRedo;

public class ExtendedTextField extends JTextField
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ExtendedTextField. A default model is created, the initial string is
     * null, and the number of columns is set to 0.
     */
    public ExtendedTextField()
    {
        super();
        initialize();
    }

    /**
     * Constructs a new ExtendedTextField that uses the given text storage model and the given
     * number of columns. This is the constructor through which the other constructors
     * feed. If the document is null, a default model is created.
     * @param doc the text storage to use; if this is null, a default will be provided by calling the createDefaultModel method
     * @param text the initial string to display, or null
     * @param columns the number of columns to use to calculate the preferred width >= 0; if columns  is set to zero, the preferred
     * width will be whatever naturally results from the component implementation.
     */
    public ExtendedTextField(Document doc, String text, int columns)
    {
        super(doc, text, columns);
        initialize();
    }

    /**
     * Constructs a new empty ExtendedTextField with the specified number of columns. A default
     * model is created and the initial string is set to null.
     * @param columns the number of columns to use to calculate the preferred width; if columns is set
     * to zero, the preferred width will be whatever naturally results from the component
     * implementation.
     */
    public ExtendedTextField(int columns)
    {
        super(columns);
        initialize();
    }

    /**
     * Constructs a new ExtendedTextField initialized with the specified text. A default model
     * is created and the number of columns is 0.
     * @param text the text to be displayed, or null
     */
    public ExtendedTextField(String text)
    {
        super(text);
        initialize();
    }

    /**
     * Constructs a new ExtendedTextField initialized with the specified text and columns. A default
     * model is created.
     * @param text the text to be displayed, or null
     * @param columns the number of columns to use to calculate the preferred width; if columns
     * is set to zero, the preferred width will be whatever naturally results
     * from the component implementation
     */
    public ExtendedTextField(String text, int columns)
    {
        super(text, columns);
        initialize();
    }

    /**
     * Initialize's specific behaviour for this class.
     */
    protected void initialize()
    {
        // Setup the popup menu

        // Add the mouse listener
        this.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent evt)
            {
                requestFocus();
                dealWithMousePress(evt);
            }
        });
    }

    /**
     * The mouse has been pressed over this text field. Popup the cut/paste menu.
     * @param evt mouse event
     */
    protected void dealWithMousePress(MouseEvent evt)
    {
        // Only interested in the right button
        if(SwingUtilities.isRightMouseButton(evt))
        {
            //if(MenuSelectionManager.defaultManager().getSelectedPath().length>0)
            //return;
            new TextPopUpMenu(this, evt);
        }
    }
}
