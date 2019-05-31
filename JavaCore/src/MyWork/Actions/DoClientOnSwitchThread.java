package MyWork.Actions;

import MyWork.CurrentlyRunningFrame;
import MyWork.NodesClass.Customer;
import MyWork.NodesClass.Switch;

import java.util.concurrent.ConcurrentHashMap;

import static MyWork.Config.*;
import static MyWork.Config.DELETE_S;
import static MyWork.Config.LINE;

public class DoClientOnSwitchThread implements Runnable {
    private Customer aCustomer;
    private Switch aSwitch;
    private boolean correct = true;
    private String aToDo;
    private CurrentlyRunningFrame runningFrame;
    private ConcurrentHashMap<String, String> resultMap;

    public DoClientOnSwitchThread(String dataSwitch,
                           boolean root,
                           Customer customer,
                           String toDo,
                           CurrentlyRunningFrame fr,
                           ConcurrentHashMap<String, String> resultMap,
                           String enterPass) {
        this.aCustomer = customer;
        this.aToDo = toDo; // delete or create CREATE_S or DELETE_S
        this.runningFrame = fr;
        this.resultMap = resultMap;

        String[] connection = dataSwitch.split(SEPARATOR_PORT);
        if (connection.length == 3 && correct) {
            String upPort = connection[0];
            String ipSw = connection[1];
            String downPort = connection[2];
            aSwitch = new Switch(ipSw, upPort, downPort, root, enterPass);
        }

    } // ** constructor

    @Override
    public void run() {
        int idLineOnCurrentProcess = -1;
//        super.run();

        if (correct) {
            Thread.currentThread().setName("Do on " + aSwitch.getIp());
            String message = aToDo + " на свитче " + aSwitch.getIp();
            idLineOnCurrentProcess = runningFrame.addLine(message, Thread.currentThread());

            if (aToDo.equals(CREATE_S)) { // <-------------------- CREATE SECTION
                String result = aSwitch.createClient(aCustomer);
                if (result.contains(SUCCESS_S))
                    result = "Success " + aSwitch.getIp() + " " + CREATE_S + " " + result + LINE;
                else
                    result = "[Error] DoClientOnSwitchThread->run->createClient \n" +
                            "[Error] " + aSwitch.getIp() + " " + CREATE_S + " " + result + LINE;

                resultMap.put(aSwitch.getIp(), result);

            } else if (aToDo.contains(DELETE_S)) { // <-------------------- DELETE SECTION
                String result = aSwitch.deleteClient(aCustomer);
                if (result.contains(SUCCESS_S))
                    result = "Success " + aSwitch.getIp() + " " + DELETE_S + " " + result + LINE;
                else
                    result = "[Error] DoClientOnSwitchThread->run->createClient \n" +
                            "[Error] " + aSwitch.getIp() + " " + DELETE_S + " " + result + LINE;
                resultMap.put(aSwitch.getIp(), result);
            }
        } // ** if correct

        if (idLineOnCurrentProcess > 0)
            runningFrame.removeLine(idLineOnCurrentProcess);

    } // ** run()
} // ** class DoClientOnSwitchThread
