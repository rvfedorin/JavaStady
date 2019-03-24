package chapter10;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class SimpleFrameTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            SimpleFrame frame = new SimpleFrame("Моё окно.");

            frame.add(new TextLabel());
//            frame.pack();
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
        setSize(scrWidth/4, (int) (scrHeight/2.85));
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
        String message = "Строка в окне!!";

        Graphics2D g2 = (Graphics2D) g;
        Font sansBold14 = new Font("SansSerif", Font.BOLD, 14);
        g2.setFont(sansBold14);
        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D bounds = sansBold14.getStringBounds(message, context);

        g2.drawRect((int) bounds.getX() + X,
                    (int) bounds.getY() + Y,
                       (int) bounds.getWidth(),
                       (int) bounds.getHeight());
        g2.drawString(message, X, Y);
    }

    @Override
    public Dimension getPreferredSize() {

        return new Dimension(X*5, Y*2);
    }
}  // class TextLabel

class MyImage extends JComponent {
    @Override
    public void paintComponent(Graphics g) {
        Image img = new ImageIcon("ico.jpg").getImage();
        g.drawImage(img, 150, 150, null);
    }
}
