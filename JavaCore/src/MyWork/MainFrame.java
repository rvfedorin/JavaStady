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
        JMenuItem workBDItem = new JMenuItem("Работа с БД");
        JMenuItem multiDelVlanItem = new JMenuItem("Массовое создание vlan");
        JMenuItem multiCreateVlanItem = new JMenuItem("Массовое удаление vlan");
        toolsMenu.add(workBDItem);
        toolsMenu.add(multiDelVlanItem);
        toolsMenu.add(multiCreateVlanItem);

        JMenu switchMenu = new JMenu("Switch");
        menuBar.add(switchMenu);
        JMenuItem pathToSwItem = new JMenuItem("Путь до свитча");
        JMenuItem allConnectSwItem = new JMenuItem("Все подключения от свитча");
        switchMenu.add(pathToSwItem);
        switchMenu.add(allConnectSwItem);

        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
        JMenuItem manualItem = new JMenuItem("Manual");
        JMenuItem aboutlItem = new JMenuItem("About");
        helpMenu.add(manualItem);
        helpMenu.add(aboutlItem);

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
