package MyWork.NodesClass;

import MyWork.Tools.CiscoSpeedFormat;
import MyWork.Tools.CryptDecrypt;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static MyWork.Config.*;

public class Cisco {
    private final String UNNUMBERED = "unnumber";
    private final String FREE_INT_COMMAND = "sh int des | inc Svobodno";
    private final String SHOW_INT_DES_COMMAND = "sh int des | inc ";
    private final Pattern LAN_PATTERN = Pattern.compile(" (\\d{3,7}) (" + IP_PATTERN + ")/(\\d{2})");
    private String ip;
    private char[] key;
    private Telnet connect;


    public Cisco(String ip, char[] pass) {
        this.ip = ip;
        this.key = pass;
        this.connect = null;
    }

    public String getIP() {
        return ip;
    }

    private boolean connect() {
        boolean successConnect;
        connect = new Telnet(getIP(), 23);
        successConnect = connect.auth(CryptDecrypt.getEncrypt(new String(key), CISCO_PASS));
        if (successConnect) {
            ArrayList<String> commands = new ArrayList<>();
            commands.add("en");
            commands.add(CryptDecrypt.getEncrypt(new String(key), CISCO_EN_PASS));
            connect.sendListCommands(commands);
        } // ** to enable mod

        return successConnect;
    }

    public String runCommand(String command) {
        String result = "";
        ArrayList<String> commands = new ArrayList<>();
        commands.add(command);

        if (connect()) {
            result = connect.sendListCommands(commands);
            closeConnect();
        }

        return result;
    } // ** runCommand()

    public String getFreeInt() {
        String result = "";
        ArrayList<String> commands = new ArrayList<>();
        commands.add(FREE_INT_COMMAND);

        if (connect()) {
            result = connect.sendListCommands(commands);
            result = formatFreeInt(result);
            closeConnect();
        }

        return result;
    } // ** getFreeInt()

    public String createClient(Customer client) {
        String result = "createClient -> ";

        Unix unix = new Unix(client.getCity().getCoreUnix(), key);
        String clSettings = unix.getClFromClConf(client.getMnemokod(), true);
        System.out.println("clSettings " + clSettings);

        if (!clSettings.contains("Error")) {
            int clientCount = 1;
            String secondary = "";
            String intCisco = getIntCisco(client.getVlan());
            if(intCisco.contains("Error")) {
                result = intCisco;
            } else {
                System.out.println("intCisco " + intCisco);
                String speedSize = null;
//            CLIENT_TYPE client_type = null;
                ArrayList<String> ipAddressesCommands = new ArrayList<>(); // create string route to config

                ////////// START ROUTE CONFIG //////////////////////////
                for (String clientLine : clSettings.split("\n")) {
                    Matcher lanM = LAN_PATTERN.matcher(clientLine);
                    if (lanM.find() && !lanM.group(3).equals("32")) {
                        System.out.println(lanM.group());
                        speedSize = lanM.group(1) + "k";

                        if (clientCount == 2) {
                            secondary = " secondary";
                        }
                        String toConfigLine = "ip address " + getGw(lanM.group(2)) + " " + getMask(lanM.group(3)) + secondary;
                        ipAddressesCommands.add(toConfigLine);
                        clientCount++;
                    } else if (clientLine.toLowerCase().contains(UNNUMBERED)) {
                        Pattern speedP = Pattern.compile(" (\\d{3,7}) " + IP_PATTERN + ".*");
                        Matcher speedM = speedP.matcher(clientLine);
                        if (speedM.find()) {
                            speedSize = speedM.group(1) + "k";
                        }
                        Matcher ipM = IP_PATTERN.matcher(clientLine);

                        if (ipM.find()) {
                            String toConfigLine = "ip route " + ipM.group() + " 255.255.255.255 " + intCisco + " 10";
                            //ip route 77.235.218.94 255.255.255.255 TenGigabitEthernet0/3/0.890 10
                            ipAddressesCommands.add(toConfigLine);
                        }
                    } // if unnumbered
                } // for every ip line
                System.out.println("speedSize " + speedSize);
                ////////// END ROUTE CONFIG //////////////////////////

                //////// START CREATION /////////////////////////////////////
                if (speedSize != null && ipAddressesCommands.size() > 0) {
                    result = "\n" + SUCCESS_S;
                    result += "\n" + runCreatingCustomerOnCisco(intCisco, ipAddressesCommands, client, speedSize) + "\n";

                }
                ////////// END CREATION /////////////////////////////////////
            } // ** if(intCisco.contains("Error"))
        } else {
            result = clSettings;
        }

        return result;
    } // ** createClient()

    public String deleteClient(Customer client) {
        String result = "deleteClient -> ";

        Unix unix = new Unix(client.getCity().getCoreUnix(), key);
        String clSettings = unix.getClFromClConf(client.getMnemokod(), true);
        System.out.println("clSettings " + clSettings);

        if (!clSettings.contains("Error")) {
            String intCisco = getIntCisco(client.getVlan());
            if(intCisco.contains("Error")) {
                result = intCisco;
            } else {
                System.out.println("intCisco " + intCisco);
//            CLIENT_TYPE client_type = null;
                ArrayList<String> ipAddressesCommands = new ArrayList<>(); // create string route to config

                ////////// START ROUTE CONFIG //////////////////////////
                for (String clientLine : clSettings.split("\n")) {
                    Matcher lanM = LAN_PATTERN.matcher(clientLine);
                    if (lanM.find() && !lanM.group(3).equals("32")) {
                        System.out.println(lanM.group());
                        String toConfigLine = "ip unnumbered " + getUnnumberInt();
                        ipAddressesCommands.add(toConfigLine);
                    } else if (clientLine.toLowerCase().contains(UNNUMBERED)) {
                        Matcher ipM = IP_PATTERN.matcher(clientLine);

                        if (ipM.find()) {
                            String toConfigLine = "no ip route " + ipM.group() + " 255.255.255.255";
                            ipAddressesCommands.add(toConfigLine);
                        }
                    } // if unnumbered
                } // for every ip line
                ////////// END ROUTE CONFIG //////////////////////////

                //////// START DELETEION /////////////////////////////////////
                if (ipAddressesCommands.size() > 0) {
                    result = "\n" + SUCCESS_S;
                    result += "\n" + runDeleteCustomerOnCisco(intCisco, ipAddressesCommands) + "\n";

                }
                ////////// END DELETEION /////////////////////////////////////
            } // ** if(intCisco.contains("Error"))
        } else {
            result = clSettings;
        }

        return result;
    } // ** deleteClient()

    public ArrayList<String> changeSpeed(HashMap<String, String> mnemoWithNewSpeed) {
        ArrayList<String> result = new ArrayList<>();
        boolean freshClientsconf = false;
        ArrayList<String> commands = new ArrayList<>();

        if (connect == null) {
            connect();
        }

        for (String mnemo : mnemoWithNewSpeed.keySet()) {
            String newSpeed = mnemoWithNewSpeed.get(mnemo);
            IP_TYPE ip_type;
            String interfaceCl = null;


            Region region = CITIES.getOrDefault(mnemo.split("-")[0], null);
            if (region != null) {
                String out;
                Unix unix = new Unix(region.getCoreUnix(), key);
                String clSettings;
                if (freshClientsconf)
                    clSettings = unix.getClFromClConf(mnemo, false);
                else {
                    clSettings = unix.getClFromClConf(mnemo, true);
                    freshClientsconf = true;
                }
                String clIPLine = clSettings.split("\n")[0];
                Matcher lanM = LAN_PATTERN.matcher(clIPLine);
                if (lanM.find() && !lanM.group(3).equals("32")) {
                    ip_type = IP_TYPE.IP;
                    String toConfigLine = "sh ip int br | inc " + getGw(lanM.group(2) + " ");
                    out = connect.sendCommand(toConfigLine);
                    interfaceCl = getIntFromSpeedOut(out, ip_type);

                } else if (clIPLine.toLowerCase().contains(UNNUMBERED)) {
                    Matcher ipM = IP_PATTERN.matcher(clIPLine);

                    if (ipM.find()) {
                        ip_type = IP_TYPE.ROUTE;
                        String toConfigLine = "sh conf | sec " + ipM.group() + " 255.255.255.255";
                        out = connect.sendCommand(toConfigLine);
                        interfaceCl = getIntFromSpeedOut(out, ip_type);
                    }
                }

                if (interfaceCl != null) {
                    String shRunIntOut;
                    SPEED_TYPE speed_type;
                    ArrayList<String> shRunInt = new ArrayList<>();
                    shRunInt.add("sh run int " + interfaceCl);
                    shRunIntOut = connect.sendListCommands(shRunInt);
                    ArrayList<Object> currentSpeed = getCurrentSpeedOnInt(shRunIntOut);
                    speed_type = (SPEED_TYPE) currentSpeed.get(0);
                    currentSpeed.remove(0);

                    commands.add("conf t");
                    commands.add("int " + interfaceCl);
                    currentSpeed.forEach(e -> {
                        commands.add("no " + e);
                    });
                    commands.addAll(CiscoSpeedFormat.getFormattedSpeed(speed_type, newSpeed));
                    commands.add("end");
//                    System.out.println(commands);
                } else {
                    result.add("[Error] " + mnemo + " not found on cisco " + ip);
                }// ** if (interfaceCl != null)
            } else {
                result.add("[Error] " + mnemo + " Region not found. Node[Cisco]-[changeSpeed()]");
            } // if we have region
        } // for every mnemo-speed

        result.add(formatOut(connect.sendListCommands(commands)));

        if (connect != null) {
            closeConnect();
        }
        return result;
    } // ** changeSpeed()

    private String getIntFromSpeedOut(String out, IP_TYPE type) {
        String result = null;
        for (String line : out.split("\n")) {
            if (line.contains("#"))
                continue;
            line = line.replaceAll("\\s+", " ");
            String[] lineByBlocks = line.split(" ");
            switch (type) {
                case IP:
                    result = lineByBlocks[0];
                    break;
                case ROUTE:
                    if (lineByBlocks.length > 3)
                        result = lineByBlocks[4];
                    break;
            } // ** switch
        } // ** for lines out

        return result;
    }

    private String runCreatingCustomerOnCisco(String interfaceCisco, ArrayList<String> ipAddressesCommands, Customer client, String speedSize) {
        String result = "";
        String shRunIntOut;
        ArrayList<String> shRunInt = new ArrayList<>();
        shRunInt.add("sh run int " + interfaceCisco);
        System.out.println(shRunInt);

        if (connect()) {
            System.out.println("Connected to cisco.");
            shRunIntOut = connect.sendListCommands(shRunInt);
            ArrayList<String> createClientCommands = new ArrayList<>();
            createClientCommands.add("conf t");
            createClientCommands.add("int " + interfaceCisco);
            createClientCommands.add("description " + client.getMnemokod());
            createClientCommands.add("no shutdown");
            SPEED_TYPE speedType;
//            System.out.println("shRunIntOut: " + shRunIntOut);
            if (!shRunIntOut.contains("ip unnumbered Loopback"))
                createClientCommands.add("ip unnumbered " + getUnnumberInt());

            ArrayList<Object> currentSpeed = getCurrentSpeedOnInt(shRunIntOut);
            speedType = (SPEED_TYPE) currentSpeed.get(0);
            currentSpeed.remove(0);
            currentSpeed.forEach(e -> {
                createClientCommands.add("no " + e);
            });

            System.out.println("speedType = " + speedType);

            ArrayList<String> speedToConfig;
            if (speedType != null) {
                speedToConfig = CiscoSpeedFormat.getFormattedSpeed(speedType, speedSize);
                createClientCommands.addAll(speedToConfig);
                createClientCommands.addAll(ipAddressesCommands);
                createClientCommands.add("end");
//                createClientCommands.add("wr");

                result = formatOut(connect.sendListCommands(createClientCommands));
            } else {
                System.out.println("No found speed type.");
            }

            closeConnect();
        } // if connect

        return result;
    }

    private ArrayList<Object> getCurrentSpeedOnInt(String shRunIntOut) {
        ArrayList<Object> result = new ArrayList<>();
        SPEED_TYPE speedType = null;
        ArrayList<String> currentSpeed = new ArrayList<>();
        for (String line : shRunIntOut.split("\n")) {
            if (line.contains("service-policy")) {
                currentSpeed.add(line);
//                speedType = "service-policy";
                speedType = SPEED_TYPE.POLICY;
            } else if (line.contains("rate-limit")) {
                currentSpeed.add(line);
//                speedType = "rate-limit";
                speedType = SPEED_TYPE.RATE;
            }
        } // for line - find current speed and create command to del
        if (speedType == null)
            speedType = getSpeedType();

        result.add(speedType);
        result.addAll(currentSpeed);

        return result;
    }

    private String runDeleteCustomerOnCisco(String interfaceCisco, ArrayList<String> ipAddressesCommands) {
        String result = "";

        if (connect()) {
            System.out.println("Connected to cisco.");

            ArrayList<String> deleteClientCommands = new ArrayList<>();
            deleteClientCommands.add("conf t");
            deleteClientCommands.add("int " + interfaceCisco);
            deleteClientCommands.add("description Svobodno");
            deleteClientCommands.add("shutdown");

            deleteClientCommands.addAll(ipAddressesCommands);
            deleteClientCommands.add("end");
//            createClientCommands.add("wr");

            result = formatOut(connect.sendListCommands(deleteClientCommands));

            closeConnect();
        } // if connect

        return result;
    } // ** runDeleteCustomerOnCisco()

    private String formatOut(String out) {
        StringBuilder result = new StringBuilder();
//        out = out.replaceAll("\b", "");
        for (String s : out.split("\n")) {
            if (s.split("#").length > 1) {
                result.append(s).append("\n");
            }
        }
        return result.toString();
    } // ** formatOut()

    private String getUnnumberInt() {
        String result;
        boolean needCloseConnect = false;
        ArrayList<String> shConfSecLoopback = new ArrayList<>();
        shConfSecLoopback.add("sh conf | sec ip unnumbered Loopback");
        shConfSecLoopback.add("q");
        if (connect == null) {
            connect();
            needCloseConnect = true;
        }

        if (connect != null) {
            String out = connect.sendListCommands(shConfSecLoopback);
            HashMap<String, Integer> count = new HashMap<>();

            Pattern loopbackP = Pattern.compile("Loopback\\d{1,2}");
            Matcher loopbackM = loopbackP.matcher(out);

            for (String s : out.split("\n")) {
                if (loopbackM.find()) {
                    Integer oldCount = count.get(loopbackM.group());
                    if (oldCount == null)
                        oldCount = 0;

                    count.put(loopbackM.group(), (oldCount + 1));
                }
            } // for every line

            result = Collections.max(count.entrySet(), Comparator.comparing(Map.Entry::getValue)).getKey();
        } else {
            result = null;
        }

        if (needCloseConnect)
            closeConnect();

        return result;
    } // ** getUnnumberInt()

    private SPEED_TYPE getSpeedType() {
        SPEED_TYPE result = null;
        String out = connect.sendCommand("sh conf | sec service-policy");
        out += connect.sendCommand("sh conf | sec rate-limit");
        int count = 0;
        for (String line : out.split("\n")) {
            if (line.contains("rate-limit")) {
                count++;
                result = SPEED_TYPE.RATE;
            } else if (line.contains("service-policy")) {
                count++;
                result = SPEED_TYPE.POLICY;
            }

            if (count > 6)
                break;
        }

        return result;
    } // ** getSpeedType()

    private String getMask(String cidr) {
        int power = 32 - Integer.valueOf(cidr);
        int hosts = 256 - (int) Math.pow(2, power);
        return "255.255.255." + hosts;
    }

    private String getIntCisco(String vlan) {
        String result = "[Error] No found interface.";
        String showIntOut = runCommand(SHOW_INT_DES_COMMAND + vlan + " ");
        Matcher intCisM = Pattern.compile(".*?\\." + vlan).matcher(showIntOut);
        if (intCisM.find()) {
            result = intCisM.group();

        } // for line out
        return result;
    }

    private String getGw(String lan) {
        String result;
        lan = lan.replaceAll("\\s+", "");
        String[] lanBlocks = lan.split("\\.");
        if (lanBlocks.length == 4) {
            result = lanBlocks[0] + "."
                    + lanBlocks[1] + "."
                    + lanBlocks[2] + "."
                    + (Integer.valueOf(lanBlocks[3]) + 1);
        } else {
            result = "[Error] ";
        }

        return result;
    }

    private String formatFreeInt(String rawOut) {
        boolean start = false;
        StringBuilder result = new StringBuilder();

        for (String line : rawOut.split("\n")) {
            if (start) {
                line = line.replaceAll("\\s+", " ");
                String[] partsOfLine = line.split(" ");
                if (partsOfLine[0].length() > 4) {
                    String[] intVirtual = partsOfLine[0].split("\\.");
                    String interfaceFree = (intVirtual.length > 1 ? intVirtual[1] : partsOfLine[0]) +
                            (line.contains("down") ? " down\n" : " up\n");

                    result.append(interfaceFree);
                }
            } // ** if useful information
            if (line.contains(FREE_INT_COMMAND)) {
                start = true;
            }
        } // ** for

        return result.toString();
    }

    private void closeConnect() {
        if (connect != null) {
            connect.close();
            connect = null;
        }
    } // ** closeConnect()
} // ** class Cisco
