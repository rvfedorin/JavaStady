package MyWork.ExtendStandart;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public interface AdapterDocumentListener extends DocumentListener {
    @Override
    default void insertUpdate(DocumentEvent e) {act();}
    @Override
    default void removeUpdate(DocumentEvent e) {act();}
    @Override
    default void changedUpdate(DocumentEvent e) {act();}

    void act();
}
