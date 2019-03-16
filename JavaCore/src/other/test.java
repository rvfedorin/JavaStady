package other;

public class test {
    public static void main(String[] args) {
        String first = "pass";
        String second = "key";
//        System.out.println(encode(first, second));

        int it = 55;
        int sub = 0xfffffff8;
        int res = it & sub;
        System.out.println(it << 8);
//        System.out.println(0 & 0x0ff);
//        System.out.println(Integer.toBinaryString(sub));
//        System.out.println(Integer.toBinaryString(it));
//        System.out.println(Integer.toBinaryString(res));
//        System.out.println(res);

        int nStrLen = first.length();
        int nI;
        int nPos = 0;
        for (nI = 0; nI < nStrLen; nI++)
        {
            char cActChar = first.charAt(nI);
            System.out.println((byte) ((cActChar >> 8) & 0x0ff));
            System.out.println((byte) (cActChar & 0x0ff));
        }


    }  // close main

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
