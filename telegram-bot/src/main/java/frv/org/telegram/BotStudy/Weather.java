/*
 * Created by Roman V. Fedorin
 */
package frv.org.telegram.BotStudy;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolf
 */
public class Weather {
    // https://samples.openweathermap.org/data/2.5/weather?q=London&appid=b6907d289e10d714a6e88b30761fae22

    public static String getWeather(String city, Model model) {
        URL url = null;
        String result = "";
        
        try {
            url = new URL("https://samples.openweathermap.org/data/2.5/weather?q=" + city + "&appid=b6907d289e10d714a6e88b30761fae22");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Weather.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (Scanner in = new Scanner((InputStream) url.getContent())) {
            while (in.hasNext()) {
                result += in.nextLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("[->] " + result);
        return result;
    }

}
