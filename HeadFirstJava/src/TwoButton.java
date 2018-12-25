import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TwoButton {
    private JFrame frame;
    private JLabel label;
    MyDrawPanel panel;
    private int counter = 0;
    private int x = 40;
    private int y = 40;

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

        panel = new MyDrawPanel();

        frame.getContentPane().add(BorderLayout.WEST, label);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.getContentPane().add(BorderLayout.EAST, lButton);
        frame.getContentPane().add(BorderLayout.SOUTH, cButton);

        frame.setSize(300, 300);
        frame.setVisible(true);

        int i = 0;
        int direction = 1;
        while (i < 60) {
            if (x >= 300 || x - 10 <= 0) {
                direction *= -1;
            }

            x += 2*direction;
            y += 2*direction;

            panel.repaint();
//            i ++;
            try {
                Thread.sleep(50);
            } catch (Exception ex) { }
        }

    }  // close void go(_)

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

    class MyDrawPanel extends JPanel{
        public void paintComponent(Graphics g){
            g.fillRect(0, 0, this.getWidth(), this.getHeight());

            int red = (int) (Math.random() * 255);
            int green = (int) (Math.random() * 255);
            int blue = (int) (Math.random() * 255);

            Color c = new Color(red, green, blue);
            g.setColor(c);
            g.fillOval(x, y, 30, 30);
        }
    }

} // close class TwoButton
