package part6;

import java.util.Arrays;

public class StackTest {
    public static void main(String[] args) {
        Stack stack = new Stack(5);

        for(int i=1; i<8; i++) {
            try {
                stack.push(i);
            } catch (StackFullExc ex) {
                System.out.println(ex);
            }

        }
        System.out.println(stack);
        try {
            System.out.println(stack.pop());
            System.out.println(stack.pop());
        } catch (StackEmptyExc ex) {
            System.out.println(ex);
        }

        System.out.println("=========");

        for(int i=1; i<8; i++) {
            try {
                System.out.println(stack.pop());
            } catch (StackEmptyExc ex) {
                System.out.println(ex);
            }
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

    public void push(int newElement) throws StackFullExc{
        if (currentPos >= stack.length) {
            throw new StackFullExc();
        }

        stack[currentPos] = newElement;
        getPos = currentPos;
        currentPos ++;
    }

    public Object pop() throws StackEmptyExc{
        if (getPos <= 0) {
            throw new StackEmptyExc();
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

class StackFullExc extends Exception {
    @Override
    public String toString(){
        return "Стэк заполнен.";
    }
}

class StackEmptyExc extends Exception {
    @Override
    public String toString(){
        return "Стэк пуст.";
    }
}
