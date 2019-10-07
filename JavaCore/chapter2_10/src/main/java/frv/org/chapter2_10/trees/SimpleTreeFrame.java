/*
 * Created by Roman V. Fedorin
 */
package frv.org.chapter2_10.trees;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;

/**
 *
 * @author Wolf
 */
public class SimpleTreeFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public SimpleTreeFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        JTree tree = new SimpleTree().getTree();
        this.add(new JScrollPane(tree));
    }

    public static void main(String[] args) {
        JFrame frame = new SimpleTreeFrame();
        frame.setVisible(true);
    }
}
