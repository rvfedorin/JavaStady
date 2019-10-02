/*
 * Created by Roman V. Fedorin
 */
package frv.org.chapter2_10.cell_render;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Wolf
 */
public class PlanetTableModel extends AbstractTableModel {

    public static final int PALNET_COLUMN = 0;
    public static final int MOONS_COLUMN = 2;
    public static final int GASEOUS_COLUMN = 3;
    public static final int COLOR_COLUMN = 4;
    private static final String path = "../resources/";
    
    private final String[] columnNames = {"Planet", "Radius", "Moon", "Gaseous", "Color", "Image"};
    private final Object[][] cells = {
        {"Mercury", 2440.0, 0, false, Color.YELLOW, new ImageIcon(getClass().getResource(path + "Mercury.gif"))},
        {"Venus", 6052.0, 0, false, Color.YELLOW, new ImageIcon(getClass().getResource(path + "Venus.gif"))},
        {"Earth", 6348.0, 1, false, Color.BLUE, new ImageIcon(getClass().getResource(path + "Earth.gif"))},
        {"Mars", 3397.0, 2, false, Color.RED, new ImageIcon(getClass().getResource(path + "Mars.gif"))},
        {"Jupiter", 71492.0, 16, false, Color.ORANGE, new ImageIcon(getClass().getResource(path + "Jupiter.gif"))},
        {"Saturn", 60268.0, 0, false, Color.ORANGE, new ImageIcon(getClass().getResource(path + "Saturn.gif"))},
        {"Uranus", 25559.0, 0, false, Color.BLUE, new ImageIcon(getClass().getResource(path + "Uranus.gif"))},
        {"Neptune", 24766.0, 8, false, Color.BLUE, new ImageIcon(getClass().getResource(path + "Neptune.gif"))},
        {"Pluto", 1137.0, 1, false, Color.BLACK, new ImageIcon(getClass().getResource(path + "Pluto.gif"))}
    };

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        cells[rowIndex][columnIndex] = aValue;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == PALNET_COLUMN 
                || columnIndex == MOONS_COLUMN 
                || columnIndex == GASEOUS_COLUMN 
                || columnIndex == COLOR_COLUMN; 
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return cells[0][columnIndex].getClass();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    @Override
    public int getRowCount() {
        return cells.length;
    }

    @Override
    public int getColumnCount() {
        return cells[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return cells[rowIndex][columnIndex];
    }

}
