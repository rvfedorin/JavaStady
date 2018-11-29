import java.util.Date;

class test {
    public static void main(String[] args) {
        int x = 10000000;
        float y = 22.22f;

        String s = String.format("Проверка форматирования числа: %,d из %.1f", x, y);
        System.out.println(s);

        Date today = new Date();
        System.out.println(String.format("%tc", today));

        System.out.println(String.format("%tA/%tB/%td", today, today, today));
        // or
        System.out.println(String.format("%tA/%<tB/%<td", today));
    }
}

