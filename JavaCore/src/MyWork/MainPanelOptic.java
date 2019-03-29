package MyWork;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static MyWork.Config.*;

public class MainPanelOptic extends JPanel {
    public InputPanel inputPanel;
    public RightPartPanel rightPanel;
    public SelectActionPanel selectActionPanel;

    MainPanelOptic() {
        setLayout(new BorderLayout());

        inputPanel = new InputPanel();
        rightPanel = new RightPartPanel();
        selectActionPanel = new SelectActionPanel();
        selectActionPanel.allButtonMap.get(CHANGE_SPEED_S);

        add(inputPanel);
        add(rightPanel, BorderLayout.EAST);
        add(selectActionPanel, BorderLayout.SOUTH);

        // START Change city by mnemokod //////////////////////////////////////////////////
        JTextField mnemokod = (JTextField) inputPanel.allTF.get(MNEMOKOD_S);
        mnemokod.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {act();}
            @Override
            public void removeUpdate(DocumentEvent e) {act();}
            @Override
            public void changedUpdate(DocumentEvent e) {act();}

            private void act() {
                String key = mnemokod.getText().trim().split(MNEMOKOD_DELIMITER_S)[0];
                if (CITIES.containsKey(key)){
                    rightPanel.citiesComboBox.setSelectedItem(CITIES.get(key));
                    MainPanelOptic.this.setVisible(false);
                    MainPanelOptic.this.setVisible(true);
                }  // if
            }  // act()
        }); // DocumentListener()
        // END Change city by mnemokod //////////////////////////////////////////////////
    } // const

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
        String createCis = Boolean.toString(((JCheckBox) inputPanel.allTF.get(CREATE_CISCO_S)).isSelected());

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
            if (lab.equals(UNTAGGED_S) || lab.equals(CREATE_CISCO_S)) {
                allTF.put(lab, new JCheckBox(lab));
            } else {
                JTextField tempTextField = new JTextField(18);
                tempTextField.setBackground(this.getBackground());
                tempTextField.setBorder(BorderFactory.createCompoundBorder(
                        new CustomeBorder(),
                        BorderFactory.createEmptyBorder(0,2,0,2)

                ));
                allTF.put(lab, tempTextField);
            } // if
        } // for(String lab: labels)

        JPanel inp = new JPanel(new GridLayout(LABELS.length, 1, 1, 1));
        JPanel lab = new JPanel(new GridLayout(LABELS.length, 1, 1, 1));

        for (String name: LABELS) {
            if (name.equals(UNTAGGED_S) || name.equals(CREATE_CISCO_S)) {
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

        citiesComboBox = new JComboBox<>();
//        citiesComboBox.setPreferredSize(new Dimension(80, 6));
//        citiesComboBox.setPrototypeDisplayValue("XX");

        for(String c: CITIES.values()) {
            citiesComboBox.addItem(c);
        }

        add(citiesComboBox);
        add(freeVlanBut);
        add(new Label());
        add(freePortBut);
        add(new Label());
        add(changeSpeedBut);
    }
}

