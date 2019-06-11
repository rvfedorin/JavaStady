package other;


import MyWork.NodesClass.Cisco;
import MyWork.NodesClass.Customer;


public class test {
    public static void main(String[] args) {
        char[] key = "".toCharArray();
        String ip = "";
        Cisco cisco = new Cisco(ip, key);
        Customer customer = new Customer("Orel", "Orel-test", "1431", "no", "no", "no");
        System.out.println(cisco.createClient(customer));


    } // ** main()


} // ** class test


// repeat text /////////////////////////////////////////////////////////////////////////////////
//            String space = " ";
//            k = String.join("", Collections.nCopies(lengthLabel - k.length(), space)) + k;
///////////////////////////////////////////////////////////////////////////////////////////////