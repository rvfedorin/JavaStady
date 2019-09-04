package other;


public class test {
    public static void main(String[] args) {

    }
} // ** class test


// repeat text /////////////////////////////////////////////////////////////////////////////////
//
//            String k = String.join("", Collections.nCopies(20, "=")); // 1ms
//        OR
//            String s = Stream.generate(() -> "=").limit(20).collect(Collectors.joining());  // 223ms
///////////////////////////////////////////////////////////////////////////////////////////////