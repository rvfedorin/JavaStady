package actions;

import static gui.ProgressBar.progressBar;
import tools.SSH;

import static tools.Config.*;
import static tools.CryptDecrypt.getEncrypt;

public class StartSession implements Runnable {
    private String mnemokod;
    private String ipClient;
    private String passClient;
    private char[] key;
    private SSH ssh;

    public StartSession(String mnemokod, String ipClient, String passClient, char[] key) {
        this.mnemokod = mnemokod;
        this.ipClient = ipClient;
        this.passClient = passClient;
        this.key = key;
    }

    @Override
    public void run() {
        progressBar.setIndeterminate(true);
        StringBuilder startCommand = new StringBuilder(); //"ssh support@172.16.55.2 \"/usr/local/bin/sss adanini5-800 Tohwoo 10.20.10.156\"";
        startCommand.append("php start_session.php ");
        startCommand.append(mnemokod).append(" ");
        startCommand.append(passClient).append(" ");
        startCommand.append(ipClient);

        try {
            ssh = new SSH(getEncrypt(new String(key), WORK), key);
            String[] commands = new String[2];
            commands[0] = "cd /usr/local/apache/htdocs";
            commands[1] = startCommand.toString();

            String response = ssh.shellConnection(commands);

            String result = parseOut(response);
            System.out.println("ssh response: " + result);
            ShowDialogs.info(result);

        } catch (Exception ex) {
            ex.printStackTrace();
            ShowDialogs.info("Ошибка подключения. StartSession->run()");
        } finally {
            progressBar.setIndeterminate(false);
            ssh.closeSession();
        }
    }

    private String parseOut(String rawOut) {
        StringBuilder result = new StringBuilder();
        boolean getOut = false;

        for(String line: rawOut.split("\n")) {
            if(line.contains("Try") || getOut) {
                result.append(line).append("\n");
                getOut = true;
                if (line.contains("Success")) {
                    break;
                }
            }
        }

        if(!result.toString().contains("Success")) {
            result = new StringBuilder("Не удалось запустить сессию.");
        }

        return result.toString();
    }

    @Override
    public String toString() {
        return "StartSession{" +
                "mnemokod='" + mnemokod + '\'' +
                ", ipClient='" + ipClient + '\'' +
                ", passClient='" + passClient + '\'' +
                '}';
    }
}
