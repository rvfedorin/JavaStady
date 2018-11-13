public class Bottles {
    public static void main (String[] args)
    {
        int bottlesAll = 99;

        while (bottlesAll > 0)
        {
            if (bottlesAll == 1) {
                System.out.println("Осталась последняя бутылка!!");
                break;
            } else {
              System.out.println("Осталось " + bottlesAll + " бутылок.");
              bottlesAll -= 1;
            }
        }
    }
}
