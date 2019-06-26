package MyWork.NodesClass;

import java.io.*;
import java.util.HashMap;

import static MyWork.Config.BD_REGIONS_FILE;
import static MyWork.Config.CITIES;

public class BD {

    public static HashMap<String, Region> getRegions() {
        HashMap<String, Region> regionsHashMap = new HashMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BD_REGIONS_FILE))) {
            regionsHashMap = (HashMap<String, Region>) ois.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
            regionsHashMap.put("[Error] file with DB Regions not found.", null);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            regionsHashMap.put("[Error] HashMap not found in " + BD_REGIONS_FILE, null);
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
