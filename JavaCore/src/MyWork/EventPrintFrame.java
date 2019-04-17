package MyWork;

import MyWork.ExtendStandart.ExtendedTextArea;

import javax.swing.*;
import java.awt.*;
//import java.awt.*;

public class EventPrintFrame extends JFrame {
    public ExtendedTextArea textField;

    EventPrintFrame() {
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Events");
        this.setLocation(this.getX()+10, this.getY()+10);

//        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
//        int x = (int) (dimension.getWidth() - getWidth())/4;
//        int y = (int) (dimension.getHeight() - getHeight())/4;
//        this.setLocation(x, y);


        textField = new ExtendedTextArea(15, 70);
        textField.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textField);
        JPanel panel = new JPanel();
        panel.add(scrollPane);

        this.add(panel);
        this.pack();

//        this.setVisible(true);
    }

    public void printEvent(String text) {
        textField.append(text + "\n");
    }
}
