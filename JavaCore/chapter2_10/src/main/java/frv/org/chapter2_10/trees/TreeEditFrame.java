/*
 * Created by Roman V. Fedorin
 */
package frv.org.chapter2_10.trees;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Wolf
 */
public class TreeEditFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
    private SimpleTree tree;

    public TreeEditFrame() {
        this.setTitle("TreeEditFrame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        tree = new SimpleTree();
        tree.getTree().setEditable(true);
        this.add(new JScrollPane(tree.getTree()), BorderLayout.CENTER);

        makeButtons();
    }

    private void makeButtons() {
        JPanel panel = new JPanel();
        JButton addSiblingButton = new JButton("Add Sibling");
        addSiblingButton.addActionListener(e -> {
            DefaultMutableTreeNode selectedNode
                    = (DefaultMutableTreeNode) tree.getTree().getLastSelectedPathComponent();

            if (selectedNode == null) {
                return;
            }
            DefaultMutableTreeNode parent
                    = (DefaultMutableTreeNode) selectedNode.getParent();

            if (parent == null) {
                return;
            }
            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("New");

            int selectedIndex = parent.getIndex(selectedNode);
            tree.getModel().insertNodeInto(newNode, parent, selectedIndex + 1);

            TreeNode[] nodes = tree.getModel().getPathToRoot(newNode);
            TreePath path = new TreePath(nodes);
            tree.getTree().scrollPathToVisible(path);
        });
        panel.add(addSiblingButton);

        JButton addChildButton = new JButton("Add Child");
        addChildButton.addActionListener(e -> {
            DefaultMutableTreeNode selectedNode
                    = (DefaultMutableTreeNode) tree.getTree().getLastSelectedPathComponent();

            if (selectedNode == null) {
                return;
            }

            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("New");
            tree.getModel().insertNodeInto(newNode,
                    selectedNode,
                    selectedNode.getChildCount());

            // display new Node
            TreeNode[] nodes = tree.getModel().getPathToRoot(newNode);
            TreePath path = new TreePath(nodes);
            tree.getTree().scrollPathToVisible(path);
        });
        panel.add(addChildButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            DefaultMutableTreeNode selectedNode
                    = (DefaultMutableTreeNode) tree.getTree().getLastSelectedPathComponent();

            if (selectedNode != null && selectedNode.getParent() != null) {
                tree.getModel().removeNodeFromParent(selectedNode);
            }
        });
        panel.add(deleteButton);
        this.add(panel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        JFrame frame = new TreeEditFrame();
        frame.setVisible(true);
    }

}
