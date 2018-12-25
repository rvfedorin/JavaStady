package guess_game;

public class Player {
    int num = 0;

    public void gess()
    {
        num = (int) (Math.random() * 10);
        System.out.println("Думаю это число - " + num);
    }
}
