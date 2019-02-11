package chapter7;

public class AssertTest {
    public static void main(String[] args) {
        int i = 0;

        assert i>1: "Error: i < 1";
        System.out.println("END");
    }
}
