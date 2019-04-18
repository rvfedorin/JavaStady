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
} // ** class Switch
