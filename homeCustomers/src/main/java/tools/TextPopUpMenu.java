package tools;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class TextPopUpMenu {

    public TextPopUpMenu(JTextComponent jComp, MouseEvent evt) {

        JPopupMenu menu = new JPopupMenu();
        menu.add(new CutAction(jComp));
        menu.add(new CopyAction(jComp));
        menu.add(new PasteAction(jComp));
        menu.add(new DeleteAction(jComp));
        menu.addSeparator();
        menu.add(new SelectAllAction(jComp));

        // Display the menu
        Point pt = SwingUtilities.convertPoint(evt.getComponent(), evt.getPoint(), jComp);
        menu.show(jComp, pt.x, pt.y);
    }

    class SelectAllAction extends AbstractAction {
        private static final long serialVersionUID = 1L;
        /**
         * Icon to displayed against this action.
         */
        //static final private ImageIcon icon =
        //        new ImageIcon(ClassLoader.getSystemResource("toolbarButtonGraphics/general/Import16.gif"));

        /**
         * The component the action is associated with.
         */
        protected JTextComponent comp;

        /**
         * Default constructor.
         *
         * @param comp The component the action is associated with.
         */
        public SelectAllAction(JTextComponent comp) {
            super("Select All" /*,icon*/);
            this.comp = comp;
        }

        /**
         * Action has been performed on the component.
         *
         * @param e ignored
         */
        public void actionPerformed(ActionEvent e) {
            comp.selectAll();
            /* Need to also selectAll() via a later because in the case of FormattedText fields
             * the field is re-drawn if the request is made durring a focusGained event.
             * This is a pain but there doesn't appear to be any need solution to this and it is
             * a known swing bug but it isn't going to be fixed anytime soon. */
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    comp.selectAll();
                }
            });

        }

        /**
         * Checks if the action can be performed.
         *
         * @return True if the action is allowed
         */
        public boolean isEnabled() {
            return comp.isEnabled()
                    && comp.getText().length() > 0;
        }


    }

    class PasteAction extends AbstractAction {
        //static final private ImageIcon icon =
        //        new ImageIcon(ClassLoader.getSystemResource("toolbarButtonGraphics/general/Paste16.gif"));

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        /**
         * The component the action is associated with.
         */
        JTextComponent comp;

        /**
         * Default constructor.
         *
         * @param comp The component the action is associated with.
         */
        public PasteAction(JTextComponent comp) {
            super("Paste" /*,icon*/);
            this.comp = comp;
        }

        /**
         * Action has been performed on the component.
         *
         * @param e ignored
         */
        public void actionPerformed(ActionEvent e) {
            comp.paste();
        }

        /**
         * Checks if the action can be performed.
         *
         * @return True if the action is allowed
         */
        public boolean isEnabled() {
            if (comp.isEditable() && comp.isEnabled()) {
                Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(this);
                return contents.isDataFlavorSupported(DataFlavor.stringFlavor);
            } else
                return false;
        }
    }

    class DeleteAction extends AbstractAction {
        /**
         * Icon to displayed against this action.
         */
        //static final private ImageIcon icon =
        //        new ImageIcon(ClassLoader.getSystemResource("toolbarButtonGraphics/general/Delete16.gif"));

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        /**
         * The component the action is associated with.
         */
        JTextComponent comp;

        /**
         * Default constructor.
         *
         * @param comp The component the action is associated with.
         */
        public DeleteAction(JTextComponent comp) {
            super("Delete" /*,icon*/);
            this.comp = comp;
        }

        /**
         * Action has been performed on the component.
         *
         * @param e ignored
         */
        public void actionPerformed(ActionEvent e) {
            comp.replaceSelection(null);
        }

        /**
         * Checks if the action can be performed.
         *
         * @return True if the action is allowed
         */
        public boolean isEnabled() {
            return comp.isEditable()
                    && comp.isEnabled()
                    && comp.getSelectedText() != null;
        }
    }

    class CutAction extends AbstractAction {
        /**
         * Icon to displayed against this action.
         */
        //static final private ImageIcon icon =
        //        new ImageIcon(ClassLoader.getSystemResource("toolbarButtonGraphics/general/Cut16.gif"));

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        /**
         * The component the action is associated with.
         */
        JTextComponent comp;

        /**
         * Default constructor.
         *
         * @param comp The component the action is associated with.
         */
        public CutAction(JTextComponent comp) {
            super("Cut" /*,icon*/);
            this.comp = comp;
        }

        /**
         * Action has been performed on the component.
         *
         * @param e ignored
         */
        public void actionPerformed(ActionEvent e) {

            comp.cut();
        }

        /**
         * Checks if the action can be performed.
         *
         * @return True if the action is allowed
         */
        public boolean isEnabled() {
            return comp.isEditable()
                    && comp.isEnabled()
                    && comp.getSelectedText() != null;
        }
    }

    class CopyAction extends AbstractAction {
        /**
         * Icon to displayed against this action.
         */
        //static final private ImageIcon icon =
        //        new ImageIcon(ClassLoader.getSystemResource("toolbarButtonGraphics/general/Copy16.gif"));

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        /**
         * The component the action is associated with.
         */
        JTextComponent comp;

        /**
         * Default constructor.
         *
         * @param comp The component the action is associated with.
         */
        public CopyAction(JTextComponent comp) {
            super("Copy" /*,icon*/);
            this.comp = comp;

        }

        /**
         * Action has been performed on the component.
         *
         * @param e ignored
         */
        public void actionPerformed(ActionEvent e) {
            comp.copy();
        }

        /**
         * Checks if the action can be performed.
         *
         * @return True if the action is allowed
         */
        public boolean isEnabled() {
            return comp.isEnabled()
                    && comp.getSelectedText() != null;
        }
    }
}

