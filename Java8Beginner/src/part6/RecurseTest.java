package part6;

public class RecurseTest {
    public static void main(String[] args) {
        String str = "12345";
        RecurseTest rev = new RecurseTest();
        rev.reverseString(str);

        String s2 = "2345";
    }

    public void reverseString(String s) {
        if (s.length() == 1) {
            System.out.println(s);
        } else {
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<=s.length()-2; i++) {
                sb.append(s.charAt(i));
            }
            System.out.print(s.charAt(s.length()-1));
            reverseString(sb.toString());
        }
    }
}
