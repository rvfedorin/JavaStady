package chapter10;

import javax.swing.*;
import java.awt.*;

public class SimpleFrameTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            SimpleFrame frame = new SimpleFrame("Моё окно.");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
} // SimpleFrameTest

class SimpleFrame extends JFrame {
//    private final static int DEFAULT_WIDTH = 300;
//    private final static int DEFAULT_HEIGHT = 200;

    SimpleFrame() {
        setLocationByPlatform(true);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dimension = kit.getScreenSize();
        int scrWidth = dimension.width;
        int scrHeight = dimension.height;
        setSize(scrWidth/4, (int) (scrHeight/2.85));
    }

    SimpleFrame(String title) {
        this();
        setTitle(title);
    }
}
