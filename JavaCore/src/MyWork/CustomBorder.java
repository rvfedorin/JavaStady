package MyWork;

import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

@SuppressWarnings("serial")
public class CustomBorder extends AbstractBorder {
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y,
                            int width, int height) {
        super.paintBorder(c, g, x, y, width, height);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setPaint(Color.LIGHT_GRAY);
        Shape shape = new RoundRectangle2D.Float(0, 0, c.getWidth()-1, c.getHeight()-1,8, 8);
        g2d.draw(shape);
//        g2d.setColor(Color.gray);
//        g2d.setStroke(new BasicStroke(2));
//        g2d.drawRoundRect(0, 0, c.getWidth()-1, c.getHeight()-1, 8, 8);
    }
}
