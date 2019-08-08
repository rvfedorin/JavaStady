package Calculator;

public class StateY extends State {
    boolean typing = false;

    void clear(Context context) {
        context.state = new StateX();
        context.state.clear(context);
    }

    void digit(Context context, char key) {
        if(!typing) {
            context.y = 0;
            typing = true;
        }
        context.y = context.y * 10 + Character.getNumericValue(key);
    }

    void arifm(Context context, char key) {
        equal(context);
        context.state.arifm(context, key);
    }

    void equal(Context context) {
        context.state = new StateAnswer();
        context.state.equal(context);
    }
}
