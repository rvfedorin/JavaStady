/*
 * Created by Roman V. Fedorin
 */
package frv.org.chapter2_11.splashScreen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.SplashScreen;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/**
 *
 * @author Wolf
 */
public class SplashScreenTest {

    private static final int DEAFAULT_WIDTH = 300;
    private static final int DEAFAULT_HEIGHT = 300;

    private static SplashScreen splash;

    private static void drawOnSplash(int percent) {
        Rectangle bounds = splash.getBounds();
        Graphics2D g2 = splash.createGraphics();
        int height = 20;
        int x = 2;
        int y = bounds.height - height - 2;
        int width = bounds.width - 4;
        Color brightPurple = new Color(76, 36, 121);
        g2.setColor(brightPurple);
        g2.fillRect(x, y, width * percent / 100, height);
        splash.update();
    }

    private static void init1() {
        splash = SplashScreen.getSplashScreen();
        if (splash == null) {
            System.err.println("Did you specialy a splash image?");
            JOptionPane.showMessageDialog(null, "Did you specialy a splash image?");
            System.exit(1);
        }
        try {
            for (int i = 0; i <= 100; i++) {
                drawOnSplash(i);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
        }
    } // ** init1()

    private static void init2() {
        final Image image = new ImageIcon(splash.getImageURL()).getImage();

        final JFrame splashFrame = new JFrame();
        splashFrame.setUndecorated(true);

        final JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(image, 0, 0, null);
            }
        };

        final JProgressBar progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        panel.setLayout(new BorderLayout());
        panel.add(progressBar, BorderLayout.SOUTH);

        splashFrame.add(panel);
        splashFrame.setBounds(splash.getBounds());
        splashFrame.setVisible(true);

        new SwingWorker<Void, Integer>() {

            @Override
            protected Void doInBackground() throws Exception {
                try {
                    for (int i = 0; i <= 100; i++) {
                        publish(i);
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                }

                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                chunks.stream().map((chunk) -> {
                    progressBar.setString("Loading module " + chunk);
                    return chunk;
                }).map((chunk) -> {
                    progressBar.setValue(chunk);
                    return chunk;
                }).forEachOrdered((_item) -> {
                    panel.repaint();
                });
                
                
            }

            @Override
            protected void done() {
                splashFrame.setVisible(false);
                
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(DEAFAULT_WIDTH, DEAFAULT_HEIGHT);
                frame.setTitle("Splash test");
                frame.setVisible(true);
            }
        }.execute();
    } // ** init2()
    
    public static void main(String[] args) {
        init1();
        EventQueue.invokeLater(() -> init2());
    }
}
