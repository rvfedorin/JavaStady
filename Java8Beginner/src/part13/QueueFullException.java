package part13;

public class QueueFullException extends Exception {
    int size;

    QueueFullException(int s) {
        size = s;
    }

    @Override
    public String toString() {
        return "Очередь заполнена. Максимальный размер очереди " + size;
    }
}
