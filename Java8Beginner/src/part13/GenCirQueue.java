package part13;

public class GenCirQueue<T> implements IGenQ<T> {
    private T[] queue;
    private int putloc, getloc;
    private boolean afterLoop;

    GenCirQueue(T[] q) {
        queue = q;
        putloc = getloc = 0;
        afterLoop = false;
    }

    @Override
    public void put(T ch) {
        if (putloc > queue.length - 1) {
            putloc = 0;
            getloc = 1;
            afterLoop = true;
        } else if (afterLoop) {
            getloc++;
        }
//        System.out.println("put " + (putloc) + " get " + getloc + " ch " +ch);
        queue[putloc++] = ch;
    }

    @Override
    public T get() throws QueueEmptyException {
        if (getloc > queue.length - 1) {
            getloc = 0;
            afterLoop = false;
        }
        if (getloc == 0 && putloc == 0 && !afterLoop) throw new QueueEmptyException();
        if (queue[getloc] == null) throw new QueueEmptyException();
        return queue[getloc++];
    }

    public int length() {
        return queue.length;
    }

    public static void main(String[] args) {

        GenCirQueue<Integer> queue = new GenCirQueue<>(new Integer[10]);

        for (int o = 15; o < 1000; o++) {

            for (int i = 0; i < o; i++) {
                queue.put(i);
            }
            for (int j = 0; j < queue.length(); j++) {
                try {
                    Integer q = queue.get();
                    System.out.println(q);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("========================");
        }
    }
}


