package Calculator;

public class StateX extends State {
    boolean typing = false;

    void clear(Context context) {
        context.x = 0;
        context.y = 0;
        context.o = '+';
    }

    void digit(Context context, char key) {
        if(!typing) {
            context.x = 0;
            typing = true;
        }
        context.x = context.x * 10 + Character.getNumericValue(key);
    }

    void arifm(Context context, char key) {
        context.state = new StateAction();
        context.state.arifm(context, key);
    }

    void equal(Context context) {
        context.state = new StateAnswer();
        context.state.equal(context);
    }
}
