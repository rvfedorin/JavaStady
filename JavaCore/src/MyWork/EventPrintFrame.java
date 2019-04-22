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

//        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
//        int x = (int) (dimension.getWidth() - getWidth())/4;
//        int y = (int) (dimension.getHeight() - getHeight())/4;
//        this.setLocation(x, y);

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
