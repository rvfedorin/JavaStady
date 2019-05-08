package chapter14;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

public class SwingWorkerTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(()-> {
            JFrame frame = new SwingWorkerFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });

    }
} // ** SwingWorkerTest

class SwingWorkerFrame extends JFrame {
    private JFileChooser chooser;
    private JTextArea textArea;
    private JLabel statusLine;
    private JMenuItem open;
    private JMenuItem cancel;
    private SwingWorker<StringBuilder, ProgressData> textReader;
    private static final int TEXT_ROWS = 20;
    private static final int TEXT_COLUMNS = 60;

    SwingWorkerFrame(){
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));

        textArea = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
        add(new JScrollPane(textArea));

        statusLine = new JLabel(" ");
        add(statusLine,BorderLayout.SOUTH);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("Tools");
        menuBar.add(menu);

        open = menu.add("Open");
        open.addActionListener(e -> {
            int result = chooser.showOpenDialog(null);

            if(result == JFileChooser.APPROVE_OPTION) {
                textArea.setText(" ");
                open.setEnabled(false);
                textReader = new TextReader(chooser.getSelectedFile());
                textReader.execute();
                cancel.setEnabled(true);
            }
        });

        cancel = menu.add("Cancel");
        cancel.setEnabled(false);
        cancel.addActionListener(e -> {
            textReader.cancel(true);
        });
        pack();
    } // ** SwingWorkerFrame()

    private class ProgressData{
        int number;
        String line;
    }

    private class TextReader extends SwingWorker<StringBuilder, ProgressData>{
        private File textFile;
        private StringBuilder text;

        TextReader(File textFile) {
            this.textFile = textFile;
            text = new StringBuilder();
        }

        @Override
        protected StringBuilder doInBackground() throws Exception {
            int lineNumber = 0;
            try(Scanner in = new Scanner(new FileInputStream(textFile))) {
                while (in.hasNextLine()) {
                    String line = in.nextLine();
                    lineNumber++;
                    text.append(line).append("\n");
                    ProgressData progressData = new ProgressData();
                    progressData.number = lineNumber;
                    progressData.line = line;
                    publish(progressData);
                    Thread.sleep(100);
                }
            }
            return text;
        } // ** doInBackground()

        @Override
        protected void process(List<ProgressData> data) {
            if(isCancelled()) return;
            StringBuilder b = new StringBuilder();
            statusLine.setText("" + data.get(data.size()-1).number);
            for(ProgressData p: data) b.append(p.line).append("\n");
            textArea.append(b.toString());
        }

        @Override
        protected void done() {
            try{
                StringBuilder result = get(); // StringBuilder text
                textArea.setText(result.toString());
                statusLine.setText("Done.");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (CancellationException ex) {
                textArea.setText("");
                statusLine.setText("Cancelled");
            } catch (ExecutionException ex) {
                statusLine.setText("" + ex.getCause());
            }
            cancel.setEnabled(false);
            open.setEnabled(true);
        } // ** done()
    } // ** class TextReader
} // ** class SwingWorkerFrame
