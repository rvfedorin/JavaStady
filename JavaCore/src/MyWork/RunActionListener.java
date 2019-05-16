package MyWork;

import MyWork.ExtendStandart.ExtendedOpenFile;
import MyWork.Intranet.ExcelIntranet;
import MyWork.Intranet.Intranet;
import MyWork.Intranet.WebIntranet;
import MyWork.NodesClass.Customer;
import MyWork.NodesClass.Region;
import MyWork.NodesClass.Switch;
import MyWork.Tools.Formatting;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.*;

import static MyWork.Config.*;
import static MyWork.Config.INTRANET_TYPE.EXCEL;
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
        for (String s : allData) {
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

        Intranet intranet = null;
        String pathFromIntranet = null;

        if (fineData) {
            try {
                mainFrame.customer = new Customer(city, mnemokod, vlan, IPswitch, port, untagged);
            } catch (IllegalArgumentException ex) {
                fineData = false;
                eventPrint.printEvent("[Error] " + ex.toString() + " " + city);
            }



            if (CURRENT_INTRANET_TYPE == EXCEL) {
                try {
                    intranet = new ExcelIntranet(mainFrame.customer.getCity());
                } catch (FileNotFoundException ex) {
                    eventPrint.printEvent(ex.toString());
                }
            } else {
                intranet = new WebIntranet(mainFrame.authDialog.getPass(), mainFrame.customer.getCity());
            }

            if (intranet != null) {
                pathFromIntranet = intranet.getFullPath(mainFrame.customer.getIPswitch());
            } else {
                fineData = false;
            }
        } // ** if (fineData)


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
                if (line != null && !line.matches("^\\s*$")) {
//                    System.out.println(line);
                    String tempLine = line;
                    line = getParsedString(line);
//                    System.out.println(line);

                    String[] formattedSpeed;
                    // get prefix of mnemokod
                    String key = line.split("-")[0];
                    // if first letter in lowercase
                    if (key != null && !key.isEmpty()) {
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

                    if (citySpeed != null) { // if we have found city
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
                        if (formattedSpeed != null && !formattedSpeed[0].contains("Error")) {
                            for (String s : formattedSpeed) {
                                frameEvent.printEvent(s);
                            }
                        } else if (formattedSpeed != null) {
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
        ArrayList<Future> allThreadsSwitch = new ArrayList<>(); // threads connections to switches

        String[] piecesOfPath = pathFromIntranet.split(SEPARATOR_CONNECTION);
        String[] connectionList = Formatting.formatPath(pathFromIntranet);
        if (connectionList != null) {// connectionList 2P192.168.1.1P12
            ExecutorService pool = Executors.newCachedThreadPool();
            StringBuilder humanPath = new StringBuilder();
            for (String s : connectionList) {
                String[] cellConnect = s.split(SEPARATOR_PORT);
                String node = "[" + cellConnect[0] + "]" + cellConnect[1] + "[" + cellConnect[2] + "] <=>";
                humanPath.append(node);
            }
            eventPrint.printEvent(humanPath.toString());
            eventPrint.printEvent(LINE);
            int i = 0;

            for (String connectData : piecesOfPath) {
                String ip;
                ip = connectionList[i].split(SEPARATOR_PORT)[1];

                if (SWITCH_PATTERN.matcher(connectData).find()) {
                    boolean root = false; // <<------------------------------------- NEED CHECK IF ROOT
                    DoClientOnSwitchThread tempThr = new DoClientOnSwitchThread(
                            connectionList[i],
                            root,
                            customer,
                            ToDo,
                            fr,
                            resultMap);
                    Future task = pool.submit(tempThr);
                    eventPrint.printEvent("Do on " + ip);
                    allThreadsSwitch.add(task);
                } else if (MIKROTIK_PATTERN.matcher(connectData).find()) {
                    System.out.println("ДЕЛАЕМ НА МИКРОТИКЕ");
                }
                i++;
            } // ** for(all switches)

            for (Future task : allThreadsSwitch) {
                try {
                    task.get();
                } catch (ExecutionException | InterruptedException ex) {
                    eventPrint.printEvent("[Error] ControlDoOnPathThreads -->> run()");
                    ex.printStackTrace();
                }
            } // ** for(all join)

            for (String PortIpPort : connectionList) {
                String ip = PortIpPort.split(SEPARATOR_PORT)[1];
                String result = resultMap.getOrDefault(ip, "[Error] " + ip + " not found in queue.");
                eventPrint.printEvent(result);
            } // ** for on path
            eventPrint.printEvent(BLOCK);

            pool.shutdown();
        } else {
            eventPrint.printEvent("[Error] wrong path format ControlDoOnPathThreads -> run()");
        }// main if

    } // ** run()
} // ** class ControlDoOnPathThreads

class DoClientOnSwitchThread implements Runnable {
    private Customer aCustomer;
    private Switch aSwitch;
    private boolean correct = true;
    private String aToDo;
    private CurrentlyRunningFrame runningFrame;
    private ConcurrentHashMap<String, String> resultMap;

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

        String[] connection = dataSwitch.split(SEPARATOR_PORT);
        if (connection.length == 3 && correct) {
            String upPort = connection[0];
            String ipSw = connection[1];
            String downPort = connection[2];
            aSwitch = new Switch(ipSw, upPort, downPort, root);
        }

    } // ** constructor

    @Override
    public void run() {
        int idLineOnCurrentProcess = -1;
//        super.run();
        try {
            if (correct) {
                Thread.currentThread().setName("Do on " + aSwitch.getIp());
                String message = aToDo + " на свитче " + aSwitch.getIp();
                idLineOnCurrentProcess = runningFrame.addLine(message, Thread.currentThread());
                Thread.sleep(1000);
                if (aToDo.equals(CREATE_S)) { // <-------------------- CREATE
                    String result = aSwitch.createClient(aCustomer);
                    if (result.equals(SUCCESS_S))
                        result = "Success " + aSwitch.getIp() + " " + result + " " + CREATE_S;
                    else
                        result = "[Error] " + result + " " + CREATE_S;
                    resultMap.put(aSwitch.getIp(), result);

                } else if (aToDo.equals(DELETE_S)) { // <-------------------- DELETE
                    System.out.println("Delete on sw " + aSwitch.getIp());
                    resultMap.put(aSwitch.getIp(), "Success " + " " + DELETE_S);
                }
            } // ** if correct
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            resultMap.put(aSwitch.getIp(), "[Error] " + e.toString());
            e.printStackTrace();
        } finally {
            if (idLineOnCurrentProcess > 0)
                runningFrame.removeLine(idLineOnCurrentProcess);
        }
    }
} // ** class DoClientOnSwitchThread
