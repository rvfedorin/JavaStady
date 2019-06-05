package chapter2_1;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {
    public static void main(String[] args) {
        Stream<BigInteger> integerStream = Stream.iterate(BigInteger.ONE, n -> n.add(BigInteger.ONE))
                .filter(e -> e.intValue() % 2 == 0);

        show("iterate", integerStream);

        try {
            Path path = Paths.get("The_Count_of_Monte_Cristo.txt");
            String text = new String(Files.readAllBytes(path));
            Stream<String> words = Stream.of(text.split("\\PL+"));
            show("words", words);

            Stream<String> anotherWords = Pattern.compile("\\PL+")
                    .splitAsStream(text).skip(10)
                    .map(String::toUpperCase);
            show("anotherWords", anotherWords);

        } catch (IOException ex) {
            System.out.println("Ошибка чтения файла.");
        }

        Stream<String> echo = Stream.generate(() -> "Echo");
        show("echo", echo);

        Stream<String> uniq = Stream.of("1", "1", "1", "2", "1", "3", "3", "1").distinct();
        show("uniq", uniq);

    } // ** main()

    private static <T> void show(String title, Stream<T> stream) {

        List<T> list = stream.limit(10)
                .collect(Collectors.toList());

        for (T i : list) {
            System.out.println(title + ": " + i);
        }
        System.out.println("-------------------------------------");
    } // ** show
}
