package MyWork;

import MyWork.ExtendStandart.ExtendedOpenFile;
import MyWork.NodesClass.Customer;
import MyWork.NodesClass.Region;
import MyWork.NodesClass.Switch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static MyWork.Config.*;
import static MyWork.Tools.SpeedFileParser.getParsedString;
import static MyWork.Tools.CiscoSpeedFormat.getFormattedSpeed;

public class RunActionListener implements ActionListener {
    private MainWindow mainFrame;

    RunActionListener(MainWindow frame) {
        mainFrame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        MainPanelOptic workPanel = mainFrame.mainPanel.opticPanel;
        CurrentlyRunningFrame runningFrame = mainFrame.currentlyRunning;

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

        String pathFromIntranet = "95.80.127.253(<\\/th> <td>Cisco ASR1001 <\\/td> ) [0] <=> [1] 172.17.199.254(<\\/th> <td>D-Link DGS-3120-24TC<) [22] <=> [10] 172.17.199.250(<\\/th> <td>D-Link DES-3200-10 Fa) [2] <=>  172.17.196.2(DGSSkyMAN R5000-Omxb\\\" >\\n         ) [0] <=> [0] 172.17.196.78(<\\/th> <td>SkyMAN R5000-Sm\\/5.30) [0] <=> \n";

        if (action.equals(CREATE_S)) {
            if (Boolean.valueOf(createCis)) System.out.println("С созданием на Cisco.");

            System.out.println("Создание клиента: ");

            // Создаём
            mainFrame.customer = new Customer(city, mnemokod, vlan, IPswitch, port, untagged);
            for(String connectData: pathFromIntranet.split(SEPARATOR_CONNECTION)) {
                if(SWITCH_PATTERN.matcher(connectData).find()) {
                    new DoClientOnSwitchThread(
                            connectData,
                            false,
                            mainFrame.customer,
                            CREATE_S,
                            runningFrame).start();
                }
            }
            System.out.println(mainFrame.customer);

        } else if (action.equals(DELETE_S)) {
            // Удаляем
            System.out.println("Удаление клиента: ");
            mainFrame.customer = new Customer(city, mnemokod, vlan, IPswitch, port, untagged);
            for(String connectData: pathFromIntranet.split(SEPARATOR_CONNECTION)) {
                if(SWITCH_PATTERN.matcher(connectData).find())
                    new DoClientOnSwitchThread(
                            connectData,
                            false,
                            mainFrame.customer,
                            DELETE_S,
                            runningFrame).start();
            }
            System.out.println(mainFrame.customer);

        } else if (action.equals(CHANGE_SPEED_S)) {
            // Меняем скорость
            System.out.println("Смена скорости.");
            mainFrame.eventPrintFrame.printEvent("Смена скорости.");
            new ChangeSpeedThread("speedChange", mainFrame.eventPrintFrame);

        } else {
            mainFrame.eventPrintFrame.printEvent("Не понятно.");
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
                    line = getParsedString(line);
//                    System.out.println(line);

                    String[] formattedSpeed;
                    // get prefix of mnemokod
                    String key = line.split("-")[0];
                    // if first letter in lowercase
                    if(key != null && !key.isEmpty()) {
                        key = key.substring(0, 1).toUpperCase() + key.substring(1);
                    } else {
                        frameEvent.printEvent(tempLine);
                        frameEvent.printEvent("[!!!] Error. City key not found!");
                        frameEvent.printEvent(LINE);
                        continue;
                    }

                    String[] clientNewSpeed = line.split(" ");  // ** [0] mnemokod; [1] speed
                    // get OP
                    Region citySpeed = CITIES.getOrDefault(key, null);

                    if(citySpeed != null) { // if we have found city
                        if (clientNewSpeed.length >= 2) {
                            formattedSpeed = getFormattedSpeed("service-policy", clientNewSpeed[1]);
                        } else {
                            frameEvent.printEvent(tempLine);
                            frameEvent.printEvent("[!!!] Error parse line speed.");
                            frameEvent.printEvent(LINE);
                            continue;
                        }

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
                        frameEvent.printEvent("ОП " + citySpeed.getCity());
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
        frameEvent.printEvent(BLOCK);
    } // ** readFile(BufferedReader frSpeedFile)
}

class DoClientOnSwitchThread extends Thread {
    private Customer aCustomer;
    private Switch aSwitch;
    private boolean correct = true;
    private String aToDo;
    int idLine;
    CurrentlyRunningFrame runningFrame;

    DoClientOnSwitchThread(String dataSwitch, boolean root, Customer customer, String toDo, CurrentlyRunningFrame fr) {
        aCustomer = customer;
        aToDo = toDo; // delete or create CREATE_S or DELETE_S
        runningFrame = fr;
        String upPort = "";
        String ipSw = "";
        String downPort = "";

        Pattern fullConnect = Pattern.compile("\\[(.*)] (\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3})\\(.*\\) \\[(.*)]");
        Pattern withoutUpPort = Pattern.compile("(\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3})\\(.*\\) \\[(.*)]");
        Pattern withoutDownPort = Pattern.compile("\\[(.*)] (\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3})\\(.*\\)");

        Matcher fullConnectM = fullConnect.matcher(dataSwitch);
        Matcher withoutUpPortM = withoutUpPort.matcher(dataSwitch);
        Matcher withoutDownPortM = withoutDownPort.matcher(dataSwitch);

        if(fullConnectM.find()) {
            upPort = fullConnectM.group(1);
            ipSw = fullConnectM.group(2);
            downPort = fullConnectM.group(3);
        } else if(withoutUpPortM.find()) {
            upPort = "null";
            ipSw = withoutUpPortM.group(1);
            downPort = withoutUpPortM.group(2);
        } else if(withoutDownPortM.find()) {
            upPort = withoutDownPortM.group(1);
            ipSw = withoutDownPortM.group(2);
            downPort = "null";

        } else {
            System.out.println(dataSwitch);
            correct = false;
            System.out.println("CreateClientOnSwitch -> Error pattern.");
        }

        if(correct) {
            aSwitch = new Switch(ipSw, upPort, downPort, root);
            if (aToDo.equals(CREATE_S)) {
                System.out.println("Create on sw " + aSwitch.getIp());
                aSwitch.createClient(aCustomer);
            } else if (aToDo.equals(DELETE_S)) {
                System.out.println("Delete on sw " + aSwitch.getIp());
            }
        } // ** if correct
    } // ** constructor

    @Override
    public void run() {
        idLine = runningFrame.addLine(aToDo + " на свитче " + aSwitch.getIp(), this);
        super.run();
        try {
            sleep(1000);
            if(correct)
                System.out.println(aSwitch);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            runningFrame.removeLine(idLine);
        }
    }
} // ** class DoClientOnSwitchThread
