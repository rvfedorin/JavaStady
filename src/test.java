import javax.swing.*;
import java.awt.*;

class test {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton b1 = new JButton("shock me");
        JButton b2 = new JButton("bliss");
        JButton b3 = new JButton("huh?");

        panel.add(b1);
        panel.add(b2);
        panel.add(b3);

        frame.getContentPane().add(BorderLayout.EAST, panel);
        frame.setSize(300, 300);
        frame.setVisible(true);


    } // public static void main
}

