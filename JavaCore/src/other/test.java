package other;

import static MyWork.Config.IP_PATTERN;

public class test {
    public static void main(String[] args) {

        String ip = " <=>  [28] 172.16.48.254(switch) [9] <=> [25] 172.16.48.42(switch) [3] <=> [9] 172.16.49.134(switch)  [3]";
        ip = ip.replaceAll("\\(.*?\\)|]|\\[", "");
        System.out.println(ip);
        int i = 0;
        ip = ip.replaceFirst(" <=> ", "").replaceAll("\\s+", " ").trim();
        for (String node : ip.split(" <=> ")) {
//            System.out.println(node.replaceAll("\\s*", ""));
//            System.out.println(i++);
            String[] portSwitchPort = node.split(" ");
            System.out.println("NODE "+node);
            System.out.println(portSwitchPort.length);
            String upPort = null, ipSw = null, downPort = null;
            if (portSwitchPort.length == 3) {
                upPort = portSwitchPort[0].replaceAll("\\s*", "");
                ipSw = portSwitchPort[1].replaceAll("\\s*", "");
                downPort = portSwitchPort[2].replaceAll("\\s*", "");
            } else if (portSwitchPort.length == 2) {
                if (IP_PATTERN.matcher(portSwitchPort[0]).find()) {
                    upPort = "null";
                    ipSw = portSwitchPort[0].replaceAll("\\s*", "");
                    downPort = portSwitchPort[1].replaceAll("\\s*", "");
                } else {
                    upPort = portSwitchPort[0].replaceAll("\\s*", "");
                    ipSw = portSwitchPort[1].replaceAll("\\s*", "");
                    downPort = "null";
                }
            }
            System.out.println("--> " + upPort + " " + ipSw + " " + downPort);
        }


    }
}


// repeat text /////////////////////////////////////////////////////////////////////////////////
//            String space = " ";
//            k = String.join("", Collections.nCopies(lengthLabel - k.length(), space)) + k;
///////////////////////////////////////////////////////////////////////////////////////////////