/*
 * Proprietary software.
 * 
 */
package rv.fedorin.codewars;

import java.util.Arrays;

/**
 *
 * @author R. V. Fedorin
 */
public class Spiralizor {

    private static String to = "east";
    private static int col = 0;
    private static int row = 0;
    private static int seen = 0;

    public static int[][] spiralize(int size) {
        int[][] maze = new int[size][size];
        for (int i = 0; i < size; i++) {
            int[] temp = new int[size];
            Arrays.fill(temp, 0);
            maze[i] = temp;
        }

        boolean doWork = true;

        int closed = 0;
        int endPosition = maze.length - 1;

        while (doWork) {
            switch (to) {
                case "east":
                    closed = getNextPosition(maze, row, col + 1, endPosition);
                    if (!setVal(maze, closed)) {
                        to = "south";
                        seen++;
                    } else {
                        col++;
                        seen = 0;
                    }
                    break;
                case "north":
                    closed = getNextPosition(maze, row - 1, col, endPosition);
                    if (!setVal(maze, closed)) {
                        to = "east";
                        seen++;
                    } else {
                        row--;
                        seen = 0;
                    }
                    break;
                case "west":
                    closed = getNextPosition(maze, row, col - 1, endPosition);
                    if (!setVal(maze, closed)) {
                        to = "north";
                        seen++;
                    } else {
                        col--;
                        seen = 0;
                    }
                    break;
                case "south":
                    closed = getNextPosition(maze, row + 1, col, endPosition);
                    if (!setVal(maze, closed)) {
                        to = "west";
                        seen++;
                    } else {
                        row++;
                        seen = 0;
                    }
                    break;
            } // switch
            if (seen > 2) {
                if (maze.length % 2 == 0) {
                    stepBack();
                    stepBack();
                    stepBack();
                    maze[row][col] = 0;
                }
                to = "east";
                col = 0;
                row = 0;
                seen = 0;
                doWork = false;
            }
//            System.out.println("row " + row + " col " + col + " closed " + closed);
//            printLab(maze);

        }
        return maze;
    }

    public static boolean setVal(int[][] maze, int closed) {
        if (col > maze.length - 1 || row > maze.length - 1 || row < 0 || col < 0) {
            stepBack();
            return false;
        }

        switch (closed) {
            case 2:
                // if out of border
                maze[row][col] = 1;
                break;
            case 1:
                stepBack();
                break;
            case 0:
                maze[row][col] = 1;
                return true;
        }
        return false;
    }

    public static int getNextPosition(int[][] maze, int aRow, int aCol, int endPosition) {
        int result;
        if (aCol <= endPosition && aCol >= 0 && aRow >= 0 && aRow <= endPosition) {
            result = maze[aRow][aCol];
        } else {
            result = 2;
        }
        return result;
    }

    private static void stepBack() {
        seen++;
        switch (to) {
            case "east":
                col--;
                to = "south";
                break;
            case "north":
                row++;
                to = "east";
                break;
            case "west":
                col++;
                to = "north";
                break;
            case "south":
                row--;
                to = "west";
                break;
        } // switch}
    }

    public static void main(String[] args) {
        int size = 5;
        printLab(Spiralizor.spiralize(size));
    }

    private static void printLab(int[][] maze) {
        int size = maze.length - 1;
        for (int i = 0; i <= size; i++) {
            System.out.println(Arrays.toString(maze[i]));
        }
        System.out.println("");
    }

}
