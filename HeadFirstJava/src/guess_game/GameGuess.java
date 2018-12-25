package guess_game;

public class GameGuess {

    public void StartGame()
    {

        Player p1 = new Player();
        Player p2 = new Player();
        Player p3 = new Player();

        boolean gessp1;
        boolean gessp2;
        boolean gessp3;

        int nump1;
        int nump2;
        int nump3;

        int luckyNumber = (int) (Math.random() * 10);
        System.out.println("______________________________");
        System.out.println("Загадываю число от 0 до 10...");

        while (true)
        {
            System.out.println("Загадано число - " + luckyNumber);
            p1.gess();
            p2.gess();
            p3.gess();

            nump1 = p1.num;
            nump2 = p2.num;
            nump3 = p3.num;

            gessp1 = (nump1 == luckyNumber);
            gessp2 = (nump2 == luckyNumber);
            gessp3 = (nump3 == luckyNumber);

            if (gessp1 || gessp2 || gessp3)
            {
                System.out.println("У нас есть победитель!!");
                System.out.println("Это игрок 1? - " + gessp1);
                System.out.println("Это игрок 2? - " + gessp2);
                System.out.println("Это игрок 3? - " + gessp3);
                break;
            } else {
                System.out.println("Никто не угадал, надо попробовать ещё!");
                System.out.println("______________________________");
            }

        }
    }

}
