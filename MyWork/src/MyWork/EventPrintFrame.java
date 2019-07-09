package MyWork;

import MyWork.ExtendStandart.ExtendedTextArea;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static MyWork.Config.LOG_FILE;

public class EventPrintFrame extends JFrame {
    private ExtendedTextArea textField;
    private StringBuilder unsavedText;
    private String logName = null;

    EventPrintFrame() {
        unsavedText = new StringBuilder();
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//        this.setResizable(false);
        this.setTitle("Events");
        this.setLocation(this.getX() + 10, this.getY() + 10);

        textField = new ExtendedTextArea(15, 70);
        JScrollPane scrollPane = new JScrollPane(textField);

        this.add(scrollPane);
        this.pack();

//        this.setVisible(true);
    }

    public synchronized void printEvent(String text) {
        EventQueue.invokeLater(() -> {
            textField.append(text + "\n");
            unsavedText.append(text).append("\n");

            if(unsavedText.toString().split("\n").length > 10) {
                if (!saveToFile(unsavedText.toString())) {
                    textField.append("\n[Error] Write to log.\n");
                } else {
                    unsavedText = new StringBuilder();
                }
            } // if we have many not save lines
        });
    } // ** printEvent()

    public synchronized void pDate() {
        Date date = new Date();
        SimpleDateFormat dateView = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        StringBuilder result = new StringBuilder();
        result.append("\t\t+++++++++++++++++++++++++++\n");
        result.append("\t\t[DATE] ").append(dateView.format(date)).append(" [DATE]\n");
        result.append("\t\t+++++++++++++++++++++++++++\n");

        EventQueue.invokeLater(() -> {
            unsavedText.append(result.toString());
            textField.append(result.toString());
        });
    }

    private boolean saveToFile(String text) {

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String logNameTemp = LOG_FILE + dateFormat.format(date) + ".txt";

        if(logName != null && logName.equals(logNameTemp)) {
            return saveOperation(logName, text);
        }else if (logName != null){
            boolean saveResult = saveOperation(logName, text);
            logName = logNameTemp;
            return saveResult;
        } else {
            logName = logNameTemp;
            return saveOperation(logName, text);
        }
    } // ** saveToFile(String text)

    private boolean saveOperation(String fileName, String text) {
        File log = new File(fileName);
        try (FileWriter fw = new FileWriter(log, true)) {
            fw.write(text + "\n");
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    } // ** saveOperation()

    void saveToFile() {
        saveToFile(unsavedText.toString());
    } // ** saveToFile(String text)
}
