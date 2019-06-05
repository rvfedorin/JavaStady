package chapter1_5;

import java.util.*;

public class EnumTest {
    public static void main(String... arts) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите размер: ");
        String input = in.next().toUpperCase();
        Size size = Enum.valueOf(Size.class, input);
        System.out.println("Size: " + size);
        System.out.println("Abbreviation: " + size.getAbbreviation());

        if (size == Size.LARGE) {
            System.out.println("It is a BIG.");
        }
    }
}

enum Size {
    SMALL("S"), MEDIUM("M"), LARGE("L");

    private String abbreviation;

    Size(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
