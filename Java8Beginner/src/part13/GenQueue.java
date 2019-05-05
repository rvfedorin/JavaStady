package part13;

public class GenQueue<T> implements IGenQ<T> {
    private T[] q;
    int putloc, getloc;

    GenQueue(T[] aRef) {
        q = aRef;
        putloc = getloc = 0;
    }

    @Override
    public void put(T ch) throws QueueFullException {
        if(putloc == q.length - 1) throw new QueueFullException(q.length);
        q[putloc++] = ch;
    }

    @Override
    public T get() throws QueueEmptyException {
        if(getloc == putloc) throw new QueueEmptyException();

        return q[getloc++];
    }
}
