package other;


import MyWork.ExtendStandart.ExtendedTextArea;

import javax.swing.*;
import java.awt.*;

public class test {
    public static void main(String[] args) {
//        String data = "Orel-IPSidK74           in      5120    95.80.109.176           -unnumbered";
        ResultMessage1 res = new ResultMessage1("Hello");
    }
}

class ResultMessage1 extends JFrame {

    public ResultMessage1 (String message) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (dimension.getWidth() - getWidth())/3;
        int y = (int) (dimension.getHeight() - getHeight())/3;
        this.setLocation(x, y);

        this.setTitle("Результат выполнения операции.");
        ExtendedTextArea textArea = new ExtendedTextArea(10, 60);
        JButton okButton = new JButton("ok");
        okButton.addActionListener(e -> this.dispose());
        textArea.append(message);

        this.add(textArea, BorderLayout.CENTER);
        this.add(okButton, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
    }
}
// repeat text /////////////////////////////////////////////////////////////////////////////////
//            String space = " ";
//            k = String.join("", Collections.nCopies(lengthLabel - k.length(), space)) + k;
///////////////////////////////////////////////////////////////////////////////////////////////