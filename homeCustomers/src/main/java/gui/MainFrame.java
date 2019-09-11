package gui;

import actions.GetStatus;
import actions.StartSession;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static tools.Config.ISG_1;
import static tools.Config.ISG_2;
import static tools.CryptDecrypt.getEncrypt;

public class MainFrame extends JFrame {
    private HashMap<String, JTextField> labelsWithInputs;
    private char[] key;

    public MainFrame() {
        super();
        Dimension DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
        Double X_SCREEN_SIZE = DIMENSION.getWidth();
        Double Y_SCREEN_SIZE = DIMENSION.getHeight();
        int x = (int) (X_SCREEN_SIZE - getWidth()) / 3;
        int y = (int) (Y_SCREEN_SIZE - getHeight()) / 3;
        setLocation(x, y);
    } // ** constructor

    public void createGUI() {
        this.setTitle("Homes customers");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        labelsWithInputs = new HashMap<>();
        String[] labels = new String[]{"Mnemokod: ", "IP: ", "Pass: "};
        String[] ipIsg = null;
        try {
            ipIsg = new String[]{getEncrypt(new String(key), ISG_1), getEncrypt(new String(key), ISG_2)};
        } catch (NullPointerException ex) {
            System.exit(0);
        }

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        JPanel inputPanel = new JPanel();
        JPanel panelLabels = new JPanel(new GridLayout(labels.length, 1, 1, 8));
        JPanel panelInputs = new JPanel(new GridLayout(labels.length, 1, 1, 1));
        inputPanel.add(panelLabels);
        inputPanel.add(panelInputs);


        for (String name : labels) {
            JTextField input = new JTextField(18);
            labelsWithInputs.put(name, input);

            panelLabels.add(new JLabel(name));
            panelInputs.add(input);
        }

        JPanel radioButtonPanel = new JPanel();
        radioButtonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(2, 2, 2, 2),
                BorderFactory.createTitledBorder("ISG ")
        ));
        ButtonGroup buttonGroup = new ButtonGroup();
        boolean selected = true;
        for (String name : ipIsg) {
            JRadioButton tempButton = new JRadioButton(name, selected);
            tempButton.setBorder(BorderFactory.createEmptyBorder());
            tempButton.setActionCommand(name);

            buttonGroup.add(tempButton);
            radioButtonPanel.add(tempButton);
            selected = false;
        } // for

        JButton getStatusButton = new JButton("Узнать статус");
        getStatusButton.addActionListener(e -> {
            String mnemokod = getData("Mnemokod: ").getText();
            String ipClient = getData("IP: ").getText();
            String passClient = getData("Pass: ").getText();
            String ipISG = buttonGroup.getSelection().getActionCommand();

            if (mnemokod != null && ipClient != null && passClient != null && ipISG != null) {
                new Thread(new GetStatus(mnemokod, ipClient, passClient, ipISG, key)).start();
            } else {
                System.out.println("Error getStatusButton.addActionListener");
            }
        });

        JButton runSessionButton = new JButton("Запустить сессию"); //StartSession
        runSessionButton.addActionListener(e -> {
            String mnemokod = getData("Mnemokod: ").getText().trim();
            String ipClient = getData("IP: ").getText().trim();
            String passClient = getData("Pass: ").getText().trim();

            new Thread(new StartSession(mnemokod, ipClient, passClient, key)).start();

        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(getStatusButton);
        buttonPanel.add(runSessionButton);

        mainPanel.add(inputPanel);
        mainPanel.add(radioButtonPanel);
        mainPanel.add(buttonPanel);

        MainMenu mainMenu = new MainMenu(this);

        this.add(mainPanel);
        this.pack();
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setKey(final char[] key) {
        this.key = key;
    }

    public JTextField getData(String name) {
        return labelsWithInputs.getOrDefault(name, null);
    }
} // ** class MainFrame
