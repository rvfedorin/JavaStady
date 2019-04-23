package MyWork;

import MyWork.ExtendStandart.ExtendedTextArea;

import javax.swing.*;

public class EventPrintFrame extends JFrame {
    public ExtendedTextArea textField;

    EventPrintFrame() {
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//        this.setResizable(false);
        this.setTitle("Events");
        this.setLocation(this.getX()+10, this.getY()+10);

        textField = new ExtendedTextArea(15, 70);
        JScrollPane scrollPane = new JScrollPane(textField);

        this.add(scrollPane);
        this.pack();

//        this.setVisible(true);
    }

    public void printEvent(String text) {
        textField.append(text + "\n");
    }
}
