package MyWork.WebIntranet;

import MyWork.CryptDecrypt;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static MyWork.Tools.ConvertRussian.convertToRus;
import static MyWork.Config.*;

public class ConnectToIntranet {
    private static final String PASS = "passpass#";

    private static final String url = "https://intranet.ptl.ru/AutorizUser.php";
    private static final String connectionURL = "https://intranet.ptl.ru/connection/";
    private static final String editClientURL = "https://intranet.ptl.ru/connection/EditClient.php";

    public static void main(String[] args) {
        Map<String, String> loginCookies = connectAndGetCookie(url);
        loginCookies.put("ActiveReg", "6");
        loginCookies.put("ActiveRegName", "Ростов-на-Дону");

        getBody(connectionURL, loginCookies); // list of connection in region ActiveReg
        String customer = findCustomer(loginCookies, "RND-MegaTel");
        String customerConnect = findCustomerConnect(loginCookies, "1"); // id - number client in previous getBody()

        System.out.println(convertToRus(customer));
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

    public static String findCustomer(Map<String, String> cookie, String data) {
        Connection.Response response;
        String result;

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
            return "Error. " + ex.toString();
        }

        if (response == null || response.body() == null)
            result = "Error findCustomer()";
        else
            result = response.body();


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

} // ** class
