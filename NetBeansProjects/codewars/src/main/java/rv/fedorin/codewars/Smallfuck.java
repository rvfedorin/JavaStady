/*
 * Proprietary software.
 * 
 */
package rv.fedorin.codewars;

import java.util.Stack;

/**
 *
 * @author R. V. Fedorin
 */
public class Smallfuck {

    public static String interpreter(String code, String tape) {
        String[] dataByBits = tape.split("");
        String[] commandsByBits = code.split("");
        Stack<Integer> returnPositions = new Stack<>();
        int posData = 0;
        int posCommand = 0;

        while (true) {
            try {
                String toDo = commandsByBits[posCommand];
                switch (toDo) {
                    case ">":
                        posData++;
                        break;
                    case "<":
                        posData--;
                        break;
                    case "*":
                        int bit = Integer.parseInt(dataByBits[posData]);
                        dataByBits[posData] = String.valueOf(Math.abs(bit - 1));
                        break;
                    case "[":
                        if (!dataByBits[posData].equals("0")) {
                            if (returnPositions.isEmpty() || returnPositions.peek() != posCommand) {
                                returnPositions.push(posCommand);
                            }
                        } else {
                            int endC = 1;
                            while (endC > 0) {
                                String next = commandsByBits[++posCommand];
                                if (next.contains("]")) {
                                    endC--;
                                } else if (next.contains("[")) {
                                    endC++;
                                }
                            }
                        }
                        break;
                    case "]":
                        if (!dataByBits[posData].equals("0")) {
                            posCommand = returnPositions.peek() - 1;
                        } else {
                            returnPositions.pop();
                        }
                        break;
                }
                posCommand++;
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        return String.join("", dataByBits);
    }

//> - Move pointer to the right (by 1 cell)
//< - Move pointer to the left (by 1 cell)
//* - Flip the bit at the current cell
//[ - Jump past matching ] if value at current cell is 0
//] - Jump back to matching [ (if value at current cell is nonzero)
    public static void main(String[] args) {
        System.out.println(Smallfuck.interpreter("*>*>*>*>*>*>*>*>*>*>*>*>*>*>*>*>*>*>", "00000000000000000000000000"));
        System.out.println(Smallfuck.interpreter("[[]*>*>*>]", "000"));
        System.out.println(Smallfuck.interpreter("[*>[>*>]>]", "11001"));
        System.out.println(Smallfuck.interpreter("[>[*>*>*>]>]", "10110"));
        System.out.println(Smallfuck.interpreter("*>*>>*>>>*>*", "00101100"));
        System.out.println(Smallfuck.interpreter("*[>*]", "00000000000000000000000000000000000000000000000000000000000000000000000000"));
        System.out.println(Smallfuck.interpreter("*[>*]", "00001000000000000000000000000000000000000000000000000000000000000000000000"));
        System.out.println(Smallfuck.interpreter("[>*]", "000000000000000000000000000000000000000000000000000000000000000000000000000"));
        System.out.println(Smallfuck.interpreter(("<<<*>*>*>*>*>>>*>>>>>*>*"), "0000000000000000"));
    }

//11111111111111111100000000
//000
//01100
//10101
//11111111
//11111111111111111111111111111111111111111111111111111111111111111111111111
//11110000000000000000000000000000000000000000000000000000000000000000000000
//000000000000000000000000000000000000000000000000000000000000000000000000000
//0000000000000000
}
