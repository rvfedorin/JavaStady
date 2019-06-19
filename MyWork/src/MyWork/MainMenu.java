package MyWork;


import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;

import static MyWork.Config.*;

import MyWork.ActionsMenu.*;

public class MainMenu {
    private MainWindow mainFrame;
    private JMenuBar menuBar;

    private JMenu toolsMenu;
    private JMenuItem workBDItem;
    private JMenuItem clientSettings;
    private JMenuItem multiDelVlanItem;
    private JMenuItem multiCreateVlanItem;
    private JMenuItem findCustomer;

    private JMenu switchMenu;
    private JMenuItem pathToSwItem;
    private JMenuItem allConnectSwItem;

    private JMenu view;
    JMenuItem logPrint;
    JMenuItem currentRunning;

    private JMenu helpMenu;
    private JMenuItem manualItem;
    private JMenuItem aboutItem;

    MainMenu(MainWindow frame) {
        mainFrame = frame;
        createMenu();
    }

    private void createMenu() {
        menuBar = new JMenuBar();
        mainFrame.setJMenuBar(menuBar);

        toolsMenu = new JMenu("Tools");
        menuBar.add(toolsMenu);
        workBDItem = toolsMenu.add("Работа с БД");
        toolsMenu.addSeparator();
        clientSettings = toolsMenu.add("Полные настройки клиента.");
        clientSettings.addActionListener(e -> new ClientFullSettings(mainFrame.eventPrintFrame));
        findCustomer = toolsMenu.add("Найти клиента.");
        findCustomer.addActionListener(e ->
                new FindCustomer(mainFrame.eventPrintFrame, mainFrame.authDialog.getPass())
        );
        toolsMenu.addSeparator();
        multiDelVlanItem = toolsMenu.add("Массовое создание vlan");
        multiCreateVlanItem = toolsMenu.add("Массовое удаление vlan");

        switchMenu = new JMenu("Switch");
//        switchMenu.addMenuListener(new SwitchMenuListener());
        menuBar.add(switchMenu);
        pathToSwItem = switchMenu.add("Путь до свитча");
        pathToSwItem.addActionListener(e ->
                new FullPathToSw(mainFrame.eventPrintFrame, mainFrame.authDialog.getPass())
        );
        allConnectSwItem = switchMenu.add("Все подключения от свитча");
        allConnectSwItem.addActionListener(e->
                new AllConnectionFromSw(mainFrame.eventPrintFrame, mainFrame.authDialog.getPass()));

        view = new JMenu("View");
        view.addMenuListener(new ViewMenuListener());
        menuBar.add(view);
        logPrint = view.add("Set visible events");
        logPrint.addActionListener(e -> mainFrame.eventPrintFrame.setVisible(true));
        currentRunning = view.add("Set visible currents processes");
        currentRunning.addActionListener(e -> mainFrame.currentlyRunning.setVisible(true));
        view.addSeparator();

        UIManager.LookAndFeelInfo[] lookInfo = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo newLook : lookInfo) {
            String nameLook = newLook.getName();
            String classNameLook = newLook.getClassName();
            JMenuItem tempItem = view.add(nameLook);
            tempItem.addActionListener(e -> {
                try {
                    UIManager.setLookAndFeel(classNameLook);
                } catch (Exception exLaF) {
                    exLaF.printStackTrace();
                }
                SwingUtilities.updateComponentTreeUI(mainFrame);
                mainFrame.pack();
            }); // ActionListener
        } // for views

        helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
        manualItem = helpMenu.add("Manual");
        manualItem.addActionListener(e -> new ManualDialog(mainFrame));
        aboutItem = helpMenu.add("About");
        aboutItem.addActionListener(e -> new AboutDialog(mainFrame));
        Image temp = new ImageIcon("ico.jpg").getImage();
        ImageIcon imageIcon = new ImageIcon(temp.getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        aboutItem.setIcon(imageIcon);

    } // createMenu()

    class SwitchMenuListener implements MenuListener {
        @Override
        public void menuSelected(MenuEvent e) {
            JTextField switchTF = (JTextField) mainFrame.mainPanel.opticPanel.inputPanel.allTF.get(IP_SWITCH_S);
            if (switchTF.getText().length() < 6) {
                pathToSwItem.setEnabled(false);
                allConnectSwItem.setEnabled(false);
                pathToSwItem.setToolTipText("Необходимо предварительно указать IP свитча.");
                allConnectSwItem.setToolTipText("Необходимо предварительно указать IP свитча.");
                mainFrame.setVisible(true);
            } else {
                pathToSwItem.setEnabled(true);
                allConnectSwItem.setEnabled(true);
                pathToSwItem.setToolTipText("Показывает цепочку пути до свитча с линками.");
                allConnectSwItem.setToolTipText("Показывает все подключения от свитча по цепочке.");
                mainFrame.setVisible(true);
            }
        } // menuSelected()

        @Override
        public void menuDeselected(MenuEvent e) {
        }

        public void menuCanceled(MenuEvent e) {
        }
    }

    class ViewMenuListener implements MenuListener {
        @Override
        public void menuSelected(MenuEvent e) {
            if (mainFrame.eventPrintFrame.isVisible()) {
                logPrint.setEnabled(false);
            } else {
                logPrint.setEnabled(true);
            }

            if (mainFrame.currentlyRunning.isVisible()) {
                currentRunning.setEnabled(false);
            } else {
                currentRunning.setEnabled(true);
            }
        } // menuSelected()

        @Override
        public void menuDeselected(MenuEvent e) {
        }

        public void menuCanceled(MenuEvent e) {
        }
    } // class SwitchMenuListener

} // class MainMenu

