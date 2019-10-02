/*
 * Created by Roman V. Fedorin
 */
package frv.org.chapter2_10.cell_render;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Wolf
 */
public class TableCellRenderFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 500;
    private static final String path = "../resources/";

    public TableCellRenderFrame() throws HeadlessException {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        
        TableModel model = new PlanetTableModel();
        JTable table = new JTable(model);
        table.setRowSelectionAllowed(false);
        
        // set up renders and editors
        table.setDefaultEditor(Color.class, new ColorTableCellEditor());
        table.setDefaultRenderer(Color.class, new ColorTableCellRenderer());
        
        JComboBox<Integer> moonCombo = new JComboBox<>();
        for(int i = 0; i < 21; i++) {
            moonCombo.addItem(i);
        }
        
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn moonColumn = columnModel.getColumn(PlanetTableModel.MOONS_COLUMN);
        moonColumn.setCellEditor(new DefaultCellEditor(moonCombo));
        moonColumn.setHeaderRenderer(table.getDefaultRenderer(ImageIcon.class));
        moonColumn.setHeaderValue(new ImageIcon(getClass().getResource(path + "Moons.gif")));
        
        // show table
        table.setRowHeight(100);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new TableCellRenderFrame();
            frame.setTitle("With render");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
