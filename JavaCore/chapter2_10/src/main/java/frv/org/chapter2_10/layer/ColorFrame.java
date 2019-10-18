/*
 * Created by Roman V. Fedorin
 */
package frv.org.chapter2_10.layer;

import java.awt.AWTEvent;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.LayerUI;

/**
 *
 * @author Wolf
 */
public class ColorFrame extends JFrame {

    private JPanel panel;
    private JTextField redField;
    private JTextField blueField;
    private JTextField greenField;

    public ColorFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("ColorFrame");

        panel = new JPanel();

        panel.add(new JLabel("Red: "));
        redField = new JTextField("255", 3);
        panel.add(redField);

        panel.add(new JLabel("Blue: "));
        blueField = new JTextField("255", 3);
        panel.add(blueField);

        panel.add(new JLabel("Green: "));
        greenField = new JTextField("255", 3);
        panel.add(greenField);

        LayerUI<JPanel> layerUI = new PanelLayer();
        JLayer<JPanel> layer = new JLayer<>(panel, layerUI);

        add(layer);
        pack();
    }

    class PanelLayer extends LayerUI<JPanel> {

        @Override
        public void installUI(JComponent c) {
            super.installUI(c); 
            ((JLayer<?>) c).setLayerEventMask(AWTEvent.KEY_EVENT_MASK | AWTEvent.FOCUS_EVENT_MASK);
        }

        @Override
        public void uninstallUI(JComponent c) {
            ((JLayer<?>) c).setLayerEventMask(0);
            super.uninstallUI(c);
        }

        @Override
        protected void processKeyEvent(KeyEvent e, JLayer<? extends JPanel> l) {
            l.repaint();
        }

        @Override
        protected void processFocusEvent(FocusEvent e, JLayer<? extends JPanel> l) {
            if (e.getID() == FocusEvent.FOCUS_GAINED) {
                Component c = e.getComponent();
                c.setFont(getFont().deriveFont(Font.BOLD));
            }

            if (e.getID() == FocusEvent.FOCUS_LOST) {
                Component c = e.getComponent();
                c.setFont(getFont().deriveFont(Font.PLAIN));
            }
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            super.paint(g, c);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .3f));
            try {
                int red = Integer.parseInt(redField.getText().trim());
                int green = Integer.parseInt(greenField.getText().trim());
                int blue = Integer.parseInt(blueField.getText().trim());
                g2.setPaint(new Color(red, green, blue));
                g2.fillRect(0, 0, c.getWidth(), c.getHeight());
                g2.dispose();
            } catch (Exception e) {
            }
        }

    }

    public static void main(String[] args) {
        ColorFrame frame = new ColorFrame();
        frame.setVisible(true);
    }

}
