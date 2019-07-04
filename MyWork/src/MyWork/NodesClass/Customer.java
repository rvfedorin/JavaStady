package MyWork.NodesClass;

import static MyWork.Config.*;

public class Customer {
    private Region city;
    private String mnemokod;
    private String vlan;
    private String IPConnection;
    private String port;
    private boolean untagged;

    public Customer(String city, String mnemokod, String vlan, String IPConnection, String port, String untagged)
            throws IllegalArgumentException{

        boolean fineData = true;
        String[] allData = new String[]{city, mnemokod, vlan, IPConnection, port, untagged};

        for (String s: allData) {
            if (s.length() < 1) {
                fineData = false;
                break;
            }
        } // for

        String prefix = CITIES_BY_NAME.getOrDefault(city, "none");
        boolean hasCity = CITIES.containsKey(prefix);
        if(!hasCity){
            System.out.println("City not found.");
            throw new IllegalArgumentException("<< class Customer: City not found. >>");
        }

        if (fineData) {
            this.city = CITIES.get(prefix);
            this.mnemokod = mnemokod;
            this.vlan = vlan;
            this.IPConnection = IPConnection;
            if(IPConnection.contains("root")) {
                this.city.setCoreSwitch(IPConnection.split("root")[1]);
                this.IPConnection = IPConnection.split("root")[0];
            }
            this.port = port;
            this.untagged = Boolean.valueOf(untagged);
        } else {
            throw new IllegalArgumentException("<< class Customer: Not all field are filled. >>");
        } // if (fineData)
    }

    public Region getCity() {
        return city;
    }

    public String getMnemokod() {
        return mnemokod;
    }

    public String getVlan() {
        return vlan;
    }

    public String getIPConnection() {
        return IPConnection;
    }

    public String getPort() {
        return port;
    }

    public boolean getUntagged() {
        return untagged;
    }

    @Override
    public String toString() {
        return LINE + "\n" +
                   "ОП подключения: " + getCity().getCity() + "\n" +
                   "Клиент: " + getMnemokod() + "\n" +
                   "Номер vlan: " + getVlan() + "\n" +
                   "IP свитча: " + getIPConnection() + "\n" +
                   "Порт подключения: " + getPort() + "\n" +
                   "Растагирование: " + getUntagged() + "\n" +
                   LINE + "\n";
    } // ** toString()
}
