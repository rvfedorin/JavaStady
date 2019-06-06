package other;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static MyWork.Config.SPEEDS;

public class test {
    public static void main(String[] args) {
        String upPortSw = "2";
        String s = "DES-3200-10:admin#show ports 2 details\n" +
                "Command: show ports 2 details\n" +
                "\n" +
                "Port : 2\n" +
                "--------------------\n" +
                "Port Status                 : Link Up\n" +
                "Description                 :\n" +
                "HardWare Type               : Fast Ethernet\n" +
                "MAC Address                 : C0-A0-BB-42-D0-82\n" +
                "Bandwidth                   : 10000Kbit\n" +
                "Auto-Negotiation            : Enabled\n" +
                "Duplex Mode                 : Half Duplex\n" +
                "Flow Control                : Disabled\n" +
                "MDI                         : Normal\n" +
                "Address Learning            : Enabled\n" +
                "Last Clear of Counter       : 241 hours 44 mins ago\n" +
                "BPDU Hardware Filtering Mode: Disabled\n" +
                "Queuing Strategy            : FIFO\n" +
                "TX Load                     :   0/100,          0 bits/sec,        0 packets/sec\n" +
                "RX Load                     :   0/100,          0 bits/sec,        0 packets/sec\n";

        Pattern upPortP = Pattern.compile("Port : " + upPortSw + ".*(\\d{5,9}Kbit)");
//        Pattern upPortP = Pattern.compile("Port : (" + upPortSw + ".*)");
        Matcher upPortM = upPortP.matcher(s.replaceAll("\n", " "));

        if(upPortM.find()) {
            System.out.println("Gr upPort ->> " + upPortM.group(0));
            String upPort = SPEEDS.getOrDefault(upPortM.group(1), "?");
            System.out.println("upPort ->> " + upPort);
        }

    } // ** main()
} // ** class test


// repeat text /////////////////////////////////////////////////////////////////////////////////
//            String space = " ";
//            k = String.join("", Collections.nCopies(lengthLabel - k.length(), space)) + k;
///////////////////////////////////////////////////////////////////////////////////////////////