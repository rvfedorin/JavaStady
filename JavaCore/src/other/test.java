package other;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class test {
    public static void main(String[] args) {
        String fileName = "./test.txt";

        try {
            Files.write(
                    Paths.get(fileName),
                    "\ntest test1".getBytes(),
                    Files.exists(Paths.get(fileName)) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE_NEW);
        } catch (IOException ex) {
            System.out.println("Error opening file");
        }
    }

} // ** class test


// repeat text /////////////////////////////////////////////////////////////////////////////////
//
//            String k = String.join("", Collections.nCopies(20, "=")); // 1ms
//        OR
//            String s = Stream.generate(() -> "=").limit(20).collect(Collectors.joining());  // 223ms
///////////////////////////////////////////////////////////////////////////////////////////////