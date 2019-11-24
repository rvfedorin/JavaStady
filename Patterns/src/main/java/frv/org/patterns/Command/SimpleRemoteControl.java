/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.Command;

/**
 *
 * @author Wolf
 */
public class SimpleRemoteControl {
    Command slot;
    
    public void setCommand(Command command) {
        slot = command;
    }
    
    public void buttonWasPressed() {
        slot.execute();
    }
}
