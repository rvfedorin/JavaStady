/*
 * Created by Roman V. Fedorin
 */
package frv.org.chapter2_10.trees;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Wolf
 */
public class ClassTreeFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;
    private static final String PATH = "../resources/";

    private DefaultMutableTreeNode root;
    private DefaultTreeModel model;
    private JTree tree;
    private JTextField jTextField;
    private JTextArea jTextArea;

    public ClassTreeFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setTitle(getClass().getSimpleName());

        // the root of the class tree is Object
        root = new DefaultMutableTreeNode(java.lang.Object.class);
        model = new DefaultTreeModel(root);
        tree = new JTree(model);

        // add this class to populate the tree with some data
        addClass(getClass());

        // set up node icons
        ClassNameTreeCellRenderer renderer = new ClassNameTreeCellRenderer();
        renderer.setClosedIcon(
                new ImageIcon(getClass().getResource(PATH + "red-ball.gif")));

        renderer.setOpenIcon(
                new ImageIcon(getClass().getResource(PATH + "yellow-ball.gif")));

        renderer.setLeafIcon(
                new ImageIcon(getClass().getResource(PATH + "blue-ball.gif")));

        tree.setCellRenderer(renderer);

        // set up selection mode
        tree.addTreeSelectionListener(e -> {
            // the user selected other node -- update description
            TreePath path = tree.getSelectionPath();
            if (path == null) {
                return;
            }
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
            Class<?> c = (Class<?>) selectedNode.getUserObject();
            String description = getFieldDescription(c);
            jTextArea.setText(description);
        });

        int selectMode = TreeSelectionModel.SINGLE_TREE_SELECTION;
        tree.getSelectionModel().setSelectionMode(selectMode);

        jTextArea = new JTextArea();

        // add tree and text area
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(new JScrollPane(tree));
        panel.add(new JScrollPane(jTextArea));

        add(panel, BorderLayout.CENTER);

        addTextField();
    }

    private DefaultMutableTreeNode addClass(Class<?> aClass) {
        // add a new class to the tree
        
        // skip non-class types
        if(aClass.isInterface()|| aClass.isPrimitive()) {
            return null;
        }
        
        // if the class is already in the tree, return its node
        DefaultMutableTreeNode node = findUserObject(aClass);
        if(node != null) {
            return node;
        }
        
        // class is not present -- first add class parent recursively
        Class<?> parentClass = aClass.getSuperclass();
        DefaultMutableTreeNode parent;
        if(parentClass == null) {
            parent = root;
        } else {
            parent = addClass(parentClass);
        }
        
        // add the class as a child to the parent
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(aClass);
        model.insertNodeInto(newNode, parent, parent.getChildCount());
        
        // make the node visible
        TreePath path = new TreePath(model.getPathToRoot(newNode));
        tree.makeVisible(path);
        
        return newNode;
    }

    private String getFieldDescription(Class<?> c) {
        // use reflection to find types and names of fields
        StringBuilder sb = new StringBuilder();
        Field[] fields = c.getDeclaredFields();
        for(Field field: fields) {
            if((field.getModifiers() & Modifier.STATIC) != 0) {
                sb.append("static ");
            }
            sb.append(field.getType().getSimpleName());
            sb.append(" ");
            sb.append(field.getName());
            sb.append("\n");
        }
        
        return sb.toString();
    }

    /*
    * add the text field and "Add" button to add a new class
     */
    private void addTextField() {
        JPanel panel = new JPanel();

        ActionListener actionListener = event -> {
            // add a class whose name is in the text field
            try {
                String text = jTextField.getText();
                addClass(Class.forName(text));

                jTextField.setText("");

            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Class not found\n" + e.getMessage());
            }
        };

        // new class name are typed into this text field
        jTextField = new JTextField(20);
        jTextField.addActionListener(actionListener);
        panel.add(jTextField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(actionListener);
        panel.add(addButton);

        add(panel, BorderLayout.SOUTH);
    }

    public DefaultMutableTreeNode findUserObject(Object o) {
        // find the node containing a user object
        Enumeration<TreeNode> e = (Enumeration<TreeNode>) root.breadthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
            if (node.getUserObject().equals(o)) {
                return node;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        JFrame frame = new ClassTreeFrame();
        frame.setVisible(true);
        
    }
}
