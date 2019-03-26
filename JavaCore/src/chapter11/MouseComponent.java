package chapter11;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class MouseComponent extends JComponent {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    private static final int SIDELENGHT = 10;
    private ArrayList<Rectangle2D> squares;
    private Rectangle2D current;

    MouseComponent() {
        squares = new ArrayList<>();
        current = null;

        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMotionHandler());
    } // const

    @Override
    public Dimension getPreferredSize() {return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);}

    @Override
    public void paintComponent(Graphics g) {
         Graphics2D graphics2D = (Graphics2D) g;

        for(Rectangle2D r: squares) {
            graphics2D.draw(r);
        }
    } // paintComponent

    public Rectangle2D find(Point2D p) {
        for(Rectangle2D r: squares) {
            if (r.contains(p)) {
                return r;
            }
        } // for
        return null;
    } // find()

    public void add(Point2D p) {
        double x = p.getX();
        double y = p.getY();

        current = new Rectangle2D.Double(x - SIDELENGHT/2, y - SIDELENGHT/2, SIDELENGHT, SIDELENGHT);
        squares.add(current);
        repaint();
    }

    public void remove(Rectangle2D s) {
        if(s == null) return;
        if (s == current) current = null;
        squares.remove(s);
        repaint();
    } // remove()

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent event) {
            current = find(event.getPoint());
            if (current == null) {
                MouseComponent.this.add(event.getPoint());
            }
        } // mousePressed()

        @Override
        public void mouseClicked(MouseEvent event) {
            current = find(event.getPoint());
            if (current != null && event.getClickCount() >= 2) {
                MouseComponent.this.remove(current);
            }
        } // mouseClicked()
    } // class MouseHandler

    private class MouseMotionHandler implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent e) {
            if (current != null) {
                if (e.getModifiersEx() == InputEvent.BUTTON1_DOWN_MASK) {
                    int dx = e.getX();
                    int dy = e.getY();
                    int width = (int) current.getWidth();
                    int height = (int) current.getHeight();

                    current.setFrame(dx, dy, width, height);

                } else if (e.getModifiersEx() == InputEvent.BUTTON3_DOWN_MASK) {
                    int dx = e.getX();
                    int dy = e.getY();
                    int x = (int) current.getX();
                    int y = (int) current.getY();

                    current.setFrame(x, y, dx-x, dy-y);
                }
                repaint();
            } // if (current != null)
        } // close mouseDragged()

        @Override
        public void mouseMoved(MouseEvent e) {
            if (MouseComponent.this.find(e.getPoint()) == null) {
                setCursor(Cursor.getDefaultCursor());
            } else {
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            }
        } // close mouseMover()
    } // class MouseMotionHandler
} // class MouseComponent



