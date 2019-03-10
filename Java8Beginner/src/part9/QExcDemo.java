package part9;

public class QExcDemo {
    public static void main(String[] args) {
        FixedQueue fq = new FixedQueue(2);
        try {
            System.out.println("It is the first get " + fq.get());
        } catch (QueueEmptyException ex) {
            System.out.println(ex);
        }

        try {
            fq.put('a');
            fq.put('b');
            fq.put('c');
            System.out.println("It is the END added ");

        } catch (QueueFullException ex) {
            System.out.println(ex);
        }

        try {
            System.out.println(fq.get());
            System.out.println(fq.get());
            System.out.println(fq.get());
        } catch (QueueEmptyException ex) {
            System.out.println(ex);
        }

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

        DynQueue dq = new DynQueue();
        System.out.println(dq.get());
        dq.put('a');
        dq.put('b');
        dq.put('c');
        System.out.println(dq.get());
        System.out.println(dq.get());
        System.out.println(dq.get());

    } // main()

    private static void printLine() {
        for (int i = 0; i < 40; i++) {
            System.out.print("=");
        }
        System.out.println();
    }
}


class FixedQueue implements ICharQ2 {
    private char[] queue;
    private int position;

    FixedQueue(int size) {
        queue = new char[size];
        position = 0;
    }

    @Override
    public void put(char ch) throws QueueFullException{
        if (position < queue.length) {
            queue[position] = ch;
            position += 1;
        } else {
            throw new QueueFullException(queue.length);
        }
    }

    @Override
    public char get() throws QueueEmptyException{
        if (position-1 >= 0) {
            position -= 1;
            return queue[position];
        }
        throw new QueueEmptyException();
    }
} // class FixedQueue

class CircularQueue implements ICharQ2 {
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

class DynQueue implements ICharQ2 {
    private char[] queue;
    private int position;

    DynQueue() {
        queue = new char[2];
        position = 0;
    }

    @Override
    public void put(char ch) {
        if (position >= queue.length) {
            char[] t = new char[queue.length * 2];
            for (int i = 0; i < queue.length; i++) {
                t[i] = queue[i];
            }
            queue = t;
        }
        queue[position] = ch;
        position += 1;
    }

    @Override
    public char get() {
        if (position-1 >= 0) {
            position -= 1;
            return queue[position];
        }
        System.out.print("Empty");
        return (char) 0;
    }
} // class DynQueue

class QueueFullException extends Exception {
    int size;

    QueueFullException(int s) {
        size = s;
    }

    @Override
    public String toString(){
        return "Очередь заполнена. Размер очереди = " + size;
    }
}

class QueueEmptyException extends Exception {
    @Override
    public String toString(){
        return "Очередь пуста.";
    }
}
