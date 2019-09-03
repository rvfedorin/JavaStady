package other;

import java.util.Arrays;

public class test {
    public static void main(String[] args) {
        String[] a = "1234".split("");
        System.out.println(String.join("", a));
    }

} // ** class test


// repeat text /////////////////////////////////////////////////////////////////////////////////
//
//            String k = String.join("", Collections.nCopies(20, "=")); // 1ms
//        OR
//            String s = Stream.generate(() -> "=").limit(20).collect(Collectors.joining());  // 223ms
///////////////////////////////////////////////////////////////////////////////////////////////