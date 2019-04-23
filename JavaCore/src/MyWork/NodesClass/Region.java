package MyWork.NodesClass;

public class Region {
    private String city;
    private String prefix;
    private String id;
    private String coreSwitch;
    private String coreCisco;
    private String coreUnix;
    private String lanMB;

    public Region(String prefix, String city, String id) {
        setPrefix(prefix);
        setCity(city);
        setId(id);
        setCoreSwitch("");
        setCoreCisco("");
        setCoreUnix("");
        setLanMB("");
    }

    public Region(String prefix, String city, String id, String unix) {
        setPrefix(prefix);
        setCity(city);
        setId(id);
        setCoreSwitch("");
        setCoreCisco("");
        setCoreUnix(unix);
        setLanMB("");
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

    public String getCoreCisco() {
        return coreCisco;
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

    public void setCoreCisco(String coreCisco) {
        this.coreCisco = coreCisco;
    }

    public void setCoreUnix(String coreUnix) {
        this.coreUnix = coreUnix;
    }

    public void setLanMB(String lanMB) {
        this.lanMB = lanMB;
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
    }
}
