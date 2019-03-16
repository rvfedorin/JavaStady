package part10;

import java.io.*;

public class FileCopy2 {
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Необходимо ввести имена файлов.");
            return;
        }

        try (FileInputStream fis = new FileInputStream(args[0]);
             FileOutputStream fos = new FileOutputStream(args[1])) {
            int i;

            do {
                i = fis.read();
                if (i != -1) fos.write(i);
            } while (i != -1);

        } catch (IOException ex) {
            System.out.println(ex);
        }
    } // close main
} // close class FileCopy2
