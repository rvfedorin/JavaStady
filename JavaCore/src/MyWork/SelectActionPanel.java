package MyWork;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class SelectActionPanel extends JPanel {
    public Map<String, JRadioButton> allButtonMap;
    public static String[] labelsButton = {"Создать", "Удалить", "Сменить скорость"};
    public ButtonGroup group;

    SelectActionPanel() {
        allButtonMap = new HashMap<>();
        group = new ButtonGroup();
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 0, 10, 0),
                BorderFactory.createTitledBorder("Выберите действие: ")));
        boolean selected = true;

        for(String name: labelsButton) {
            JRadioButton tempButton = new JRadioButton(name, selected);
            tempButton.setActionCommand(name);
            allButtonMap.put(name, tempButton);
            group.add(tempButton);
            add(tempButton);
            selected = false;
        } // for
    } // const
}
