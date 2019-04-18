package MyWork.NodesClass;

public class Switch {
    private String ip;
    private String upPort;
    private String downPort;
    private boolean root;

    public Switch(String ip, String upPort, String downPort, boolean root) {
        this.ip = ip;
        this.upPort = upPort;
        this.downPort = downPort;
        this.root = root;
    } // ** constructor

    @Override
    public String toString() {
        return "Switch{" +
                "ip='" + ip + '\'' +
                ", upPort='" + upPort + '\'' +
                ", downPort='" + downPort + '\'' +
                ", root=" + root +
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
        String pDown = "";
        String pUp = "";
        if(!getDownPort().contains("null")) pDown = "," + getDownPort();
        if(!getDownPort().contains("null") && getUpPort().contains("null")) pDown = getDownPort();
        if(!getUpPort().contains("null")) pUp = getUpPort();

        String create = "create vlan " + customer.getMnemokod() + " tag " + customer.getVlan();
        String confTag = "conf vlan " + customer.getMnemokod() + " add tagged " + pUp + pDown;

        if(customer.getUntagged() && getIp().contains(customer.getIPswitch())) {
            String confUntag = "conf vlan " + customer.getMnemokod() + " add untagged " + customer.getPort();
            System.out.println(confUntag);
        }

        System.out.println(create);
        System.out.println(confTag);


        return "Ok";
    }
} // ** class Switch
