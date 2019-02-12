package part8;

public class IQDemo {
    public static void main(String[] args) {
        FixedQueue fq = new FixedQueue(2);
        System.out.println("It is the first get " + fq.get());
        fq.put('a');
        fq.put('b');
        fq.put('c');
        System.out.println("It is the END added ");
        System.out.println(fq.get());
        System.out.println(fq.get());
        System.out.println(fq.get());

        printLine();

        CircularQueue cq = new CircularQueue(2);
        System.out.println(cq.get());
        cq.put('a');
        cq.put('b');
        cq.put('c');
        System.out.println(cq.get());
        System.out.println(cq.get());
        System.out.println(cq.get());

        printLine();

    } // main()

    private static void printLine() {
        for (int i = 0; i < 40; i++) {
            System.out.print("=");
        }
        System.out.println();
    }

} // IQDemo

class FixedQueue implements ICharQ {
    private char[] queue;
    private int position;

    FixedQueue(int size) {
        queue = new char[size];
        position = 0;
    }

    @Override
    public void put(char ch) {
        if (position < queue.length) {
            queue[position] = ch;
            position += 1;
        } else {
            System.out.println("The queue is full.");
        }
    }

    @Override
    public char get() {
        if (position-1 >= 0) {
            position -= 1;
            return queue[position];
        }
        return (char) 0;
    }
} // class FixedQueue

class CircularQueue implements ICharQ {
    private char[] queue;
    private int putloc, getloc;
    private boolean notEmpty, endGet;

    CircularQueue(int size) {
        queue = new char[size];
        putloc = getloc = 0;
        notEmpty = endGet = false;
    }

    @Override
    public void put(char ch) {
        if (putloc < queue.length) {
            queue[putloc] = ch;
            putloc += 1;
            if (notEmpty) getloc += 1;
            notEmpty = true;
        } else {
            putloc = getloc = 0;
            queue[putloc] = ch;
        }
    }

    @Override
    public char get() {
        char res;
        if (getloc > 0 && notEmpty) {
            putloc -= 1;
            res = queue[getloc];
            getloc -= 1;
        } else if(getloc == 0 && notEmpty) {
            putloc = 0;
            if (!endGet) {
                res = queue[getloc];
                endGet = true;
                getloc = queue.length - 1;
            } else {
                notEmpty = false;
                getloc = 0;
                res = (char) 0;
                System.out.print("Empty");
            }
        } else {
            System.out.print("Empty");
            res = (char) 0;
        }
        return res;
    }
} // class CircularQueue
