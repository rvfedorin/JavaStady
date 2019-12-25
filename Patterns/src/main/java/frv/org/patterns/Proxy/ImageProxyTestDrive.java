package frv.org.patterns.Proxy;

import java.net.URL;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author wolf
 */
public class ImageProxyTestDrive {

    ImageComponent imageComponent;
    JFrame frame;
    JMenuBar menuBar;
    JMenu menu;
    HashMap<String, String> cds;

    public static void main(String[] args) throws Exception {
        ImageProxyTestDrive iptd = new ImageProxyTestDrive();
    }

    public ImageProxyTestDrive() throws Exception {
        this.frame = new JFrame("CD Cover Viewer");
        this.cds = new HashMap<>();

        cds.put("Buddha Bar", "http://images.amazon.com/images/P/B00009XBYK.01.LZZZZZZZ.jpg");
        cds.put("Ima", "http://images.amazon.com/images/P/B000005IRM.01.LZZZZZZZ.jpg");
        cds.put("Karma", "http://images.amazon.com/images/P/B000005DCB.01.LZZZZZZZ.gif");
        cds.put("MCMXC A.D.", "http://images.amazon.com/images/P/B000002URV.01.LZZZZZZZ.jpg");
        cds.put("Northern Exposure", "http://images.amazon.com/images/P/B000003SFN.01.LZZZZZZZ.jpg");
        cds.put("Selected Ambient Works, Vol. 2", "http://images.amazon.com/images/P/B000002MNZ.01.LZZZZZZZ.jpg");

        URL initialURL = this.getCDUrl("Selected Ambient Works, Vol. 2");
        menuBar = new JMenuBar();
        menu = new JMenu("Favorite CDs");
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        cds.keySet().stream()
                .map((title) -> new JMenuItem(title))
                .forEachOrdered((item) -> {
                    menu.add(item);
                    item.addActionListener(e -> {
                        imageComponent.setIcon(new ImageProxy(getCDUrl(e.getActionCommand())));
                        frame.repaint();
                    });
                });

        ImageProxy imageProxy = new ImageProxy(initialURL);
        imageComponent = new ImageComponent(imageProxy);
        frame.getContentPane().add(imageComponent);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public final URL getCDUrl(String name) {
        try {
            return new URL((String) cds.get(name));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
