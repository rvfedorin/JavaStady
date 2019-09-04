package other;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class WriteDataToFile {
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

}
