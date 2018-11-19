package simple_dot_com;

public class SimpleDotComGame {
    public static void main (String[] args)
    {
        int numOfGuess = 0;
        GameHelper helper = new GameHelper();

        SimpleDotCom dot = new SimpleDotCom();
        int[] loc = {2, 3, 4};
        dot.SetLocation(loc);

        while (true)
        {
            String userGeuess = helper.getUserInput("Введите число: ");
            String result = dot.CheckYourSelf(userGeuess);

            numOfGuess++;

            if (result.equals("Попал"))
            {
                System.out.println("Успушное попадание!");
            }
            else if (result.equals("Мимо"))
            {
                System.out.println("Мимо!");
            }
            else if (result.equals("Потопил"))
            {
                System.out.println("Успушное попадание!");
                System.out.println("Вам потребовалось: " + numOfGuess + " попыток.");
                break;
            }
        }
    }
}
