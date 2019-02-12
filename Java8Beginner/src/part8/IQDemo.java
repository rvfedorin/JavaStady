package part8;

public class IQDemo {
    public static void main(String[] args) {
        FixedQueue fq = new FixedQueue(2);
        fq.put('a');
        fq.put('d');
        fq.put('e');
        System.out.println(fq.get());
        System.out.println(fq.get());
        System.out.println(fq.get());

    } // main()
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
}
