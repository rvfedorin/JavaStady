package MyWork;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        } else {
            return;
        }

    } // actionPerformed(ActionEvent e)
} // class RunActionListener
