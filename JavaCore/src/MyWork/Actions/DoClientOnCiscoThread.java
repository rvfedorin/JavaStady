package MyWork.Actions;

import MyWork.CurrentlyRunningFrame;
import MyWork.NodesClass.Cisco;
import MyWork.NodesClass.Customer;
import MyWork.NodesClass.Switch;

import java.util.concurrent.ConcurrentHashMap;

import static MyWork.Config.*;
import static MyWork.Config.DELETE_S;
import static MyWork.Config.LINE;


public class DoClientOnCiscoThread implements Runnable {
    private Customer aCustomer;
    private Cisco cisco;
    private boolean correct = true;
    private String aToDo;
    private CurrentlyRunningFrame runningFrame;
    private ConcurrentHashMap<String, String> resultMap;

    public DoClientOnCiscoThread(String dataSwitch,
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
            String ipCisco = connection[1];
            cisco = new Cisco(ipCisco, enterPass.toCharArray());
        }

    } // ** constructor

    @Override
    public void run() {
        int idLineOnCurrentProcess = -1;
//        super.run();

        if (correct) {
            Thread.currentThread().setName("Do on " + cisco.getIP());
            String message = aToDo + " на cisco " + cisco.getIP();
            idLineOnCurrentProcess = runningFrame.addLine(message, Thread.currentThread());

            if (aToDo.equals(CREATE_S)) { // <-------------------- CREATE SECTION
                String result = cisco.createClient(aCustomer);
                if (result.contains(SUCCESS_S))
                    result = "Success " + cisco.getIP() + " " + CREATE_S + " " + result + LINE;
                else
                    result = "[Error] DoClientOnCiscoThread->run->createClient \n" +
                            "[Error] " + cisco.getIP() + " " + CREATE_S + " " + result + LINE;

                resultMap.put(cisco.getIP(), result);

            } else if (aToDo.contains(DELETE_S)) { // <-------------------- DELETE SECTION
                String result = cisco.deleteClient(aCustomer);
                if (result.contains(SUCCESS_S))
                    result = "Success " + cisco.getIP() + " " + DELETE_S + " " + result + LINE;
                else
                    result = "[Error] DoClientOnCiscoThread->run->deleteClient \n" +
                            "[Error] " + cisco.getIP() + " " + DELETE_S + " " + result + LINE;
                resultMap.put(cisco.getIP(), result);
            }
        } // ** if correct

        if (idLineOnCurrentProcess > 0)
            runningFrame.removeLine(idLineOnCurrentProcess);

    } // ** run()
} // ** class DoClientOnSwitchThread

