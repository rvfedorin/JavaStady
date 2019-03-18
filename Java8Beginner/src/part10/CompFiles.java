package part10;

import java.io.FileInputStream;
import java.io.IOException;

public class CompFiles {
    public static void main(String[] args) {
        String file1 = "file1";
        String file2 = "file2";

        try (FileInputStream fis = new FileInputStream(file1);
             FileInputStream fis2 = new FileInputStream(file2)) {

            int i1, i2, str=1, col=0;
            char a;

            do {
                i1 = fis.read();
                i2 = fis2.read();

                System.out.println(Character.toLowerCase((char) i1));

                if (i1 == 10) continue;

                if (i1 == 13) {
                    str++;
                    col = 0;
                } else {
                    col++;
                }

                if (i1 != i2) {
                    System.out.println("Файлы не одинаковые.");
                    System.out.println("Символы различаются в строке: " + str + " позиции: " + col);
                    return;
                }

            } while (i1 != -1 || i2 != -1);

        } catch (IOException ex) {
            System.out.println("Ошилка открытия файла. " + ex);
        }

        System.out.println("Файлы одинаковые.");
    }
}
