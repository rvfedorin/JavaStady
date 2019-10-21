package actions;

import gui.MainFrame;
import static gui.ProgressBar.progressBar;
import tools.SSH;

import static tools.Config.*;
import static tools.CryptDecrypt.getEncrypt;

public class GetStatus implements Runnable {

    private String mnemokod;
    private String ipClient;
    private String passClient;
    private String ipISG;
    private char[] key;
    private MainFrame parent;
    private SSH ssh;
    private boolean detailed;

    public GetStatus(String mnemokod, String ipClient, String passClient, String ipISG, char[] key, MainFrame parent) {
        this.mnemokod = mnemokod;
        this.ipClient = ipClient;
        this.passClient = passClient;
        this.ipISG = ipISG;
        this.key = key;
        this.parent = parent;
        detailed = false;
    }

    @Override
    public void run() {
        progressBar.setIndeterminate(true);
//        String showSessionCommand = "show subscriber session | inc " + mnemokod;
        String showSessionCommand = "show subscriber session username " + mnemokod + " feature accounting";
        String showARP = "show arp " + ipClient;

        try {
            ssh = new SSH(getEncrypt(new String(key), WORK), key);
            String[] commands = new String[2];
            if (ipClient.length() > 7) {
                commands[0] = showARP;
            } else {
                commands[0] = "";
            }
            commands[1] = showSessionCommand;

            String response = ssh.telnetConnection(commands,
                    getEncrypt(new String(key), SSH_LOGIN),
                    getEncrypt(new String(key), SSH_PASS_L),
                    ipISG);

            String result = parseOut(response, showSessionCommand);
//            System.out.println(result);
            if (detailed) {
                String uid = getUID(result);
                if (uid != null && uid.length() > 2) {
                    String showDetailedSessionCommand = "show subscriber session uid " + uid;
                    commands[0] = showDetailedSessionCommand;

                    String responseDetail = ssh.telnetConnection(commands,
                            getEncrypt(new String(key), SSH_LOGIN),
                            getEncrypt(new String(key), SSH_PASS_L),
                            ipISG);

                    result += "\n\n" + clearDetailOut(responseDetail, ">" + showDetailedSessionCommand);
                } else {
                    result += "\nНе удалось получить ID сессии.\n";
                }
            }
//            ShowDialogs.info(result);
            parent.resultFrame.showResult(result);

        } catch (Exception ex) {
            ex.printStackTrace();
            ShowDialogs.info("Ошибка подключения к ISG. GetStatus->run()");
        } finally {
            ssh.closeSession();
            progressBar.setIndeterminate(false);
        }
    }

//    private String getUID(String result) {
//        result = result.replaceAll("\\s", " ");
//        return result.split(" ")[0];
//    }
    private String getUID(String result) {
        String response = "";
        for (String line : result.split("\n")) {
            if (line.contains("UID")) {
                for (String block : line.split(",")) {
                    if (block.contains("UID")) {
                        response = block.replace("UID: ", ipClient);
                        return response;
                    }
                }
            }
        }
        return response;
    }

    private String parseOut(String rawOut, String replace) {
//Protocol  Address          Age (min)  Hardware Addr   Type   Interface
//Internet  10.20.244.11            0   5404.a68d.af44  ARPA   TenGigabitEthernet0/0/0.418

//Type: IPv4, UID: 4599, State: authen, Identity: podolskaya18-1H-1
//IPv4 Address: 10.20.184.79
//Session Up-time: 4d03h   , Last Changed: 4d00h
        String result = "";
        rawOut = rawOut.replaceAll(replace, "");
        StringBuilder sbSess = new StringBuilder();
        StringBuilder sbMac = new StringBuilder();
        String titleS = "\nИнформация по сессии: \n";
        String titleM = "Информация по маку: \n";

        for (String line : rawOut.split("\n")) {
            if (line.contains("Protocol") || line.contains("Internet")) {
                sbMac.append(line).append("\n");

            } else if (line.contains("Type") || line.contains("Address") || line.contains("Session")) {
                sbSess.append(line).append("\n");
            }
        }
        if (ipClient.length() < 7) {
            result = titleM + "Необходимо указать IP.\n";
        } else if (sbMac.length() > 0) {
            result += titleM + sbMac.toString() + "\n";
        } else {
            result = titleM + "mac адрес, для IP " + ipClient + ", в arp таблице не найден.\n";
        }

        if (mnemokod.length() < 3) {
            result += titleS + "Необходимо указать мнемокод.\n";
        } else if (sbSess.length() > 0) {
            result += titleS + sbSess.toString();
        } else {
            result += titleS + "Сессия не запущена.\n";
        }

        return result;
    }

    private String clearDetailOut(String rawData, String startNeedData) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean startWrite = false;

        for (String line : rawData.split("\n")) {
            if (line.contains(startNeedData)) {
                startWrite = true;
            } else if (line.contains("More")) {
                startWrite = false;
            }
            if (startWrite) {
                stringBuilder.append(line).append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public GetStatus detailed(boolean det) {
        detailed = det;
        return this;
    }

    @Override
    public String toString() {
        return "GetStatus{"
                + "mnemokod='" + mnemokod + '\''
                + ", ipClient='" + ipClient + '\''
                + ", passClient='" + passClient + '\''
                + ", ipISG=" + ipISG
                + '}';
    }
}
