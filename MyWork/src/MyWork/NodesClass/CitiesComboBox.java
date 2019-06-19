package MyWork.NodesClass;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

import static MyWork.Config.CITIES;

public class CitiesComboBox extends JComboBox<String> {
    private static ComboBoxModel ssm = null;

    public CitiesComboBox(){
        setBorder(BorderFactory.createEmptyBorder(0, 14, 0, 0));
        setPreferredSize(new Dimension(100, 20));

        ArrayList<String> cityKeys = new ArrayList(CITIES.keySet());
        cityKeys.sort(Comparator.comparing(String::valueOf));
        for(String city: cityKeys) {
            this.addItem(CITIES.get(city).getCity());
        }
        if (ssm == null) {
            ssm = this.getModel();
        } else {
            this.setModel(ssm);
        }
    } // ** constructor

} // ** class CitiesComboBox
