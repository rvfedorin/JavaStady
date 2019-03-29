package MyWork;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RunActionListener implements ActionListener {
    private MainWindow mainFrame;

    RunActionListener(MainWindow frame) {
        mainFrame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        MainPanelOptic workPanel = mainFrame.mainPanel.opticPanel;

        // String[]{mnemokod, vlan, IPswitch, port, untagged, createCis, city, action}
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

        if (fineData) {

            System.out.println(mnemokod);
            System.out.println(vlan);
            System.out.println(IPswitch);
            System.out.println(port);
            System.out.println(untagged);
            System.out.println(createCis);
            System.out.println(city);
            System.out.println(action);
            System.out.println("==================================");

        } else {
            System.out.println("Введены не все данные.");
        } // if (fineData)

        mainFrame.customer = new Customer(mnemokod, vlan, IPswitch, port, untagged);
        System.out.println(mainFrame.customer);

    } // actionPerformed(ActionEvent e)
} // class RunActionListener
