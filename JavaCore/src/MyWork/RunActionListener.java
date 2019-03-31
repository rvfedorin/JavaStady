package MyWork;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static MyWork.Config.*;

public class RunActionListener implements ActionListener {
    private MainWindow mainFrame;

    RunActionListener(MainWindow frame) {
        mainFrame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        MainPanelOptic workPanel = mainFrame.mainPanel.opticPanel;

        //getAllData() return String[]{mnemokod, vlan, IPswitch, port, untagged, createCis, city, action}
        String[] allData = workPanel.getAllData();
        boolean fineData = true;

        for (String s: allData) {
            if (s.length() < 1) {
                fineData = false;
                break;
            }
        } // for

        String mnemokod = allData[0];
        String vlan = allData[1];
        String IPswitch = allData[2];
        String port = allData[3];
        String untagged = allData[4];
        String createCis = allData[5];
        String city = allData[6];
        String action = allData[7];

        if (action.equals(CREATE_S)) {
            // Создаём
            if (Boolean.valueOf(createCis)) System.out.println("С созданием на Cisco.");

            System.out.println("Создание клиента: ");

            mainFrame.customer = new Customer(city, mnemokod, vlan, IPswitch, port, untagged);
            System.out.println(mainFrame.customer);
        } else if (action.equals(DELETE_S)) {
            // Удаляем
            System.out.println("Удаление клиента: ");
            mainFrame.customer = new Customer(city, mnemokod, vlan, IPswitch, port, untagged);
            System.out.println(mainFrame.customer);
        } else if (action.equals(CHANGE_SPEED_S)) {
            // Меняем скорость
            System.out.println("Смена скорости.");
            File speedFile = new File(SPEEDS_FILE);
            try (BufferedReader frSpeedFile = new BufferedReader(new FileReader(speedFile))) {
                String line;
                int i = 1;
                do {
                    line = frSpeedFile.readLine();
                    if(line != null) {
                        String key = line.split("-")[0];
                        String citySpeed = CITIES.getOrDefault(key, null);
                        if(citySpeed != null) {
                            System.out.println(">" + line.trim() + "<" + " строка " + i);
                            System.out.println("ОП " + citySpeed);
                            System.out.println(LINE);
                        }

                        i++;
                    }
                } while (line != null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            return;
        } // if selection action

    } // actionPerformed(ActionEvent e)
} // class RunActionListener
