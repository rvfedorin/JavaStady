/*
 * Created by Roman V. Fedorin
 */
package frv.org.chapter2_10;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Wolf
 */
public class TableTest {
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new PlanetTableFrame();
            frame.setTitle("Table test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
    
}

class PlanetTableFrame extends JFrame {
    private String[] columnNames = {"Planet", "Radius", "Moon", "Gaseous", "Color" };
    private Object[][] cells = {
        {"Mercury", 2440.0, 0, false, Color.YELLOW},
        {"Venus", 6052.0, 0, false, Color.YELLOW},
        {"Mars", 3397.0, 2, false, Color.ORANGE},
        {"Saturn", 60268.0, 0, false, Color.ORANGE},
        {"Uranus", 25559.0, 0, false, Color.BLUE},
        {"Pluto", 1137.0, 1, false, Color.BLACK}
    };

    public PlanetTableFrame() {
        final JTable jTable = new JTable(cells, columnNames);
        jTable.setAutoCreateRowSorter(true);
        JScrollPane jsp = new JScrollPane(jTable);
        jsp.setPreferredSize(new Dimension(500, 150));
        add(jsp, BorderLayout.CENTER);
        JButton printButton = new JButton("Print");
        printButton.addActionListener(e -> {
            try{jTable.print();} 
            catch (Exception ex) {ex.printStackTrace();}
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(printButton);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
    }
    
}
