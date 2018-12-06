import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PullAnimate {
    int x = 1;
    int y = 1;
    int i;
    MyDrawP drawP;

    public static void main(String[] args){
        PullAnimate gui = new PullAnimate();
        gui.go();

    }

    public void go(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawP = new MyDrawP();
        JButton button = new JButton("Start");
        button.addActionListener(new StartButton());
        frame.getContentPane().add(BorderLayout.CENTER, drawP);
        frame.getContentPane().add(BorderLayout.SOUTH, button);
        frame.setSize(500, 270);
        frame.setVisible(true);

        changXY();
        System.out.println("END");

    }

    private void changXY(){
        for (i = 0; i < 124; i++, y++, x++){
            x++;
            drawP.repaint();


            try{
                Thread.sleep(50);
            } catch (Exception ex) {}
        }
    }

    class MyDrawP extends JPanel{
        public void paintComponent(Graphics g){
            g.setColor(Color.white);
            g.fillRect(0, 0, 500, 250);
            g.setColor(Color.blue);
            g.fillRect(x, y, 500-x*2, 250-y*2);
        }
    }

    class StartButton implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            x = 1;
            y = 1;
            i = 1;
//            changXY();
        }
    }
}
