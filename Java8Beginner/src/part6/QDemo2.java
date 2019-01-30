package part6;

public class QDemo2 {
    public static void main(String[] args) {
        Queue q = new Queue(4);
        Queue q2 = new Queue(q);

        q.put(1);

        for (int i = 0; i < 6; i++) {
            q.put((char) ('A' + i));
        }

        for (int i = 0; i < 6; i++) {
            System.out.println(q.get());
        }

        System.out.println("=================");
        for (int i = 0; i < 4; i++) {
            q2.put((char) ('A' + i));
            System.out.println(q2.get());
        }

        System.out.println("=================");
        char[] l = {'A', 'B'};
        Queue q3 = new Queue(l);
        for (int i = 0; i < 4; i++) {
            System.out.println(q3.get());
        }

    }
}

class Queue {
    private Object[] q;
    private int putloc, getloc;

    Queue(int size) {
        q = new Object[size + 1];
        putloc = getloc = 0;
    } // close constructor

    Queue(Queue queue) {
        putloc = queue.putloc;
        getloc = queue.getloc;

        q = new Object[queue.q.length];

        for( int i = getloc+1; i <= putloc; i ++) {
            q[i] = queue.q[i];
        }
    } // close constructor

    Queue(char[] l) {
        putloc = 0;
        getloc = 0;
        q = new Object[l.length+1];

        for( int i = 0; i < l.length; i ++) {
            put(l[i]);
        }
    } // close constructor

    public void put (Object ch) {
        if (q.length -1 == putloc) {
            System.out.println("Очередь заполнена.");
            return;
        }

        putloc ++;
        q[putloc] = ch;

    } // close put()

    Object get() {
        if (putloc == getloc) {
            System.out.print("Очередь пуста.");
            return (char) 0;
        }

        getloc ++;
        return q[getloc];
    }

} // close class Queue