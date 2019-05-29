package MyWork;

import MyWork.NodesClass.Customer;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import javax.swing.*;
import java.awt.*;

import static MyWork.Config.*;

// ************************ MAIN IN ************************************
public class MainFrame {
    public static void main(String[] args) {
        EventQueue.invokeLater(()-> {
            MainWindow mainWindow = new MainWindow();
//            AuthDialog authDialog = new AuthDialog(mainWindow);

            // ************** AUTH ****************
            if (!mainWindow.authDialog.isSuccessAuth()) {
                System.exit(0);
            } else {
                // ************** END AUTH ****************
                mainWindow.setVisible(true);
                mainWindow.eventPrintFrame.setVisible(true);
                mainWindow.eventPrintFrame.pDate();
                mainWindow.eventPrintFrame.printEvent("Success logged.");
                mainWindow.eventPrintFrame.printEvent(LINE);

//            mainWindow.currentlyRunning.setVisible(false);
            }
        }); // ** EventQueue.invokeLater
    } // ** main()
} // ** class MainFrame
// ************************* MAIN OUT **********************************

class MainWindow extends JFrame {
//    private final static int DEFAULT_WIDTH = 380;
//    private final static int DEFAULT_HEIGHT = 380;
    AuthDialog authDialog;
    MainPanel mainPanel;
    CurrentlyRunningFrame currentlyRunning;
    EventPrintFrame eventPrintFrame;
    Customer customer;
    JLabel statusPanel;

    private MainMenu mainMenu;

    MainWindow(){
        try {
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        int x = (int) (X_SCREEN_SIZE - getWidth())/3;
        int y = (int) (Y_SCREEN_SIZE - getHeight())/3;
        setLocation(x, y);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Работа с клиентами.");
//        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setResizable(false);

        authDialog = new AuthDialog(this);
        mainMenu = new MainMenu(this);
        mainPanel = new MainPanel();
        currentlyRunning = new CurrentlyRunningFrame(this);
        eventPrintFrame = new EventPrintFrame();
        add(mainPanel, BorderLayout.CENTER);

        // RUN BUTTON //
        JPanel buttonPanel = new JPanel();
        JButton runButton = new JButton("Выполнить");
        RunActionListener runActionListener = new RunActionListener(this);
        runButton.setPreferredSize(new Dimension(120, 30));
        runButton.addActionListener(runActionListener);
        buttonPanel.add(runButton);
        // END RUN BUTTON //

        mainPanel.add(buttonPanel); // !! MAIN PANEL

        statusPanel = new JLabel("Ready.");
        statusPanel.setBorder(BorderFactory.createEmptyBorder(0,4,0,0));
        add(statusPanel, BorderLayout.SOUTH);
        pack();

    } // ************************ close const


} // ************************ close class MainWindow

class MainPanel extends JPanel {
    MainPanelOptic opticPanel;
    private JPanel mbPanel;
    private JPanel rwrPanel;



    MainPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(2,4,2,0));
        opticPanel = new MainPanelOptic();
        mbPanel = new MainPanelMB();
        rwrPanel = new MainPanelRWR();

        add(opticPanel);

    }
} // ************************ close class MainPanel
