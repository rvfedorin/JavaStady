package MyWork.Actions;

import MyWork.CurrentlyRunningFrame;
import MyWork.EventPrintFrame;
import MyWork.ExtendStandart.ExtendedOpenFile;
import MyWork.NodesClass.Region;

import java.io.BufferedReader;
import java.io.IOException;

import static MyWork.Config.BLOCK;
import static MyWork.Config.CITIES;
import static MyWork.Config.LINE;
import static MyWork.Tools.CiscoSpeedFormat.getFormattedSpeed;
import static MyWork.Tools.SpeedFileParser.getParsedString;

public class ChangeSpeedThread extends Thread {
    private EventPrintFrame frameEvent;
    private CurrentlyRunningFrame runningFrame;

    public ChangeSpeedThread(String name, EventPrintFrame printEvent, CurrentlyRunningFrame fr) {
        super(name);
        this.frameEvent = printEvent;
        this.runningFrame = fr;
        this.start();
    }

    @Override
    public void run() {
        int idLineOnCurrentProcess = runningFrame.addLine("Смена скоростей.", this);
        try {
            BufferedReader frSpeedFile = ExtendedOpenFile.readFile();
            if (frSpeedFile != null) readFile(frSpeedFile);
        } catch (Exception ex) {
            frameEvent.pDate();
            frameEvent.printEvent("Error in ChangeSpeedThread -> run()");
            System.out.println("Error in ChangeSpeedThread -> run()");
            throw ex;
        } finally {
            runningFrame.removeLine(idLineOnCurrentProcess);
        }
    } // ** run()

    private void readFile(BufferedReader frSpeedFile) {
        String line = "";

        do {
            try {
                line = frSpeedFile.readLine();
                if (line != null && !line.matches("^\\s*$")) {
//                    System.out.println(line);
                    String tempLine = line;
                    line = getParsedString(line);
//                    System.out.println(line);

                    String[] formattedSpeed;
                    // get prefix of mnemokod
                    String key = line.split("-")[0];
                    // if first letter in lowercase
                    if (key != null && !key.isEmpty()) {
                        key = key.substring(0, 1).toUpperCase() + key.substring(1);
                    } else {
                        frameEvent.pDate();
                        frameEvent.printEvent(tempLine);
                        frameEvent.printEvent("[!!!] Error. City key not found!");
                        frameEvent.printEvent(LINE);
                        continue;
                    }

                    String[] clientNewSpeed = line.split(" ");  // ** [0] mnemokod; [1] speed
                    // get OP
                    Region citySpeed = CITIES.getOrDefault(key, null);

                    if (citySpeed != null) { // if we have found city
                        if (clientNewSpeed.length >= 2) {
                            formattedSpeed = getFormattedSpeed("service-policy", clientNewSpeed[1]);
                        } else {
                            frameEvent.pDate();
                            frameEvent.printEvent(tempLine);
                            frameEvent.printEvent("[!!!] Error parse line speed.");
                            frameEvent.printEvent(LINE);
                            continue;
                        }

                        // ************** START REMOVE AFTER TESTS ********************************
//                        System.out.println(">" + line.trim() + "<" + " строка " + i);
//                        System.out.println("ОП " + citySpeed);
//
//                        if(formattedSpeed != null && !formattedSpeed[0].contains("Error")) {
//                            for (String s : formattedSpeed) {
//                                System.out.println(s);
//                            }
//                        } else if(formattedSpeed != null) {
//                            System.out.println(formattedSpeed[0] + formattedSpeed[1]);
//                        } else {
//                            System.out.println("getFormattedSpeed return NULL!");
//                        }
//
//                        System.out.println(LINE);
                        // ************** END REMOVE AFTER TESTS ********************************

                        // ************** START PRINT EVENT ********************************
                        frameEvent.pDate();
                        frameEvent.printEvent(tempLine);
                        frameEvent.printEvent(line);
                        frameEvent.printEvent("ОП " + citySpeed.getCity());
                        if (formattedSpeed != null && !formattedSpeed[0].contains("Error")) {
                            for (String s : formattedSpeed) {
                                frameEvent.printEvent(s);
                            }
                        } else if (formattedSpeed != null) {
                            frameEvent.printEvent(formattedSpeed[0] + formattedSpeed[1]);
                        } else {
                            frameEvent.printEvent("getFormattedSpeed return NULL!");
                        }
                        frameEvent.printEvent(LINE);
                        // ************** END PRINT EVENT ********************************

                        Thread.sleep(1000); // for test
                    }
                } // if(line != null)
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
        } while (line != null);
        frameEvent.printEvent(BLOCK);
    } // ** readFile(BufferedReader frSpeedFile)
}