/*
 * Created by Roman V. Fedorin
 */
package frv.org.classloader;

import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Wolf
 */
public class ClassLoaderTest {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame classLoaderFrame = new ClassLoaderFrame();
            classLoaderFrame.setTitle("Class Loader Test");
            classLoaderFrame.setVisible(true);
        });
    }

} // class ClassLoaderTest

class ClassLoaderFrame extends JFrame {

    private JTextField keyField = new JTextField("3", 4);
    private JTextField nameField = new JTextField("Calc", 20);
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public ClassLoaderFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);

        add(new JLabel("Class "), new GBC(0, 0).anchor("east"));
        add(new JLabel("Key "), new GBC(0, 1).anchor("east"));
        add(nameField, new GBC(1, 0).anchor("west").weight(100, 0));
        add(keyField, new GBC(1, 1).anchor("west").weight(100, 0));
        
        JButton loadButton = new JButton("Load");
        add(loadButton, new GBC(0, 2).gridWH(2, 1));
        loadButton.addActionListener((ActionEvent e) -> {
            runClass(nameField.getText(), keyField.getText());
        });

        pack();
    } // ** constructor
    
    public void runClass(String name, String key) {
        try {
            ClassLoader loader = new CryptoClassLoader(Integer.parseInt(key));
            Class<?> c = loader.loadClass(name);
            Method m = c.getMethod("main", String[].class);
            m.invoke(null, (Object) new String[] {});
        } catch (Throwable ex) {
            Logger.getLogger(ClassLoaderFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex);
        }
        
    } // ** runClass()

} // ** ClassLoaderFrame

class CryptoClassLoader extends ClassLoader {
    private int key;

    public CryptoClassLoader(int key) {
        this.key = key;
    }
    
    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] classBytes = null;
            classBytes = loadClassBytes(name);
            Class<?> cl = defineClass(name, classBytes, 0, classBytes.length);
            if(cl == null) throw new ClassNotFoundException(name);
            return cl;
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new ClassNotFoundException(name);
        }
    } // ** findClass()
    
    public byte[] loadClassBytes(String name) throws IOException {
        String cname = name.replace('.', '/') + ".crypt";
        cname = "resources/" + cname;
        cname = this.getClass().getResource(cname).getPath();
        cname = cname.substring(cname.indexOf(":")+1, cname.length());
        byte[] bytes = Files.readAllBytes(Paths.get(cname));
        for(int i = 0; i < bytes.length; i++) {
            byte temp = bytes[i];
            bytes[i] = (byte)(bytes[i] - key);
        }
        return bytes;
    }
    
} // ** CryptoClassLoader
