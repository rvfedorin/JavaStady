/*
 * Created by Roman V. Fedorin
 */
package frv.org.chapter2_10;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Wolf
 */
public class InvestmentTable {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new InvestmentTableFrame();
            frame.setTitle("InvestmentTable");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class InvestmentTableFrame extends JFrame {

    public InvestmentTableFrame() {
        TableModel model = new InvestmentTableModel(30, 5, 10);
        JTable table = new JTable(model);
        add(new JScrollPane(table));
        pack();
    }
    
}

class InvestmentTableModel extends AbstractTableModel {
    private static final double INITIAL_BALANS = 100000.0;
    private final int years;
    private final int minRate;
    private final int maxRate;

    public InvestmentTableModel(int years, int minRate, int maxRate) {
        this.years = years;
        this.minRate = minRate;
        this.maxRate = maxRate;
    }

    @Override
    public int getRowCount() {
        return years;
    }

    @Override
    public int getColumnCount() {
        return maxRate - minRate + 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        double rate = (columnIndex + minRate) / 100.0;
        int nperiods = rowIndex;
        double futureBalance = INITIAL_BALANS * Math.pow(1 + rate, nperiods);
        return String.format("%.2f", futureBalance);
    }

    @Override
    public String getColumnName(int column) {
        return (column + minRate) + "%";
    }
    
}