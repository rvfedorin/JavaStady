package MyWork;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import MyWork.NodesClass.Region;

public abstract class Config {
    public static final String VERSION = "1.0";

/////////////// **************** START GUI STRINGS ************* ////////////////////////
//
    public static final String CREATE_S = "Создать";
    public static final String DELETE_S = "Удалить";
    public static final String CHANGE_SPEED_S = "Сменить скорость";
    public static final String TITLE_BORDER_S = "Выберите действие: ";
    public static final String MNEMOKOD_DELIMITER_S = "-";
    public static final String MNEMOKOD_S = "Мнемокод: ";
    public static final String NUMBER_VLAN_S = "Номер vlan: ";
    public static final String IP_SWITCH_S = "IP свитча: ";
    public static final String PORT_S = "Порт: ";
    public static final String UNTAGGED_S = "Untagged";
    public static final String CREATE_CISCO_S = "Создать на Cisco";
    public static final String DELETE_CISCO_S = "Удалить на Cisco";
    public static final String ACT_ON_CISCO_S = "Действие на Cisco";
    public static final String LINE = "===================================";
    public static final String BLOCK = "" +
            "\n*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=" +
            "\n*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=" +
            "\n*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=\n";

    public static String[] LABELS = {
            MNEMOKOD_S,
            NUMBER_VLAN_S,
            IP_SWITCH_S,
            PORT_S,
            UNTAGGED_S,
            ACT_ON_CISCO_S};
//
/////////////// **************** END GUI STRINGS ************* ////////////////////////


    public static final String SEPARATOR_CONNECTION = " <=> ";
    public static final Pattern SWITCH_PATTERN = Pattern.compile("DES|switch|D-Link|DGS");

    public static final String SPEEDS_FILE = "speeds.txt";
    public static final String LOG_FILE = "log.txt";


    public static final Map<String, Region> CITIES;
    static {
        CITIES = new HashMap<>();
        CITIES.put("Orel", new Region("Orel", "Orel", "23"));
        CITIES.put("Kr", new Region("Kr", "Kursk", "24"));
        CITIES.put("Mag", new Region("Mag", "Magnitogorsk", "46"));
        CITIES.put("Vrzh", new Region("Vrzh", "Voronezh", "47"));

        CITIES.put("Bel", new Region("Bel", "Belgorod", "38"));
        CITIES.put("Lp", new Region("Lp", "Lipetsk", "39"));
        CITIES.put("Raz", new Region("Raz", "Ryazan", "40"));
        CITIES.put("Chel", new Region("Chel", "Chelyabinsk", "41"));

        CITIES.put("Br", new Region("Br", "Bryansk", "7"));
        CITIES.put("Kq", new Region("Kq", "Kaluga", "8"));
        CITIES.put("Tul", new Region("Tul", "Tula", "9"));
        CITIES.put("Sm", new Region("Sm", "Smolensk", "10"));

        CITIES.put("Kol", new Region("Kol", "Kolomna", "42"));
        CITIES.put("Kd", new Region("Kd", "Krasnodar", "43"));
        CITIES.put("Rnd", new Region("Rnd", "Rostov", "6"));
        CITIES.put("Vol", new Region("Vol", "Volgograd", "45"));

        CITIES.put("Ek", new Region("Ek", "Ekaterinburg", "25"));
        CITIES.put("St", new Region("St", "Stavropol", "26"));

        CITIES.put("Net", new Region("Net", "Net", "999"));

    }

    public static final Map<String, String> CITIES_BY_NAME;
    static {
        CITIES_BY_NAME = new HashMap<>();
        CITIES.forEach((k, v) -> CITIES_BY_NAME.put(k, v.getCity()));
    }



/////////////// **************** START UNIX SETTINGS ************* ////////////////////////
//

    public static final String CLIENTS_CONF = "/etc/Clients.conf";
//
/////////////// **************** END UNIX SETTINGS ************* ////////////////////////

/////////////// **************** START PASS STRINGS ************* ////////////////////////
//
    public static final String IV = "31415926";
    public static final String RUN_LOGIN = "admin";
    public static final String RUN_PASS = "jaNUpm9cNWvfct86RHRwwQ==";
    public static final String INTRANET_CONNECT_LOGIN = "bXFDZHf5gYHnnQkHAQL4+A==";
    public static final String INTRANET_CONNECT_PASS = "xXHPPl7DZLd/M+T0fm7O4w==";
//
/////////////// **************** END PASS STRINGS ************* ////////////////////////

/////////////// **************** START WEB INTRANET ************* ////////////////////////
//
    public static final String AUTH_URL = "https://intranet.ptl.ru/AutorizUser.php";
    public static final String CONNECTION_URL = "https://intranet.ptl.ru/connection/";
    public static final String EDIT_CLIENT_URL = "https://intranet.ptl.ru/connection/EditClient.php";
//
////////////// **************** END WEB INTRANET ************* ////////////////////////

} // END Config
