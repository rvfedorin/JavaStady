/*
 * Created by Roman V. Fedorin
 */
package frv.org.chapter2_10.trees;

import java.awt.Component;
import java.awt.Font;
import java.lang.reflect.Modifier;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author Wolf
 */
public class ClassNameTreeCellRenderer extends DefaultTreeCellRenderer {
    
    private Font plainFont = null;
    private Font italicFont = null;

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus); 
        
        // get the user object
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Class<?> c = (Class<?>) node.getUserObject();
        
        // the first time, derive italic font from plane font
        if(plainFont == null) {
            plainFont = getFont();
            
            if(plainFont != null) {
                italicFont = plainFont.deriveFont(Font.ITALIC);
            }
        }
        
        // set font to italic if the class is abstract, plain otherwise 
        if((c.getModifiers() & Modifier.ABSTRACT) == 0) {
            setFont(plainFont);
        } else {
            setFont(italicFont);
        }
        return this;
    }
    
}
