package other;


import MyWork.Tools.SSH;

public class test {
    public static void main(String[] args) {
        String remoteClientsConf = "/etc/Clients.conf";
        String localClientConf = "Clients.conf";
        char[] pass = "pass".toCharArray();

        SSH ssh = new SSH("IP", pass);
        boolean result = ssh.downloadFile(remoteClientsConf, localClientConf);
        System.out.println(result);


    } // ** main()


} // ** class test


// repeat text /////////////////////////////////////////////////////////////////////////////////
//            String space = " ";
//            k = String.join("", Collections.nCopies(lengthLabel - k.length(), space)) + k;
///////////////////////////////////////////////////////////////////////////////////////////////