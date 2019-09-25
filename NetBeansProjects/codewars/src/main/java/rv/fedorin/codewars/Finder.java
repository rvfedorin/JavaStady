/*
 * Proprietary software.
 * 
 */
package rv.fedorin.codewars;

import java.util.ArrayList;

/**
 *
 * @author R. V. Fedorin
 */
public class Finder {

    static boolean pathFinder(String maze) {
        if(maze.startsWith("W")) return false;
        if(!maze.contains("W")) return true;
        
        ArrayList<int[]> cross = new ArrayList<>();
        char[][] mazeArr = buildMaze(maze);

        int x = 0;
        int y = 0;
        int weight = 0;
        mazeArr[y][x] = (char)(weight+'0') ;
        
        while (true) {
            if (isFree(x + 1, y, mazeArr)) {
                int tempWeight = weight + 1;
                mazeArr[y][x + 1] = (char)(tempWeight+'0');
                cross.add(new int[]{y, x + 1});
            }
            if (isFree(x - 1, y, mazeArr)) {
                int tempWeight = weight + 1;
                mazeArr[y][x - 1] = (char)(tempWeight+'0');
                cross.add(new int[]{y, x - 1});
            }
            if (isFree(x, y + 1, mazeArr)) {
                int tempWeight = weight + 1;
                mazeArr[y + 1][x] = (char)(tempWeight+'0');
                cross.add(new int[]{y+1, x});
            }
            if (isFree(x, y - 1, mazeArr)) {
                int tempWeight = weight + 1;
                mazeArr[y - 1][x] = (char)(tempWeight+'0');
                cross.add(new int[]{y-1, x});
            }

            if (cross.size() > 0) {
                x = cross.get(cross.size() - 1)[1];
                y = cross.get(cross.size() - 1)[0];
                weight = Integer.valueOf(mazeArr[y][x]);
                cross.remove(cross.size() - 1);
            } else {
                char lastSimb = mazeArr[mazeArr.length - 1][mazeArr[0].length - 1];
                return !(lastSimb == ".".charAt(0) || lastSimb == "W".charAt(0));
            }
        }
    }

    public static boolean isFree(int x, int y, char[][] arr) {
        if (x < 0 || y < 0) {
            return false;
        }

        int maxX = arr[0].length - 1;
        int maxY = arr.length - 1;

        if (x > maxX || y > maxY) {
            return false;
        }
        return arr[y][x] == ".".charAt(0);
    }

    public static char[][] buildMaze(String maze) {
        char[][] mazeArray = new char[maze.split("\n").length][];

        int lineIndex = 0;
        for (String line : maze.split("\n")) {
            mazeArray[lineIndex] = new char[line.length()];
            int colIndex = 0;
            for (String step : line.split("")) {
                mazeArray[lineIndex][colIndex++] = step.charAt(0);
            }
            lineIndex++;
        }
        return mazeArray;
    }

    public static void main(String[] args) {
        String a = ".W.\n"
                 + ".W.\n"
                 + "...",
                
                b = ".W.\n"
                  + ".W.\n"
                  + "W..",
                
                c = "......\n"
                + "......\n"
                + "......\n"
                + "......\n"
                + "......\n"
                + "......",
                
                d = "......\n"
                + "......\n"
                + "......\n"
                + "......\n"
                + ".....W\n"
                + "....W.",
                
                e = "W.....\n"
                + "......\n"
                + "W.W.W.\n"
                + "......\n"
                + ".....W\n"
                + "......",
                
                f = ".W.W..\n"
                + "...W..\n"
                + "W.W.W.\n"
                + "......\n"
                + "WW.W.W\n"
                + "......",
                
                g = ".W....\n"
                + "...W..\n"
                + "WWWWW.\n"
                + "......\n"
                + "WWWWW.\n"
                + "......";

        System.out.println("Reuslt: " + pathFinder(b));
    }

    static void printLab(String[][] lab) {
        System.out.println("====================================");
        for (String[] line : lab) {
            for (String col : line) {
                System.out.print(col + " ");
            }
            System.out.println("");
        }
        System.out.println("====================================");
    }

}
