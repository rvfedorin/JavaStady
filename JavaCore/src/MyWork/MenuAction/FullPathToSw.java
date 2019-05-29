package MyWork.MenuAction;

import MyWork.EventPrintFrame;
import MyWork.ExtendStandart.ExtendedTextField;
import MyWork.Intranet.ExcelIntranet;
import MyWork.Intranet.Intranet;
import MyWork.Intranet.WebIntranet;
import MyWork.NodesClass.CitiesComboBox;
import MyWork.NodesClass.Switch;
import MyWork.Verifiers.IPVerifier;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.*;

import static MyWork.Config.*;
import static MyWork.Config.INTRANET_TYPE.EXCEL;

public class FullPathToSw extends JFrame {
    private char[] passKey;
    private EventPrintFrame toPrint;
    private String ipSw;
    private String city;
    private boolean withLinks;

    public FullPathToSw(EventPrintFrame toPrint, char[] passKey) {
        this.toPrint = toPrint;
        this.passKey = passKey;

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (dimension.getWidth() - getWidth()) / 3;
        int y = (int) (dimension.getHeight() - getHeight()) / 3;
        this.setLocation(x, y);

        this.setTitle("Маршрут до свитча.");
        this.setResizable(false);

        CitiesComboBox citiesComboBox = new CitiesComboBox();
        citiesComboBox.setPreferredSize(new Dimension(100, 20));

        JLabel ipSwJL = new JLabel("IP свитча: ");
        ipSwJL.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        ExtendedTextField ipSwField = new ExtendedTextField(20);
        ipSwField.setInputVerifier(new IPVerifier(ipSwField));

        JCheckBox withLinksField = new JCheckBox("Показать с линками");
        withLinksField.setSelected(true);

        JButton okButton = new JButton("ok");
        okButton.addActionListener(e -> {
            if (ipSwField.getInputVerifier().verify(ipSwField)) {
                ipSw = ipSwField.getText();
                withLinks = withLinksField.isSelected();
                city = CITIES_BY_NAME.get(citiesComboBox.getItemAt(citiesComboBox.getSelectedIndex()));
                getFullPathToSw();
                this.dispose();
            }
        });

        JPanel inputPanel = new JPanel(new GridLayout(2, 1));
        JPanel labelPanel = new JPanel(new GridLayout(2, 1));

        labelPanel.add(ipSwJL);
        labelPanel.add(new JLabel());
        inputPanel.add(ipSwField);
        inputPanel.add(withLinksField);

        JPanel allInput = new JPanel();

        allInput.add(labelPanel);
        allInput.add(inputPanel);

        this.add(citiesComboBox, BorderLayout.NORTH);
        this.add(allInput, BorderLayout.CENTER);
        this.add(okButton, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
        ipSwField.requestFocusInWindow();
    }

    private void getFullPathToSw() {
        String rawPath = getPath();
        if (withLinks) {
            toPrint.printEvent(LINE);
            toPrint.pDate();
            toPrint.printEvent("С линками");
            toPrint.printEvent(getLinks(rawPath));
            toPrint.printEvent(LINE);
        } else {
            String clearPath = rawPath.replaceAll("\\(.*?\\)", "");
            toPrint.printEvent(LINE);
            toPrint.pDate();
            toPrint.printEvent(clearPath);
            toPrint.printEvent(LINE);
        }
    } // ** getFullPathToSw()

    private String getPath() {
        // return view [28] 172.16.48.254(switch) [9] <=> [25] 172.16.48.42(switch) [3] <=> [9] 172.16.49.134(switch)
        String result;

        Intranet intranet = null;
        if (CURRENT_INTRANET_TYPE == EXCEL) {
            try {
                intranet = new ExcelIntranet(passKey, CITIES.get(city));
            } catch (FileNotFoundException ex) {
                toPrint.printEvent(LINE);
                toPrint.pDate();
                toPrint.printEvent(ex.toString());
                toPrint.printEvent(LINE);
            }
        } else {
            intranet = new WebIntranet(passKey, CITIES.get(city));
        }

        if (intranet != null && ipSw != null) {
            result = intranet.getFullPath(ipSw);
        } else {
            result = "[Error] class FullPathToSw -> getFullPathToSw -> if(intranet != null)";
        }

        return result;
    } // ** getPath()

    private String getLinks(String path) {
        String result = "[Error] can't get links.";
        StringBuilder resultBuilder = new StringBuilder();
        ExecutorService poolThreads = Executors.newCachedThreadPool();
        ConcurrentHashMap<String, FutureTask> nodesResponse = new ConcurrentHashMap<>();
        StringBuilder ipPath = new StringBuilder();

        for (String node : path.split(SEPARATOR_CONNECTION)) {
            if (SWITCH_PATTERN.matcher(node).find()) {
                node = node.replaceAll("\\(.*?\\)|]|\\[", "");
                String[] portSwitchPort = node.split(" ");
                String upPort = null, ipSw = null, downPort = null;
                if (portSwitchPort.length == 3) {
                    upPort = portSwitchPort[0].replaceAll("\\s*", "");
                    ipSw = portSwitchPort[1].replaceAll("\\s*", "");
                    downPort = portSwitchPort[2].replaceAll("\\s*", "");
                } else if (portSwitchPort.length == 2) {
                    if (IP_PATTERN.matcher(portSwitchPort[0]).find()) {
                        upPort = "null";
                        ipSw = portSwitchPort[0].replaceAll("\\s*", "");
                        downPort = portSwitchPort[1].replaceAll("\\s*", "");
                    } else {
                        upPort = portSwitchPort[0].replaceAll("\\s*", "");
                        ipSw = portSwitchPort[1].replaceAll("\\s*", "");
                        downPort = "null";
                    }
                }
                if (ipSw != null) {
                    Switch sw = new Switch(ipSw, upPort, downPort, false, new String(passKey));
                    getNodeLinksConnect nodeLinksConnect = new getNodeLinksConnect(sw);
                    FutureTask<String> task = new FutureTask<>(nodeLinksConnect);
                    poolThreads.submit(task);
                    ipPath.append(SEPARATOR_CONNECTION).append(ipSw);
                    nodesResponse.put(ipSw, task);
                } else {
                    resultBuilder.append("[").append(node).append("]");
                }
                String ipPathStr = ipPath.toString().replaceFirst(SEPARATOR_CONNECTION, "");
                for(String ip: ipPathStr.split(SEPARATOR_CONNECTION)) {
                    System.out.println(nodesResponse.get(ip));
                    resultBuilder.append(nodesResponse.get(ip));
                }
            } // ** if it is switch
            result = resultBuilder.toString();
        }
        return result;
    }
} // ** class

class getNodeLinksConnect implements Callable<String> {
    private Switch swNode;

    public getNodeLinksConnect(Switch sw) {
        swNode = sw;
    }

    @Override
    public String call() {
        String result = "";
        ArrayList<String> commands = new ArrayList<>();
        commands.add("sh ports " + swNode.getUpPort() + " detail");
        commands.add("q");
        String resultCommand = swNode.runCommand(commands);

        result = resultCommand;
        return result;
    }

} // ** class getNodeLinksConnect
