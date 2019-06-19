package MyWork.NodesClass;

public class Region {
    private String city;
    private String prefix;
    private String id;
    private String coreSwitch;
    private String rootPort;
    private String devCellId;
    private String coreCisco;
    private String coreUnix;
    private String lanMB;

    public Region(String prefix, String city, String id) {
        setPrefix(prefix);
        setCity(city);
        setId(id);
        setCoreSwitch("");
        setRootPort("");
        setDevCellId("");
        setCoreCisco("");
        setCoreUnix("");
        setLanMB("");
    }

    public Region(String prefix, String city, String id, String unix, String coreSwitch, String rootPort, String devCellId, String lanMB) {
        setPrefix(prefix);
        setCity(city);
        setId(id);
        setCoreSwitch(coreSwitch);
        setRootPort(rootPort);
        setDevCellId(devCellId);
        setCoreCisco(calcIPCisco(unix));
        setCoreUnix(unix);
        setLanMB(lanMB); // 95.80.120.44X172.17.239.129X172.16.44.235X28
    }

    public String getCity() {
        return city;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getId() {
        return id;
    }

    public String getCoreSwitch() {
        return coreSwitch;
    }

    public String getDevCellId() {
        return devCellId;
    }

    public String getCoreCisco() {
        return coreCisco;
    }

    public String getRootPort() {
        return rootPort;
    }

    public String getCoreUnix() {
        return coreUnix;
    }

    public String getLanMB() {
        return lanMB;
    }

    ///////// SETTERS /////////////////////////////
    public void setCity(String city) {
        this.city = city;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCoreSwitch(String coreSwitch) {
        this.coreSwitch = coreSwitch;
    }

    public void setRootPort(String rootPort) {
        this.rootPort = rootPort;
    }

    public void setDevCellId(String devCellId) {
        this.devCellId = devCellId;
    }

    public void setCoreCisco(String coreCisco) {
        this.coreCisco = coreCisco;
    }

    public void setCoreUnix(String coreUnix) {
        this.coreUnix = coreUnix;
    }

    public void setLanMB(String lanMB) {
        this.lanMB = lanMB;
    }

    private String calcIPCisco(String ipUnix) {
        String[] ipUnixbyPart = ipUnix.split("\\.");
        String ipCisco = ipUnixbyPart[0] +
                "." + ipUnixbyPart[1] +
                "." + ipUnixbyPart[2] +
                "." + (new Integer(ipUnixbyPart[3]) - 1);

        return ipCisco;
    }

    @Override
    public String toString() {
        return "Region{" +
                "city='" + city + '\'' +
                ", prefix='" + prefix + '\'' +
                ", id='" + id + '\'' +
                ", coreSwitch='" + coreSwitch + '\'' +
                ", coreCisco='" + coreCisco + '\'' +
                ", coreUnix='" + coreUnix + '\'' +
                ", lanMB='" + lanMB + '\'' +
                '}';
    } // ** toString()
} // ** class
