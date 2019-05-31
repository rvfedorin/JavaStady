package MyWork;

import MyWork.ExtendStandart.ExtendedTextArea;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventPrintFrame extends JFrame {
    private ExtendedTextArea textField;

    EventPrintFrame() {
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//        this.setResizable(false);
        this.setTitle("Events");
        this.setLocation(this.getX() + 10, this.getY() + 10);

        textField = new ExtendedTextArea(15, 70);
        JScrollPane scrollPane = new JScrollPane(textField);

        this.add(scrollPane);
        this.pack();

//        this.setVisible(true);
    }

    public void printEvent(String text) {
        EventQueue.invokeLater(() -> {
            textField.append(text + "\n");
        });
    } // ** printEvent()

    public void pDate() {
        Date date = new Date();
        SimpleDateFormat dateView = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        EventQueue.invokeLater(() -> {
            textField.append("\t\t[DATE] " + dateView.format(date) + " [DATE]\n");
            textField.append("\t\t===========================\n");
        });
    }
}
