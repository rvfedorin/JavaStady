package MyWork.NodesClass;

import static MyWork.Config.*;

public class Customer {
    private Region city;
    private String mnemokod;
    private String vlan;
    private String IPswitch;
    private String port;
    private String untagged;

    public Customer(String city, String mnemokod, String vlan, String IPswitch, String port, String untagged)
            throws IllegalArgumentException{

        boolean fineData = true;
        String[] allData = new String[]{city, mnemokod, vlan, IPswitch, port, untagged};
        String prefix = CITIES_BY_NAME.getOrDefault(city, "none");
        boolean hasCity = CITIES.containsKey(prefix);

        for (String s: allData) {
            if (s.length() < 1) {
                fineData = false;
                break;
            }
        } // for

        if(!hasCity){
            fineData = false;
            System.out.println("City not found.");
        }

        if (fineData) {
            this.city = CITIES.get(prefix);
            this.mnemokod = mnemokod;
            this.vlan = vlan;
            this.IPswitch = IPswitch;
            this.port = port;
            this.untagged = untagged;
        } else {
            throw new IllegalArgumentException("<< class Customer: Not all field are filled. >>");
        } // if (fineData)
    }

    @Override
    public String toString() {
        String s = LINE + "\n" +
                   "ОП подключения: " + city.getCity() + "\n" +
                   "Клиент: " + mnemokod + "\n" +
                   "Номер vlan: " + vlan + "\n" +
                   "IP свитча: " + IPswitch + "\n" +
                   "Порт подключения: " + port + "\n" +
                   "Растагирование: " + untagged + "\n" +
                   LINE + "\n";
        return s;
    }
}
