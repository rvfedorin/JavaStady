package MyWork.Intranet;

import MyWork.NodesClass.Region;

public abstract class Intranet {
    Region region;

    public abstract String getFullPath(String ipDev);
    public abstract String allConnectionFromSwitch(String switchIP, boolean onlySw);
    public abstract String findClient(String mnemokod);
}
