

/*
 * Created by Roman V. Fedorin
 */
/**
 *
 * @author Wolf
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(" = " + getMask(24));
    }

    public static String getMask(int len) {
        StringBuilder sb = new StringBuilder();

        while (len > 0) {
            if (len > 8) {
                sb.append((int)Math.pow(2, 8)-1).append(".");
            } else {
                sb.append(256 - (int)Math.pow(2, 8 - len));
            }
            len = len - 8;
        }
        return sb.toString();
    }
}
