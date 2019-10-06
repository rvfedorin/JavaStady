/*
 * Created by Roman V. Fedorin
 */
package frv.org.chapter2_10.cell_render;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.beans.EventHandler;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Wolf
 */
public class ColorTableCellEditor extends AbstractCellEditor implements TableCellEditor {
    private JColorChooser colorChooser;
    private JDialog colorDialog;
    private JPanel panel;

    public ColorTableCellEditor() {
        
        panel = new JPanel();
        // prepare color dialog
        
        colorChooser = new JColorChooser();
        colorDialog = JColorChooser.createDialog(null, 
                "Planet color", 
                false, 
                colorChooser, 
                EventHandler.create(ActionListener.class, this, "stopCellEditing"), 
                EventHandler.create(ActionListener.class, this, "cancelCellEditing"));
    }
    
    
    @Override
    public Object getCellEditorValue() {
        return colorChooser.getColor();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // this is where we get the current color value. 
        // We store it in the dialog in case the user starts editing
        colorChooser.setColor((Color) value);
        return panel;
    }

    @Override
    public void cancelCellEditing() {
        // editing is canceled, hide dialog
        colorDialog.setVisible(false);
        super.cancelCellEditing();
    }

    @Override
    public boolean stopCellEditing() {
        // editing is complete, hide dialog
        colorDialog.setVisible(false);
        super.stopCellEditing();
        
        // tell caller it is ok to use color value
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        // start editing
        colorDialog.setVisible(true);
        
        // tell caller it is ok to select this cell
        return true;
    }
    
    

}
