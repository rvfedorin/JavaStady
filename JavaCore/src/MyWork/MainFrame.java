package MyWork;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
//import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import javax.swing.*;
import java.awt.*;

// ************************ MAIN IN ************************************
public class MainFrame {
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        AuthDialog authDialog = new AuthDialog(mainWindow);
        // ************** AUTH ****************
        if (!authDialog.isSuccessAuth()) {
            System.exit(0);
        }
        // ************** END AUTH ****************
        mainWindow.setVisible(true);
    } // ** main()
} // ** class MainFrame
// ************************* MAIN OUT **********************************

class MainWindow extends JFrame {
    private final static int DEFAULT_WIDTH = 380;
    private final static int DEFAULT_HEIGHT = 380;
    MainPanel mainPanel;
//    CurrentlyRunningPanel currentlyRunning;
    Customer customer;
    private MainMenu mainMenu;

    MainWindow(){
        try {
//            UIManager.setLookAndFeel(new NimbusLookAndFeel());
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (dimension.getWidth() - getWidth())/3;
        int y = (int) (dimension.getHeight() - getHeight())/3;
        setLocation(x, y);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Работа с клиентами.");
//        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setResizable(false);

        mainMenu = new MainMenu(this);
        mainPanel = new MainPanel();
//        currentlyRunning = new CurrentlyRunningPanel();
        add(mainPanel, BorderLayout.CENTER);
//        add(currentlyRunning, BorderLayout.EAST);

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

    } // ************************ close const


} // ************************ close class MainWindow

class MainPanel extends JPanel {
    MainPanelOptic opticPanel;
    private JPanel mbPanel;
    private JPanel rwrPanel;



    MainPanel() {
        opticPanel = new MainPanelOptic();
        mbPanel = new MainPanelMB();
        rwrPanel = new MainPanelRWR();

        add(opticPanel);

    }
} // ************************ close class MainPanel
