import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TwoButton {
    private JFrame frame;
    private JLabel label;
    private int counter = 0;

    public static void main(String[] args){
        TwoButton gui = new TwoButton();
        gui.go();
    }

    private void go(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton cButton = new JButton("Change color");
        cButton.addActionListener(new ColorListener());

        JButton lButton = new JButton("Add count");
        lButton.addActionListener(new LabelListener());

        String sLab = "Нажатий: " + counter;
        label = new JLabel(sLab);

        MyDrawPanel panel = new MyDrawPanel();

        frame.getContentPane().add(BorderLayout.WEST, label);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.getContentPane().add(BorderLayout.EAST, lButton);
        frame.getContentPane().add(BorderLayout.SOUTH, cButton);

        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    class ColorListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.repaint();
        }
    } // close class ColorListener

    class LabelListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            counter++;
            String sLab = "Нажатий: " + counter;
            label.setText(sLab);
        }
    } // close class LabelListener

} // close class TwoButton
