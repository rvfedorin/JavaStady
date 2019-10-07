/*
 * Created by Roman V. Fedorin
 */
package frv.org.chapter2_10.trees;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

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
        
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("World");
        DefaultMutableTreeNode country = new DefaultMutableTreeNode("USA");
        DefaultMutableTreeNode state = new DefaultMutableTreeNode("California");
        DefaultMutableTreeNode city = new DefaultMutableTreeNode("San Jose");
        root.add(country);
        country.add(state);
        state.add(city);
        city = new DefaultMutableTreeNode("Cupertino");
        state.add(city);
        state = new DefaultMutableTreeNode("Michigan");
        city = new DefaultMutableTreeNode("An Arbor");
        country.add(state);
        state.add(city);
        
        country = new DefaultMutableTreeNode("Germany");
        root.add(country);
        state = new DefaultMutableTreeNode("Schleswig-Holstein");
        country.add(state);
        city = new DefaultMutableTreeNode("Kiel");
        state.add(city);
        
        JTree tree = new JTree(root);
        this.add(new JScrollPane(tree));
    }
    
    
    
    public static void main(String[] args) {
        JFrame frame = new SimpleTreeFrame();
        frame.setVisible(true);
    }
}
