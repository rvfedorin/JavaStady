package part13;

public interface IGenQ<T> {

    public void put(T ch) throws QueueFullException;
    public T get() throws QueueEmptyException;
}
