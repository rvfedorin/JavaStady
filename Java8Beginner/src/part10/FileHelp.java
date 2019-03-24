package part10;

import java.io.*;

public class FileHelp {
    public static void main(String[] args) {
        Help help = new Help("help");
        help.printThemes();
        help.helpon(help.getSelection());
    } // main
}// clss FileHelp

class Help {
    private String helpFile;

    Help(String hf) {
        helpFile = hf;
    } // Help(String)

    boolean helpon(int what) {
        int ch;
        String topic, info;

        try (BufferedReader br = new BufferedReader(new FileReader(helpFile))) {
            do {
                ch = br.read();
                if ( ch == '#') {
                    topic = br.readLine();
                    if(topic.charAt(0) == what) {
                        do {
                            info = br.readLine();
                            if(info != null) System.out.println(info);
                        } while((info != null) && (info.compareTo("") != 0));
                        return true;
                    } // if topic
                } // if #
            } while (ch != -1);
        } catch (IOException ex) {
            System.out.println(ex);
            return false;
        }

        return false;
    } // helpon()

    int getSelection() {
        int topic = 0;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Выберете номер темы: ");
        try {
            topic = br.read();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return topic;

    } // getSelection

    void printThemes() {
        String str;

        try (BufferedReader br = new BufferedReader(new FileReader(helpFile))) {
            do {
                str = br.readLine();
                if (str != null && str.startsWith("#")) {
                    System.out.println(str.replace('#', ' '));
                }
            } while (str != null);
            System.out.println();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    } // printThemes
} // class Help
