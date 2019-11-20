/*
 * Created by Roman V. Fedorin
 */
package frv.org.patterns.Command;

/**
 *
 * @author Wolf
 */
public class RemoteControlTest {

    public static void main(String[] args) {
        SimpleRemoteControl simpleRemoteControl = new SimpleRemoteControl();

        Light light = new Light();
        LightOnCommand lightOnCommand = new LightOnCommand(light);
        LightOffCommand lightOffCommand = new LightOffCommand(light);

        GarageDoor garageDoor = new GarageDoor();
        GarageDoorOpenCommand garageDOCom = new GarageDoorOpenCommand(garageDoor);
        GarageDoorCloseCommand garageDCCom = new GarageDoorCloseCommand(garageDoor);

        simpleRemoteControl.setCommand(lightOnCommand);
        simpleRemoteControl.buttonWasPressed();
        simpleRemoteControl.setCommand(garageDOCom);
        simpleRemoteControl.buttonWasPressed();

        RemoteControl rc = new RemoteControl();
        rc.setCommand(0, garageDOCom, garageDCCom);
        rc.setCommand(1, lightOnCommand, lightOffCommand);
        System.out.println(rc);
    }
}
