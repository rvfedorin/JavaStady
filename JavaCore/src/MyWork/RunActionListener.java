package MyWork;

import MyWork.ExtendStandart.ExtendedOpenFile;
import MyWork.NodesClass.Customer;
import MyWork.NodesClass.Region;
import MyWork.NodesClass.Switch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
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
        EventPrintFrame eventPrint = mainFrame.eventPrintFrame;

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

        String mnemokod = allData[0].trim();
        String vlan = allData[1].trim();
        String IPswitch = allData[2].trim();
        String port = allData[3].trim();
        String untagged = allData[4].trim();
        String createCis = allData[5].trim();
        String city = allData[6].trim();
        String action = allData[7].trim();

        // obtained from WebIntranet
        String pathFromIntranet = "95.80.127.253(<\\/th> <td>Cisco ASR1001 <\\/td> ) [0] <=> [1] 172.17.199.254(<\\/th> <td>D-Link DGS-3120-24TC<) [22] <=> [10] 172.17.199.250(<\\/th> <td>D-Link DES-3200-10 Fa) [2] <=>  172.17.196.2(DGSSkyMAN R5000-Omxb\\\" >\\n         ) [0] <=> [0] 172.17.196.78(<\\/th> <td>SkyMAN R5000-Sm\\/5.30) [0] <=> \n";

        if (fineData)
            try {
                mainFrame.customer = new Customer(city, mnemokod, vlan, IPswitch, port, untagged);
            } catch (IllegalArgumentException ex) {
                fineData = false;
                eventPrint.printEvent("[Error] " + ex.toString() + " " + city);
            }

        if (fineData && action.equals(CREATE_S)) {
            if (Boolean.valueOf(createCis))
                System.out.println("С созданием на Cisco.");

            System.out.println("Создание клиента: ");
            eventPrint.printEvent("Создание клиента: ");
            pathFromIntranet = pathFromIntranet.replaceAll("<\\\\/th>|<\\\\/td>|\\\\n|<td>|\\n|\\\\\"|\\s+", "");
            // Создаём
            new Thread(new ControlDoOnPathThreads(
                            pathFromIntranet,
                            mainFrame.customer,
                            CREATE_S,
                            runningFrame,
                            eventPrint)
            ).start();

            System.out.println(mainFrame.customer);
            eventPrint.printEvent(mainFrame.customer.toString());

        } else if (fineData && action.equals(DELETE_S)) {
            // Удаляем
            System.out.println("Удаление клиента: ");
            eventPrint.printEvent("Удаление клиента: ");
            new Thread(new ControlDoOnPathThreads(
                            pathFromIntranet,
                            mainFrame.customer,
                            DELETE_S,
                            runningFrame,
                            eventPrint)
            ).start();

            System.out.println(mainFrame.customer);
            eventPrint.printEvent(mainFrame.customer.toString());

        } else if (action.equals(CHANGE_SPEED_S)) {
            // Меняем скорость
            System.out.println("Смена скорости.");
            eventPrint.printEvent("Смена скорости.");
            new ChangeSpeedThread("speedChange", eventPrint, runningFrame);

        } else {
            eventPrint.printEvent("[Error] Получены не все данные. RunActionListener -> actionPerformed()");
        } // ** if selection action

    } // ** actionPerformed(ActionEvent e)
} //
// ** class RunActionListener

class ChangeSpeedThread extends Thread {
    private EventPrintFrame frameEvent;
    private CurrentlyRunningFrame runningFrame;

    ChangeSpeedThread(String name, EventPrintFrame printEvent, CurrentlyRunningFrame fr) {
        super(name);
        this.frameEvent = printEvent;
        this.runningFrame = fr;
        this.start();
    }
    @Override
    public void run() {
        int idLineOnCurrentProcess = runningFrame.addLine("Смена скоростей.", this);
        try {
            BufferedReader frSpeedFile = ExtendedOpenFile.readFile();
            if (frSpeedFile != null) readFile(frSpeedFile);
        } catch (Exception ex) {
            System.out.println("Error in ChangeSpeedThread -> run()");
            throw ex;
        } finally {
            runningFrame.removeLine(idLineOnCurrentProcess);
        }
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

class ControlDoOnPathThreads implements Runnable {
    private String ToDo;
    private String pathFromIntranet;
    private Customer customer;
    private CurrentlyRunningFrame fr;
    private EventPrintFrame eventPrint;
    private ConcurrentHashMap<String, String> resultMap;

    ControlDoOnPathThreads(String pathFromIntranet,
                           Customer customer,
                           String toDo,
                           CurrentlyRunningFrame fr,
                           EventPrintFrame eventPr) {
        this.ToDo = toDo;
        this.pathFromIntranet = pathFromIntranet;
        this.customer = customer;
        this.fr = fr;
        this.eventPrint = eventPr;
        this.resultMap = new ConcurrentHashMap<>();
    }

    @Override
    public void run() {
        ArrayList<DoClientOnSwitchThread> allThreadsSwitch = new ArrayList<>(); // threads connections to switches
        eventPrint.printEvent(pathFromIntranet.replaceAll("\\(.*\\)", ""));

        for(String connectData: pathFromIntranet.split(SEPARATOR_CONNECTION)) {
            if(SWITCH_PATTERN.matcher(connectData).find()) {
                boolean root = false; // CHECK IF ROOT
                DoClientOnSwitchThread tempThr = new DoClientOnSwitchThread(
                        connectData,
                        root,
                        customer,
                        ToDo,
                        fr,
                        resultMap);
                tempThr.setName("Do on " + connectData);
                tempThr.start();
                eventPrint.printEvent("Do on " + connectData);
                allThreadsSwitch.add(tempThr);
            }
        } // ** for(all switches)

        for(Thread thread: allThreadsSwitch) {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        } // ** for(all join)
    } // ** run()
} // ** class ControlDoOnPathThreads

class DoClientOnSwitchThread extends Thread {
    private Customer aCustomer;
    private Switch aSwitch;
    private boolean correct = true;
    private String aToDo;
    private CurrentlyRunningFrame runningFrame;
    ConcurrentHashMap<String, String> resultMap;

    DoClientOnSwitchThread(String dataSwitch,
                           boolean root,
                           Customer customer,
                           String toDo,
                           CurrentlyRunningFrame fr,
                           ConcurrentHashMap<String, String> resultMap) {
        this.aCustomer = customer;
        this.aToDo = toDo; // delete or create CREATE_S or DELETE_S
        this.runningFrame = fr;
        this.resultMap = resultMap;
        String upPort = "";
        String ipSw = "";
        String downPort = "";

//        Pattern fullConnect = Pattern.compile("\\[(.*)] (\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3})\\(.*\\) \\[(.*)]");
//        Pattern withoutUpPort = Pattern.compile("(\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3})\\(.*\\) \\[(.*)]");
//        Pattern withoutDownPort = Pattern.compile("\\[(.*)] (\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3})\\(.*\\)");

        Pattern fullConnect = Pattern.compile("\\[(.*)](\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3})\\(.*\\)\\[(.*)]");
        Pattern withoutUpPort = Pattern.compile("(\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3})\\(.*\\)\\[(.*)]");
        Pattern withoutDownPort = Pattern.compile("\\[(.*)](\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3})\\(.*\\)");

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
            correct = false;
            resultMap.put(ipSw, "[Error] " + dataSwitch + "\n[Error] CreateClientOnSwitch -> Error pattern.");
            System.out.println(dataSwitch);
            System.out.println("CreateClientOnSwitch -> Error pattern.");
        }
        if(correct)
            aSwitch = new Switch(ipSw, upPort, downPort, root);

    } // ** constructor

    @Override
    public void run() {
        int idLineOnCurrentProcess = -1;
        super.run();
        try {
            if(correct) {
                String message = aToDo + " на свитче " + aSwitch.getIp();
                idLineOnCurrentProcess = runningFrame.addLine(message, this);
                sleep(1000);
                if (aToDo.equals(CREATE_S)) {
                    System.out.println("Create on sw " + aSwitch.getIp());
                    aSwitch.createClient(aCustomer);
                    resultMap.put(aSwitch.getIp(), "Success " + CREATE_S);
                } else if (aToDo.equals(DELETE_S)) {
                    System.out.println("Delete on sw " + aSwitch.getIp());
                    resultMap.put(aSwitch.getIp(), "Success " + DELETE_S);
                }
            } // ** if correct
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(idLineOnCurrentProcess > 0)
                runningFrame.removeLine(idLineOnCurrentProcess);
        }
    }
} // ** class DoClientOnSwitchThread
