package MyWork.ExtendStandart;

import java.awt.event.*;
import javax.swing.*;

import static MyWork.Tools.UndoRedoText.undoRedo;
import MyWork.Tools.*;

public class ExtendedTextArea extends JTextArea {

    public ExtendedTextArea(int rows, int columns)
        {
            super(rows, columns);
            initialize();


            // START CTRL+Z CTRL+Y REALIZATION
            undoRedo(this);
            // END CTRL+Z CTRL+Y REALIZATION

        } // ** const

        private void initialize()
        {
            // Setup the popup menu

            // Add the mouse listener
            this.addMouseListener(new MouseAdapter()
            {
                public void mouseClicked(MouseEvent event)
                {
                    requestFocus();
                    dealWithMousePress(event);
                }
            });
        }

        /**
         * The mouse has been pressed over this text field. Popup the cut/paste menu.
         * @param evt mouse event
         */
        private void dealWithMousePress(MouseEvent evt)
        {
            // Only interested in the right button
            if(SwingUtilities.isRightMouseButton(evt))
            {
                //if(MenuSelectionManager.defaultManager().getSelectedPath().length>0)
                //return;
                new TextPopUpMenu(this, evt);
            } // ** if(SwingUtilities.isRightMouseButton(evt))
        } // ** dealWithMousePress(MouseEvent evt)

} // ** class ExtendedTextArea

