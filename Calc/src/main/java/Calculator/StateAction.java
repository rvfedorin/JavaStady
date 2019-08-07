package Calculator;

public class StateAction extends State {
    void clear(Context context) {
        context.state = new StateX();
        context.state.clear(context);
    }

    void digit(Context context, char key) {
        context.state = new StateY();
        context.state.digit(context, key);
    }

    void arifm(Context context, char key) {
        context.o = key;
    }

    void equal(Context context) {
        context.y = context.x;
        context.state = new StateAnswer();
        context.state.equal(context);
    }
}
