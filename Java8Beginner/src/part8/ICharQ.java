package part8;

public interface ICharQ {
    void put(char ch);
    char get();

    default void reset() {}
}
