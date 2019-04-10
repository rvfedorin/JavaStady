package MyWork;

import MyWork.Tools.CiscoSpeedFormat;
import MyWork.Tools.SpeedFileParser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

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

        // checks if all data was filled
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
            mainFrame.eventPrintFrame.printEvent("Смена скорости.");
            new ChangeSpeedThread("speedChange", mainFrame.eventPrintFrame);
        } else {
            return;
        } // if selection action

    } // actionPerformed(ActionEvent e)
} // class RunActionListener

class ChangeSpeedThread extends Thread {
    private EventPrintFrame frameEvent;

    ChangeSpeedThread(String name, EventPrintFrame printEvent) {
        super(name);
        this.frameEvent = printEvent;
        this.start();
    }
    @Override
    public void run() {
        BufferedReader frSpeedFile = ExtendedOpenFile.readFile();
        if (frSpeedFile != null) readFile(frSpeedFile);
    } // ** run()

    private void readFile(BufferedReader frSpeedFile) {
        String line = "";

        do {
            try {
                line = frSpeedFile.readLine();
                if(line != null && !line.matches("^\\s*$")) {
//                    System.out.println(line);
                    String tempLine = line;
                    line = SpeedFileParser.getParsedString(line);
//                    System.out.println(line);

                    String[] formattedSpeed;
                    // get prefix of mnemokod
                    String key = line.split("-")[0];
                    // if first letter in lowercase
                    if(key != null && !key.isEmpty()) {
                        key = key.substring(0, 1).toUpperCase() + key.substring(1);
                    } else {
                        frameEvent.printEvent(tempLine);
                        frameEvent.printEvent("[!!!] Error. City not found!");
                        frameEvent.printEvent(LINE);
                        continue;
                    }

                    String[] clientNewSpeed = line.split(" ");  // ** [0] mnemokod; [1] speed
                    // get OP
                    String citySpeed = CITIES.getOrDefault(key, "--- Error. City not found! ---");
                    if (clientNewSpeed.length >= 2) {
                        formattedSpeed = CiscoSpeedFormat.getFormattedSpeed("service-policy", clientNewSpeed[1]);
                    } else {
                        frameEvent.printEvent(tempLine);
                        frameEvent.printEvent("[!!!] Error parse line speed.");
                        frameEvent.printEvent(LINE);
                        continue;
                    }

                    if(citySpeed != null) {
                        // ************** START REMOVE AFTER TESTS ********************************
//                        System.out.println(">" + line.trim() + "<" + " строка " + i);
//                        System.out.println("ОП " + citySpeed);
//
//                        if(formattedSpeed != null && !formattedSpeed[0].contains("Error")) {
//                            for (String s : formattedSpeed) {
//                                System.out.println(s);
//                            }
//                        } else if(formattedSpeed != null) {
//                            System.out.println(formattedSpeed[0] + formattedSpeed[1]);
//                        } else {
//                            System.out.println("getFormattedSpeed return NULL!");
//                        }
//
//                        System.out.println(LINE);
                        // ************** END REMOVE AFTER TESTS ********************************

                        // ************** START PRINT EVENT ********************************
                        frameEvent.printEvent(tempLine);
                        frameEvent.printEvent(line);
                        frameEvent.printEvent("ОП " + citySpeed);
                        if(formattedSpeed != null && !formattedSpeed[0].contains("Error")) {
                            for (String s : formattedSpeed) {
                                frameEvent.printEvent(s);
                            }
                        } else if(formattedSpeed != null) {
                            frameEvent.printEvent(formattedSpeed[0] + formattedSpeed[1]);
                        } else {
                            frameEvent.printEvent("getFormattedSpeed return NULL!");
                        }
                        frameEvent.printEvent(LINE);
                        // ************** END PRINT EVENT ********************************

                        Thread.sleep(1000); // for test
                    }
                } // if(line != null)
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }

        } while (line != null);
    } // ** readFile(BufferedReader frSpeedFile)
}