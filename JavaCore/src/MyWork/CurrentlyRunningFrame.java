package MyWork;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CurrentlyRunningFrame extends JFrame{
    private int idNumber;
    private MainWindow mainWindow;
    private JPanel sumPanel;
//    private ArrayList<Thread> threads;
    private HashMap<String, JPanel> labels;

    CurrentlyRunningFrame(MainWindow mWin){
        idNumber = 0;
        mainWindow = mWin;
//        threads = new ArrayList<>();
        labels = new HashMap<>();
        int xLoc = mainWindow.getX() + 400;
        int yLoc = mainWindow.getY() - 6;
        setLocation(xLoc, yLoc);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setTitle("Выполняющиеся процессы: ");
        setSize(350, 200);

        add(new JLabel("Выполняющиеся процессы: "), BorderLayout.NORTH);
        sumPanel = new JPanel();
        sumPanel.setLayout(new BoxLayout(sumPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(sumPanel);

        this.add(scrollPane, BorderLayout.CENTER);

    } // const

    public synchronized int addLine(String nameProc, Thread thread) {
        idNumber++;
//        threads.add(thread);

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel num = new JLabel(String.valueOf(idNumber));
        JLabel name = new JLabel(nameProc);
        JButton stopThreadButton = new JButton("stop");

        row.add(num);
        row.add(name);
        row.add(stopThreadButton);

        sumPanel.add(row);
        labels.put(String.valueOf(idNumber), row);
//        sumPanel.repaint();
        this.setVisible(true);

        return idNumber;
    } // ** addLine()

    public synchronized void removeLine(int id) {
        if(id >= 0 && labels.containsKey(String.valueOf(id))) {
            sumPanel.remove(labels.get(String.valueOf(id)));
            labels.remove(String.valueOf(id));
            sumPanel.repaint();
            this.setVisible(true);
        }
    } // ** removeLina()
}
