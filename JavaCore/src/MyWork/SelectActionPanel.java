package MyWork;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.HashMap;
import java.util.Map;

public class SelectActionPanel extends JPanel {
    public Map<String, JRadioButton> allButtonMap;
    public static String[] labelsButton = {"Создать", "Удалить", "Сменить скорость"};

    SelectActionPanel() {
        allButtonMap = new HashMap<>();
        ButtonGroup group = new ButtonGroup();
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 0, 10, 0),
                BorderFactory.createTitledBorder("Выберите действие: ")));
        boolean selected = true;

        for(String name: labelsButton) {
            JRadioButton tempButton = new JRadioButton(name, selected);
            allButtonMap.put(name, tempButton);
            group.add(tempButton);
            add(tempButton);
            selected = false;
        }


    } // const
}
