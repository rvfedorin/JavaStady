package MyWork;

import java.util.HashMap;
import java.util.Map;

public abstract class Config {
    public static final String VERSION = "1.0";

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

    public static final String SPEEDS_FILE = "speeds.txt";


    public static final Map<String, String> CITIES;
    static {
        CITIES = new HashMap<>();
        CITIES.put("Orel", "Orel");
        CITIES.put("Kr", "Kursk");
        CITIES.put("Mag", "Magnitogorsk");
        CITIES.put("Vrzh", "Voronezh");
    }

    public static String[] LABELS = {
            MNEMOKOD_S,
            NUMBER_VLAN_S,
            IP_SWITCH_S,
            PORT_S,
            UNTAGGED_S,
            ACT_ON_CISCO_S};

    public static final String IV = "31415926";
    public static final String RUN_LOGIN = "admin";
    public static final String RUN_PASS = "jaNUpm9cNWvfct86RHRwwQ==";

} // END Config
