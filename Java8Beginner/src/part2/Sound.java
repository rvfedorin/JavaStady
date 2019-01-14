package part2;

public class Sound {
    private static final int SPEED = 1100;

    public static void main(String[] args) {
        double timeInSecond;

        timeInSecond = 7.2;

        double result = timeInSecond * SPEED;

        System.out.println("Расстояние до места вспышки молнии " + result + " футов.");
    }
}
