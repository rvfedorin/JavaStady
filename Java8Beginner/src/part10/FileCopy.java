package part10;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopy {
    public static void main(String[] args) {
        int i = 0;
        FileInputStream fis = null;
        FileOutputStream fos = null;

        String natural_file = "file1";
        String copy_file = "file2";

        try {
            fis = new FileInputStream(natural_file);
            fos = new FileOutputStream(copy_file);

            while (i != -1) {
                i = fis.read();
                if(i > -1) fos.write(i);
            }

        } catch (IOException ex) {
            System.out.println(ex);
        } finally {
            try {
                if (fis != null) fis.close();
            } catch (IOException e) {
                System.out.println(e);
            }
            try {
                if (fos != null) fis.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }

    }
}
