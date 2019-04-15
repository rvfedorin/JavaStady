package other;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
    public static void main(String[] args) {
        Map<String, String> result = new HashMap<>();

        String s1 = "{\"status\":200,\"result\":\"<tr class='resSearch'  >\\n                <th scope='row'>1<\\/th>\\n                <td class='showMore'>RND-Eldakoyug<\\/td>\\n                <td>88.86.80.109<\\/td>\\n                <td>vlan103<\\/td>\\n                <td>\\u0443\\u043b. 1-\\u0430\\u044f \\u041b\\u0443\\u0433\\u043e\\u0432\\u0430\\u044f, \\u0434\\u043e\\u043c \\u2116 17, \\u043a\\u043e\\u0440\\u043f. \\\"\\u0411\\\"<\\/td>\\n                <td>172.18.40.90 - rwr<\\/td>                                                                             \\n                <td><\\/td> \\n                <td><\\/td>                        \\n                <\\/tr>\"}";

        String row, mnemo, ipCl, vlan, address, ipCon, tel, descr;

//        ParsePath(s1);

        Pattern patternHead = Pattern.compile("status\":" +
                "(\\d{1,3}).*" +
                "(\\d{1,5}).*showMore'>" +
                "([\\w\\d-]*)(.*)");

        Matcher res = patternHead.matcher(s1);
        if (res.find()) {
            result.put("status", res.group(1));
            result.put("row", res.group(2));
            result.put("mnemokod", res.group(3));

            String[] tileS = res.group(4).split("\\\\n");
            result.put("ipCl", getTextInTD(tileS[1]));
            result.put("vlan", getTextInTD(tileS[2]));
            result.put("address", getTextInTD(tileS[3]));
            result.put("ipCon", getTextInTD(tileS[4]));
            result.put("tel", getTextInTD(tileS[5]));
            result.put("description", getTextInTD(tileS[6]));
        } else {
            result.put("status", "Error Pattern no found.)");
        } // ** if find

        System.out.println(result);

    } // ** main()

    private static String ParsePath(String s) {
        String leftPort = "";
        String rightPort = "";
        String ip = "";

        StringBuilder path = new StringBuilder();

        for(String temp: s.split("auto")) {
            leftPort = "";
            rightPort = "";
            ip = "";
            System.out.println(temp);

            String[] row = temp.split("<br>");
            Pattern port = Pattern.compile("\\[.*]");
            Pattern ipP = Pattern.compile("\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}");

            if (row.length > 1) {
                Matcher leftPortM = port.matcher(row[0]);
                Matcher ipMatch = ipP.matcher(row[1]);
                Matcher rightPortM = port.matcher(row[1]);

                if(leftPortM.find()) leftPort = leftPortM.group();
                if(ipMatch.find()) ip = ipMatch.group();
                if(rightPortM.find()) rightPort = rightPortM.group();

                path.append(leftPort + " " + ip + " " + rightPort + " <=> ");

            } // ** if(row.length > 1)
        } // ** for
        System.out.println(path.toString());
        return path.toString();
    } // ** ParsePath(String s)

    private static String getTextInTD(String s) {
        Pattern inTd = Pattern.compile("<td>(.*)<\\\\/td>");
        Matcher matcher = inTd.matcher(s);
        if (matcher.find())
            return matcher.group(1);
        else
            return "";
    }
}

// repeat text /////////////////////////////////////////////////////////////////////////////////
//            String space = " ";
//            k = String.join("", Collections.nCopies(lengthLabel - k.length(), space)) + k;
///////////////////////////////////////////////////////////////////////////////////////////////