/*
 * Proprietary software.
 * 
 */
package rv.fedorin.codewars;

import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author R. V. Fedorin
 */
public class BattleField {

    public static boolean fieldValidator(int[][] field) {
        HashMap<Integer, Integer> shipsAll = new HashMap<>();
        shipsAll.put(4, 1);
        shipsAll.put(3, 2);
        shipsAll.put(2, 3);
        shipsAll.put(1, 4);

        HashMap<Integer, Integer> shipsHas = new HashMap<>();
        shipsHas.put(4, 0);
        shipsHas.put(3, 0);
        shipsHas.put(2, 0);
        shipsHas.put(1, 0);

        int border = field.length - 1;
        for (int i = 0; i <= border; i++) {
            for (int j = 0; j <= border; j++) {
                if (field[i][j] == 1) {
                    int lenShip = getLenShip(field, j, i);
                    if (!checkShip(field, lenShip, j, i)) {
                        return false;
                    }
                    if (lenShip > 4) {
                        return false;
                    } else {
                        shipsHas.put(lenShip, shipsHas.get(lenShip) + 1);
                        if (shipsHas.get(lenShip) > shipsAll.get(lenShip)) {
                            return false;
                        }
                    }
                }
            }
        }
        return shipsHas.equals(shipsAll);
    }

    private static int getLenShip(
            int[][] field,
            int xPos, int yPos) {

        int ship = 0;
        String direction = getDirection(field, xPos, yPos);

        for (int i = 0; i < field.length; i++) {
            try {
                switch (direction) {
                    case "right":
                        if (field[yPos][xPos + i] == 1) {
                            ship++;
                        } else {
                            i += field.length;
                        }
                        break;
                    case "down":
                        if (field[yPos + i][xPos] == 1) {
                            ship++;
                        } else {
                            i += field.length;
                        }
                        break;
                } // switch (direction)

            } catch (IndexOutOfBoundsException e) {

            }
        }
        return ship;
    }
    
        public static String getDirection(int[][] field, int xStart, int yStart) {
        String direction = "right";
        try {
            if (field[yStart][xStart + 1] == 0) {
                direction = "down";
            }
        } catch (IndexOutOfBoundsException ex) {
            direction = "down";
        }

        return direction;
    }

    public static boolean checkShip(int[][] field, int lenShip, int xStart, int yStart) {
        boolean result = true;
        String direction = getDirection(field, xStart, yStart);
        int stepBack = 0;
        int stepForward = 0;
        int borderShip = lenShip;
        int shipX = xStart;
        int shipY = yStart;

        switch (direction) {
            case "right":
                if (xStart > 0) {
                    xStart--;
                    stepBack = 1;
                    borderShip++;
                }
                if (xStart + borderShip < field.length - 1) {
                    stepForward = 1;
                    borderShip++;
                }
                break;
            case "down":
                if (yStart > 0) {
                    yStart--;
                    stepBack = 1;
                    borderShip++;
                }
                if (yStart + borderShip < field.length - 1) {
                    stepForward = 1;
                    borderShip++;
                }
                break;
        }

        for (int i = 0; i < borderShip; i++) {
            switch (direction) {
                case "right":
                    if (i == 0 && stepBack == 1 || i == borderShip && stepForward == 1) {
                        int centrValue = field[yStart][xStart + i];
                        if (centrValue == 1 || centrValue == 9) {
                            result = false;
                        }
                    }
                    if (yStart > 0) {
                        int topValue = field[yStart - 1][xStart + i];
                        if (topValue == 1 || topValue == 9) {
                            result = false;
                        }
                    }
                    if (yStart < field.length - 1) {
                        int downValue = field[yStart + 1][xStart + i];
                        if (downValue == 1 || downValue == 9) {
                            result = false;
                        }
                    }
                    break;
                case "down":
                    if (i == 0 && stepBack == 1 || i == borderShip && stepForward == 1) {
                        int midleValue = field[yStart + i][xStart];
                        if (midleValue == 1 || midleValue == 9) {
                            result = false;
                        }
                    }
                    if (xStart < field.length - 1) {
                        int rightValue = field[yStart + i][xStart + 1];
                        if (rightValue == 1 || rightValue == 9) {
                            result = false;
                        }
                    }
                    if (xStart > 0) {
                        int leftValue = field[yStart + i][xStart - 1];
                        if (leftValue == 1 || leftValue == 9) {
                            result = false;
                        }
                    }
                    break;
            } // switch (direction)

        }
        if(result) {
            markShip(field, shipX, shipY, lenShip, direction);
        }

        return result;
    }

    public static void markShip(int[][] field, int xStart, int yStart, int lenShip, String direction) {
        for (int i = 0; i < lenShip; i++) {
            switch (direction) {
                case "right":
                    field[yStart][xStart + i] = 9;
                    break;
                case "down":
                    field[yStart + i][xStart] = 9;
                    break;
            }
        }
    }

    public static void main(String[] args) {
        int[][] battleField = {
            {1, 0, 0, 0, 0, 1, 1, 0, 0, 0},
            {1, 0, 1, 0, 0, 0, 0, 0, 1, 0},
            {1, 0, 1, 0, 1, 1, 1, 0, 1, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1}};

        System.out.println("Result=" + BattleField.fieldValidator(battleField));
    }

    private static void printLab(int[][] maze) {
        int size = maze.length - 1;
        for (int i = 0; i <= size; i++) {
            System.out.println(Arrays.toString(maze[i]));
        }
        System.out.println("");
    }
}
