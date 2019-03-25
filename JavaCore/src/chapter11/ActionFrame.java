package chapter11;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ActionFrame extends JFrame {
    private JPanel jPanel;
    public ActionColor actionColorB;
    public ActionColor actionColorR;
    public ActionColor actionColorG;

    ActionFrame() {
        jPanel = new JPanel();
        this.add(jPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 200);
        actionColorB = new ActionColor("Blue", new ImageIcon("ico.jpg"), Color.BLUE);
        actionColorR = new ActionColor("Red", new ImageIcon("ico.jpg"), Color.RED);
        actionColorG = new ActionColor("Green", new ImageIcon("ico.jpg"), Color.GREEN);
    }

    public static void main(String[] args) {
        ActionFrame frame = new ActionFrame();

        JButton toBlue = new JButton(frame.actionColorB);
        JButton toRed = new JButton(frame.actionColorR);
        JButton toGreen = new JButton(frame.actionColorG);

        frame.jPanel.add(toBlue);
        frame.jPanel.add(toRed);
        frame.jPanel.add(toGreen);

        frame.setVisible(true);
    }

    class ActionColor extends AbstractAction {

        ActionColor(String name, Icon icon, Color c) {
            this.putValue(Action.NAME, name);
            this.putValue(Action.SMALL_ICON, icon);
            this.putValue("color", c);
            this.putValue(Action.SHORT_DESCRIPTION, "Изменить цвет фона на " + name.toLowerCase());
        } // const

        @Override
        public void actionPerformed(ActionEvent event) {
            Color c = (Color) this.getValue("color");
            jPanel.setBackground(c);
        } // actionPerformed

    } // class ActionColor

} // class ActionFrame
