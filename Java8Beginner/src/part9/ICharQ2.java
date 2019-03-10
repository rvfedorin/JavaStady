package part9;

public interface ICharQ2 {
    void put(char ch) throws QueueFullException;
    char get() throws QueueEmptyException;

    default void reset() {}
}
