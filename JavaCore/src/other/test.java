package other;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class test {
    public static void main(String[] args) {
        Instant start = Instant.now();
        String s = Stream.generate(() -> "=").limit(20).collect(Collectors.joining());
        System.out.println(s);
        Instant finished = Instant.now();
        System.out.println("Execution takes: " + Duration.between(start, finished).toMillis() + "ms");

        long start2 = Instant.now().toEpochMilli();
        String s2 = String.join("", Collections.nCopies(20, "="));
        System.out.println(s2);
        long finished2 = Instant.now().toEpochMilli();
        System.out.println("Execution takes: " + (finished2 - start2) + "ms");

    }

} // ** class test


// repeat text /////////////////////////////////////////////////////////////////////////////////
//
//            String k = String.join("", Collections.nCopies(20, "=")); // 1ms
//        OR
//            String s = Stream.generate(() -> "=").limit(20).collect(Collectors.joining());  // 223ms
///////////////////////////////////////////////////////////////////////////////////////////////