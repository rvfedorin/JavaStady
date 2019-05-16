package MyWork;

import MyWork.ExtendStandart.AdapterDocumentListener;
import MyWork.ExtendStandart.CustomBorder;
import MyWork.ExtendStandart.ExtendedTextField;
import MyWork.NodesClass.Region;
import MyWork.Verifiers.IPVerifier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import static MyWork.Config.*;
import static MyWork.ExtendStandart.ExtendedOpenFile.openSystemFile;

public class MainPanelOptic extends JPanel {
    public InputPanel inputPanel;
    public RightPartPanel rightPanel;
    public SelectActionPanel selectActionPanel;

    MainPanelOptic() {
        setLayout(new BorderLayout());

        inputPanel = new InputPanel();
        rightPanel = new RightPartPanel();
        selectActionPanel = new SelectActionPanel();

        // START LISTENER SELECTION PANEL
        selectActionPanel.allButtonMap.get(CHANGE_SPEED_S).addActionListener(new ActionSelectionListener());
        selectActionPanel.allButtonMap.get(CREATE_S).addActionListener(new ActionSelectionListener());
        selectActionPanel.allButtonMap.get(DELETE_S).addActionListener(new ActionSelectionListener());
        // END LISTENER SELECTION PANEL

        add(inputPanel);
        add(rightPanel, BorderLayout.EAST);
        add(selectActionPanel, BorderLayout.SOUTH);

        // START Change city by mnemokod //////////////////////////////////////////////////
        JTextField mnemokod = (JTextField) inputPanel.allTF.get(MNEMOKOD_S);
        mnemokod.getDocument().addDocumentListener(new AdapterDocumentListener() {
            @Override
            public void act() {
                String key = "";
                String text = mnemokod.getText();
                if (text.length() > 0) {
                    key = text.trim().split(MNEMOKOD_DELIMITER_S)[0];
                    if (key.length() > 1) {
                        key = key.toLowerCase();
                        key = key.substring(0, 1).toUpperCase() + key.substring(1);
                    }
                }

                if (CITIES.containsKey(key)){
                    rightPanel.citiesComboBox.setSelectedItem(CITIES.get(key).getCity());
                }  // if
            }  // act()
        }); // DocumentListener()
        // END Change city by mnemokod //////////////////////////////////////////////////

    } // const

    private class ActionSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(selectActionPanel.allButtonMap.get(CREATE_S).isSelected()) {
                rightPanel.changeSpeedBut.setEnabled(false);
                inputPanel.allTF.forEach((k, v) -> v.setEnabled(true));
                ((JCheckBox) inputPanel.allTF.get(ACT_ON_CISCO_S)).setText(CREATE_CISCO_S);
            } else if(selectActionPanel.allButtonMap.get(DELETE_S).isSelected()) {
                rightPanel.changeSpeedBut.setEnabled(false);
                JCheckBox temCheck = (JCheckBox) inputPanel.allTF.get(ACT_ON_CISCO_S);
                temCheck.setText(DELETE_CISCO_S);
                temCheck.setSelected(false);
                inputPanel.allTF.forEach((k, v) -> {
                    if(k.equals(UNTAGGED_S) || k.equals(PORT_S)) {
                        v.setEnabled(false);
                    } else {
                        v.setEnabled(true);
                    } // if equals
                });
            } else if(selectActionPanel.allButtonMap.get(CHANGE_SPEED_S).isSelected()) {
                rightPanel.changeSpeedBut.setEnabled(true);
                inputPanel.allTF.forEach((k, v) -> v.setEnabled(false));
            }
        }
    } // class ActionSelectionListener

    /*
     * Get all data from field
     * @return String[]{mnemokod, vlan, IPswitch, port, untagged, createCis, city, action}
     */
    public String[] getAllData(){

        String mnemokod = ((JTextField) inputPanel.allTF.get(MNEMOKOD_S)).getText().trim();
        String vlan = ((JTextField) inputPanel.allTF.get(NUMBER_VLAN_S)).getText().trim();
        String IPswitch = ((JTextField) inputPanel.allTF.get(IP_SWITCH_S)).getText().trim();
        String port = ((JTextField) inputPanel.allTF.get(PORT_S)).getText().trim();
        String untagged = Boolean.toString(((JCheckBox) inputPanel.allTF.get(UNTAGGED_S)).isSelected());
        String createCis = Boolean.toString(((JCheckBox) inputPanel.allTF.get(ACT_ON_CISCO_S)).isSelected());

        JComboBox<String> cityBox = rightPanel.citiesComboBox;
        String city = cityBox.getItemAt(cityBox.getSelectedIndex());

        String action = selectActionPanel.group.getSelection().getActionCommand();

        return new String[]{mnemokod, vlan, IPswitch, port, untagged, createCis, city, action};
    }
} // class MainPanelOptic


class InputPanel extends JPanel {
    public Map<String, JComponent> allTF;

    InputPanel() {
        setLayout(new BorderLayout());
        allTF = new HashMap<>();

        for(String lab: LABELS) {
            if (lab.equals(UNTAGGED_S)) {
                allTF.put(lab, new JCheckBox(lab));
            } else if (lab.equals(ACT_ON_CISCO_S)) {
                allTF.put(lab, new JCheckBox(CREATE_CISCO_S));
            } else {
                JTextField tempTextField = new ExtendedTextField(18);
                tempTextField.setBorder(BorderFactory.createCompoundBorder(
                        new CustomBorder(),
                        BorderFactory.createEmptyBorder(0,2,0,2)
                ));

                allTF.put(lab, tempTextField);
            } // if
        } // for(String lab: labels)

        JTextField ipSwitchListen = (JTextField) allTF.get(IP_SWITCH_S);
        ipSwitchListen.setInputVerifier(new IPVerifier(ipSwitchListen));

        JPanel inp = new JPanel(new GridLayout(LABELS.length, 1, 1, 1));
        JPanel lab = new JPanel(new GridLayout(LABELS.length, 1, 1, 1));

        for (String name: LABELS) {
            if (name.equals(UNTAGGED_S) || name.equals(ACT_ON_CISCO_S)) {
                lab.add(new JLabel());
                inp.add(allTF.get(name));
            } else {
                lab.add(new JLabel(name, SwingConstants.LEFT));
                inp.add(allTF.get(name));
            }
        }

        add(lab, BorderLayout.WEST);
        add(inp, BorderLayout.CENTER);

    } // const

} // class InputPanel

class RightPartPanel extends JPanel {
    public JButton freeVlanBut;
    public JButton freePortBut;
    public JButton changeSpeedBut;
    public JComboBox<String> citiesComboBox;

    RightPartPanel() {

//        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setLayout(new GridLayout(6,0));
        setBorder(BorderFactory.createEmptyBorder(0,4,0,2));
        freeVlanBut = new JButton("Найти свободный влан");
        freePortBut = new JButton("Найти свободный порт");
        changeSpeedBut = new JButton("Файл скоростей.");
        changeSpeedBut.setEnabled(false);
        changeSpeedBut.addActionListener(e -> {
            openSystemFile();
        });

        citiesComboBox = new JComboBox<>();
        citiesComboBox.setBorder(BorderFactory.createEmptyBorder(0, 14, 0, 0));
        citiesComboBox.setPreferredSize(new Dimension(100, 20));

        JPanel comboPanel = new JPanel(new BorderLayout());
        comboPanel.add(citiesComboBox, BorderLayout.NORTH);

        for(Region c: CITIES.values()) {
            citiesComboBox.addItem(c.getCity());
        }

        add(comboPanel);
        add(freeVlanBut);
        add(new JLabel());
        add(freePortBut);
        add(new JLabel());
        add(changeSpeedBut);
    }
}

