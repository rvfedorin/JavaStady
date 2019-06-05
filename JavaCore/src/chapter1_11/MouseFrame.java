package chapter1_11;

import javax.swing.*;

public class MouseFrame extends JFrame {

    MouseFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new MouseComponent());
        pack();
    }

    public static void main(String[] args) {
        MouseFrame frame = new MouseFrame();
        frame.setVisible(true);
    } // main()
}// class MouseFrame
