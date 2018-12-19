package part15;

import java.util.*;
import java.io.*;
import java.net.*;

public class VerySimpleChatServer {
    private ArrayList clientOutputStreams;

    public class ClientHandler implements Runnable {
        BufferedReader reader;
        Socket sock;

        public ClientHandler (Socket clientSocker) {
            try {
                sock = clientSocker;
                InputStreamReader sr = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(sr);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }  // close constructor

        @Override
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println(message + "\n");
                    tellEveryOne(message);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } // close run()
    }

    public static void main(String[] args) {
        new VerySimpleChatServer().go();
    }

    public void go() {
        clientOutputStreams = new ArrayList();
        try {
            ServerSocket server = new ServerSocket(5000);

            while (true) {
                Socket clientSocket = server.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutputStreams.add(writer);

                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
                System.out.println("Got a connection ...");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }  // close go()

    private void tellEveryOne(String message) {
        Iterator it = clientOutputStreams.iterator();

        while (it.hasNext()) {
            try {
                PrintWriter wr = (PrintWriter) it.next();
                wr.println(message);
                wr.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }  // close tellEveryOne(String message)

}  // END VerySimpleChatServer
