package actions;

import tools.SSH;

import static tools.Config.*;
import static tools.CryptDecrypt.getEncrypt;

public class GetStatus implements Runnable {
    private String mnemokod;
    private String ipClient;
    private String passClient;
    private String ipISG;
    private char[] key;
    private SSH ssh;

    public GetStatus(String mnemokod, String ipClient, String passClient, String ipISG, char[] key) {
        this.mnemokod = mnemokod;
        this.ipClient = ipClient;
        this.passClient = passClient;
        this.ipISG = ipISG;
        this.key = key;
    }

    @Override
    public void run() {
        String showSessionCommand = "show subscriber session | inc " + mnemokod;
        try {
            ssh = new SSH(getEncrypt(new String(key), WORK), key);
            String[] commands = new String[1];
            commands[0] = showSessionCommand;

            String response = ssh.telnetConnection(commands,
                    getEncrypt(new String(key), SSH_LOGIN),
                    getEncrypt(new String(key), SSH_PASS_L),
                    ipISG);

            String result = parseOut(response, showSessionCommand);
            System.out.println(result);
            ShowDialogs.info(result);

        } catch (Exception ex) {
            ex.printStackTrace();
            ShowDialogs.info("Ошибка подключения к ISG. GetStatus->run()");
        } finally {
            ssh.closeSession();
        }
    }

    private String parseOut(String rawOut, String replace) {
        String result = "Сессия не запущена.";
        rawOut = rawOut.replaceAll(replace, "");

        for(String line: rawOut.split("\n")) {
            if(line.contains(mnemokod)) {
                result = line;
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return "GetStatus{" +
                "mnemokod='" + mnemokod + '\'' +
                ", ipClient='" + ipClient + '\'' +
                ", passClient='" + passClient + '\'' +
                ", ipISG=" + ipISG +
                '}';
    }
}
