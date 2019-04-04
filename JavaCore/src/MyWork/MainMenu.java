package MyWork;

import javax.swing.*;
import java.awt.*;

public class MainMenu {
    private JFrame mainFrame;
    private JMenuBar menuBar;

    private JMenu toolsMenu;
    private JMenuItem workBDItem;
    private JMenuItem multiDelVlanItem;
    private JMenuItem multiCreateVlanItem;

    private JMenu switchMenu;
    private JMenuItem pathToSwItem;
    private JMenuItem allConnectSwItem;

    private JMenu helpMenu;
    private JMenuItem manualItem;
    private JMenuItem aboutItem;

    MainMenu(JFrame frame) {
        mainFrame = frame;
        createMenu();
    }

    private void createMenu() {
        menuBar = new JMenuBar();
        mainFrame.setJMenuBar(menuBar);

        toolsMenu = new JMenu("Tools");
        menuBar.add(toolsMenu);
        workBDItem = toolsMenu.add("Работа с БД");
        multiDelVlanItem = toolsMenu.add("Массовое создание vlan");
        multiCreateVlanItem = toolsMenu.add("Массовое удаление vlan");

        switchMenu = new JMenu("Switch");
        menuBar.add(switchMenu);
        pathToSwItem = switchMenu.add("Путь до свитча");
        pathToSwItem.setEnabled(false);
        allConnectSwItem = switchMenu.add("Все подключения от свитча");
        allConnectSwItem.setEnabled(false);

        helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
        manualItem = helpMenu.add("Manual");
        aboutItem = helpMenu.add("About");
        Image temp = new ImageIcon("ico.jpg").getImage();
        ImageIcon imageIcon = new ImageIcon(temp.getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        aboutItem.setIcon(imageIcon);

    } // createMenu()
}
