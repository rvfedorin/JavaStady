/*
 * Created by Roman V. Fedorin
 */
package frv.org.chapter2_10;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Wolf
 */
public class PlanetTable2 extends JFrame {

    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 500;

    private static final int COLOR_COLUMN = 4;
    private static final int IMAGE_COLUMN = 5;

    private JTable table;
    private HashSet<Integer> removedRowIndex;
    private ArrayList<TableColumn> removedColumns;
    private JCheckBoxMenuItem rowsItem;
    private JCheckBoxMenuItem columnsItem;
    private JCheckBoxMenuItem cellsItem;

    private String[] columnNames = {"Planet", "Radius", "Moon", "Gaseous", "Color", "Image"};
    private Object[][] cells = {
        {"Mercury", 2440.0, 0, false, Color.YELLOW, new ImageIcon(getClass().getResource("resources/Mercury.gif"))},
        {"Venus", 6052.0, 0, false, Color.YELLOW, new ImageIcon(getClass().getResource("resources/Venus.gif"))},
        {"Earth", 6348.0, 1, false, Color.BLUE, new ImageIcon(getClass().getResource("resources/Earth.gif"))},
        {"Mars", 3397.0, 2, false, Color.RED, new ImageIcon(getClass().getResource("resources/Mars.gif"))},
        {"Jupiter", 71492.0, 16, false, Color.ORANGE, new ImageIcon(getClass().getResource("resources/Jupiter.gif"))},
        {"Saturn", 60268.0, 0, false, Color.ORANGE, new ImageIcon(getClass().getResource("resources/Saturn.gif"))},
        {"Uranus", 25559.0, 0, false, Color.BLUE, new ImageIcon(getClass().getResource("resources/Uranus.gif"))},
        {"Neptune", 24766.0, 8, false, Color.BLUE, new ImageIcon(getClass().getResource("resources/Neptune.gif"))},
        {"Pluto", 1137.0, 1, false, Color.BLACK, new ImageIcon(getClass().getResource("resources/Pluto.gif"))}
    };

    public PlanetTable2() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        TableModel model = new DefaultTableModel(cells, columnNames) {
            @Override
            public Class<?> getColumnClass(int c) {
                return cells[0][c].getClass();
            }
        };
        table = new JTable(model);

        table.setRowHeight(100);
        table.getColumnModel().getColumn(COLOR_COLUMN).setMinWidth(250);
        table.getColumnModel().getColumn(IMAGE_COLUMN).setMinWidth(100);

        final TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        sorter.setComparator(COLOR_COLUMN,
                Comparator.comparing(Color::getBlue)
                        .thenComparing(Color::getGreen)
                        .thenComparing(Color::getRed));

        sorter.setSortable(IMAGE_COLUMN, false);
        add(new JScrollPane(table), BorderLayout.CENTER);

        removedRowIndex = new HashSet<>();
        removedColumns = new ArrayList<>();

        final RowFilter<TableModel, Integer> filter = new RowFilter<TableModel, Integer>() {
            @Override
            public boolean include(RowFilter.Entry<? extends TableModel, ? extends Integer> entry) {
                return !removedRowIndex.contains(entry.getIdentifier());
            }
        };

        // create menu
        JMenuBar jMenuBar = new JMenuBar();
        setJMenuBar(jMenuBar);

        JMenu selectionMenu = new JMenu("Selection");
        jMenuBar.add(selectionMenu);

        rowsItem = new JCheckBoxMenuItem("Rows");
        columnsItem = new JCheckBoxMenuItem("Columns");
        cellsItem = new JCheckBoxMenuItem("Cells");

        rowsItem.setSelected(table.getRowSelectionAllowed());
        columnsItem.setSelected(table.getColumnSelectionAllowed());
        cellsItem.setSelected(table.getCellSelectionEnabled());

        rowsItem.addActionListener(e -> {
            table.clearSelection();
            table.setRowSelectionAllowed(rowsItem.isSelected());
            updateCheckBoxMenuItems();
        });

        columnsItem.addActionListener(e -> {
            table.clearSelection();
            table.setColumnSelectionAllowed(columnsItem.isSelected());
            updateCheckBoxMenuItems();
        });

        cellsItem.addActionListener(e -> {
            table.clearSelection();
            table.setCellSelectionEnabled(cellsItem.isSelected());
            updateCheckBoxMenuItems();
        });

        selectionMenu.add(rowsItem);
        selectionMenu.add(columnsItem);
        selectionMenu.add(cellsItem);

        JMenu tableMenu = new JMenu("Edit");
        jMenuBar.add(tableMenu);

        JMenuItem hideColumnsItem = new JMenuItem("Hide columns");
        hideColumnsItem.addActionListener(e -> {
            int[] selected = table.getSelectedColumns();
            TableColumnModel columnModel = table.getColumnModel();
            // remove columns from view, starting at the last
            // index so that column numbers are not affected
            for (int i = selected.length - 1; i >= 0; i--) {
                TableColumn column = columnModel.getColumn(selected[i]);
                table.removeColumn(column);
                removedColumns.add(column);
            }
        });
        tableMenu.add(hideColumnsItem);

        JMenuItem showColumnsItem = new JMenuItem("Show columns");
        showColumnsItem.addActionListener(e -> {
            removedColumns.forEach((tc) -> {
                table.addColumn(tc);
            });
            removedColumns.clear();
        });
        tableMenu.add(showColumnsItem);

        JMenuItem hideRowsItem = new JMenuItem("Hide rows");
        hideRowsItem.addActionListener(e -> {
            int[] selected = table.getSelectedRows();
            for (int i : selected) {
                removedRowIndex.add(table.convertRowIndexToModel(i));
            }
            sorter.setRowFilter(filter);
        });
        tableMenu.add(hideRowsItem);

        JMenuItem showRowsItem = new JMenuItem("Show rows");
        showRowsItem.addActionListener(e -> {
            removedRowIndex.clear();
            sorter.setRowFilter(filter);
        });
        tableMenu.add(showRowsItem);
        
        JMenuItem printSelectedItems = new JMenuItem("Print selection");
        printSelectedItems.addActionListener(e -> {
            int[] selected = table.getSelectedRows();
            System.out.println("Selected rows: " + Arrays.toString(selected));
            selected = table.getSelectedColumns();
            System.out.println("Selected columns: " + Arrays.toString(selected));
            
        });
        tableMenu.add(printSelectedItems);
        
        
    }

    private void updateCheckBoxMenuItems() {
        rowsItem.setSelected(table.getRowSelectionAllowed());
        columnsItem.setSelected(table.getColumnSelectionAllowed());
        cellsItem.setSelected(table.getCellSelectionEnabled());
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new PlanetTable2();
            frame.setTitle("Planet Table");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
