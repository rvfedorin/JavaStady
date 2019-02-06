package part6;

import java.util.Arrays;

public class StackTest {
    public static void main(String[] args) {
        Stack stack = new Stack(5);

        for(int i=1; i<8; i++) {
            stack.push(i);
        }
        System.out.println(stack);

        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println("=========");

        for(int i=1; i<8; i++) {
            System.out.println(stack.pop());
        }

    } // close main()
} // close class StackTest

class Stack {
    private int currentPos;
    private int getPos;
    private int[] stack;

    Stack(int count) {
        stack = new int[count];
        currentPos = 0;
        getPos = 0;
    }

    public void push(int newElement) {
        if (currentPos >= stack.length) {
            System.out.println("stack is full");
            return;
        }

        stack[currentPos] = newElement;
        getPos = currentPos;
        currentPos ++;
    }

    public Object pop() {
        if (getPos <= 0) {
            System.out.print("stack is empty");
            return (char) 0;
        }
        int temp = stack[getPos];
        getPos --;
        currentPos --;
        return temp;
    }

    @Override
    public String toString() {
        int[] temp = new int[currentPos];
        for(int i=0; i<=getPos; i++) {
            temp[i] = stack[i];
        }
        return Arrays.toString(temp);
    }
}
