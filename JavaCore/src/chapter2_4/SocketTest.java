package chapter2_4;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketTest {
    public static void main(String[] args) {
        try(Socket socket = new Socket("time-a-g.nist.gov", 13);
            Scanner in = new Scanner(socket.getInputStream())) {
            while (in.hasNextLine()) {
                System.out.println(in.nextLine());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
