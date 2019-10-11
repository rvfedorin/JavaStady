/*
 * Created by Roman V. Fedorin
 */
package frv.org.chapter2_10.trees;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Wolf
 */
public class SimpleTree {
    private JTree tree;

    public SimpleTree() {
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

        DefaultTreeModel model = new DefaultTreeModel(root);
        tree = new JTree(model);
    }

    public JTree getTree() {
        return tree;
    }
    
    public DefaultTreeModel getModel() {
        return (DefaultTreeModel) tree.getModel();
    }
    
}
