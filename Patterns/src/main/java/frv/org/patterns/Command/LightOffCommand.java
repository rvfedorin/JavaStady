/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.Command;

/**
 *
 * @author Wolf
 */
public class LightOffCommand implements Command {
    Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute() {
        light.off();
    }
    
    
    
}
