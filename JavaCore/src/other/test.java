package other;

public class test {
    public static void main(String[] args) {
        String first = "pass";
        String second = "key";

        System.out.println(encode(first, second));
    }

    public static byte[] encode (String pass, String key) {
        byte[] passB = pass.getBytes();
        byte[] keyB = key.getBytes();
        byte[] result = new byte[pass.length()];

        for (int i = 0; i < pass.length(); i ++) {
            result[i] = (byte) (passB[i] ^ keyB[(i % key.length())]);
        }

        return result;
    }
}
