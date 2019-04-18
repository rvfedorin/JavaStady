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
//
/////////////// **************** END GUI STRINGS ************* ////////////////////////

    public static final String SEPARATOR_CONNECTION = " <=> ";
    public static final Pattern SWITCH_PATTERN = Pattern.compile("DES|switch|D-Link|DGS");

    public static final String SPEEDS_FILE = "speeds.txt";
    public static final String LOG_FILE = "log.txt";


    public static final Map<String, Region> CITIES;
    static {
        CITIES = new HashMap<>();
        CITIES.put("Orel", new Region("Orel", "Orel", "1"));
        CITIES.put("Kr", new Region("Kr", "Kursk", "2"));
        CITIES.put("Mag", new Region("Mag", "Magnitogorsk", "3"));
        CITIES.put("Vrzh", new Region("Vrzh", "Voronezh", "4"));
    }

    public static final Map<String, String> CITIES_BY_NAME;
    static {
        CITIES_BY_NAME = new HashMap<>();
        CITIES_BY_NAME.put("Orel", "Orel");
        CITIES_BY_NAME.put("Kursk", "Kr");
        CITIES_BY_NAME.put("Magnitogorsk", "Mag");
        CITIES_BY_NAME.put("Voronezh", "Vrzh");
    }

    public static String[] LABELS = {
            MNEMOKOD_S,
            NUMBER_VLAN_S,
            IP_SWITCH_S,
            PORT_S,
            UNTAGGED_S,
            ACT_ON_CISCO_S};

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
