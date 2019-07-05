package MyWork.NodesClass;

import MyWork.Tools.CryptDecrypt;

import javax.swing.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

import static MyWork.Config.*;

public class Switch {
    private final Pattern ERROR_PATTERN;
    private final String LOGIN = "admin";
    private String pass;
    private String ip;
    private String upPort;
    private String downPort;
    private boolean root;

    public Switch(String ip, String upPort, String downPort, boolean root, String pass) {
        ERROR_PATTERN = ERROR_ON_SWITCHES_PATTERN;
        this.ip = ip;
        this.upPort = upPort;
        this.downPort = downPort;
        this.root = root;
        this.pass = pass;
    } // ** constructor

    @Override
    public String toString() {
        return "Switch{" +
                "ip='" + getIp() + '\'' +
                ", upPort='" + getUpPort() + '\'' +
                ", downPort='" + getDownPort() + '\'' +
                ", root=" + isRoot() +
                '}';
    }

    public String getIp() {
        return ip;
    }

    public String getUpPort() {
        return upPort;
    }

    public String getDownPort() {
        return downPort;
    }

    public boolean isRoot() {
        return root;
    }

    public String createClient(Customer customer) {
        String result = "\n";
        Telnet connect = new Telnet(getIp(), 23);
        boolean successConnect = connect.auth(LOGIN, CryptDecrypt.getEncrypt(pass, SW_PASS));

        if (successConnect) {
            String pDown = "";
            String pUp = "";
            if (!getDownPort().contains("null")) pDown = "," + getDownPort();
            if (!getDownPort().contains("null") && getUpPort().contains("null")) pDown = getDownPort();
            if (!getUpPort().contains("null")) pUp = getUpPort();

            boolean recreate = false;

            do {
                String create = "create vlan " + customer.getMnemokod() + " tag " + customer.getVlan();
                result += connect.sendCommand(create) + "\n";
                if (!ERROR_PATTERN.matcher(result).find()) {
                    result += addPortsToVlan(customer, connect, pUp, pDown);
                    recreate = false;
                } else if (result.contains("exist") && !recreate) {
                    String message = "Vlan " + customer.getVlan() + " exist on " + getIp() + "\nУдалить и создать новый?";
                    String title = "Выберите действие";
                    int input = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
                    if (input == JOptionPane.YES_OPTION) {
                        String delete = "delete vlan vlanid " + customer.getVlan();
                        result = "\n" + connect.sendCommand(delete) + "\n";
                        recreate = true;
                    } // if we recreate vlan
                }  else {
                    recreate = false;
                } // ** if vlan error then exit
            } while (recreate);

            connect.close();

            if (isSuccess(result))
                return SUCCESS_S + formatResult(result);
            else
                return ERROR_S + formatResult(result);
        } else { // ** if connected
            return ERROR_S + " Can't connect.\n";
        } // ** if not connected
    } // ** createClient(Customer customer)

    private String addPortsToVlan(Customer customer, Telnet connect, String pUp, String pDown) {
        StringBuilder response = new StringBuilder();

        String confTag = "conf vlan " + customer.getMnemokod() + " add tagged " + pUp + pDown;
        response.append(connect.sendCommand(confTag)).append("\n");

        if (getIp().contains(customer.getIPConnection())) {
            String confUntag;
            if (customer.getUntagged()) {
                confUntag = "config vlan default delete " + customer.getPort() + "\n";
                response.append((connect.sendCommand(confUntag) + "\n").replaceAll("Fail!", ""));
                confUntag = "conf vlan " + customer.getMnemokod() + " add untagged " + customer.getPort();
            } else
                confUntag = "conf vlan " + customer.getMnemokod() + " add tagged " + customer.getPort();

            response.append(connect.sendCommand(confUntag)).append("\n");

        } // ** if untagged or not
        response.append(connect.sendCommand("Saving", "save\r\n")).append("\n");

        return response.toString();
    } // ** addPortsToVlan()

    public String deleteClient(Customer customer) {
        String result = "\n";
        Telnet connect = new Telnet(getIp(), 23);
        boolean successConnect = connect.auth(LOGIN, CryptDecrypt.getEncrypt(pass, SW_PASS));
        boolean ok = false;

        if (successConnect) {
            String deleteByName = "delete vlan " + customer.getMnemokod();
            result += connect.sendCommand(deleteByName) + "\n";
            if (!isSuccess(result)) {
                String deleteByNumber = "delete vlan vlanid " + customer.getVlan();
                result += connect.sendCommand(deleteByNumber) + "\n";
                if (isSuccess(result)) {
                    result += connect.sendCommand("Saving", "save\r\n") + "\n";
                    ok = true;
                } // ** save if customer is deleted
            } else {
                result += connect.sendCommand("Saving", "save\r\n") + "\n";
                ok = true;
            } // ** if it is with error
            connect.close();
            if (ok)
                return SUCCESS_S + formatResult(result);
            else
                return ERROR_S + formatResult(result);
        } else { // ** if connected
            return ERROR_S + " Can't connect.\n";
        }
    } // ** deleteClient()

    private String formatResult(String toFormat) {
        StringBuilder result = new StringBuilder("\n");
        for (String line : toFormat.split("\n")) {
            line = line.trim();
            line = line.replaceAll("\u001B\\[5D", " ");
            if (line.length() > 0 && !line.contains("#")) {
                result.append("\t").append(line).append("\n");
                continue;
            }

            if (line.split("#").length < 2) {
                continue;
            }
            result.append(line).append("\n");
        } // ** for every line
//        System.out.println(result.toString());
        return result.toString();
    } // ** formatResult()

    private boolean isSuccess(String result) {
        for (String line : result.split("\n")) {
            if (ERROR_PATTERN.matcher(line).find()) {
                return false;
            }
        }
        return true;
    }

    public String showVlanByName(String vlanName) {
        String result = "[Error] connected to " + getIp();
        Telnet connect = new Telnet(getIp(), 23);
        boolean successConnect = connect.auth(LOGIN, CryptDecrypt.getEncrypt(pass, SW_PASS));
        String showVlanCommand = "show vlan " + vlanName;

        if (successConnect) {
            result = formatResult(connect.sendCommand(showVlanCommand) + "\n");
        }

        return result;
    } // ** showVlanByName(String vlanName)

    public String showVlanByNumber(int number) {
        String result = "[Error] connected to " + getIp();
        Telnet connect = new Telnet(getIp(), 23);

        boolean successConnect = connect.auth(LOGIN, CryptDecrypt.getEncrypt(pass, SW_PASS));
        String showVlanCommand = "show vlan vlanid " + number;

        if (successConnect) {
            result = connect.sendCommand(showVlanCommand) + "\n";
        }
        return result;
    }

    public String runCommand(ArrayList<String> command) {
        String result = "[Error] connected to " + getIp();
        Telnet connect = new Telnet(getIp(), 23);
        boolean successConnect = connect.auth(LOGIN, CryptDecrypt.getEncrypt(pass, SW_PASS));

        if (successConnect) {
            result = formatResult(connect.sendListCommands(command));
        }

        return result;
    } // ** showVlanByName(String vlanName)
} // ** class Switch
