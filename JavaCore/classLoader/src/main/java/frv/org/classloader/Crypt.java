/*
 * Created by Roman V. Fedorin
 */
package frv.org.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 *
 * @author Wolf
 */
public class Crypt {

    public static void main(String[] args) {
        String clName = "toCr";

        for (File f : new File(clName).listFiles()) {
            if (f.getName().endsWith("class")) {
                try (FileInputStream in = new FileInputStream(f);
                        FileOutputStream out = new FileOutputStream(f.getName().replace("class", "crypt"));
                        OutputStreamWriter osw = new OutputStreamWriter(out, "utf-8")) {
                    int key = 3;
                    int ch;
                    while ((ch = in.read()) != -1) {
                        byte c = (byte) (ch + key);
                        out.write(c);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
} // ** class Crypt
