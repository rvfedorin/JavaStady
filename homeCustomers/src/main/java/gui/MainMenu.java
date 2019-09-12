package gui;

import actionsMenu.Calc;

import javax.swing.*;

import static tools.Config.VERSION;

class MainMenu {


    MainMenu(JFrame frame) {
        createMenu(frame);
    }

    private void createMenu(JFrame frame) {
        JMenuBar menuBar;

        JMenu toolsMenu;
        JMenuItem calc;

        JMenu helpMenu;
        JMenuItem aboutItem;

        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        toolsMenu = new JMenu("Tools");
        menuBar.add(toolsMenu);
        calc = toolsMenu.add("Калькулятор скорости");
        calc.addActionListener(e -> {
            new Calc().setupGui();
        });
        toolsMenu.addSeparator();


        helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
        aboutItem = helpMenu.add("About");
        aboutItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,
                    "ver. " + VERSION + "\nPowered by Roman V. Fedorin",
                    "Homes Customers",
                    JOptionPane.INFORMATION_MESSAGE);
        });


    } // createMenu()
}
