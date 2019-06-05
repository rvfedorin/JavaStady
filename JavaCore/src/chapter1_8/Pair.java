package chapter1_8;

public class Pair<T> {
    private T first;
    private T second;

    public Pair(T f, T s) {
        setFirst(f);
        setSecond(s);
    }

    public Pair() {
        setFirst(null);
        setSecond(null);
    }

    public void setFirst(T toSet) {
        first = toSet;
    }

    public void setSecond(T toSet) {
        second = toSet;
    }

    public T getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }
}
