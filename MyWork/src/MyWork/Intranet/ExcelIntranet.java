package MyWork.Intranet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import MyWork.NodesClass.Customer;
import MyWork.NodesClass.Region;
import MyWork.NodesClass.Switch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.Match;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import static MyWork.Config.*;

public class ExcelIntranet extends Intranet {
    private File fileXLS;
    private HSSFWorkbook workbook;
    private Region region;
    private char[] pass;

    public ExcelIntranet(char[] key, Region reg) throws FileNotFoundException {
        boolean found = false;
        pass = key;
        region = reg;
        if (region == null) throw new FileNotFoundException("Region not found dir.");
        File[] dir = new File(INTRANETS_PATH + region.getCity()).listFiles();
        if (dir == null) throw new FileNotFoundException("Intranet " + region.getCity() + " not found dir.");
        for (File f : dir) {
            if (f.getName().contains(region.getCity() + ".xls")) {
                fileXLS = f;
                found = true;
            }
        } // ** for files
        if (!found) throw new FileNotFoundException("Intranet " + region.getCity() + " not found.");
        try {
            workbook = new HSSFWorkbook(new FileInputStream(fileXLS));
        } catch (IOException ex) {
            throw new FileNotFoundException("Intranet " + region.getCity() + " error open workbook. ");
        }

    } // ** constructor

    public static void main(String[] args) {
        try {
            char[] key = "pass".toCharArray();
            ExcelIntranet intranet = new ExcelIntranet(key, CITIES.get("Kr"));
//            String path = intranet.getFullPath("172.17.0.154");
//            System.out.println(path);
            // 28-172.16.48.254-11--25-172.16.44.237-26--10-172.16.42.246-7--1-172.17.239.110-10--10-172.16.48.158-8--1-172.17.154.86
            // [28] 172.16.48.254() [11] <=> [25] 172.16.44.237() [26] <=> [10] 172.16.42.246() [7] <=> [1] 172.17.239.110() [10] <=> [10] 172.16.48.158() [8] <=> [1] 172.17.154.86()

            //172.17.150.238
//            String allConnection = intranet.allConnectionFromSwitch("172.17.110.122", false);
//            System.out.println(allConnection);

            String customerFrom = intranet.findClient("Kr-GrandOptKM");
            // 24-172.17.0.10-16--26-172.17.1.38-25--25-172.17.1.42-5--1-172.17.1.138
            System.out.println(customerFrom);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private String findConnect(String ipDev) {
        String connect = "";
        int cellID;
        try {
            cellID = Integer.valueOf(region.getDevCellId());
        } catch (Exception ex) {
            return "[Error] when try get ID of region (package Intranet -> class ExcelIntranet)";
        }
        HSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.rowIterator();

        while (rows.hasNext()) {
            Row row = rows.next();
            if (row == null) continue;
            Cell cellDevIP = row.getCell(cellID);
            String cellValue;
            try {
                cellValue = cellDevIP.getStringCellValue();
            } catch (Exception ex) {
                continue;
            }
            if (cellValue.equals(ipDev)) {
                String ip = cellValue;

                if (ip.equals(region.getCoreSwitch())) { // ** check if it is broken because it id root sw
                    connect = "[" + region.getRootPort() + "] " + region.getCoreSwitch() + "(switch)";
                    break;
                }
                String from = row.getCell(cellID + 1).getStringCellValue().replaceAll("\\d/", "");

                Matcher ipM = IP_PATTERN.matcher(ip);

                Matcher fromM = Pattern.compile("(\\d{1,2}).*?(" + IP_PATTERN + ").*?(\\d{1,2})").matcher(from);
                if (fromM.find() && ipM.find()) {
                    connect = findConnect(fromM.group(2)) + " [" + fromM.group(1) + "] " + SEPARATOR_CONNECTION + " [" + fromM.group(3) + "] " + ipM.group() + "(switch)";
                } else {
                    if (ipDev.equals(region.getCoreSwitch()))
                        connect = "[" + region.getRootPort() + "] " + region.getCoreSwitch() + "(switch)";
                    else
                        connect = "[Error] Broken path";
                }
            }
        } // ** while
        if (connect.length() < 8)
            connect = "[Error] Broken path [[" + ipDev + "]] [[" + region.getCity() + "]]";

        return connect;
    }

    private Set<String> allIPConnected(String ipSw) {
        Set<String> allIP = new HashSet<>();
        String ipToAdd;

        if (!IP_PATTERN.matcher(ipSw).find()) return allIP;
        allIP.add(ipSw);

        int cellID = Integer.valueOf(region.getDevCellId());

        Iterator<Row> rows = workbook.getSheetAt(0).rowIterator();
        while (rows.hasNext()) {
            Row row = rows.next();
            Cell connecting = row.getCell(cellID + 1);
            String cellData;
            try {
                cellData = connecting.getStringCellValue();
            } catch (IllegalStateException | NullPointerException ex) {
                continue;
            }

            Matcher cellDataM = Pattern.compile(".*?(" + IP_PATTERN + ").*?").matcher(cellData);
            if(cellDataM.find()) {
                cellData = cellDataM.group(1);
            } else {
                cellData = "none";
            }

            if (cellData.equals(ipSw)) {
                ipToAdd = row.getCell(cellID).getStringCellValue();
                allIP.addAll(allIPConnected(ipToAdd));
//                System.out.print(ipToAdd);
//                System.out.println("  -->> " + cellData);
            }
        } // ** while (rows.hasNext())
        return allIP;
    } // ** allIPConnected(String ipSw)

    private String getCustomerOnIP(String IP) {
        StringBuilder customers = new StringBuilder();
        Iterator<Row> rows = workbook.getSheetAt(0).rowIterator();
        int cellID = Integer.valueOf(region.getDevCellId());

        while (rows.hasNext()) {
            Row row = rows.next();
            Cell cell = row.getCell(cellID);
            String cellData;
            try {
                cellData = cell.getStringCellValue();
            } catch (IllegalStateException | NullPointerException ex) {
                continue;
            }

            Matcher cellDataM = Pattern.compile(".*?(" + IP_PATTERN + ").*?").matcher(cellData);
            if(cellDataM.find()) {
                cellData = cellDataM.group(1);
            } else {
                cellData = "none";
            }

            if (cellData.equals(IP)) {
                int emptyLine = 0;
                while (emptyLine < 3) {
                    row = rows.next();
                    String customerMnemo;
                    try {
                        customerMnemo = row.getCell(0).getStringCellValue();
                    } catch (IllegalStateException | NullPointerException ex) {
                        emptyLine++;
                        continue;
                    }
                    if (customerMnemo.startsWith(region.getPrefix())) {
                        customers.append("\t" + customerMnemo + "\n");
                    } else {
                        emptyLine++;
                    }
                }
                break;
            } // ** if(cellData.contains(IP))
        } // ** while (rows.hasNext())

        return customers.toString();
    } // ** getCustomerOnIP(String IP)

    private String findConnectedFrom(String ip, String upPorts, String vlanName, int vlanNumber) {
        StringBuilder result = new StringBuilder();
        String connectedLine = "";

        for (String port : upPorts.split(",")) {
            port = port.trim();
            if (port.length() < 1)
                continue;

            String connectFrom = "";
            int cellID = Integer.valueOf(region.getDevCellId());
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.rowIterator();
            while (rows.hasNext()) {
                Row row = rows.next();
                if (row == null) continue;
                Cell cellDevConnect = row.getCell(cellID + 1);
                Pattern connectPattern = Pattern.compile(".*\\D" + port + "\\D.*\\D" + ip + "\\D.*");
                if (cellDevConnect != null) {
                    try {
                        String cellValue = cellDevConnect.getStringCellValue().replaceAll(IP_PATTERN + "/[\\d]{1,2}", " ");
                        cellValue = cellValue.replaceAll("0/", "");  // remove if there is port view Gi0/2
                        Matcher matcher = connectPattern.matcher(cellValue);
                        if (matcher.find()) {
//                            System.out.println("Pattern port:" + port + " IP:" + ip);
                            connectedLine = matcher.group();
//                            System.out.println("matcher.find() " + connectFrom);
                            Cell cellDev = row.getCell(cellID);
                            connectFrom = cellDev.getStringCellValue();
                            break;
                        }
                    } catch (Exception ex) {}
                }
            } // ** while looking for row by row
            Matcher getIP = IP_PATTERN.matcher(connectFrom);
            if (getIP.find()) {
                connectFrom = getIP.group();
//                System.out.println("Connect from " + connectFrom);
                Switch node = new Switch(connectFrom, port, "none", false, String.valueOf(pass));
                String vlanOnSw = node.showVlanByNumber(vlanNumber);

                if (!ERROR_ON_SWITCHES_PATTERN.matcher(vlanOnSw).find()) {
                    if (!vlanOnSw.contains("Error")) {
//                System.out.println("VLAN " + vlanOnSw);
                        String portsAndVlan = getTaggedPortsAndVlan(vlanOnSw, node.getUpPort());
//                System.out.println("GET TAGGED " + portsAndVlan);
                        if (!portsAndVlan.contains("untagged") && portsAndVlan.split("XXX").length > 1) {
                            String ports = portsAndVlan.split("XXX")[0];
                            int newVlanNumber = Integer.valueOf(portsAndVlan.split("XXX")[1]);
                            result.append(node.getIp()).append(" -> ");
                            result.append(findConnectedFrom(node.getIp(), ports, vlanName, newVlanNumber));
                        }

                        if (portsAndVlan.contains("untagged"))
                            result.append(node.getIp());
                    } else {// ** if Error in node.showVlanByNumber(vlanNumber);
                        result.append(vlanOnSw);
                    }
                } else {
                    result.append("\nNot found on ").append(connectFrom).append("(").append(connectedLine).append(") [END]\n");
                }

            } else {
                result.append("[END]\n");
            }
        }

        return result.toString();
    } // ** findConnectedFrom()

    /*
     * return:
     *  "[Error]" - if error on switch
     *  "1,2,3" - if have tagged ports and have not untagged
     *  "untagged" - if untagged
     */
    private String getTaggedPortsAndVlan(String showVlanOut, String upPort) {
        String result = "[Error]";
        String ports = "";
        int vlanNumber = 0;
        boolean untagged = false;
        boolean hasNotTag = true;

        for (String line : showVlanOut.split("\n")) {
            if (ERROR_ON_SWITCHES_PATTERN.matcher(line).find())
                break;
            line = line.toLowerCase();
            if (line.contains("name")) {
                String stringVlanNumber = line.split("name")[0];
                stringVlanNumber = stringVlanNumber.replaceAll("\\D", "");
                vlanNumber = Integer.valueOf(stringVlanNumber);
            }
            if (line.contains("tagged") && hasNotTag) {
                ports = line;
                hasNotTag = false;
            }

            if (line.contains("untagged")) {
                String havePorts = line.replaceAll("\\D", "");
                if (havePorts.length() > 0) {
                    untagged = true;
                    break;
                }
            }
        } // ** for output from switch (show vlan)

        if (!untagged && vlanNumber != 0 && ports.length() > 1) {
            ports = ports.replaceAll("1:", "").replaceAll("[^0-9,\\-]", "");
            ports = ports.replaceAll(upPort, "");

            if (ports.split("-").length > 1) {
                int portStart = Integer.valueOf(ports.split("-")[0]);
                int portEnd = Integer.valueOf(ports.split("-")[1]);
                String newPorts = "";
                for (int port = portStart; port < portEnd; port++) {
                    newPorts += port;
                    if (port > portStart)
                        newPorts += ",";
                }
                ports = newPorts;
            }


            result = ports + "XXX" + vlanNumber;
        }

        if (untagged)
            result = "untagged";

        return result;
    }

    @Override
    public String getFullPath(String ipDev) {
        return findConnect(ipDev);
    }

    @Override
    public String allConnectionFromSwitch(String switchIP, boolean onlySw) {
        String result = "Все подключения от: " + switchIP + "\n";
        Set<String> allIP = new HashSet<>();
        allIP.add(switchIP);
        allIP.addAll(allIPConnected(switchIP));
        if (onlySw) {
            for (String ip : allIP) {
                result += ip + "; \n";
            }
        } else {
            for (String ip : allIP) {
                result += ip + "\n";
                result += getCustomerOnIP(ip);
            }
        }
        return result;
    }

    @Override
    public String findClient(String mnemokod) {
        String result = "Customer not found on root.";
        Switch rootSw = new Switch(region.getCoreSwitch(), region.getRootPort(), "null", true, String.valueOf(pass));

        String ports;
        int vlanNumber;
        String vlanOnSw = rootSw.showVlanByName(mnemokod);
        String portsAndVlan = getTaggedPortsAndVlan(vlanOnSw, rootSw.getUpPort());

        if (!portsAndVlan.contains("untagged") && portsAndVlan.split("XXX").length > 1) {
            ports = portsAndVlan.split("XXX")[0];
            vlanNumber = Integer.valueOf(portsAndVlan.split("XXX")[1]);

            result = rootSw.getIp() + " -> " + findConnectedFrom(region.getCoreSwitch(), ports, mnemokod, vlanNumber);
//            System.out.println("findClient -->> " + result);
        }

        if (portsAndVlan.contains("untagged"))
            result = rootSw.getIp();

        return result;
    }
} // ** class ExcelIntranet
