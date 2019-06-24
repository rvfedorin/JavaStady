package MyWork.Actions;

import MyWork.EventPrintFrame;
import MyWork.NodesClass.Switch;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static MyWork.Config.LINE;

public class FreePorts extends Thread {
    private char[] pass;
    private String swIP;
    private EventPrintFrame toPrint;
    private Switch aSwitch;
    private JTextField portField = null;
    private boolean addedToField = false;

    public FreePorts(String ip, char[] key, EventPrintFrame print) {
        swIP = ip;
        pass = key;
        toPrint = print;


        aSwitch = new Switch(swIP, "null", "null", false, new String(pass));
        System.out.println("Создан объект свитча: " + ip);
    }

    public FreePorts(String ip, char[] key, EventPrintFrame print, JTextField aPortField) {
        swIP = ip;
        pass = key;
        toPrint = print;
        portField = aPortField;


        aSwitch = new Switch(swIP, "null", "null", false, new String(pass));
        System.out.println("Создан объект свитча: " + ip);
    }

    @Override
    public void run() {
        super.run();
        getFreePorts();
    }

    private void getFreePorts() {
        ArrayList<String> showPortsCommand = new ArrayList<>();
        ArrayList<String> showVlanPortsCommand = new ArrayList<>();
        String out;
        System.out.println("Поиск свободных портов на свитче: " + aSwitch.getIp());
        toPrint.printEvent("Поиск свободных портов на свитче: " + aSwitch.getIp());
        showPortsCommand.add("show ports");
        showPortsCommand.add("n");
        showPortsCommand.add("n");
        showPortsCommand.add("n");
        showPortsCommand.add("q");

        out = aSwitch.runCommand(showPortsCommand);
        out = out.replaceAll("\\(C\\)|\\(F\\)", "");
        System.out.println(out);

        if (out.contains("Error")) {
//            System.out.println("--->>> " + out);
            toPrint.printEvent(out);
        } else {
            String[] ports = getWithoutLinks(out);

            for (String port : ports) {
                showVlanPortsCommand.add("show vlan ports " + port);
                showVlanPortsCommand.add("q");

                System.out.println("Порт без линка: " + port);
            } // ** for ports without link

            String outVlans = aSwitch.runCommand(showVlanPortsCommand);
            int count = 0;

            for (String port : ports) {
                if (!hasVlans(outVlans, port)) {
                    if(!addedToField) {
                        portField.setText(port);
                        addedToField = true;
                    }
                    toPrint.printEvent("Свободный порт: " + port);
                    count++;
                }
            } // ** for out ports

            if(count == 0)
                toPrint.printEvent("Свободных портов не найдено");
            toPrint.printEvent(LINE);
        }

    } // ** getFreePorts()

    private String[] getWithoutLinks(String out) {
        String[] result;
        ArrayList<String> portsList = new ArrayList<>();
        Set<String> portsWithLinkSet = new HashSet<>();
        Set<String> portsWithoutLinkSet = new HashSet<>();
        String[] linesOut = out.split("\n");

        for (String line : linesOut) {
            if (line.contains("Down")) {
                line = line.replaceAll("\\s+", " ");
                String port = line.trim().split(" ")[0];
                if (!portsWithoutLinkSet.contains(port) && !portsWithLinkSet.contains(port)) {
                    portsList.add(port);
                    portsWithoutLinkSet.add(port);
                }
            } else {
                line = line.replaceAll("\\s+", " ");
                String port = line.trim().split(" ")[0];
                portsWithLinkSet.add(port);
                portsWithoutLinkSet.remove(port);
                portsList.remove(port);
            }
        } // ** for every line

        result = portsList.toArray(new String[0]);

        return result;
    }

    private boolean hasVlans(String out, String port) {
        boolean result = true;
        ArrayList<String> vlans = new ArrayList<>();

        Pattern vlanPattern = Pattern.compile(port + " (\\d{1,4})");
        Pattern vlanPattern2 = Pattern.compile("(\\d{1,4}) [-X]");
        int count = 0;

        for (String line : out.split("\n")) {
            line = line.replaceAll("\\s+", " ");
            Matcher matcher = vlanPattern.matcher(line);
            if (matcher.find()) {
                vlans.add(matcher.group(1));
            } else if (line.contains("Port " + port)) {
                count = 1;
            }

            if (count > 0 && count < 4) {
                if(count == 1) {
                    count++;
                    continue;
                }
                Matcher matcher1 = vlanPattern2.matcher(line);

                if(matcher1.find()) {
                    vlans.add(matcher1.group(1));
                    count++;
                } else if(line.contains("Port ")) { // it means started a next port
                    count = 4;
                }
            }
        }
        System.out.println(vlans);
        vlans.remove("1");
        if (vlans.size() <= 0) {
            result = false;
        }

        return result;
    }

}
