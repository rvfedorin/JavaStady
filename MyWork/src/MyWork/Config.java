package MyWork;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Pattern;

import MyWork.NodesClass.BD;
import MyWork.NodesClass.Region;

import javax.swing.*;

public abstract class Config {
    private static Properties conf;
    private static File confFile = new File("conf.ini");
    static {
        conf = new Properties();
        try {
            conf.load(new FileInputStream(confFile));
        } catch (IOException ex) {
            try { // create default conf file
                confFile.createNewFile();
                conf.setProperty("CURRENT_INTRANET_TYPE", "excel");
                conf.setProperty("SPEEDS_FILE", "speeds.txt");
                conf.setProperty("LOG_FILE", ".\\log\\log");
                conf.setProperty("REMOTE_CLIENTS_CONF_FILE", "/etc/Clients.conf");
                conf.setProperty("LOCAL_CLIENTS_CONF_FILE", "Clients.conf");
                conf.setProperty("BD_REGIONS_FILE", "regions.dat");
                conf.setProperty("INTRANETS_PATH", "C:\\INTRANETS\\");
                conf.store(new FileOutputStream(confFile), null);
                conf.load(new FileInputStream(confFile));
            } catch (IOException ioex) {
                JOptionPane.showMessageDialog(null,
                        "Error create default config file (conf.ini)",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            } // ** don't create
        }
    } // ** static conf

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
    public static final String IP_SWITCH_S = "IP свитча:       ";
    public static final String IP_MB_S = "IP мобибокса: ";
    public static final String PORT_S = "Порт: ";
    public static final String UNTAGGED_S = "Untagged";
    public static final String CREATE_CISCO_S = "Создать на Cisco";
    public static final String DELETE_CISCO_S = "Удалить на Cisco";
    public static final String ACT_ON_CISCO_S = "Действие на Cisco";
    public static final String LINE = "====================================================";
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

/////////////// **************** START PASS GENERATE ************* ////////////////////////
//
    public static final String PASS_SYMBOLS = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
    public static final int PASS_LENGTH = 15;
//
/////////////// **************** END PASS GENERATE ************* ////////////////////////


    private static final Dimension DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
    public static final Double X_SCREEN_SIZE = DIMENSION.getWidth();
    public static final Double Y_SCREEN_SIZE = DIMENSION.getHeight();

    public enum IP_TYPE {IP, ROUTE}

    public enum SPEED_TYPE {POLICY, RATE}

    public enum CONNECT_TYPE {OPTIC, MB, RWR}

    public enum INTRANET_TYPE {WEB, EXCEL}
    public static final INTRANET_TYPE CURRENT_INTRANET_TYPE;
    static {
        if(conf.getProperty("CURRENT_INTRANET_TYPE").equalsIgnoreCase("web")) {
            CURRENT_INTRANET_TYPE = INTRANET_TYPE.WEB;
        } else {
            CURRENT_INTRANET_TYPE = INTRANET_TYPE.EXCEL;
        }
    }


    public static final String SEPARATOR_CONNECTION = "<=>";
    public static final String SEPARATOR_PORT = "P";
    public static final Pattern SWITCH_PATTERN = Pattern.compile("DES|switch|D-Link|DGS");
    public static final Pattern MB_PATTERN = Pattern.compile("Mobibox|MB");
    public static final Pattern MIKROTIK_PATTERN = Pattern.compile("mikrotik");
    public static final Pattern RWR_SECTOR_PATTERN = Pattern.compile("Omx");
    public static final Pattern RWR_CLIENT_PATTERN = Pattern.compile("Sm");
    public static final Pattern CISCO_PATTERN = Pattern.compile("cisco|Cisco");
    public static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
    public static final Pattern ERROR_ON_SWITCHES_PATTERN = Pattern.compile("Invalid|Error|Fail|exist|vlanid 2-4094|Entries : 0|possible");

    /////////////// **************** START FILES ************* ////////////////////////
    public static final String SPEEDS_FILE = conf.getProperty("SPEEDS_FILE");
    public static final String LOG_FILE = conf.getProperty("LOG_FILE");
    public static final String REMOTE_CLIENTS_CONF_FILE = conf.getProperty("REMOTE_CLIENTS_CONF_FILE");
    public static final String LOCAL_CLIENTS_CONF_FILE = conf.getProperty("LOCAL_CLIENTS_CONF_FILE");
    public static final String BD_REGIONS_FILE = conf.getProperty("BD_REGIONS_FILE");
    public static final String INTRANETS_PATH = conf.getProperty("INTRANETS_PATH");
    /////////////// **************** END FILES ************* ////////////////////////


    public static HashMap<String, Region> CITIES = BD.getRegions();

//    static {
//        CITIES = new HashMap<>();
//        CITIES.put("Orel", new Region("Orel", "Orel", "23", "213.170.117.254", "172.16.48.254", "28", "2", "95.80.120.44X172.17.239.129X172.16.44.235X28"));
//        CITIES.put("Kr", new Region("Kr", "Kursk", "24", "88.86.91.254", "172.17.0.10", "24", "3", "95.80.98.181X172.17.151.1X172.17.1.38X2"));
//        CITIES.put("Mag", new Region("Mag", "Magnitogorsk", "46", "79.175.33.254", "172.17.84.254", "1", "2", "79.175.7.205X172.17.221.129X172.17.84.254X2"));
//        CITIES.put("Vrzh", new Region("Vrzh", "Voronezh", "47", "95.80.106.254", "172.17.168.254", "1", "2", "95.80.119.41X172.17.171.129X172.17.168.254X11"));
//
//        CITIES.put("Bel", new Region("Bel", "Belgorod", "38", "79.175.22.254", "172.17.64.102", "16", "2", ""));
//        CITIES.put("Lp", new Region("Lp", "Lipetsk", "39", "79.175.50.254", "172.17.96.254", "1", "2", "95.80.121.251X172.17.218.1X172.17.96.254X17"));
//        CITIES.put("Raz", new Region("Raz", "Ryazan", "40", "88.86.64.254", "172.16.50.254", "1", "2", ""));
//        CITIES.put("Chel", new Region("Chel", "Chelyabinsk", "41", "79.175.32.254", "172.17.80.254", "1", "3", "31.200.197.248X172.17.83.129X172.17.80.254X19"));
//
//        CITIES.put("Br", new Region("Br", "Bryansk", "7", "79.175.53.254", "172.17.92.2", "1", "2", "95.80.99.173X172.17.231.129X172.17.92.10X12"));
//        CITIES.put("Kq", new Region("Kq", "Kaluga", "8", "213.170.124.254", "172.16.12.254", "1", "2", ""));
//        CITIES.put("Tul", new Region("Tul", "Tula", "9", "213.170.119.254", "172.16.30.62", "1", "2", ""));
//        CITIES.put("Sm", new Region("Sm", "Smolensk", "10", "95.80.95.254", "172.17.144.254", "1", "3", "95.80.84.115X172.18.27.129X172.17.144.254X15"));
//
//        CITIES.put("Kol", new Region("Kol", "Kolomna", "42", "88.86.77.38", "172.16.60.242", "1", "2", "88.86.83.253X172.16.65.129X172.16.60.242X4"));
//        CITIES.put("Kd", new Region("Kd", "Krasnodar", "43", "79.175.41.254", "172.17.79.250", "1", "2", ""));
//        CITIES.put("Rnd", new Region("Rnd", "Rostov", "6", "95.80.127.254", "", "", "", ""));
//        CITIES.put("Vol", new Region("Vol", "Volgograd", "45", "79.175.30.254", "", "", "", ""));
//
//        CITIES.put("Ek", new Region("Ek", "Ekaterinburg", "25", "79.175.48.254", "", "", "", ""));
//        CITIES.put("St", new Region("St", "Stavropol", "26", "95.80.94.254", "", "", "", ""));
//
//        CITIES.put("Net", new Region("Net", "Net", "999"));
//    }

    public static final HashMap<String, String> CITIES_BY_NAME;

    static {
        CITIES_BY_NAME = new HashMap<>();
        CITIES.forEach((k, v) -> CITIES_BY_NAME.put(v.getCity(), k));
    }

//    public static final HashMap<String, String > SPEEDS;
//    static {
//        SPEEDS = new HashMap<>();
//        SPEEDS.put("10G", "TenG");
//        SPEEDS.put("1000M", "Gi");
//        SPEEDS.put("100M", "Fa");
//    }

    public static final HashMap<String, String> SPEEDS;

    static {
        SPEEDS = new HashMap<>();
        SPEEDS.put("10000Kbit", "10Fa");
        SPEEDS.put("10M", "10Fa");
        SPEEDS.put("100000Kbit", "100Fa");
        SPEEDS.put("100M", "100Fa");
        SPEEDS.put("1000000Kbit", "1Gi");
        SPEEDS.put("1000M", "1Gi");
        SPEEDS.put("10000000Kbit", "10Gi");
    }

    public static final String SUCCESS_S = "Ok";
    public static final String ERROR_S = "[Error]";


    /////////////// **************** START PASS STRINGS ************* ////////////////////////
//
    public static final String IV = "31415926";
    static final String RUN_LOGIN = "admin";
    static final String RUN_PASS = "jaNUpm9cNWvfct86RHRwwQ==";
    public static final String INTRANET_CONNECT_LOGIN = "bXFDZHf5gYHnnQkHAQL4+A==";
    public static final String INTRANET_CONNECT_PASS = "xXHPPl7DZLd/M+T0fm7O4w==";
    public static final String SW_PASS = "jaNUpm9cNWvfct86RHRwwQ==";
    public static final String CISCO_PASS = "0VndzNNjOgIYdLuYolLLpw==";
    public static final String CISCO_EN_PASS = "L4AroI1w8tkZhbVw9zkSPA==";
    public static final String SSH_LOGIN = "LI+vCBkIo1U=";
    public static final String SSH_PASS = "fyzWJkoD10g=";
    public static final String MB_PASS = "grat7igguQOqzqIpGgS2hA==";

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
