package MyWork.NodesClass;

import MyWork.Tools.CiscoSpeedFormat;
import MyWork.Tools.CryptDecrypt;
import MyWork.Tools.SSH;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static MyWork.Config.*;

public class Cisco {
    private final String UNNUMBERED = "unnumbered";
//    private enum CLIENT_TYPE {UNNUM, LAN}
    private final String FREE_INT_COMMAND = "sh int des | inc Svobodno";
    private final String SHOW_INT_DES_COMMAND = "sh int des | inc ";
    private String ip;
    private char[] key;
    private Telnet connect;

    public Cisco(String ip, char[] pass) {
        this.ip = ip;
        this.key = pass;
        this.connect = null;
    }

    private boolean connect() {
        boolean successConnect;
        connect = new Telnet(ip, 23);
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

        String clSettings = getClFromClConf(client.getMnemokod(), client.getCity().getCoreUnix());
        System.out.println("clSettings " + clSettings);

        if (!clSettings.contains("Error")) {
            int clientCount = 1;
            String secondary = "";
            String intCisco = getIntCisco(client.getVlan());
            System.out.println("intCisco " + intCisco);
            String speedSize = null;
//            CLIENT_TYPE client_type = null;
            ArrayList<String> ipAddressesCommands = new ArrayList<>(); // create string route to config

            ////////// START ROUTE CONFIG //////////////////////////
            for (String clientLine : clSettings.split("\n")) {
                Pattern lanP = Pattern.compile(" (\\d{3,7}) (" + IP_PATTERN + ")/(\\d{2})");
                Matcher lanM = lanP.matcher(clientLine);
                if (lanM.find() && !lanM.group(3).equals("32")) {
                    System.out.println(lanM.group());
                    speedSize = lanM.group(1) + "k";

                    if (clientCount == 2) {
                        secondary = " secondary";
                    }
                    String toConfigLine = "ip address " + getGw(lanM.group(2)) + " " + getMask(lanM.group(3)) + secondary;
                    ipAddressesCommands.add(toConfigLine + "\n");
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
                        ipAddressesCommands.add(toConfigLine + "\n");
                    }
                } // if unnumbered
            } // for every ip line
            System.out.println("speedSize " + speedSize);
            ////////// END ROUTE CONFIG //////////////////////////

            //////// START CREATION /////////////////////////////////////
            if(speedSize != null && ipAddressesCommands.size() > 0)
                result = runCreatingCustomerOnCisco(intCisco, ipAddressesCommands, client, speedSize);
            ////////// END CREATION /////////////////////////////////////
        } else {
            result = clSettings;
        }

        return result;
    } // ** createClient()

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
            String speedType = null;
//            System.out.println("shRunIntOut: " + shRunIntOut);

            for(String line: shRunIntOut.split("\n")) {
                if(line.contains("service-policy")) {
                    createClientCommands.add("no "+line);
                    speedType = "service-policy";
                } else if (line.contains("rate-limit")) {
                    createClientCommands.add("no "+line);
                    speedType = "rate-limit";
                }
            } // for line - find current speed and create command to del
            if(speedType == null)
                speedType = getSpeedType();

            System.out.println("speedType = " + speedType);

            String[] speedToConfig;
            if(speedType != null) {
                speedToConfig = CiscoSpeedFormat.getFormattedSpeed(speedType, speedSize);
                createClientCommands.addAll(Arrays.asList(speedToConfig));
                createClientCommands.addAll(ipAddressesCommands);
                createClientCommands.add("end");
                createClientCommands.add("wr");

                result = connect.sendListCommands(createClientCommands);
//                result = createClientCommands.toString();
            } else {
                System.out.println("No found speed type.");
            }

            closeConnect();
        } // if connect

        return result;
    }

    private String formatOut(String out) {
        StringBuilder result = new StringBuilder();
        for(String s: out.split("\n")) {
            if(s.split("#").length > 1) {
                result.append(s).append("\n");
            }
        }
        return result.toString();
    } // ** formatOut()

    private String getSpeedType() {
        String result = null;
        String out = connect.sendCommand("sh conf | sec service-policy");
        out += connect.sendCommand("sh conf | sec rate-limit");
        int count = 0;
        for(String line: out.split("\n")) {
            if(line.contains("rate-limit")) {
                count++;
                result = "rate-limit";
            } else if(line.contains("service-policy")) {
                count++;
                result = "service-policy";
            }

            if(count > 6)
                break;
        }

        return result;
    } // ** getSpeedType()

    private String getMask(String cidr) {
        int power = 32 - Integer.valueOf(cidr);
        int hosts = 256 - (int) Math.pow(2, power);
        String mask = "255.255.255." + hosts;
        return mask;
    }

    private String getIntCisco(String vlan) {
        String result = "";
        String showIntOut = runCommand(SHOW_INT_DES_COMMAND + vlan + " ");
        Matcher intCisM = Pattern.compile(".*?\\." + vlan).matcher(showIntOut);
        if (intCisM.find()) {
            result = intCisM.group();

        } // for line out
        return result;
    }

    private String getGw(String lan) {
        String result;
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

    private String getClFromClConf(String mnemokod, String ipUnix) {
        String result = "[Error] Not found client in " + LOCAL_CLIENTS_CONF_FILE;
        StringBuilder foundClients = new StringBuilder();

        SSH ssh = new SSH(ipUnix, key);
        boolean successDownload = ssh.downloadFile(REMOTE_CLIENTS_CONF_FILE, LOCAL_CLIENTS_CONF_FILE);
        if (successDownload) {
            try (BufferedReader br = new BufferedReader(new FileReader(LOCAL_CLIENTS_CONF_FILE))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.replaceAll("\\s+", " ");
                    if (line.toLowerCase().contains(mnemokod.toLowerCase() + " ")) {
                        foundClients.append(line).append("\n");
                    }
                } // while every line
                if(foundClients.length() > 0)
                    result = foundClients.toString();
            } catch (IOException ioex) {
                ioex.printStackTrace();
                result = "[Error] open file " + LOCAL_CLIENTS_CONF_FILE;
            }
        } // if we get Clients.conf
        return result;
    } // ** getClFromClConf()

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
