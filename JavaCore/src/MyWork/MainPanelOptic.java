package MyWork;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainPanelOptic extends JPanel {
    public JPanel inputPanel;
    public JPanel rightPanel;

    MainPanelOptic() {
        setLayout(new BorderLayout());
        inputPanel = new InputPanel();
        rightPanel = new RightPartPanel();
        add(inputPanel);
        add(rightPanel, BorderLayout.EAST);
    }
} // class MainPanelOptic


class InputPanel extends JPanel {
    public Map<String, JTextField> allTF;
    private static String[] labels = {"Мнемокод: ", "Номер vlan: ", "IP свитча: ", "Порт: "};

    InputPanel() {
        setLayout(new BorderLayout());
        allTF = new HashMap<>();

        for(String lab: labels) {
            allTF.put(lab, new JTextField(20));
        }

        JPanel inp = new JPanel(new GridLayout(labels.length, 1));
        JPanel lab = new JPanel(new GridLayout(labels.length, 1));

//        allTF.forEach((k, v) -> {
//            lab.add(new JLabel(k, SwingConstants.LEFT));
//            inp.add(v);
//        });

        for (String name: labels) {
            lab.add(new JLabel(name, SwingConstants.LEFT));
            inp.add(allTF.get(name));
        }

        add(lab, BorderLayout.WEST);
        add(inp, BorderLayout.CENTER);

    } // const
} // class InputPanel

class RightPartPanel extends JPanel {
    JButton freeVlan;
    JButton freePort;

    RightPartPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        freeVlan = new JButton("Найти свободный влан.");
        freePort = new JButton("Найти свободный порт.");
        add(freeVlan);
        add(new Label());
        add(freePort);
        add(new Label());
    }
}