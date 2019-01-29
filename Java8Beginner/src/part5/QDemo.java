package part5;

public class QDemo {
    public static void main(String[] args) {
        Queue q = new Queue(4);

        q.put(1);

        for (int i = 0; i < 6; i++) {
            q.put((char) ('A' + i));
        }
        for (int i = 0; i < 6; i++) {
            System.out.println(q.get());
        }

    }
} // close class QDemo

class Queue {
    private Object[] q;
    private int putloc, getloc;

    Queue(int size) {
        q = new Object[size + 1];
        putloc = getloc = 0;
    } // close constructor

    public void put (Object ch) {
        if (q.length -1 == putloc) {
            System.out.println("Очередь заполнена.");
            return;
        }

        putloc ++;
        q[putloc] = ch;

    } // close put()

    public Object get() {
        if (putloc == getloc) {
            System.out.println("Очередь пуста.");
            return (char) 0;
        }

        getloc ++;
        return q[getloc];
    }

} // close class Queue
