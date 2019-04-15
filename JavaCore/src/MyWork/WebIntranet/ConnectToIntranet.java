package MyWork.WebIntranet;

import MyWork.CryptDecrypt;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static MyWork.Tools.ConvertRussian.convertToRus;
import static MyWork.Config.*;

public class ConnectToIntranet {
    private static final String PASS = "!purumpumpum!";

    private static final String url = "https://intranet.ptl.ru/AutorizUser.php";
    private static final String connectionURL = "https://intranet.ptl.ru/connection/";
    private static final String editClientURL = "https://intranet.ptl.ru/connection/EditClient.php";

    public static void main(String[] args) {
        Map<String, String> loginCookies = connectAndGetCookie(url);
        loginCookies.put("ActiveReg", "6");
        loginCookies.put("ActiveRegName", "Ростов-на-Дону");

        getBody(connectionURL, loginCookies); // list of connection in region ActiveReg

        // return Map with keys: status, where, row, mnemokod, ipCl, vlan, address, ipCon, tel, description;
        Map<String, String> customer = findCustomer(loginCookies, "RND-Panda");

        String customerConnect = findCustomerConnect(loginCookies, "1"); // id - number client in previous getBody()

        if(customer != null && !customer.get("status").contains("Error")) {
            System.out.println(customer);
        }
        System.out.println(LINE);
        System.out.println(ParsePath(convertToRus(customerConnect)));
    }

    public static Map<String, String> connectAndGetCookie(String url){
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
        return login.cookies();
    } // ** connectAndGetCookie(url)

    public static String getBody(String url, Map<String,String> cookie) {
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
    } // ** getBody(utl, cookie)

    public static Map<String, String> findCustomer(Map<String, String> cookie, String data) {
        // return Map with keys: status, where, row, mnemokod, ipCl, vlan, address, ipCon, tel, description;
        Connection.Response response;
        Map<String, String> result = new HashMap<>();

        cookie.put("ActivePage", "1");
        cookie.put("LimitElems", "10");

        try {
            response = Jsoup
                    .connect(editClientURL)
                    .cookies(cookie)
                    .method(Connection.Method.POST)
                    .data("Client-mod", "search")
                    .data("data", data)
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

    public static String findCustomerConnect(Map<String, String> cookie, String id) {
        Connection.Response response;
        String result;

        cookie.put("ActivePage", "1");
        cookie.put("LimitElems", "10");

        try {
            response = Jsoup
                    .connect(editClientURL)
                    .cookies(cookie)
                    .method(Connection.Method.POST)
                    .data("Client-mod", "GetMap")
                    .data("id", id)
                    .execute();
        } catch (IOException ex) {
            return "Error. " + ex.toString();
        }

        if (response == null || response.body() == null)
            result = "Error findCustomer()";
        else
            result = response.body();
        return result;
    }

//    private static Map<String, String> getMapResponse(String toParse) {
//        // keys = status, result;
//        toParse = toParse.replaceAll(",\"", "xXxX\"");
//        toParse = toParse.trim();
//        toParse = toParse.substring(1, toParse.length()-1);
//
//        String[] sAr = toParse.split("xXxX");
//        Map<String, String> result = new HashMap<>();
//
//        for (String ss: sAr) {
//            String[] temp = ss.split("\":");
//
//            if(temp.length == 2)
//                result.put(temp[0].replaceAll("\"", "").trim(), temp[1]);
//            else
//                result.put("Error", ss);
//        }
//
//        return result;
//    }

    private static String getTextInTD(String s) {
        Pattern inTd = Pattern.compile("<td>(.*)<\\\\/td>");
        Matcher matcher = inTd.matcher(s);
        if (matcher.find())
            return matcher.group(1);
        else
            return "";
    }

    private static String ParsePath(String s) {
        String leftPort = "";
        String rightPort = "";
        String ip = "";

        StringBuilder path = new StringBuilder();

        for(String temp: s.split("auto")) {
            leftPort = "";
            rightPort = "";
            ip = "";
//            System.out.println(temp);

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

        return path.toString();
    } // ** ParsePath(String s)

} // ** class
