package part15;

import java.io.*;
import java.net.*;

public class DailyAdviceClient {

    public void go() {
        try {
            Socket socket = new Socket("127.0.0.1", 4242);
            InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            String advice = bufferedReader.readLine();
            bufferedReader.close();
            System.out.println("Сегодня ты должен: " + advice);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DailyAdviceClient client = new DailyAdviceClient();
        client.go();
    }
}
