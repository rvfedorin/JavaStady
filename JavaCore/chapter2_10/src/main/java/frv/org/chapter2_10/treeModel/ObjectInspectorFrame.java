/*
 * Created by Roman V. Fedorin
 */
package frv.org.chapter2_10.treeModel;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;

/**
 *
 * @author Wolf
 */
public class ObjectInspectorFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;
    private JTree tree;

    public ObjectInspectorFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setTitle(getClass().getSimpleName());
        
        // we inspect this frame object
        Variable v = new Variable(getClass(), "this", this);
        ObjectTreeModel model = new ObjectTreeModel();
        model.setRoot(v);
        
        // construct and show tree 
        tree = new JTree(model);
        add(new JScrollPane(tree), BorderLayout.CENTER);
        
    }

    public static void main(String[] args) {
        JFrame frame = new ObjectInspectorFrame();
        frame.setVisible(true);
    }
}
