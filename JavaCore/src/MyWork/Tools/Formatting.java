package MyWork.Tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static MyWork.Config.IP_PATTERN;
import static MyWork.Config.SEPARATOR_CONNECTION;
import static MyWork.Config.SEPARATOR_PORT;

public class Formatting {

    public static String[] formatPath(String pathRaw) {

        String[] pathByCell = pathRaw.split(SEPARATOR_CONNECTION);
        int i = 0;

        for (String connection : pathByCell) {
            String upPort;
            String ipSw;
            String downPort;

            //        Pattern fullConnect = Pattern.compile("\\[(.*)] (\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3})\\(.*\\) \\[(.*)]");
            //        Pattern withoutUpPort = Pattern.compile("(\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3})\\(.*\\) \\[(.*)]");
            //        Pattern withoutDownPort = Pattern.compile("\\[(.*)] (\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3})\\(.*\\)");

            Pattern fullConnect = Pattern.compile("\\[(.*)](" + IP_PATTERN + ")\\(.*\\)\\[(.*)]");
            Pattern withoutUpPort = Pattern.compile("(" + IP_PATTERN + ")\\(.*\\)\\[(.*)]");
            Pattern withoutDownPort = Pattern.compile("\\[(.*)](" + IP_PATTERN + ")\\(.*\\)");

            Matcher fullConnectM = fullConnect.matcher(connection);
            Matcher withoutUpPortM = withoutUpPort.matcher(connection);
            Matcher withoutDownPortM = withoutDownPort.matcher(connection);

            if (fullConnectM.find()) {
                upPort = fullConnectM.group(1);
                ipSw = fullConnectM.group(2);
                downPort = fullConnectM.group(3);
            } else if (withoutUpPortM.find()) {
                upPort = "null";
                ipSw = withoutUpPortM.group(1);
                downPort = withoutUpPortM.group(2);
            } else if (withoutDownPortM.find()) {
                upPort = withoutDownPortM.group(1);
                ipSw = withoutDownPortM.group(2);
                downPort = "null";
            } else {
                return null;
            }
            pathByCell[i] = upPort + SEPARATOR_PORT + ipSw + SEPARATOR_PORT + downPort;
            i++;
        } // ** for
        return pathByCell;
    }
}
