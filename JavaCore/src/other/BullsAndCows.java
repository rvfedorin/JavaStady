package other;

import java.util.ArrayList;
import java.util.Scanner;

public class BullsAndCows {
    private ArrayList<Integer> guessNumber;
    private static final int LENGTH_NUMBER = 3;
    private static final int NUMBER_OF_TRY = 10;
    private static final String QUIT = "q";

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
        boolean gameRun = true;
        Scanner cons = new Scanner(System.in);
        String userGuess;
        String result;
        char[] userNumber;
        int tryNum = 0;

        System.out.println(guessNumber);
        System.out.print("You think it is: ");
        while (gameRun) {
            tryNum += 1;

            userGuess = cons.nextLine();

            if (userGuess.equals(QUIT)) {
                System.out.println("Bye.");
                break;
            }

            userNumber = userGuess.toCharArray();
            if (userNumber.length < LENGTH_NUMBER) {
                System.out.println("For quit enter [q]");
                System.out.print("You need enter 3 number: ");
                continue;
            }

            result = checkNumber(userNumber);

            if (result.equals("WIN")) {
                System.out.println("You win!");
                gameRun = false;
            } else if (tryNum < NUMBER_OF_TRY){
                System.out.println(result);
                System.out.print("Try again: ");
            } else {
                System.out.println("You lost.");
                gameRun = false;
            }
        }

    } // close handleGame()

    private String checkNumber(char[] uGess) {
        int cows = 0;
        int bulls = 0;
        ArrayList<Integer> tempNum = (ArrayList<Integer>) guessNumber.clone();

        for (int i = 0; i < LENGTH_NUMBER; i++) {
            int dig = Character.getNumericValue(uGess[i]);

            if (dig > 10) {
                return "You need enter number or [q] to quit";
            }

            if (dig == guessNumber.get(i)) {
                bulls +=1;
                tempNum.remove((Integer) dig);
            } else if (tempNum.contains(dig)) {
                cows +=1;
                tempNum.remove((Integer) dig);
            }
        }
        if (bulls == LENGTH_NUMBER) {
            return ("WIN");
        } else {
            return ("Bulls: " + bulls + " Cows: " + cows);
        }
    }  // close String checkNumber(char[] uGess)

}
