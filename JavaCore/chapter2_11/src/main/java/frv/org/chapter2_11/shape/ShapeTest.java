/*
 * Created by Roman V. Fedorin
 */
package frv.org.chapter2_11.shape;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Arc2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author Wolf
 */
public class ShapeTest {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new ShapeTestFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Shape Test");
            frame.setVisible(true);
        });
    }
}

class ShapeTestFrame extends JFrame {

    public ShapeTestFrame() {
        int hScr = Toolkit.getDefaultToolkit().getScreenSize().height;
        int wScr = Toolkit.getDefaultToolkit().getScreenSize().width;
        setLocation(wScr/3, hScr/3);
        
        final ShapeComponent comp = new ShapeComponent();
        add(comp, BorderLayout.CENTER);
        final JComboBox<ShapeMaker> comboBox = new JComboBox();
        comboBox.addItem(new LineMaker());
        comboBox.addItem(new RectangleMaker());
        comboBox.addItem(new RoundRectangleMaker());
        comboBox.addItem(new EllipseMaker());
        comboBox.addItem(new ArcMaker());
        comboBox.addItem(new PolygonMaker());
        comboBox.addItem(new QuadCurveMaker());
        comboBox.addItem(new CubicCurveMaker());
        comboBox.addActionListener(e -> {
            ShapeMaker shapeMaker = comboBox.getItemAt(comboBox.getSelectedIndex());
            comp.setShapeMaker(shapeMaker);
        });
        add(comboBox, BorderLayout.NORTH);
        comp.setShapeMaker((ShapeMaker) comboBox.getItemAt(0));
        pack();
    }
} // class ShapeTestFrame

/*
* This class draws a shape and allows the user to move the points that define it
*/
class ShapeComponent extends JComponent {
    private Point2D[] points;
    private static final Dimension PREFERRED_SIZE = new Dimension(200, 300);
    private static Random generator = new Random();
    private static int SIZE = 10;
    private int current;
    private ShapeMaker shapeMaker;

    public ShapeComponent() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();
                for(int i = 0; i < points.length; i++) {
                    double x = points[i].getX() - SIZE /2;
                    double y = points[i].getY() - SIZE /2;
                    Rectangle2D rect = new Rectangle2D.Double(x, y, SIZE, SIZE);
                    if(rect.contains(p)) {
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
                if(current == -1) return;
                points[current] = e.getPoint();
                repaint();
            }
        });
        current = -1;
    }

    void setShapeMaker(ShapeMaker shapeMaker) {
        this.shapeMaker = shapeMaker;
        int n = this.shapeMaker.getPointCount();
        points = new Point2D[n];
        for(int i = 0; i < n; i++) {
            double x = generator.nextDouble() * getWidth();
            double y = generator.nextDouble() * getHeight();
            points[i] = new Point2D.Double(x, y);
        }
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        if (points == null) return;
        Graphics2D g2 = (Graphics2D) g;
        for (Point2D point : points) {
            double x = point.getX() - SIZE /2;
            double y = point.getY() - SIZE /2;
            g2.fill(new Rectangle2D.Double(x, y, SIZE, SIZE));
        }
        g2.draw(shapeMaker.makeShape(points));
    }
    
    @Override
    public Dimension getPreferredSize() {return PREFERRED_SIZE;}

} // class ShapeComponent

abstract class ShapeMaker {
    private int pointCount;

    public ShapeMaker(int pointCount) {
        this.pointCount = pointCount;
    }
    
    int getPointCount() {
        return pointCount;
    }
    
    public abstract Shape makeShape(Point2D[] p);
    
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

} // class ShapeMaker

class LineMaker extends ShapeMaker {

    public LineMaker() {
        super(2);
    }

    @Override
    public Shape makeShape(Point2D[] p) {
        return new Line2D.Double(p[0], p[1]);
    }
} // class LineMaker

class RectangleMaker extends ShapeMaker {

    public RectangleMaker() {
        super(2);
    }

    @Override
    public Shape makeShape(Point2D[] p) {
        Rectangle2D r = new Rectangle2D.Double();
        r.setFrameFromDiagonal(p[0], p[1]);
        return r;
    }
    
} // class RectangleMaker

class RoundRectangleMaker extends ShapeMaker {

    public RoundRectangleMaker() {
        super(2);
    }

    @Override
    public Shape makeShape(Point2D[] p) {
        RoundRectangle2D r = new RoundRectangle2D.Double(0, 0, 0, 0, 20, 20);
        r.setFrameFromDiagonal(p[0], p[1]);
        return r;
    }
    
} // class RoundRectangleMaker

class EllipseMaker extends ShapeMaker {

    public EllipseMaker() {
        super(2);
    }

    @Override
    public Shape makeShape(Point2D[] p) {
        Ellipse2D r = new Ellipse2D.Double();
        r.setFrameFromDiagonal(p[0], p[1]);
        return r;
    }
    
} // class EllipseMaker

class ArcMaker extends ShapeMaker {

    public ArcMaker() {
        super(4);
    }

    @Override
    public Shape makeShape(Point2D[] p) {
        double centerX = (p[0].getX() + p[1].getX()) / 2;
        double centerY = (p[0].getY() + p[1].getY()) / 2;
        double width = Math.abs(p[1].getX() - p[0].getX());
        double height = Math.abs(p[1].getY() - p[0].getY());
        
        double skewedStartAngle = Math.toDegrees(
                Math.atan2(-(p[2].getY() - centerY) * width,
                (p[2].getX() - centerX) * height));
        double skewedEndAngle = Math.toDegrees(
                Math.atan2(-(p[3].getY() - centerY) * width,
                (p[3].getX() - centerX) * height));
        
        double skewedAngleDifference = skewedEndAngle - skewedStartAngle;
        
        if(skewedStartAngle < 0) skewedStartAngle += 360;
        if(skewedAngleDifference < 0) skewedAngleDifference += 360;
        
        Arc2D a = new Arc2D.Double(0, 0, 0, 0, skewedStartAngle, skewedAngleDifference, Arc2D.OPEN);
        a.setFrameFromDiagonal(p[0], p[1]);
        
        GeneralPath g = new GeneralPath();
        g.append(a, false);
        
        Rectangle2D r = new Rectangle2D.Double();
        r.setFrameFromDiagonal(p[0], p[1]);
        g.append(r, false);
        
        Point2D center = new Point2D.Double(centerX, centerY);
        g.append(new Line2D.Double(center, p[2]), false);
        g.append(new Line2D.Double(center, p[3]), false);
        
        return g;
    }
    
} // class ArcMaker

class PolygonMaker extends ShapeMaker {

    public PolygonMaker() {
        super(6);
    }

    @Override
    public Shape makeShape(Point2D[] p) {
        GeneralPath s = new GeneralPath();
        s.moveTo((float) p[0].getX(), (float) p[0].getY());
        for (Point2D p1 : p) {
            s.lineTo((float) p1.getX(), (float) p1.getY());
        }
        s.closePath();
        return s;
    }
    
} // class PolygonMaker

class QuadCurveMaker extends ShapeMaker {

    public QuadCurveMaker() {
        super(3);
    }

    @Override
    public Shape makeShape(Point2D[] p) {
        return new QuadCurve2D.Double(p[0].getX(), p[0].getY(),
        p[1].getX(), p[1].getY(),
        p[2].getX(), p[2].getY());
    }
    
} // class QuadCurveMaker

class CubicCurveMaker extends ShapeMaker {

    public CubicCurveMaker() {
        super(4);
    }

    @Override
    public Shape makeShape(Point2D[] p) {
        return new CubicCurve2D.Double(p[0].getX(), p[0].getY(),
        p[1].getX(), p[1].getY(),
        p[2].getX(), p[2].getY(),
        p[3].getX(), p[3].getY());
    }
    
} // class CubicCurveMaker