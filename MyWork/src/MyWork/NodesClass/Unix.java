package MyWork.NodesClass;

import MyWork.Tools.SSH;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static MyWork.Config.LOCAL_CLIENTS_CONF_FILE;
import static MyWork.Config.REMOTE_CLIENTS_CONF_FILE;

public class Unix {
    private String ipUnix;
    private char[] key;

    public Unix (String ip, char[] entireKey) {
        ipUnix = ip;
        key = entireKey;
    } // ** constructor

    public synchronized boolean downloadClientsConf() {
        SSH ssh = new SSH(ipUnix, key);
        return ssh.downloadFile(REMOTE_CLIENTS_CONF_FILE, LOCAL_CLIENTS_CONF_FILE);
    }

    public synchronized String getClFromClConf(String mnemokod, boolean downloadClConf) {
        String result = "\n[Error] Not found client in " + LOCAL_CLIENTS_CONF_FILE + "\n";
        StringBuilder foundClients = new StringBuilder();
        boolean successDownload;
        if (downloadClConf) {
            successDownload = downloadClientsConf();
        } else {
            successDownload = true;
        }

        if (successDownload) {
            try (BufferedReader br = new BufferedReader(new FileReader(LOCAL_CLIENTS_CONF_FILE))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.replaceAll("\\s+", " ");
                    if (line.toLowerCase().contains(mnemokod.toLowerCase() + " ")) {
                        foundClients.append(line).append("\n");
                    }
                } // while every line
                if (foundClients.length() > 0)
                    result = foundClients.toString();
            } catch (IOException ioex) {
                ioex.printStackTrace();
                result = "\n[Error] open file " + LOCAL_CLIENTS_CONF_FILE + "\n";
            }
        } else {
            result = "\n[Error] download file " + LOCAL_CLIENTS_CONF_FILE + "\n";
        } // if we get Clients.conf
        return result;
    } // ** getClFromClConf()
} // ** class Unix
