package chapter7;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.*;
import java.util.logging.*;

public class LoggingImageViewer {
    public static void main(String[] args) {
        if (System.getProperty("java.util.logging.config.class") == null &&
        System.getProperty("java.util.logging.config.file") == null) {
            try {
                Logger.getLogger("chapter7.Logging").setLevel(Level.ALL);
                final int LOG_ROTATION_COUNT = 10;
                Handler handler = new FileHandler("./LoggingImageViewer.log", 0, LOG_ROTATION_COUNT);
                Logger.getLogger("chapter7.Logging").addHandler(handler);
            } catch (IOException ex) {
                Logger.getLogger("chapter7.Logging").log(Level.SEVERE, "Can't create log file handler.", ex);
            } // catch
        } // if

        EventQueue.invokeLater(() -> {
            Handler winHandler = new WindowHandler();
            winHandler.setLevel(Level.ALL);
            Logger.getLogger("chapter7.Logging").addHandler(winHandler);

            JFrame frame = new ImageViewerFrame();
            frame.setTitle("LoggingImageViewer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Logger.getLogger("chapter7.Logging").fine("Showing frame.");
            frame.setVisible(true);
                });
    } // main
} // LoggingImageViewer

class ImageViewerFrame extends JFrame{
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 400;

    private JLabel label;
    private static Logger logger = Logger.getLogger("chapter7.Logging");

    public ImageViewerFrame() {
        logger.entering("ImageViewerFrame", "<init>");
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);

        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(new FileOpenListener());
        menu.add(openItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.fine("Exiting.");
                System.exit(0);
            }
        });
        menu.add(exitItem);

        label = new JLabel();
        this.add(label);
        logger.exiting("ImageViewerFrame", "<init>");

    } // constructor ImageViewerFrame()

    private class FileOpenListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            logger.entering("ImageViewerFrame.FileOpenListener", "actionPerformed", e);

            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));

            // accept all files ending with .gif
            chooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.getName().toLowerCase().endsWith(".gif") || f.isDirectory();
                }

                @Override
                public String getDescription() {
                    return "GIF Image.";
                }
            });

            // show chooser dialog
            int r = chooser.showOpenDialog(ImageViewerFrame.this);

            // if the file accepted, set it as a icon of the label
            if (r == JFileChooser.APPROVE_OPTION) {
                String name = chooser.getSelectedFile().getPath();
                logger.log(Level.FINE, "Reading file {0}", name);
                label.setIcon(new ImageIcon(name));
            } else {
                logger.fine("File open dialog canceled.");
            } // if (r)
            logger.exiting("ImageViewerFrame.FileOpenListener", "actionPerformed");
        } // actionPerformed
    } // FileOpenListener

} // ImageViewer

class WindowHandler extends StreamHandler {
    private JFrame frame;
    private final JTextArea output;

    public WindowHandler() {
        frame = new JFrame();
        output = new JTextArea();
        output.setEditable(false);
        frame.setSize(300, 300);
        frame.add(new JScrollPane(output));
        frame.setFocusable(false);
        frame.setVisible(true);

        this.setOutputStream(new OutputStream() {
            @Override
            public void write(int b) {} // not called
            public void write(byte[] b, int off, int len) {
                output.append(new String(b, off, len));
            }
        });
    } // constructor WindowHandler

    @Override
    public void publish(LogRecord record) {
        if(!frame.isVisible()) return;
        super.publish(record);
        flush();
    } // publish()
} // WindowHandler