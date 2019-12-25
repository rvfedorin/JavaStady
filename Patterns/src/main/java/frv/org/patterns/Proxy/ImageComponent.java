package frv.org.patterns.Proxy;

import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.JComponent;

/**
 *
 * @author wolf
 */
public class ImageComponent extends JComponent {

    Icon imageIcont;

    public ImageComponent(Icon Icont) {
        this.imageIcont = Icont;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = imageIcont.getIconWidth();
        int h = imageIcont.getIconHeight();
        int x = (800 - w) / 2;
        int y = (600 - h) / 2;
        imageIcont.paintIcon(this, g, x, y);
    }

    public void setIcon(Icon Icont) {
        this.imageIcont = Icont;
    }

}
