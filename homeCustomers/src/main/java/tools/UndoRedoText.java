package tools;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public abstract class UndoRedoText {
    private static final String REDO = "Redo";
    private static final String UNDO = "Undo";

    public static void undoRedo(JTextComponent jTextComponent) {
        final UndoManager undoManager = new UndoManager(); //instantiate an UndoManager
        // Add listener for undoable events
        jTextComponent.getDocument().addUndoableEditListener(event -> undoManager.addEdit(event.getEdit()));

        // Add undo/redo actions
        jTextComponent.getActionMap().put(UNDO, new AbstractAction(UNDO) {
            @Override
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

        jTextComponent.getActionMap().put(REDO, new AbstractAction(REDO) {
            @Override
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
        jTextComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), UNDO);
        jTextComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK), REDO);
    } // ** undoRedo()

} // ** class UndoRedoText
