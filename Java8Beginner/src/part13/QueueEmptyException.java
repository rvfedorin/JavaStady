package part13;

public class QueueEmptyException extends Exception {
    @Override
    public String toString() {
        return "Очередь пуста.";
    }
}
