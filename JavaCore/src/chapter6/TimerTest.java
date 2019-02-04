package chapter6;

import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.Timer;
import javax.swing.*;


public class TimerTest {
    public static void main(String[] args) {
        TimePrinter tp = new TimePrinter();
        Timer t = new Timer(10000, tp);
        t.start();

        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}  // close class TimerTest

class TimePrinter implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("At the tone, the time is " + new Date());
        Toolkit.getDefaultToolkit().beep();
    }
}
