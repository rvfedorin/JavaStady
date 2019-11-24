/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.Command;

/**
 *
 * @author Wolf
 */
public class LightOnCommand implements Command {
    Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.on();
    }
    
    
    
}
