package MyWork.NodesClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Telnet {
    private final String END = "####END####";

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Telnet(String host, int port) {
        try {
            socket = new Socket(host, port);
            socket.setSoTimeout(10 * 1000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException ex) {
            ex.printStackTrace();
        } // ** try

    }

    ///////////////////// START MAIN //////////////////////////////////////////////////////////
    public static void main(String[] args) {
        ////////////////////// START  SWITCH ///////////////////////////////////////////
        Telnet client = new Telnet("172.16.44.58", 23);

//        client.setTimeout(5 * 1000);
        boolean resultConnect = client.auth("admin", "");
        if (resultConnect) {
            client.sendCommand("show vlan vl 4001");
            client.sendCommand("show ports 2 err_disabled");
            System.out.println("1 ==============>>>>>>>> ");
            client.sendCommand("Done", "save");


//            ArrayList<String> commands = new ArrayList<>();
//            commands.add("show ports 2");
//            commands.add("q");
//            commands.add("show ports 3");
//            commands.add("q");
//            client.sendListCommands(commands);
            System.out.println("2 ==============>>>>>>>> ");
        }
        ////////////////////// END  SWITCH ///////////////////////////////////////////

        ////////////////////// START  RWR ///////////////////////////////////////////
//        Telnet clientRWR = new Telnet("172.16.44.230", 23);
//        client.setTimeout(2 * 1000);
//        boolean resultConnectRWR = clientRWR.auth("root", "");
//        if (resultConnectRWR) {
//            client.sendCommand("mint map");
//        }
        ////////////////////// END  RWR ///////////////////////////////////////////
        client.close();
    } // ** main()
    //////////////////////////// STOP MAIN //////////////////////////////////////////////////////////

    public boolean auth(String login, String pass) {
        boolean result = false;

        try {
            out.println(login);
            sleep(100);
            out.println(pass);
            sleep(100);
            out.println(END + "\r\n");
            sleep(100);

            boolean done = false;
            while (!done) {
                try {
                    String line = in.readLine();
                    if (line.contains(END) || line.contains("Fail"))
                        done = true;
//                    System.out.println(line);
                } catch (IOException e) {
                    e.printStackTrace();
                    done = true;
                }
            } // ** while()
            result = true;
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        return result;
    } // auth()

    public void sendCommand(String command) {
        String result = "";
        try {
            out.println(command + "\r\n");
            sleep(100);
            out.println(END + "\r\n");
            sleep(100);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        boolean done = false;
        while (!done) {
            try {
                String line = in.readLine();
                if (line.length() < 1)
                    continue;
                if (line.contains(END)) {
                    done = true;
                    continue;
                }
                System.out.println(line);
            } catch (IOException ex) {
                ex.printStackTrace();
                done = true;
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out.close();
            }
        }
//        return result;
    }

    public void sendCommand(String wait, String command) {
        String result = "";
        try {
            out.println(command + "\r\n");
            sleep(100);
            out.println(END + "\r\n");
            sleep(100);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        boolean done = false;
        while (!done) {
            try {
                String line = in.readLine();
                if (line.length() < 1)
                    continue;
                if (line.contains(END)) {
                    done = true;
                    continue;
                }
                if(line.contains(wait))
                    done = true;
                System.out.println(line);
            } catch (IOException ex) {
                ex.printStackTrace();
                done = true;
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out.close();
            }
        }
//        return result;
    }

    public void sendListCommands(ArrayList<String> commands) {
        String result = "";
        try {

            for(String command: commands) {
                out.println(command + "\r\n");
                sleep(100);
            }

            out.println(END + "\r\n");
            sleep(100);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        boolean done = false;
        while (!done) {
            try {
                String line = in.readLine();
                 if (line.length() < 1 || line.length() == 80)
                    continue;
                if (line.contains(END)) {
                    done = true;
                    continue;
                }
                System.out.println(line.length());
            } catch (IOException ex) {
                ex.printStackTrace();
                done = true;
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out.close();
            }
        }
    } // ** sendListCommands()

    public void setTimeout(int mls) {
        try {
            socket.setSoTimeout(mls);
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
    } // ** setTimeout()

    public void close(){
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

} // class Telnet
