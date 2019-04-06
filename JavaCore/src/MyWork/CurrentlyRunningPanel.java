package MyWork;

import javax.swing.*;
import java.awt.*;

public class CurrentlyRunningPanel extends JPanel{
    int idNumber;

    CurrentlyRunningPanel(){
        idNumber = 0;
        setLayout(new BorderLayout());
        add(new JLabel("Выполняющиеся процессы: "), BorderLayout.NORTH);
        setPreferredSize(new Dimension(300, 300));
    } // const

    public int addLine(JPanel addPanel) {
        idNumber++;

        JPanel centerWindow = new JPanel();
        centerWindow.setLayout(new BoxLayout(centerWindow, BoxLayout.LINE_AXIS));
        add(centerWindow, BorderLayout.CENTER);
        centerWindow.add(addPanel);

        return idNumber;
    }
}
