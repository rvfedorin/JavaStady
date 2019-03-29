package MyWork;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

import static MyWork.Config.*;

public class SelectActionPanel extends JPanel {
    public Map<String, JRadioButton> allButtonMap;
    public static String[] labelsButton = {CREATE_S, DELETE_S, CHANGE_SPEED_S};
    public ButtonGroup group;

    SelectActionPanel() {
        allButtonMap = new HashMap<>();
        group = new ButtonGroup();
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 0, 10, 0),
                BorderFactory.createTitledBorder(TITLE_BORDER_S)));
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
