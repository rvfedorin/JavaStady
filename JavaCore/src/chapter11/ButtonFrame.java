package chapter11;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonFrame extends JFrame {
    private JPanel buttonPane;
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public ButtonFrame() {
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        JButton yellowButton = new JButton("Yellow");
        JButton blueButton = new JButton("Blue");
        JButton redButton = new JButton("Red");

        buttonPane = new JPanel();

        buttonPane.add(yellowButton);
        buttonPane.add(blueButton);
        buttonPane.add(redButton);

        this.add(buttonPane);

        ColorAction yAct = new ColorAction(Color.YELLOW);
        ColorAction bAct = new ColorAction(Color.BLUE);
        ColorAction rAct = new ColorAction(Color.RED);

        yellowButton.addActionListener(yAct);
        blueButton.addActionListener(bAct);
        redButton.addActionListener(rAct);

    } // constr ()

    public static void main(String[] args) {
        ButtonFrame bf = new ButtonFrame();
        bf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bf.setVisible(true);
    }

    private class ColorAction implements ActionListener {
        private Color backColor;

        ColorAction(Color c) {
            backColor = c;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            buttonPane.setBackground(backColor);
        }
    } // class ColorAction

} // class ButtonFrame


