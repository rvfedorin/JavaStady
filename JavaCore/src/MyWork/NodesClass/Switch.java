package MyWork.NodesClass;

import MyWork.Tools.CryptDecrypt;

import static MyWork.Config.ERROR_S;
import static MyWork.Config.SUCCESS_S;
import static MyWork.Config.SW_PASS;

public class Switch {
    private final String LOGIN = "admin";
    private String pass;
    private String ip;
    private String upPort;
    private String downPort;
    private boolean root;

    public Switch(String ip, String upPort, String downPort, boolean root, String pass) {
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
        Telnet connect = new Telnet(getIp(), 23);
        boolean successConnect = connect.auth(LOGIN, CryptDecrypt.getEncrypt(pass, SW_PASS));

        if(successConnect) {
            String pDown = "";
            String pUp = "";
            if (!getDownPort().contains("null")) pDown = "," + getDownPort();
            if (!getDownPort().contains("null") && getUpPort().contains("null")) pDown = getDownPort();
            if (!getUpPort().contains("null")) pUp = getUpPort();

            String create = "create vlan " + customer.getMnemokod() + " tag " + customer.getVlan();
            String confTag = "conf vlan " + customer.getMnemokod() + " add tagged " + pUp + pDown;

            connect.sendCommand(create);
            connect.sendCommand(confTag);

            if (getIp().contains(customer.getIPswitch())) {
                String confUntag;
                if(customer.getUntagged())
                    confUntag = "conf vlan " + customer.getMnemokod() + " add untagged " + customer.getPort();
                else
                    confUntag = "conf vlan " + customer.getMnemokod() + " add tagged " + customer.getPort();

                connect.sendCommand(confUntag);
            }

//        System.out.println(create);
//        System.out.println(confTag);

            connect.sendCommand("Saving", "save\r\n");

            connect.close();

            return SUCCESS_S;
        } // ** if connected
        return ERROR_S;
    }
} // ** class Switch
