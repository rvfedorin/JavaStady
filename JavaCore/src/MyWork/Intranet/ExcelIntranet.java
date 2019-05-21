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

import MyWork.NodesClass.Region;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import static MyWork.Config.*;

public class ExcelIntranet extends Intranet {
    private File fileXLS;
    private HSSFWorkbook workbook;

    public ExcelIntranet(Region reg) throws FileNotFoundException {
        boolean found = false;
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
            ExcelIntranet intranet = new ExcelIntranet(CITIES.get("Kr"));
//            String path = intranet.getFullPath("172.17.0.154");
//            System.out.println(path);
            // 28-172.16.48.254-11--25-172.16.44.237-26--10-172.16.42.246-7--1-172.17.239.110-10--10-172.16.48.158-8--1-172.17.154.86
            // [28] 172.16.48.254() [11] <=> [25] 172.16.44.237() [26] <=> [10] 172.16.42.246() [7] <=> [1] 172.17.239.110() [10] <=> [10] 172.16.48.158() [8] <=> [1] 172.17.154.86()

            //172.17.150.238
            String allConnection = intranet.allConnectionFromSwitch("172.17.110.122", false);
            System.out.println(allConnection);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private String findConnect(String ipDev) {
        String connect = "";
        int cellID = Integer.valueOf(region.getDevCellId());
        HSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.rowIterator();

        while (rows.hasNext()) {
            Row row = rows.next();
            if (row == null) continue;
            Cell cellDevIP = row.getCell(cellID);
            if (cellDevIP != null && cellDevIP.getStringCellValue().equals(ipDev)) {
                String ip = cellDevIP.getStringCellValue();
                String from = row.getCell(cellID + 1).getStringCellValue().replaceAll("\\d/", "");
                if (from.trim().length() < 8) { // ** START if connect UP is broken
                    if (ip.equals(region.getCoreSwitch())) { // ** check if it is broken because it id root sw
                        connect = "[" + region.getRootPort() + "] " + region.getCoreSwitch() + "(switch)";
                        break;
                    } else {
                        continue;
                    }
                } // ** END if connect UP is broken

                Matcher ipM = IP_PATTERN.matcher(ip);

                Matcher fromM = Pattern.compile("(\\d{1,2}).*?(" + IP_PATTERN + ").*?(\\d{1,2})").matcher(from);
                if (fromM.find() && ipM.find()) {
                    connect = findConnect(fromM.group(2)) + " [" + fromM.group(1) + "] " + SEPARATOR_CONNECTION + " [" + fromM.group(3) + "] " + ipM.group() + "(switch)";
                } else {
                    if (ipDev.equals(region.getCoreSwitch()))
                        connect = "[" + region.getRootPort() + "] " + region.getCoreSwitch() + "()";
                    else
                        connect = "[Error] Broken path";
                }
            }
        } // ** while
        if (connect.length() < 8)
            connect = "[Error] Broken path [[" + ipDev + "]]";

        return connect;
    }

    @Override
    public String getFullPath(String ipDev) {
        return findConnect(ipDev);
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
            if (cellData.contains(ipSw)) {
                ipToAdd = row.getCell(cellID).getStringCellValue();
                allIP.addAll(allIPConnected(ipToAdd));
//                System.out.print(ipToAdd);
//                System.out.println("  -->> " + cellData);
            }
        } // ** while (rows.hasNext())
        return allIP;
    } // ** allIPConnected(String ipSw)

    private String getCustomerOnIP(String IP) {
        String customers = "";
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
            if (cellData.contains(IP)) {
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
                        customers += "\t" + customerMnemo + "\n";
                    } else {
                        emptyLine++;
                    }
                }
                break;
            } // ** if(cellData.contains(IP))
        } // ** while (rows.hasNext())

        return customers;
    } // ** getCustomerOnIP(String IP)

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
} // ** class ExcelIntranet
