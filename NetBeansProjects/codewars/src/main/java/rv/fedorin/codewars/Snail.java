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
public class Snail {

    public static int[] snail(int[][] array) {
        int len = array.length;
        if (len == 1) {
            if (array[0].length == 0) {
                return new int[0];
            } else {
                return new int[]{array[0][0]};
            }
        } else if (len == 2) {
            return new int[]{array[0][0], array[0][1], array[1][1], array[1][0]};
        }

        int[] result = new int[len * len];

        boolean doWork = true;

        boolean ifCanGo;
        int indexToPut = 0;
        int endPosition = len - 1;
        int startX = 0;
        int startY = 0;
        int endX = endPosition;
        int endY = endPosition;
        int row = 0;
        int col = 0;
        String to = "east";

        while (doWork) {
            switch (to) {
                case "east":
                    ifCanGo = canGo(array, row, col + 1, startX, startY, endX, endY);
                    if (!ifCanGo) {
                        to = "south";
                        startY++;
                    } else {
                        result[indexToPut] = array[row][col];
                        indexToPut++;
                        col++;
                    }
                    break;
                case "north":
                    ifCanGo = canGo(array, row - 1, col, startX, startY, endX, endY);
                    if (!ifCanGo) {
                        to = "east";
                        startX++;
                    } else {
                        result[indexToPut] = array[row][col];
                        indexToPut++;
                        row--;
                    }
                    break;
                case "west":
                    ifCanGo = canGo(array, row, col - 1, startX, startY, endX, endY);
                    if (!ifCanGo) {
                        to = "north";
                        endY--;
                    } else {
                        result[indexToPut] = array[row][col];
                        indexToPut++;
                        col--;
                    }
                    break;
                case "south":
                    ifCanGo = canGo(array, row + 1, col, startX, startY, endX, endY);
                    if (!ifCanGo) {
                        to = "west";
                        endX--;
                    } else {
                        result[indexToPut] = array[row][col];
                        indexToPut++;
                        row++;
                    }
                    break;
            } // switch
            if (indexToPut > len * len - 1 || endX == 0) {
                doWork = false;
                result[indexToPut] = array[row][col];
            }
        } // while do work
        return result;
    }

    public static boolean canGo(int[][] maze, int aRow, int aCol, int startX, int startY, int endX, int endY) {
        return aCol <= endX 
                && aCol >= startX 
                && aRow >= startY 
                && aRow <= endY;
    }

    public static void main(String[] args) {
        int[][] array4
                = {{1, 2, 3, 33},
                {4, 5, 6, 66},
                {7, 8, 9, 99},
                {95, 96, 97, 98}};
        
        int[][] array3
                = {{1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};
        
        int[][] array2
                = {{1, 2},
                {4, 5}};

        System.out.println(Arrays.toString(Snail.snail(array2)));
    }

}
