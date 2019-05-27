package other;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static MyWork.Config.IP_PATTERN;

public class test {
    public static void main(String[] args) {
        String port = "4";
        String ip = "172.17.1.38";
        String s = "matcher.find() 172.17.151.0/25  (unnumbered sw 172.17.1.38 port 2)";
        Pattern connectPattern = Pattern.compile(".*\\D" + port + "\\D.*\\D" + ip + "\\D.*");
        Matcher getIP = connectPattern.matcher(s);
        if(getIP.find()) {
            System.out.println(getIP.group());
        }

    }
}


// repeat text /////////////////////////////////////////////////////////////////////////////////
//            String space = " ";
//            k = String.join("", Collections.nCopies(lengthLabel - k.length(), space)) + k;
///////////////////////////////////////////////////////////////////////////////////////////////