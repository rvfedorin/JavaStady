package Calculator;

public abstract class State {
    abstract void clear(Context context);
    abstract void digit(Context context, char key);
    abstract void arifm(Context context, char key);
    abstract void equal(Context context);
}
