package dot_com;

import java.util.ArrayList;

public class DotComBust {
    private GameHelper helper = new GameHelper();
    private ArrayList<DotCom> dotComsList = new ArrayList<>();
    private int numOfGuesses = 0;

    private void setUpGame() {
        // Создадим несколько сайтов и присвоим им имена
        DotCom one = new DotCom();
        one.setName("Pets.com");
        DotCom two = new DotCom();
        two.setName("eToys.com");
        DotCom three = new DotCom();
        three.setName("Go2.com");

        dotComsList.add(one);
        dotComsList.add(two);
        dotComsList.add(three);

        System.out.println("Ваша цель - потопить три 'сайта'.");
        System.out.println("Pets.com, eToys.com, Go2.com");
        System.out.println("Постарайтесь потопить их за наименьшее количество ходов.");

        for (DotCom dotComToSet: dotComsList) {
            ArrayList<String> newLocation = helper.placeDotCom(3);
            dotComToSet.SetLocationCells(newLocation);
            System.out.println(newLocation);
        }
    }

    private void startPlaying() {
        while (!dotComsList.isEmpty()) {
            String userGuess = helper.getUserInput("Сделайте ход: ");
            checkUserGuess(userGuess);
        }
        finishGame();
    }

    private void checkUserGuess(String userGuess) {
        numOfGuesses++;
        String result = "Мимо";

        for (DotCom dot: dotComsList) {
            result = dot.checkYourSelf(userGuess);

            if (result.equals("Попал")) {
                result = result + " в " +dot.getName();
                break;
            }
            if (result.equals("Потопил")) {
                dotComsList.remove(dot);
                result = "----------------------->>>> " + result + " " + dot.getName();
                break;
            }
        } // for (DotCom dot: dotComsList)
        System.out.println(result);
    }

    private void finishGame() {
        System.out.println("Все сайты ушли ко дну!!!");
        System.out.println("Количество выстрелов: " + String.valueOf(numOfGuesses));
    }

    public static void main(String[] args) {
        DotComBust game = new DotComBust();
        game.setUpGame();
        game.startPlaying();
    }

}
