package MyWork;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainPanelOptic extends JPanel {
    public InputPanel inputPanel;
    public RightPartPanel rightPanel;
    public SelectActionPanel selectActionPanel;

    MainPanelOptic() {
        setLayout(new BorderLayout());

        inputPanel = new InputPanel();
        rightPanel = new RightPartPanel();
        selectActionPanel = new SelectActionPanel();

        add(inputPanel);
        add(rightPanel, BorderLayout.EAST);
        add(selectActionPanel, BorderLayout.SOUTH);
    }
} // class MainPanelOptic


class InputPanel extends JPanel {
    public Map<String, JComponent> allTF;
    private static String[] labels = {
            "Мнемокод: ",
            "Номер vlan: ",
            "IP свитча: ",
            "Порт: ",
            "Untagged",
            "Создать на Cisco"};

    InputPanel() {
        setLayout(new BorderLayout());
        allTF = new HashMap<>();

        for(String lab: labels) {
            if (lab.equals("Untagged") || lab.equals("Создать на Cisco")) {
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

        JPanel inp = new JPanel(new GridLayout(labels.length, 1, 1, 1));
        JPanel lab = new JPanel(new GridLayout(labels.length, 1, 1, 1));

        for (String name: labels) {
            if (name.equals("Untagged") || name.equals("Создать на Cisco")) {
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
    public JButton freeVlan;
    public JButton freePort;
    private String[] cities;

    RightPartPanel() {
        cities = new String[]{"Orel", "Kursk", "Magnitogorsk", "Voronezh"};
//        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setLayout(new GridLayout(6,0));
        setBorder(BorderFactory.createEmptyBorder(0,4,0,2));
        freeVlan = new JButton("Найти свободный влан");
        freePort = new JButton("Найти свободный порт");
        JComboBox<String> citiesComboBox = new JComboBox<>();
        citiesComboBox.setPreferredSize(new Dimension(80, 6));
        citiesComboBox.setPrototypeDisplayValue("XX");

        for(String c: cities) {
            citiesComboBox.addItem(c);
        }

        add(citiesComboBox);
        add(freeVlan);
        add(new Label());
        add(freePort);
    }
}

