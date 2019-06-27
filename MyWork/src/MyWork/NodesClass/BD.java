package MyWork.NodesClass;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;

import static MyWork.Config.BD_REGIONS_FILE;

public class BD {

    public static HashMap<String, Region> getRegions() {
        HashMap<String, Region> regionsHashMap = new HashMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BD_REGIONS_FILE))) {
            regionsHashMap = (HashMap<String, Region>) ois.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "[Error] file with DB Regions not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
//            regionsHashMap.put("[Error] file with DB Regions not found.", null);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "[Error] HashMap not found in " + BD_REGIONS_FILE,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
//            regionsHashMap.put("[Error] HashMap not found in " + BD_REGIONS_FILE, null);
        }
        return regionsHashMap;
    } // ** getRegions()

    public static boolean createDB(HashMap<String, Region> mapRegions) {
        boolean result = false;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BD_REGIONS_FILE))) {
            oos.writeObject(mapRegions);
            result = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public static void main(String[] args) {
       // createDB(CITIES);
    }

} // ** class BD
