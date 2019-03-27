package MyWork;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import javax.swing.*;
//import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class MainFrame {
    public static void main(String[] args) {
        System.out.println("MainFrame");
        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
    }
} // class MainFrame

class MainWindow extends JFrame {
    private final static int DEFAULT_WIDTH = 420;
    private final static int DEFAULT_HEIGHT = 380;
    private JPanel mainPanel;

    MainWindow(){
        try {
//            UIManager.setLookAndFeel(new NimbusLookAndFeel());
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
        } catch (Exception ex) {
            System.out.println(ex);
        }
        mainPanel = new MainPanel();
        add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Работа с клиентами.");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    } // const
} // class MainWindow

class MainPanel extends JPanel {
    private JPanel opticPanel;
    private JPanel mbPanel;
    private JPanel rwrPanel;

    MainPanel() {
        opticPanel = new MainPanelOptic();
        mbPanel = new MainPanelMB();
        rwrPanel = new MainPanelRWR();

        add(opticPanel);
        add(mbPanel);
        add(rwrPanel);
    }
}
