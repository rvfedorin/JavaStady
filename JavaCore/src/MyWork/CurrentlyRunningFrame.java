package MyWork;

import javax.swing.*;
import java.awt.*;
//import java.util.ArrayList;
import java.util.HashMap;

public class CurrentlyRunningFrame extends JFrame{
    private int idNumber;
    private JPanel sumPanel;
    private HashMap<String, JPanel> labels;
    private boolean state = false;
//    private MainWindow mainWindow;
//    private ArrayList<Thread> threads;

    CurrentlyRunningFrame(MainWindow mWin){
        idNumber = 0;
//        mainWindow = mWin;
//        threads = new ArrayList<>();
        labels = new HashMap<>();
        int xLoc = mWin.getX() + 400;
        int yLoc = mWin.getY() - 6;
        setLocation(xLoc, yLoc);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setTitle("Выполняющиеся процессы: ");
        setSize(380, 200);

        JPanel panelTop = new JPanel();
        add(panelTop, BorderLayout.NORTH);
        panelTop.add(new JLabel("Выполняющиеся процессы: "));

        JLabel status = new JLabel("<<< ВЫКЛЮЧЕНО >>>");
        panelTop.add(status);
        JButton enableBut = new JButton("Enable");
        panelTop.add(enableBut);
        enableBut.addActionListener(e -> {
            if(state) {
                if(labels.isEmpty()) {
                    state = false;
                    status.setText("<<< ВЫКЛЮЧЕНО >>>");
                    enableBut.setText("Enable");
                } else {
                    status.setText("<<< ВЫКЛЮЧЕНО. Очередь не пуста. >>>");
                }
            } else {
                state = true;
                status.setText("<<< ВКЛЮЧЕНО >>>");
                enableBut.setText("Disable");
            }
        });

        sumPanel = new JPanel();
        sumPanel.setLayout(new BoxLayout(sumPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(sumPanel);

        this.add(scrollPane, BorderLayout.CENTER);

    } // const

    public synchronized int addLine(String nameProc, Thread thread) {
        if (state) {
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
        } else {
            return -1;
        }
    } // ** addLine()

    public synchronized void removeLine(int id) {
        if(state && id >= 0 && labels.containsKey(String.valueOf(id))) {
            sumPanel.remove(labels.get(String.valueOf(id)));
            labels.remove(String.valueOf(id));
            sumPanel.repaint();
            this.setVisible(true);
        }
    } // ** removeLina()
}
