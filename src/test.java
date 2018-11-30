import java.util.Calendar;
import java.util.Date;
import static java.lang.System.out;

class test {
    static int DAY_IM = 1000 * 60 * 60 * 24;

    public static void main(String[] args) {

        Calendar c = Calendar.getInstance();
        long today = c.getTimeInMillis();
        c.set(2004, 0, 7, 15, 40, 0);
        long day1 = c.getTimeInMillis();
        int id = 0;

        while (c.getTimeInMillis() < today){
            id += 1;
            String s = String.format("%d Полнолуние было в %tc", id, c);
            out.println(s);
            day1 += DAY_IM * 29.52;
            c.setTimeInMillis(day1);
        }
    } // public static void main
}

