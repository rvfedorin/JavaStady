package MyWork.NodesClass;

import MyWork.Tools.CryptDecrypt;

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

        if(successConnect) {
            String pDown = "";
            String pUp = "";
            if (!getDownPort().contains("null")) pDown = "," + getDownPort();
            if (!getDownPort().contains("null") && getUpPort().contains("null")) pDown = getDownPort();
            if (!getUpPort().contains("null")) pUp = getUpPort();

            String create = "create vlan " + customer.getMnemokod() + " tag " + customer.getVlan();
            result += connect.sendCommand(create) + "\n";

            if(!ERROR_PATTERN.matcher(result).find()) {
                String confTag = "conf vlan " + customer.getMnemokod() + " add tagged " + pUp + pDown;
                result += connect.sendCommand(confTag) + "\n";

                if (getIp().contains(customer.getIPswitch())) {
                    String confUntag;
                    if (customer.getUntagged())
                        confUntag = "conf vlan " + customer.getMnemokod() + " add untagged " + customer.getPort();
                    else
                        confUntag = "conf vlan " + customer.getMnemokod() + " add tagged " + customer.getPort();

                    result += connect.sendCommand(confUntag) + "\n";
                } // ** if untagged or not
                result += connect.sendCommand("Saving", "save\r\n") + "\n";
            } // ** if vlan exist then exit

            connect.close();
        } // ** if connected

        if(isSuccess(result))
            return SUCCESS_S + formatResult(result);
        else
            return ERROR_S + formatResult(result);
    } // ** createClient(Customer customer)

    public String deleteClient(Customer customer) {
        String result = "\n";
        Telnet connect = new Telnet(getIp(), 23);
        boolean successConnect = connect.auth(LOGIN, CryptDecrypt.getEncrypt(pass, SW_PASS));
        boolean ok = false;

        if(successConnect) {
            String deleteByName = "delete vlan " + customer.getMnemokod();
            result += connect.sendCommand(deleteByName) + "\n";
            if(!isSuccess(result)) {
                String deleteByNumber = "delete vlan vlanid " + customer.getVlan();
                result = connect.sendCommand(deleteByNumber) + "\n";
                if(isSuccess(result)) {
                    result += connect.sendCommand("Saving", "save\r\n") + "\n";
                    ok = true;
                } // ** save if customer is deleted
            } // ** if it is with error

            connect.close();
        } // ** if connected

        if(ok)
            return SUCCESS_S + formatResult(result);
        else
            return ERROR_S + formatResult(result);
    }

    private String formatResult(String toFormat) {
        StringBuilder result = new StringBuilder("\n");
        for(String line: toFormat.split("\n")) {
            line = line.trim();
            if(line.length() > 0 && !line.contains("#")) {
                result.append("\t").append(line).append("\n");
                continue;
            }
            if(line.split("#").length < 2) {
                continue;
            }
            result.append(line).append("\n");
        } // ** for every line

        return result.toString();
    } // ** formatResult()

    private boolean isSuccess(String result) {
        for(String line: result.split("\n")) {
            if(ERROR_PATTERN.matcher(line).find()) {
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

        if(successConnect) {
            result = formatResult(connect.sendCommand(showVlanCommand) + "\n");
        }

        return result;
    } // ** showVlanByName(String vlanName)

    public String showVlanByNumber(int number) {
        String result = "[Error] connected to " + getIp();
        Telnet connect = new Telnet(getIp(), 23);

        boolean successConnect = connect.auth(LOGIN, CryptDecrypt.getEncrypt(pass, SW_PASS));
        String showVlanCommand = "show vlan vlanid " + number;

        if(successConnect) {
            result = connect.sendCommand(showVlanCommand) + "\n";
        }
        return result;
    }

    public String runCommand(ArrayList<String> command) {
        String result = "[Error] connected to " + getIp();
        Telnet connect = new Telnet(getIp(), 23);
        boolean successConnect = connect.auth(LOGIN, CryptDecrypt.getEncrypt(pass, SW_PASS));

        if(successConnect) {
            result = formatResult(connect.sendListCommands(command));
        }

        return result;
    } // ** showVlanByName(String vlanName)
} // ** class Switch
