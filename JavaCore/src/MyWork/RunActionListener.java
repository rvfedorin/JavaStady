package MyWork;

import MyWork.Actions.ChangeSpeedThread;
import MyWork.Actions.DoClientOnCiscoThread;
import MyWork.Actions.DoClientOnMBThread;
import MyWork.Actions.DoClientOnSwitchThread;
import MyWork.Intranet.ExcelIntranet;
import MyWork.Intranet.Intranet;
import MyWork.Intranet.WebIntranet;
import MyWork.NodesClass.Cisco;
import MyWork.NodesClass.Customer;
import MyWork.Tools.Formatting;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.*;

import static MyWork.Config.*;
import static MyWork.Config.INTRANET_TYPE.EXCEL;

public class RunActionListener implements ActionListener {
    private MainWindow mainFrame;
    private char[] key;
    private CONNECT_TYPE connect_type;

    RunActionListener(MainWindow frame) {
        mainFrame = frame;
        key = mainFrame.authDialog.getPass();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainPanelOptic workPanel = null;


        if (mainFrame.mainPanel.jTabbedPane.getSelectedIndex() == 0) {
            workPanel = mainFrame.mainPanel.opticPanel;
            connect_type = CONNECT_TYPE.OPTIC;
        } else if (mainFrame.mainPanel.jTabbedPane.getSelectedIndex() == 1) {
            workPanel = mainFrame.mainPanel.mbPanel;
            connect_type = CONNECT_TYPE.MB;
        }


        CurrentlyRunningFrame runningFrame = mainFrame.currentlyRunning;
        EventPrintFrame eventPrint = mainFrame.eventPrintFrame;

        //getAllData() return String[]{mnemokod, vlan, IPconnection, port, untagged, createCis, city, action}
        String[] allData = null;
        if (workPanel != null)
            allData = workPanel.getAllData();
        boolean fineData = true;

        if (allData != null && allData[7].trim().equals(DELETE_S))
            allData[3] = "999";

        // checks if all data was filled
        String mnemokod, vlan, IPconnection, port, untagged, createCis, city, action;

        if (allData != null) {
            for (String s : allData) {
                if (s.length() < 1) {
                    fineData = false;
                    break;
                }
            } // for

            mnemokod = allData[0].trim();
            vlan = allData[1].trim();
            IPconnection = allData[2].trim();
            port = allData[3].trim();
            untagged = allData[4].trim();
            createCis = allData[5].trim();
            city = allData[6].trim();
            action = allData[7].trim();

            Intranet intranet = null;
            String pathFromIntranet = null;

            if (fineData) {
                try {
                    mainFrame.customer = new Customer(city, mnemokod, vlan, IPconnection, port, untagged);
                } catch (IllegalArgumentException ex) {
                    fineData = false;
                    eventPrint.pDate();
                    eventPrint.printEvent("[Error] " + ex.toString() + " " + city);
                }


                if (CURRENT_INTRANET_TYPE == EXCEL) {
                    try {
                        intranet = new ExcelIntranet(key, mainFrame.customer.getCity());
                    } catch (FileNotFoundException ex) {
                        eventPrint.pDate();
                        eventPrint.printEvent(ex.toString());
                    }
                } else {
                    intranet = new WebIntranet(key, mainFrame.customer.getCity());
                }

                if (intranet != null) {
                    switch (connect_type) {
                        case OPTIC:
                            pathFromIntranet = intranet.getFullPath(mainFrame.customer.getIPConnection());
                            pathFromIntranet = pathFromIntranet.replaceAll("<\\\\/th>|<\\\\/td>|\\\\n|<td>|\\n|\\\\\"|\\s+", "");
                            break;
                        case MB:
                            String MBmainSwitch = mainFrame.customer.getCity().getLanMB().split("X")[2];
                            String MBmainPort = mainFrame.customer.getCity().getLanMB().split("X")[3];
                            String ipConnect = mainFrame.customer.getIPConnection();
                            if (MBmainSwitch != null && MBmainPort != null) {
                                pathFromIntranet = intranet.getFullPath(MBmainSwitch);
                                pathFromIntranet = pathFromIntranet.replaceAll("<\\\\/th>|<\\\\/td>|\\\\n|<td>|\\n|\\\\\"|\\s+", "");
                                pathFromIntranet += "[" + MBmainPort + "]" + SEPARATOR_CONNECTION + "[null]" + ipConnect + "(Mobibox)";
                            }
                            break;
                        case RWR:
                            pathFromIntranet = intranet.getFullPath(mainFrame.customer.getIPConnection());
                            pathFromIntranet = pathFromIntranet.replaceAll("<\\\\/th>|<\\\\/td>|\\\\n|<td>|\\n|\\\\\"|\\s+", "");
                            break;
                    } // connection type switch

                } else {
                    fineData = false;
                }
            } // ** if (fineData)

            if (pathFromIntranet == null)
                fineData = false;

            if(fineData) {
                if (Boolean.valueOf(createCis)) {
                    System.out.println("С выполнением на Cisco.");
                    String ipCisco = mainFrame.customer.getCity().getCoreCisco();
                    pathFromIntranet = "[null]" + ipCisco + "(Cisco)[null]" + SEPARATOR_CONNECTION + pathFromIntranet;
                }
            }


            if (fineData && action.equals(CREATE_S)) {
                // Создаём
                eventPrint.pDate();
                System.out.println("Создание клиента: ");
                eventPrint.printEvent("Создание клиента: ");

                new Thread(new ControlDoOnPathThreads(
                        pathFromIntranet,
                        mainFrame.customer,
                        CREATE_S,
                        runningFrame,
                        eventPrint,
                        new String(key))
                ).start();

                System.out.println(mainFrame.customer);
                eventPrint.printEvent(mainFrame.customer.toString());

            } else if (fineData && action.equals(DELETE_S)) {
                // Удаляем
                eventPrint.pDate();

                System.out.println("Удаление клиента: ");
                eventPrint.printEvent("Удаление клиента: ");
                new Thread(new ControlDoOnPathThreads(
                        pathFromIntranet,
                        mainFrame.customer,
                        DELETE_S,
                        runningFrame,
                        eventPrint,
                        new String(key))
                ).start();

                System.out.println(mainFrame.customer);
                eventPrint.printEvent(mainFrame.customer.toString());

            } else if (action.equals(CHANGE_SPEED_S)) {
                // Меняем скорость
                eventPrint.pDate();
                System.out.println("Смена скорости.");
                eventPrint.printEvent("Смена скорости.");
                new ChangeSpeedThread("speedChange", eventPrint, runningFrame, key);

            } else {
                eventPrint.printEvent("[Error] Получены не все данные. RunActionListener -> actionPerformed()");
            } // ** if selection action
        } // if (alldata not null)

    } // ** actionPerformed(ActionEvent e)
} //
// ** class RunActionListener

class ControlDoOnPathThreads implements Runnable {
    private String ToDo;
    private String pathFromIntranet;
    private Customer customer;
    private CurrentlyRunningFrame fr;
    private EventPrintFrame eventPrint;
    private ConcurrentHashMap<String, String> resultMap;
    private String enterPass;

    ControlDoOnPathThreads(String pathFromIntranet,
                           Customer customer,
                           String toDo,
                           CurrentlyRunningFrame fr,
                           EventPrintFrame eventPr,
                           String enterPass) {
        this.ToDo = toDo;
        this.pathFromIntranet = pathFromIntranet;
        this.customer = customer;
        this.fr = fr;
        this.eventPrint = eventPr;
        this.resultMap = new ConcurrentHashMap<>();
        this.enterPass = enterPass;
    }

    @Override
    public void run() {
        ArrayList<Future> allThreadsSwitch = new ArrayList<>(); // threads connections to switches
        StringBuilder dialogMessage = new StringBuilder();

        String[] piecesOfPath = pathFromIntranet.split(SEPARATOR_CONNECTION);
        String[] connectionList = Formatting.formatPath(pathFromIntranet);
        if (connectionList != null) {// connectionList 2P192.168.1.1P12
            ExecutorService pool = Executors.newCachedThreadPool();
            StringBuilder humanPath = new StringBuilder();
            for (String s : connectionList) {
                String[] cellConnect = s.split(SEPARATOR_PORT);
                String node = "[" + cellConnect[0] + "]" + cellConnect[1] + "[" + cellConnect[2] + "] " + SEPARATOR_CONNECTION;
                humanPath.append(node);
            }
            eventPrint.pDate();
            eventPrint.printEvent(humanPath.toString());
            eventPrint.printEvent(LINE);
            int i = 0;

            for (String connectData : piecesOfPath) {
                String ip;
                ip = connectionList[i].split(SEPARATOR_PORT)[1];

                if (SWITCH_PATTERN.matcher(connectData).find()) {
                    boolean root = false;
                    if (connectionList[i].contains(customer.getCity().getCoreSwitch())) root = true;
                    DoClientOnSwitchThread tempThr = new DoClientOnSwitchThread(
                            connectionList[i],
                            root,
                            customer,
                            ToDo,
                            fr,
                            resultMap,
                            enterPass);
                    Future task = pool.submit(tempThr);
                    eventPrint.printEvent("Do on Switch " + ip);
                    allThreadsSwitch.add(task);
                } else if (MIKROTIK_PATTERN.matcher(connectData).find()) {
                    System.out.println("ДЕЛАЕМ НА МИКРОТИКЕ");
                } else if (MB_PATTERN.matcher(connectData).find()) {
                    DoClientOnMBThread tempThr = new DoClientOnMBThread(
                            customer,
                            ToDo,
                            fr,
                            resultMap,
                            enterPass);
                    Future task = pool.submit(tempThr);
                    eventPrint.printEvent("Do on Mobibox " + ip);
                    allThreadsSwitch.add(task);
                } else if (CISCO_PATTERN.matcher(connectData).find()) {
                    DoClientOnCiscoThread tempThr = new DoClientOnCiscoThread(
                            connectionList[i],
                            customer,
                            ToDo,
                            fr,
                            resultMap,
                            enterPass);
                    Future task = pool.submit(tempThr);
                    eventPrint.printEvent("Do on Cisco " + ip);
                    allThreadsSwitch.add(task);
                }
                i++;
            } // ** for(all switches)

            for (Future task : allThreadsSwitch) {
                try {
//                    task.get(10, TimeUnit.SECONDS);
                    task.get();
                } catch (ExecutionException | InterruptedException ex) {
                    eventPrint.printEvent("[Error] ControlDoOnPathThreads -->> run()");
                    ex.printStackTrace();
                    dialogMessage.append("[Error] ControlDoOnPathThreads -->> run()\n");
//                } catch (TimeoutException timeEx) {
//                    eventPrint.printEvent("[Warning] TimeoutException -> ControlDoOnPathThreads");
                }
            } // ** for(all join)

            for (String PortIpPort : connectionList) {
                String ip = PortIpPort.split(SEPARATOR_PORT)[1];
                String result = resultMap.getOrDefault(ip, "[Error] " + ip + " not found in queue.");
                dialogMessage.append(result.contains("Error") ? "[Error] " + ip + "\n" : "[Ok] " + ip + "\n");
                eventPrint.printEvent(result);
            } // ** for on path
            eventPrint.printEvent(BLOCK);

            pool.shutdown();
        } else {
            eventPrint.printEvent("[Error] wrong path format ControlDoOnPathThreads -> run()");
            dialogMessage.append("[Error] wrong path format ControlDoOnPathThreads -> run()\n");
        }// main if

        int dialogType;

        if (dialogMessage.toString().contains("Error"))
            dialogType = JOptionPane.ERROR_MESSAGE;
        else
            dialogType = JOptionPane.INFORMATION_MESSAGE;

        JOptionPane.showMessageDialog(null,
                dialogMessage,
                "Result",
                dialogType);

    } // ** run()
} // ** class ControlDoOnPathThreads


