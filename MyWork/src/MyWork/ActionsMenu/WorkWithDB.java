package MyWork.ActionsMenu;

import MyWork.EventPrintFrame;
import MyWork.ExtendStandart.ExtendedTextField;
import MyWork.NodesClass.BD;
import MyWork.NodesClass.CitiesComboBox;
import MyWork.NodesClass.Region;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static MyWork.Config.CITIES;
import static MyWork.Config.CITIES_BY_NAME;

public class WorkWithDB extends JFrame {
    private EventPrintFrame printFrame;
    private Region currentRegion;
    private HashMap<String, ExtendedTextField> fieldHashMap;
    private String[] labelsString;
    private HashMap<String, Region> regionsHashMap;


    public WorkWithDB(EventPrintFrame epf) {
        labelsString = new String[]{"Префикс", "Город", "ID для WEB", "Корневой свитч",
                "Ячейка IP оборудования", "Порт подключения Cisco", "IP Unix", "Строка МБ"};
//        setLayout(new BorderLayout());
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (dimension.getWidth() - getWidth()) / 3;
        int y = (int) (dimension.getHeight() - getHeight()) / 3;
        this.setLocation(x, y);
        this.setTitle("Работа с БД.");
        this.setResizable(false);


        printFrame = epf;
        fieldHashMap = new HashMap<>();
        //CITIES.put("Kr", new Region("Kr", "Kursk", "24", "88.86.91.254", "172.17.0.10", "24", "3", "95.80.98.181X172.17.151.1X172.17.1.38X2"));
        regionsHashMap = BD.getRegions();
        JPanel labelsWithInputs = new JPanel(new BorderLayout());
        JPanel labels;
        JPanel inputs;
        CitiesComboBox citiesComboBox;
        JPanel buttonsPanel = new JPanel();

        if (regionsHashMap != null && !withError(regionsHashMap)) {
            citiesComboBox = new CitiesComboBox(regionsHashMap);
            citiesComboBox.addActionListener(e -> {
                String city = (String) citiesComboBox.getSelectedItem();
                currentRegion = regionsHashMap.get(CITIES_BY_NAME.get(city));
                fillFields();
            });

            labels = new JPanel(new GridLayout(labelsString.length, 1));
            labels.setBorder(BorderFactory.createEmptyBorder(2, 2, 0, 4));
            inputs = new JPanel(new GridLayout(labelsString.length, 1));
            inputs.setBorder(BorderFactory.createEmptyBorder(2, 4, 0, 2));

            labelsWithInputs.add(inputs, BorderLayout.CENTER);
            labelsWithInputs.add(labels, BorderLayout.EAST);

            String city = (String) citiesComboBox.getSelectedItem();
            currentRegion = regionsHashMap.get(CITIES_BY_NAME.get(city));

            for (String label : labelsString) {
                ExtendedTextField inputField = new ExtendedTextField(35);
                fieldHashMap.put(label, inputField);
                labels.add(new JLabel(label));
                inputs.add(inputField);
            }

            fillFields();

            JButton createB = new JButton("Создать");
            createB.addActionListener(e -> {
                newReg();
                if (CITIES.containsKey(currentRegion.getPrefix()) || CITIES_BY_NAME.containsKey(currentRegion.getCity())) {
                    JOptionPane.showMessageDialog(null,
                            "Заданный город или префикс уже существует",
                            "Result",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    regionsHashMap.put(currentRegion.getPrefix(), currentRegion);
                    CITIES.put(currentRegion.getPrefix(), currentRegion);
                    CITIES.forEach((k, v) -> CITIES_BY_NAME.put(v.getCity(), k));
                    ((DefaultComboBoxModel) citiesComboBox.getModel()).addElement(currentRegion.getCity());
                    citiesComboBox.setSelectedItem(currentRegion.getCity());
                    BD.createDB(regionsHashMap);
                }
            });
            JButton editB = new JButton("Сохранить");
            editB.addActionListener(e -> {
                int yesOrNot = JOptionPane.showConfirmDialog(this,
                        "Вы действительно хотите сохранить изменения в " + currentRegion.getCity(),
                        "Подтверждение изменений",
                        JOptionPane.YES_NO_OPTION);
                if (yesOrNot == JOptionPane.YES_OPTION) {
                    newReg();
                    regionsHashMap.put(currentRegion.getPrefix(), currentRegion);
                    CITIES.put(currentRegion.getPrefix(), currentRegion);
                    CITIES.forEach((k, v) -> CITIES_BY_NAME.put(v.getCity(), k));
                    BD.createDB(regionsHashMap);
                    JOptionPane.showMessageDialog(null,
                            "Изменения сохранены",
                            "Result",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            });

            JButton removeB = new JButton("Удалить");
            removeB.addActionListener(e -> {
                int yesOrNot = JOptionPane.showConfirmDialog(this,
                        "Вы действительно хотите удалить " + currentRegion.getCity(),
                        "Подтверждение удаления",
                        JOptionPane.YES_NO_OPTION);
                if (yesOrNot == JOptionPane.YES_OPTION) {
                    regionsHashMap.remove(currentRegion.getPrefix());
                    CITIES.remove(currentRegion.getPrefix());
                    CITIES.forEach((k, v) -> CITIES_BY_NAME.put(v.getCity(), k));
                    ((DefaultComboBoxModel) citiesComboBox.getModel()).removeElement(currentRegion.getCity());
                    currentRegion = regionsHashMap.get(CITIES_BY_NAME.get(citiesComboBox.getSelectedItem()));
                    BD.createDB(regionsHashMap);
                }
            });
            buttonsPanel.add(createB);
            buttonsPanel.add(editB);
            buttonsPanel.add(removeB);


            this.add(citiesComboBox, BorderLayout.NORTH);
            this.add(labelsWithInputs, BorderLayout.CENTER);
            this.add(buttonsPanel, BorderLayout.SOUTH);
            this.pack();
            this.setVisible(true);


        } else {
            if (regionsHashMap != null) {
                for (String error : regionsHashMap.keySet()) {
                    printFrame.printEvent("\n" + error + "\n");
                }
            } else {
                printFrame.printEvent("\nNot found HashMap [WorkWithDB->constructor]\n");
            } // if HashMap is null
        } // if errors

    } // ** constructor

    private boolean withError(HashMap<String, Region> map) {
        boolean result = false;

        for (String key : map.keySet()) {
            if (key.contains("Error")) {
                result = true;
            }
        }
        return result;
    } // withError()

    private void fillFields() {
        fieldHashMap.get(labelsString[0]).setText(currentRegion.getPrefix());
        fieldHashMap.get(labelsString[1]).setText(currentRegion.getCity());
        fieldHashMap.get(labelsString[2]).setText(currentRegion.getId());
        fieldHashMap.get(labelsString[3]).setText(currentRegion.getCoreSwitch());
        fieldHashMap.get(labelsString[4]).setText(currentRegion.getDevCellId());
        fieldHashMap.get(labelsString[5]).setText(currentRegion.getRootPort());
        fieldHashMap.get(labelsString[6]).setText(currentRegion.getCoreUnix());
        fieldHashMap.get(labelsString[7]).setText(currentRegion.getLanMB());
    }

    private void newReg() {
        //new Region("Kr", "Kursk", "24", "88.86.91.254", "172.17.0.10", "24", "3", "95.80.98.181X172.17.151.1X172.17.1.38X2"));
        String pref = fieldHashMap.get(labelsString[0]).getText().trim();
        String city = fieldHashMap.get(labelsString[1]).getText().trim();
        String id = fieldHashMap.get(labelsString[2]).getText().trim();
        String sw = fieldHashMap.get(labelsString[3]).getText().trim();
        String cell = fieldHashMap.get(labelsString[4]).getText().trim();
        String rootPort = fieldHashMap.get(labelsString[5]).getText().trim();
        String unix = fieldHashMap.get(labelsString[6]).getText().trim();
        String mb = fieldHashMap.get(labelsString[7]).getText().trim();
        if(pref.length() < 1 || city.length() <1 || id.length() < 1) {
            JOptionPane.showMessageDialog(null,
                    "Поля: " + labelsString[0] + ", " + labelsString[1] + ", " + labelsString[2],
                    "Result",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        currentRegion = new Region(pref, city, id, unix, sw, rootPort, cell, mb);
    }
} // ** class WorkWithDB
