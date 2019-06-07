package other;


import MyWork.NodesClass.Cisco;

public class test {
    public static void main(String[] args) {
        char[] key = "pass".toCharArray();
        String ip = "ip";
        Cisco cisco = new Cisco(ip, key);

        System.out.println(cisco.getFreeInt());

    } // ** main()


} // ** class test


// repeat text /////////////////////////////////////////////////////////////////////////////////
//            String space = " ";
//            k = String.join("", Collections.nCopies(lengthLabel - k.length(), space)) + k;
///////////////////////////////////////////////////////////////////////////////////////////////