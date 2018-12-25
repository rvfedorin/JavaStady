package part15;

import java.io.*;
import java.net.*;

public class DailyAdviceServer {
    String[] adviceList = {"Ешьте меньшими порциями.", "Купите облегающие джинсы, нет, они не делают вас полнее.",
        "Два слова: не годится!", "Будьте честны хотябы сегодня. Скажите своему начальнику всё, что вы на самом" +
            "деле о нём думаете.", "Возможно вам стоит подобрать другую причёску."};

    public void go() {
        try {
            ServerSocket serverSocket = new ServerSocket(4242);

            while (true) {
                Socket sock = serverSocket.accept();

                PrintWriter writer = new PrintWriter(sock.getOutputStream());
                String advice = getAdvice();
                writer.println(advice);
                writer.close();
                System.out.println(advice);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }  // close go()

    private String getAdvice() {
        int random = (int) (Math.random() * adviceList.length);
        return adviceList[random];
    }

    public static void main(String[] args) {
        DailyAdviceServer server = new DailyAdviceServer();
        server.go();
    }
}
