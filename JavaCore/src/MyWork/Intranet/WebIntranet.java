package MyWork.Intranet;

import MyWork.NodesClass.Region;
import MyWork.Tools.CryptDecrypt;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static MyWork.Tools.ConvertRussian.convertToRus;
import static MyWork.Config.*;

public class WebIntranet extends Intranet{
    private static String PASS;
    private Map<String, String> loginCookies;
    private final String ID = "1"; // ** is always 1?

    public WebIntranet(char[] key, Region reg) {
        PASS = String.valueOf(key);
        region = reg;
        loginCookies = connectAndGetCookie(AUTH_URL);
        loginCookies.put("ActiveReg", region.getId());
        getBody(CONNECTION_URL, loginCookies); // list of connection in region ActiveReg
        //        loginCookies.put("ActiveRegName", "Ростов-на-Дону"); // NO MATTER YET
    }

    public static void main(String[] args) {
        WebIntranet webIntranet = new WebIntranet("123!@#".toCharArray(), CITIES.get("Rnd"));
        String mnemokod = "RND-Panda";

        System.out.println(LINE);
        System.out.println(webIntranet.getFullPath(mnemokod));
        //{ipCon=172.17.196.78 - rwr, mnemokod=RND-Panda, ipCl=88.86.80.23, address=ул. Володарского 2-я, 76, vlan=vlan321, description=, tel=, row=1, status=200}
        //===================================
        // 95.80.127.253(Cisco ASR1001  ) [0]<=>[1] 172.17.199.254(D-Link DGS-3120-24TC) [22]<=>[10] 172.17.199.250(D-Link DES-3200-10 Fa) [2]<=> 172.17.196.2(SkyMAN R5000-Omxb\" \n ) [0]<=>[0] 172.17.196.78(SkyMAN R5000-Sm\/5.30) [0]<=>
    }

    private Map<String, String> connectAndGetCookie(String url){
        Connection.Response login;
        // Auth
        try {
            // Auth
            login = Jsoup.connect(url)
                    .method(Connection.Method.POST)
                    .data("Login", CryptDecrypt.getEncrypt(PASS, INTRANET_CONNECT_LOGIN))
                    .data("Password", CryptDecrypt.getEncrypt(PASS, INTRANET_CONNECT_PASS))
                    .execute();
        } catch (IOException ex) {
            Map <String, String> error = new HashMap<>();
            error.put("Error", ex.toString());
            return error;
        }
        loginCookies = login.cookies();
        return getLoginCookies();
    } // ** connectAndGetCookie(AUTH_URL)

    private Map<String, String> getLoginCookies() {
        return loginCookies;
    }

    private static String getBody(String url, Map<String,String> cookie) {
        Connection.Response response;
        String result;
        try {
            response = Jsoup
                    .connect(url)
                    .cookies(cookie)
                    .execute();
        } catch (IOException ex) {
            return "Error. " + ex.toString();
        }

        if (response == null || response.body() == null)
            result = "Error getBody()";
        else
            result = response.body();
        return result;
    } // ** getBody(url, cookie)

    public Map<String, String> findCustomer(String mnemokod) {
        // return Map with keys: status, where, row, mnemokod, ipCl, vlan, address, ipCon, tel, description;

        Connection.Response response;
        Map<String, String> result = new HashMap<>();

        loginCookies.put("ActivePage", "1");
        loginCookies.put("LimitElems", "10");

        try {
            response = Jsoup
                    .connect(EDIT_CLIENT_URL)
                    .cookies(loginCookies)
                    .method(Connection.Method.POST)
                    .data("Client-mod", "search")
                    .data("data", mnemokod)
                    .execute();
        } catch (IOException ex) {
            result.put("status", "Error. " + ex.toString());
            return result;
        }

        if (response == null || response.body() == null)
            result.put("status", "Error findCustomer()");
        else {
            String notFormatted = response.body();

            if(notFormatted != null && !notFormatted.contains("Error")) {
                notFormatted = convertToRus(notFormatted);

                Pattern patternHead = Pattern.compile("status\":" +
                        "(\\d{1,3}).*" +
                        "(\\d{1,5}).*showMore'>" +
                        "([\\w\\d-]*)(.*)");

                Matcher res = patternHead.matcher(notFormatted);
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
            } else {
                result.put("status", "Error body()");
            }// ** if fine body
        } // if get response
//        System.out.println(result);

        return result;
    }

    public String findCustomerConnect(String mnemokod) {
        Connection.Response response;
        String result;
        Map<String, String> customer;

        customer = findCustomer(mnemokod);

        if(customer == null || customer.get("status").contains("Error")) {
            result = "Error findCustomer()";
            return result;   // <-----   NOT FOUND CUSTOMER
        }

        loginCookies.put("ActivePage", "1");
        loginCookies.put("LimitElems", "10");

        try {
            response = Jsoup
                    .connect(EDIT_CLIENT_URL)
                    .cookies(loginCookies)
                    .method(Connection.Method.POST)
                    .data("Client-mod", "GetMap")
                    .data("id", ID)
                    .execute();

        } catch (IOException ex) {
            return "Error. " + ex.toString();   // ** <---- Error connection EXIT
        }

        if (response == null || response.body() == null)
            result = "Error findCustomer()";
        else {
            result = response.body();
        }

        return result;
    }

    private static String getTextInTD(String s) {
        Pattern inTd = Pattern.compile("<td>(.*)<\\\\/td>");
        Matcher matcher = inTd.matcher(s);
        if (matcher.find())
            return matcher.group(1);
        else
            return "";
    }

    private static String ParsePath(String s) {
        String leftPort;
        String rightPort;
        String ip;
        String model;

        StringBuilder path = new StringBuilder();

        for(String temp: s.split("auto")) {
            leftPort = "";
            rightPort = "";
            ip = "";
            model = "";
//            System.out.println(temp);

            String[] row = temp.split("<br>");
            Pattern modelP = Pattern.compile("\\u041c\\u043e\\u0434\\u0435\\u043b\\u044c:(.{32})");
            Pattern port = Pattern.compile("\\[.*]");
            Pattern ipP = Pattern.compile("\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}");

            if (row.length > 1) {
                Matcher modelM = modelP.matcher(row[0]);
                Matcher leftPortM = port.matcher(row[0]);
                Matcher ipMatch = ipP.matcher(row[1]);
                Matcher rightPortM = port.matcher(row[1]);

                if(modelM.find()) {
                    String toReplace = "\\s\\s|<\\\\/th>|<td>|<\\\\/td>|<\\\\/tr>|\\\\n\\\\\"|>|<";
                    String toReplace2 = "^[\\d\\s-]";
                    model = modelM.group(1);
                    model = model.replaceAll(toReplace, "");
                    model = model.replaceAll(toReplace2, "");
                }
                if(leftPortM.find()) leftPort = leftPortM.group();
                if(ipMatch.find()) ip = ipMatch.group();
                if(rightPortM.find()) rightPort = rightPortM.group();

                path.append(leftPort + " " + ip + "(" + model + ")" + " " + rightPort + SEPARATOR_CONNECTION);
            } // ** if(row.length > 1)
        } // ** for

        return path.toString();
    } // ** ParsePath(String s)

    @Override
    public String getFullPath(String mnemokod) {

        return ParsePath(convertToRus(findCustomerConnect(mnemokod)));
    }

    @Override
    public String allConnectionFromSwitch(String switchIP, boolean onlySw) {
        return null;
    }
} // ** class
