package other;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

public class BullsAndCows {

    private static final int LENGTH_NUMBER = 3;
    private static final int NUMBER_OF_TRY = 10;
    private static final String WIN = "=== Ты выиграл!!! === ";
    private static final String LOSE = "=== Ты проиграл!!! === ";
    private static final String QUIT = "q";

    private ArrayList<Integer> guessNumber;
    private BandCGui gui;
    private int tryNum = 0;

    private CheckButtonListener checkButtonListener = new CheckButtonListener();
    private NewGameListener newGameListener = new NewGameListener();


    public static void main(String[] args) {
        BullsAndCows game = new BullsAndCows();
        int start = game.startGame();
        if (start == 1) {
            game.handleGame();
        }
    }

    private int startGame() {
        /* Generation of initial parameters */
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
        return 1;
    } // close startGame()

    private void handleGame() {
        System.out.println("I made number of " + guessNumber.size() + " digits. Try to guess it.");
        System.out.println("You have 10 attempts!");
        gui = new BandCGui();
        gui.initGui();
        gui.tellToUserL1.setText("Я загадал число из " + guessNumber.size() + " цифр. Попробуй угадай.");
        gui.tellToUserL2.setText("У тебя " + NUMBER_OF_TRY + " попыток.");
        gui.checkInput.addActionListener(checkButtonListener);

        boolean gameRun = true;
        Scanner cons = new Scanner(System.in);
        String userGuess;
        String result;
        char[] userNumber;


        System.out.println(guessNumber);
        System.out.print("You think it is: ");

        while (gameRun) {
//            tryNum += 1;

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

            if (result.equals(WIN)) {
                System.out.println("You win!");
                gameRun = false;
            } else if (tryNum < NUMBER_OF_TRY){
                System.out.println(result);
                System.out.println("У тебя ещё есть " + (NUMBER_OF_TRY - tryNum) + " попыток.");
                System.out.print("Try again: ");
            } else {
                System.out.println("You lost.");
                gameRun = false;
            }
        }  // close while (gameRun)

    } // close handleGame()

    private String checkNumber(char[] uGess) {
        /* count how many cows and bulls */
        tryNum += 1;
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
            return (WIN);
        } else if ((NUMBER_OF_TRY - tryNum) > 0) {
            return (" Быков: " + bulls + " Коров: " + cows + " ");
        } else {
            return (LOSE);
        }
    }  // close String checkNumber(char[] uGess)

    class CheckButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
//            System.out.println(gui.userInput.getText());
            System.out.println();
            String result;
            result = gui.userInput.getText();
            if (result.length() == LENGTH_NUMBER) {
                result = checkNumber(result.toCharArray());
                gui.tellToUserL1.setText("===== " + result + "=====");
                if (!result.equals(WIN) && !result.equals(LOSE)) {
                    gui.tellToUserL2.setText("У тебя ещё есть  " + (NUMBER_OF_TRY - tryNum) + " попыток.");
                } else {
                    gui.tellToUserL2.setText("");
                    gui.checkInput.setText("Попробовать ещё.");
                    gui.checkInput.removeActionListener(checkButtonListener);
                    gui.checkInput.addActionListener(newGameListener);
                }
            } else {
                gui.tellToUserL1.setText("Необходимо ввести число из трёх цифр.");
            }
        }
    } // close CheckButtonListener

    class NewGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int i = startGame();
            if (i == 1) {
                System.out.println(guessNumber);
                tryNum = 0;
                gui.tellToUserL1.setText("Я загадал число из " + guessNumber.size() + " цифр. Попробуй угадай.");
                gui.tellToUserL2.setText("У тебя " + NUMBER_OF_TRY + " попыток.");
                gui.checkInput.setText("Проверить.");
                gui.checkInput.removeActionListener(newGameListener);
                gui.checkInput.addActionListener(checkButtonListener);
            }
        }
    }

}

class BandCGui {
    JTextField userInput;
    JLabel tellToUserL1;
    JLabel tellToUserL2;
    JButton checkInput;
//    JButton startAgain;

    public void initGui() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tellToUserL1 = new JLabel();
        tellToUserL2 = new JLabel();
        userInput = new JTextField(15);
        checkInput = new JButton("Проверить.");

        JPanel mainPanel = new JPanel();
//        mainPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        mainPanel.add(tellToUserL1);
        mainPanel.add(tellToUserL2);
        mainPanel.add(userInput);
        mainPanel.add(checkInput);

        frame.add(mainPanel);
        frame.setSize(290, 200);
        frame.setVisible(true);
    }  // close initGui()



}