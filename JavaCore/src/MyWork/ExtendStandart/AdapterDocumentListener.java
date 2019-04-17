package MyWork.ExtendStandart;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public interface AdapterDocumentListener extends DocumentListener {
    @Override
    public default void insertUpdate(DocumentEvent e) {act();}
    @Override
    public default void removeUpdate(DocumentEvent e) {act();}
    @Override
    public default void changedUpdate(DocumentEvent e) {act();}

    public void act();
}
