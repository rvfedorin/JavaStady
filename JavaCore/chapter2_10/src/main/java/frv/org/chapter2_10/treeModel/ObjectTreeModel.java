/*
 * Created by Roman V. Fedorin
 */
package frv.org.chapter2_10.treeModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;


/**
 *
 * @author Wolf
 */
class ObjectTreeModel implements TreeModel {
    private Variable root;
    private EventListenerList listenerList = new EventListenerList();

    public ObjectTreeModel() {
        root = null;
    }
    

    @Override
    public Object getRoot() {
        return root;
    }
    
    public void setRoot(Variable v) {
        Variable oldRoot = v;
        root = v;
        fireTreeStructureChanged(oldRoot);
    }

    @Override
    public Object getChild(Object parent, int index) {
        ArrayList fields = ((Variable) parent).getFields();
        Field f = (Field) fields.get(index);
        Object parentValue = ((Variable) parent).getValue();
        
        try {
            return new Variable(f.getType(), f.getName(), f.get(parentValue));
        } catch (IllegalAccessException ex) {
            return null;
        }
    }

    @Override
    public int getChildCount(Object parent) {
        return ((Variable) parent).getFields().size();
    }

    @Override
    public boolean isLeaf(Object node) {
        return getChildCount(node) == 0;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        int n = getChildCount(parent);
        for(int i = 0; i < n; i++) {
            if(getChild(parent, i).equals(child)) return i;
        }
        return -1;
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
        listenerList.add(TreeModelListener.class, l);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        listenerList.remove(TreeModelListener.class, l);
    }

    private void fireTreeStructureChanged(Variable oldRoot) {
        TreeModelEvent event = new TreeModelEvent(this, new Object[] {oldRoot});
        
        for(TreeModelListener l: listenerList.getListeners(TreeModelListener.class)) {
            l.treeStructureChanged(event);
        }
    }
    
}
