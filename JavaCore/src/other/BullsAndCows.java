package other;

import java.util.ArrayList;
import java.util.Scanner;

public class BullsAndCows {
    private ArrayList<Integer> guessNumber;
    private static final int LENGTH_NUMBER = 3;
    private int cows = 0;
    private int bulls = 0;

    public static void main(String[] args) {
        BullsAndCows game = new BullsAndCows();
        int start = game.startGame();
        if (start == 1) {
            game.handleGame();
        }
    }

    private int startGame() {
        guessNumber = new ArrayList<>();
        int err = 0;
        while (guessNumber.size() < LENGTH_NUMBER) {
            int newDig = (int) (Math.random() * 10);
            if (guessNumber.contains(newDig)) {
                err += 1;
                continue;
            } else {
                guessNumber.add(newDig);
            }
            if (err > 100) {
                return 0;
            }
        }


        System.out.println("I made number of " + guessNumber.size() + " digits. Try to guess it.");
        System.out.println("You have 10 attempts!");
        return 1;
    } // close startGame()

    private void handleGame() {
        System.out.println(guessNumber);
        System.out.print("You think it is: ");
        Scanner cons = new Scanner(System.in);
        String userGuess = cons.nextLine();

        char[] userNumber = userGuess.toCharArray();

        for (int i = 0; i < LENGTH_NUMBER; i++) {
            int dig = Character.getNumericValue(userNumber[i]);
            if (dig == guessNumber.get(i)) {
                bulls +=1;
            } else if (guessNumber.contains(dig)) {
                cows +=1;
            }
        }
        System.out.println("Bulls: " + bulls + " Cows: " + cows);
    }

}
