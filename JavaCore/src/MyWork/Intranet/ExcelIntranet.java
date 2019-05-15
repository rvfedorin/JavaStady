package MyWork.Intranet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
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
        File[] dir = new File(INTRANETS_PATH + region.getCity()).listFiles();
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
            ExcelIntranet intranet = new ExcelIntranet(CITIES.get("Orel"));
            String path = intranet.getFullPath("172.17.154.86");
            System.out.println(path);
            // 28-172.16.48.254-11--25-172.16.44.237-26--10-172.16.42.246-7--1-172.17.239.110-10--10-172.16.48.158-8--1-172.17.154.86
            // [28] 172.16.48.254() [11] <=> [25] 172.16.44.237() [26] <=> [10] 172.16.42.246() [7] <=> [1] 172.17.239.110() [10] <=> [10] 172.16.48.158() [8] <=> [1] 172.17.154.86()

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
                if (from.trim().length() < 8) continue;

                Matcher ipM = IP_PATTERN.matcher(ip);

                Matcher fromM = Pattern.compile("(\\d{1,2}).*?(" + IP_PATTERN + ").*?(\\d{1,2})").matcher(from);
                if (fromM.find() && ipM.find()) {
                    connect = findConnect(fromM.group(2)) + " [" + fromM.group(1) + "] " + SEPARATOR_CONNECTION + " [" + fromM.group(3) + "] " + ipM.group() + "()";
                } else {
                    if(ipDev.equals(region.getCoreSwitch()))
                        connect = "[" + region.getRootPort() + "] " + region.getCoreSwitch() + "()";
                    else
                        connect = "[Error] Broken path";

                }
            }
        } // ** while

        if(connect.length() < 8)
            connect = "[Error] Broken path [[" + ipDev + "]]";

        return connect;
    }

    @Override
    public String getFullPath(String ipDev) {
        return findConnect(ipDev);
    }

    @Override
    public String allConnectionFromSwitch(String switchIP) {
        return null;
    }
} // ** class ExcelIntranet
