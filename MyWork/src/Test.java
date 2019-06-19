import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        String simbols = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";

        StringBuilder sb = new StringBuilder();
        List<String> listS = Arrays.asList(simbols.split(""));
        Collections.shuffle(listS);
        sb.append(listS.stream().limit(10).unordered().collect(Collectors.joining("")));

        System.out.println(sb);
    }
}
