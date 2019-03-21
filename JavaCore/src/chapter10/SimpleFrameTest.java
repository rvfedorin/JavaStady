package chapter10;

import javax.swing.*;
import java.awt.*;

public class SimpleFrameTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            SimpleFrame frame = new SimpleFrame("Моё окно.");
            frame.add(new TextLabel());
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
} // SimpleFrameTest

class SimpleFrame extends JFrame {
//    private final static int DEFAULT_WIDTH = 300;
//    private final static int DEFAULT_HEIGHT = 200;

    SimpleFrame() {
        setIconImage(new ImageIcon("ico.jpg").getImage());
        setLocationByPlatform(true);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dimension = kit.getScreenSize();
        int scrWidth = dimension.width;
        int scrHeight = dimension.height;
//        setSize(scrWidth/4, (int) (scrHeight/2.85));
    }

    SimpleFrame(String title) {
        this();
        setTitle(title);
    }
} // class SimpleFrame

class TextLabel extends JComponent {
    private final static int X = 70;
    private final static int Y = 100;

    @Override
    public void paintComponent(Graphics g) {
        g.drawString("Строка в окне!!", X, Y);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(X*5, Y*2);
    }
}
