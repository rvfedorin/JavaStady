/*
 * Created by Roman V. Fedorin
 */
package frv.org.chapter2_11.stroke;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author Wolf
 */
public class StrokeTest {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new StrokeTestFrame();
            frame.setTitle("Stroke Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class StrokeTestFrame extends JFrame {

    private StrokeComponent canvas;
    private JPanel buttonPanel;

    public StrokeTestFrame() {
        canvas = new StrokeComponent();
        add(canvas, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3));
        add(buttonPanel, BorderLayout.NORTH);

        ButtonGroup bGroupe1 = new ButtonGroup();
        makeCapButton("But Cap", BasicStroke.CAP_BUTT, bGroupe1);
        makeCapButton("Round Cap", BasicStroke.CAP_ROUND, bGroupe1);
        makeCapButton("Square Cap", BasicStroke.CAP_SQUARE, bGroupe1);

        ButtonGroup bGroupe2 = new ButtonGroup();
        makeJoinButton("Mitter Join", BasicStroke.JOIN_MITER, bGroupe2);
        makeJoinButton("Bevel Join", BasicStroke.JOIN_BEVEL, bGroupe2);
        makeJoinButton("Round Join", BasicStroke.JOIN_ROUND, bGroupe2);

        ButtonGroup bGroupe3 = new ButtonGroup();
        makeDashButton("Solid line", false, bGroupe3);
        makeDashButton("Dashed line", true, bGroupe3);
    }

    private void makeCapButton(String label, int style, ButtonGroup group) {
        boolean selected = group.getButtonCount() == 0;
        JRadioButton button = new JRadioButton(label, selected);
        buttonPanel.add(button);
        group.add(button);
        button.addActionListener(e -> {
            canvas.setCap(style);
        });
        pack();
    }

    private void makeJoinButton(String label, int style, ButtonGroup group) {
        boolean selected = group.getButtonCount() == 0;
        JRadioButton button = new JRadioButton(label, selected);
        buttonPanel.add(button);
        group.add(button);
        button.addActionListener(e -> {
            canvas.setJoin(style);
        });
        pack();
    }

    private void makeDashButton(String label, boolean style, ButtonGroup group) {
        boolean selected = group.getButtonCount() == 0;
        JRadioButton button = new JRadioButton(label, selected);
        buttonPanel.add(button);
        group.add(button);
        button.addActionListener(e -> {
            canvas.setDash(style);
        });
        pack();
    }

} // class StrokeTestFrame

class StrokeComponent extends JComponent {

    private static final Dimension PREFFERED_SIZE = new Dimension(200, 300);
    private static final int SIZE = 10;

    private Point2D[] points;
    private int current;
    private float width;
    private int cap;
    private int join;
    private boolean dash;

    public StrokeComponent() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();
                for (int i = 0; i < points.length; i++) {
                    double x = points[i].getX() - SIZE / 2;
                    double y = points[i].getY() - SIZE / 2;
                    Rectangle2D rect = new Rectangle2D.Double(x, y, SIZE, SIZE);
                    if (rect.contains(p)) {
                        current = i;
                        return;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                current = -1;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (current == -1) {
                    return;
                }
                points[current] = e.getPoint();
                repaint();
            }
        });
        points = new Point2D[3];
        points[0] = new Point2D.Double(200, 100);
        points[1] = new Point2D.Double(100, 200);
        points[2] = new Point2D.Double(200, 200);
        current = -1;
        width = 8.0F;
    }

    {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();
                for (int i = 0; i < points.length; i++) {
                    double x = points[i].getX() - SIZE / 2;
                    double y = points[i].getY() - SIZE / 2;
                    Rectangle2D rect = new Rectangle2D.Double(x, y, SIZE, SIZE);
                    if (rect.contains(p)) {
                        current = i;
                        return;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                current = -1;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (current == -1) {
                    return;
                }
                points[current] = e.getPoint();
                repaint();
            }
        });
        current = -1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        GeneralPath path = new GeneralPath();
        path.moveTo((float) points[0].getX(), (float) points[0].getY());
        for (Point2D p1 : points) {
            path.lineTo((float) p1.getX(), (float) p1.getY());
        }
        BasicStroke stroke;
        if (dash) {
            float miterLimit = 10.0F;
            float[] dashPattern = {10F, 10F, 10F, 10F, 10F, 10F, 30F, 10F, 30F,
                10F, 30F, 10F, 10F, 10F, 10F, 10F, 10F, 30F};
            float dashPhase = 0;
            stroke = new BasicStroke(width, cap, join, miterLimit, dashPattern, dashPhase);
        } else {
            stroke = new BasicStroke(width, cap, join);
        }
        g2.setStroke(stroke);
        g2.draw(path);
    }

    void setCap(int c) {
        cap = c;
        repaint();
    }

    void setJoin(int j) {
        join = j;
        repaint();
    }

    void setDash(boolean d) {
        dash = d;
        repaint();
    }

    @Override
    public Dimension getPreferredSize() { return PREFFERED_SIZE;}
    
}
