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
            mazeArray[lineIndex++] = line.toCharArray();
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

//public class Finder {
//  
//  private static class Pos {
//    final int x, y;
//    Pos(int x, int y) { this.x = x; this.y = y; } 
//    Pos[] neighbours() { return new Pos[]{ new Pos(x-1,y), new Pos(x+1,y), new Pos(x,y-1), new Pos(x,y+1) }; }
//    boolean onPath(char[][]g) { return x >= 0 && x < g[0].length && y >= 0 && y < g.length && g[y][x] == '.'; }    
//  }
//
//  static boolean pathFinder(String maze) {
//    final String rows[] = maze.split("\n");
//    final char[][] grid = new char[rows.length][];
//    for (int i = 0; i < rows.length; i++) grid[i] = rows[i].toCharArray();
//    return findExit(new Pos(0,0), grid);
//  }
//  
//  private static boolean findExit(Pos p, char[][]g) {        
//    if (p.x == g.length-1 && p.x == p.y) return true; // We made it to the exit!    
//    if (!p.onPath(g)) return false;
//    g[p.y][p.x] = 'B';  // drop a breadcrumb
//    final Pos[] n = p.neighbours();
//    return findExit(n[0],g) | findExit(n[1],g) | findExit(n[2],g) | findExit(n[3],g);
//  }
//
//}
//==========================================================================================================
//import java.util.*;
//import java.util.stream.*;
//import java.awt.Point;
//
//
//public class Finder {
//    
//    final private static List<Point> MOVES = Arrays.asList(new Point(1,0), new Point(0,1), new Point(0,-1), new Point(-1,0));
//    
//    
//    static boolean pathFinder(String maze) {
//        
//        int S = (int) Math.sqrt(maze.length()) - 1;
//        if (S == 0) return true;
//            
//        final Set<Point> bag  = new HashSet<>();
//        int x = -1;
//        for (String line: maze.split("\n")) { x++;
//            for (int y=0 ; y < line.length() ; y++) 
//                if (line.charAt(y)=='.') bag.add(new Point(x,y));
//        }
//        bag.remove(new Point(0,0));
//        
//        final Point     end    = new Point(S,S);
//        final boolean[] hasEnd = {false};
//        Set<Point>      look   = new HashSet<>(Arrays.asList(new Point(0,0)));
//        
//        while (!look.isEmpty()) {
//            if (hasEnd[0]) return true;
//            look = look.stream()
//                       .flatMap( p -> MOVES.stream().map( d -> new Point(p.x+d.x, p.y+d.y) ))
//                       .distinct()
//                       .filter(  p -> { if (p.equals(end)) hasEnd[0] = true;
//                                        if (bag.contains(p)) {
//                                            bag.remove(p);
//                                            return true;
//                                        } else return false; })
//                      .collect(Collectors.toSet());
//        }
//        return false;
//    }
//}
