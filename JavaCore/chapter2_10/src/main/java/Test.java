
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Created by Roman V. Fedorin
 */
/**
 *
 * @author Wolf
 */
public class Test {

    public static void main(String[] args) {
//        System.out.println(" = " + getMask(24));

        int[] n = {1, 9, 4, 6, 1, 6, 8, 9, 3, 1};
        List<Integer> l = Arrays.stream(n).boxed().collect(Collectors.toList());
        bubleSort((ArrayList<Integer>) l);
        System.out.println(" = " + l);

        l = Arrays.stream(n).boxed().collect(Collectors.toList());
        deepSort((ArrayList<Integer>) l);
        System.out.println(" = " + l);
    }

    public static String getMask(int len) {
        StringBuilder sb = new StringBuilder();

        while (len > 0) {
            if (len > 8) {
                sb.append((int) Math.pow(2, 8) - 1).append(".");
            } else {
                sb.append(256 - (int) Math.pow(2, 8 - len));
            }
            len = len - 8;
        }
        return sb.toString();
    }

    public static void bubleSort(ArrayList<Integer> l) {
        int operations = 0;
        int count = l.size() - 1;
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count - i; j++) {
                if (l.get(j) > l.get(j + 1)) {
                    int temp = l.get(j);
                    l.set(j, l.get(j + 1));
                    l.set(j + 1, temp);
                }
                operations++;
            }
        }
        System.out.println("Количество операций: " + operations);
    }

    public static void deepSort(ArrayList<Integer> l) {
        int operations = 0;
        int count = l.size();

        for (int i = 0; i < count; i++) {
            int j = i;
            while (j > 0 && l.get(j) < l.get(j - 1)) {
                int temp = l.get(j);
                l.set(j, l.get(j - 1));
                l.set(j - 1, temp);
                j--;
                operations++;
            }
        }
        System.out.println("Количество операций: " + operations);
    }

    public static void bubleSort2(ArrayList<Integer> l) {
        int operations = 0;
        int count = l.size() - 1;
        for (int i = count; i > 0; i--) {
            for (int j = i; j < count && l.get(j) > l.get(j + 1); j++) {
                if (l.get(j) > l.get(j + 1)) {
                    int temp = l.get(j);
                    l.set(j, l.get(j + 1));
                    l.set(j + 1, temp);
                }
                operations++;
            }
        }
        System.out.println("Количество операций: " + operations);
    }
}

