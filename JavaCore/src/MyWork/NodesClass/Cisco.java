package MyWork.NodesClass;

import MyWork.Tools.CryptDecrypt;

import java.util.ArrayList;

import static MyWork.Config.CISCO_EN_PASS;
import static MyWork.Config.CISCO_PASS;

public class Cisco {
    private final String FREE_INT_COMMAND = "sh int des | inc Svobodno";
    private String ip;
    private char[] key;
    private Telnet connect;

    public Cisco(String ip, char[] pass) {
        this.ip = ip;
        this.key = pass;
        this.connect = null;
    }

    private boolean connect(){
        boolean successConnect;
        connect = new Telnet(ip, 23);
        successConnect = connect.auth(CryptDecrypt.getEncrypt(new String(key), CISCO_PASS));
        if(successConnect) {
            ArrayList<String> commands = new ArrayList<>();
            commands.add("en");
            commands.add(CryptDecrypt.getEncrypt(new String(key), CISCO_EN_PASS));
            connect.sendListCommands(commands);
        } // ** to enable mod

        return successConnect;
    }

    public String runCommand(String command) {
        String result = "";
        ArrayList<String> commands = new ArrayList<>();
        commands.add(command);

        if(connect()) {
            result = connect.sendListCommands(commands);
            closeConnect();
        }

        return result;
    }

    public String getFreeInt(){
        String result = "";
        ArrayList<String> commands = new ArrayList<>();
        commands.add(FREE_INT_COMMAND);

        if(connect()) {
            result = connect.sendListCommands(commands);
            result = formatFreeInt(result);
            closeConnect();
        }

        return result;
    } // ** getFreeInt()

    private String formatFreeInt(String rawOut) {
        boolean start = false;
        StringBuilder result = new StringBuilder();

        for(String line: rawOut.split("\n")) {
            if(start) {
                line = line.replaceAll("\\s+", " ");
                String[] partsOfLine = line.split(" ");
                if(partsOfLine[0].length() > 4) {
                    String interfaceFree = partsOfLine[0] + (line.contains("down") ? " down\n" : " up\n");
                    result.append(interfaceFree);
                }
            } // ** if useful information
            if (line.contains(FREE_INT_COMMAND)){
                start = true;
            }
        } // ** for

        return result.toString();
    }

    private void closeConnect() {
        if(connect != null) {
            connect.close();
            connect = null;
        }
    }
}
