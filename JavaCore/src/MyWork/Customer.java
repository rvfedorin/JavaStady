package MyWork;

import static MyWork.Config.*;

public class Customer {
    private String city;
    private String mnemokod;
    private String vlan;
    private String IPswitch;
    private String port;
    private String untagged;

    Customer(String city, String mnemokod, String vlan, String IPswitch, String port, String untagged)
            throws IllegalArgumentException{

        boolean fineData = true;
        String[] allData = new String[]{city, mnemokod, vlan, IPswitch, port, untagged};

        for (String s: allData) {
            if (s.length() < 1) {
                fineData = false;
                break;
            }
        } // for

        if (fineData) {
            this.city = city;
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
        String s = LINE +
                   "ОП подключения: " + city + "\n" +
                   "Клиент: " + mnemokod + "\n" +
                   "Номер vlan: " + vlan + "\n" +
                   "IP свитча: " + IPswitch + "\n" +
                   "Порт подключения: " + port + "\n" +
                   "Растагирование: " + untagged + "\n" +
                   LINE;
        return s;
    }
}
