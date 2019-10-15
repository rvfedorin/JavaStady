package tools;

import java.awt.event.*;
import javax.swing.*;

import static tools.UndoRedoText.undoRedo;

public class ExtendedTextArea extends JTextArea {

    public ExtendedTextArea() {
        super();
        initialize();
//        setFont(normalFont);
        // START CTRL+Z CTRL+Y REALIZATION
        undoRedo(this);
        // END CTRL+Z CTRL+Y REALIZATION
    } // ** const

    public ExtendedTextArea(String text, int w, int h) {
        super(text, w, h);
        initialize();
//        setFont(normalFont);
        // START CTRL+Z CTRL+Y REALIZATION
        undoRedo(this);
        // END CTRL+Z CTRL+Y REALIZATION
    } // ** const

    private void initialize() {
        // Setup the popup menu

        // Add the mouse listener
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                requestFocus();
                dealWithMousePress(event);
            }
        });
    }

    /**
     * The mouse has been pressed over this text field. Popup the cut/paste
     * menu.
     *
     * @param evt mouse event
     */
    private void dealWithMousePress(MouseEvent evt) {
        // Only interested in the right button
        if (SwingUtilities.isRightMouseButton(evt)) {
            //if(MenuSelectionManager.defaultManager().getSelectedPath().length>0)
            //return;
            new TextPopUpMenu(this, evt);
        } // ** if(SwingUtilities.isRightMouseButton(evt))
    } // ** dealWithMousePress(MouseEvent evt)

} // ** class ExtendedTextArea

