package MyWork.Actions;

import MyWork.CurrentlyRunningFrame;
import MyWork.NodesClass.Customer;
import MyWork.NodesClass.Mobibox;

import java.util.concurrent.ConcurrentHashMap;

import static MyWork.Config.*;
import static MyWork.Config.DELETE_S;
import static MyWork.Config.LINE;

public class DoClientOnMBThread implements Runnable {
    private Customer aCustomer;
    private Mobibox rootMB;
    private String rootMBIP;
    private boolean correct = true;
    private String aToDo;
    private CurrentlyRunningFrame runningFrame;
    private ConcurrentHashMap<String, String> resultMap;

    public DoClientOnMBThread(Customer customer,
                              String toDo,
                              CurrentlyRunningFrame fr,
                              ConcurrentHashMap<String, String> resultMap,
                              String enterPass) {
        this.aCustomer = customer;
        this.aToDo = toDo; // delete or create CREATE_S or DELETE_S
        this.runningFrame = fr;
        this.resultMap = resultMap;
        this.rootMBIP = aCustomer.getCity().getLanMB().split("X")[0];

        if (rootMBIP == null || rootMBIP.length() < 7)
            correct = false;
        else
            rootMB = new Mobibox(rootMBIP, enterPass.toCharArray());
    } // ** constructor

    @Override
    public void run() {
        int idLineOnCurrentProcess = -1;
//        super.run();

        if (correct) {
            Thread.currentThread().setName("Do on MB " + rootMBIP);
            String message = aToDo + " на МБ " + rootMBIP;
            idLineOnCurrentProcess = runningFrame.addLine(message, Thread.currentThread());

            if (aToDo.equals(CREATE_S)) { // <-------------------- CREATE SECTION
                String result = rootMB.createClient(aCustomer);
                if (result.contains(SUCCESS_S))
                    result = "Success " + aCustomer.getIPConnection() + " " + CREATE_S + " " + result + LINE;
                else
                    result = "[Error] DoClientOnMBThread->run->createClient \n" +
                            "[Error] " + aCustomer.getIPConnection() + " " + CREATE_S + " " + result + LINE;

                resultMap.put(aCustomer.getIPConnection(), result);

            } else if (aToDo.contains(DELETE_S)) { // <-------------------- DELETE SECTION
                String result = rootMB.deleteClient(aCustomer);
                if (result.contains(SUCCESS_S))
                    result = "Success " + aCustomer.getIPConnection() + " " + DELETE_S + " " + result + LINE;
                else
                    result = "[Error] DoClientOnMBThread->run->deleteClient \n" +
                            "[Error] " + aCustomer.getIPConnection() + " " + DELETE_S + " " + result + LINE;
                resultMap.put(aCustomer.getIPConnection(), result);
            }
        } // ** if correct

        if (idLineOnCurrentProcess > 0)
            runningFrame.removeLine(idLineOnCurrentProcess);

    } // ** run()
} // ** class DoClientOnSwitchThread
