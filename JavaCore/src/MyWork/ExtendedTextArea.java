package MyWork;

import java.awt.Point;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class ExtendedTextArea extends JTextArea {
    private static final String REDO = "Redo";
    private static final String UNDO = "Undo";


    public ExtendedTextArea(int rows, int columns)
        {
            super(rows, columns);
            initialize();


            // START CTRL+Z CTRL+Y REALIZATION
            undoRedo();
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

                JPopupMenu menu = new JPopupMenu();
                menu.add(new MyWork.CutAction(this));
                menu.add(new MyWork.CopyAction(this));
                menu.add(new MyWork.PasteAction(this));
                menu.add(new MyWork.DeleteAction(this));
                menu.addSeparator();
                menu.add(new MyWork.SelectAllAction(this));

                // Display the menu
                Point pt = SwingUtilities.convertPoint(evt.getComponent(), evt.getPoint(), this);
                menu.show(this, pt.x, pt.y);
            } // ** if(SwingUtilities.isRightMouseButton(evt))
        } // ** dealWithMousePress(MouseEvent evt)

    private void undoRedo() {
        final UndoManager undoManager = new UndoManager(); //instantiate an UndoManager
        // Add listener for undoable events
        getDocument().addUndoableEditListener(event -> undoManager.addEdit(event.getEdit()));

        // Add undo/redo actions
        getActionMap().put(UNDO, new AbstractAction(UNDO) {
            public void actionPerformed(ActionEvent pEvt) {
                try {
                    if (undoManager.canUndo()) {
                        undoManager.undo();
                    }
                } catch (CannotUndoException e) {
                    e.printStackTrace();
                }
            }
        });

        getActionMap().put(REDO, new AbstractAction(REDO) {
            public void actionPerformed(ActionEvent pEvt) {
                try {
                    if (undoManager.canRedo()) {
                        undoManager.redo();
                    }
                } catch (CannotRedoException e) {
                    e.printStackTrace();
                }
            }
        });

        // Create keyboard accelerators for undo/redo actions (Ctrl+Z/Ctrl+Y)
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), UNDO);
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK), REDO);
        } // ** undoRedo()


} // ** class ExtendedTextArea

