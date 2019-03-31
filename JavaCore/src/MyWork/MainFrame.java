package MyWork;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
//import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import javax.swing.*;
import java.awt.*;

public class MainFrame {
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
    }
} // class MainFrame

class MainWindow extends JFrame {
    private final static int DEFAULT_WIDTH = 380;
    private final static int DEFAULT_HEIGHT = 380;
    public MainPanel mainPanel;
    public Customer customer;

    MainWindow(){
        try {
//            UIManager.setLookAndFeel(new NimbusLookAndFeel());
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
        } catch (Exception ex) {
            System.out.println(ex);
        }
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (dimension.getWidth() - getWidth())/3;
        int y = (int) (dimension.getHeight() - getHeight())/3;
        setLocation(x, y);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Работа с клиентами.");
//        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setResizable(false);

        this.createMenu();
        mainPanel = new MainPanel();
        add(mainPanel);

        // RUN BUTTON //
        JPanel buttonPanel = new JPanel();
        JButton runButton = new JButton("Выполнить");
        RunActionListener runActionListener = new RunActionListener(this);
        runButton.setPreferredSize(new Dimension(120, 30));
        runButton.addActionListener(runActionListener);
        buttonPanel.add(runButton);
        // END RUN BUTTON //

        add(buttonPanel, BorderLayout.SOUTH);
        pack();

    } // const

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu toolsMenu = new JMenu("Tools");
        menuBar.add(toolsMenu);
        JMenuItem workBDItem = toolsMenu.add("Работа с БД");
        JMenuItem multiDelVlanItem = toolsMenu.add("Массовое создание vlan");
        JMenuItem multiCreateVlanItem = toolsMenu.add("Массовое удаление vlan");

        JMenu switchMenu = new JMenu("Switch");
        menuBar.add(switchMenu);
        JMenuItem pathToSwItem = switchMenu.add("Путь до свитча");
        JMenuItem allConnectSwItem = switchMenu.add("Все подключения от свитча");

        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
        JMenuItem manualItem = helpMenu.add("Manual");
        JMenuItem aboutlItem = helpMenu.add("About");
        Image temp = new ImageIcon("ico.jpg").getImage();
        ImageIcon imageIcon = new ImageIcon(temp.getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        aboutlItem.setIcon(imageIcon);

    } // createMenu()
} // class MainWindow

class MainPanel extends JPanel {
    public MainPanelOptic opticPanel;
    private JPanel mbPanel;
    private JPanel rwrPanel;

    MainPanel() {
//        setLayout(new BorderLayout());
        opticPanel = new MainPanelOptic();
        mbPanel = new MainPanelMB();
        rwrPanel = new MainPanelRWR();

        add(opticPanel);
        add(mbPanel);
        add(rwrPanel);
    }
}
