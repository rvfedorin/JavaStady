/*
 * Created by Roman V. Fedorin
 */
package frv.org.chapter2_11.rasterImage;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import static java.lang.Thread.sleep;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Wolf
 */
public class RasterImageClass extends JFrame {

    private static final double XMIN = -2;
    private static final double XMAX = 2;
    private static final double YMIN = -2;
    private static final double YMAX = 2;
    private static final int MAX_ITERATIONS = 16;
    private static final int IMAGE_WIDTH = 400;
    private static final int IMAGE_HEIGHT = 400;
    private static JLabel label;

    public RasterImageClass() {
        BufferedImage bi = makeMandelbrot(IMAGE_WIDTH, IMAGE_HEIGHT);
        label = new JLabel(new ImageIcon(bi));
        add(label);
        pack();
    }

    private BufferedImage makeMandelbrot(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        WritableRaster wr = image.getRaster();
        ColorModel model = image.getColorModel();

        Color fractalColor = Color.GRAY;
        int argb = fractalColor.getRGB();
        Object colorData = model.getDataElements(argb, null);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double a = XMIN + i * (XMAX - XMIN) / width;
                double b = YMIN + j * (YMAX - YMIN) / height;

                if (!escapeToInfinity(a, b)) {
                    wr.setDataElements(i, j, colorData);
                }

            }
        }
        return image;
    }

    private boolean escapeToInfinity(double a, double b) {

        double x = 0.0;
        double y = 0.0;

        int iterations = 0;
        while (x <= 2 && y <= 2 && iterations < MAX_ITERATIONS) {
            double xnew = x * x - y * y + a;
            double ynew = 2 * x * y + b;
            iterations++;
            x = xnew;
            y = ynew;
        }
        return (x > 2 || y > 2);
    }

    public void setLabel(int size) {
        BufferedImage bi = makeMandelbrot(size, size);
        label.setIcon(new ImageIcon(bi));
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            RasterImageClass frame = new RasterImageClass();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

        });
    }

}
