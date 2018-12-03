import javax.swing.*;
import java.awt.event.*;


public class SimpleGui1 implements ActionListener{
    int click_count = 0;
    JButton button;

    public static void main(String[] args){
        SimpleGui1 gui1 = new SimpleGui1();
        gui1.go();

    }

    public void go(){
        JFrame frame = new JFrame();
        button = new JButton("Click me");
        button.addActionListener(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(button);
        frame.setSize(200, 100);
        frame.setVisible(true);

    }

    public void onClick(){
        click_count++;
        String s = "Clicked: " + click_count;
        button.setText(s);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        onClick();
    }
}
