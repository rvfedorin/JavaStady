package MyWork.ExtendStandart;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static MyWork.Config.SPEEDS_FILE;

public class ExtendedOpenFile {

    public static BufferedReader readFile(String fileName) {
        BufferedReader frSpeedFile = null;
        Path speedFile = Paths.get(fileName);

        try {
            frSpeedFile = Files.newBufferedReader(speedFile, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showDialog(null, "Select");
            speedFile = fileChooser.getSelectedFile().toPath();
            try {
                frSpeedFile = Files.newBufferedReader(speedFile, StandardCharsets.UTF_8);
            } catch (IOException IOex) {
                IOex.printStackTrace();
            }
        } // ** try
        return frSpeedFile;
    } // ** readFile(String)

    public static BufferedReader readFile() {
        return readFile(SPEEDS_FILE);
    } // ** readFile()

    // open file in text editor
    public static void openSystemFile(String fileName) {
        File speedFile = new File(fileName);
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(speedFile);
        } catch (Exception ex) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showDialog(null, "Select");
            speedFile = fileChooser.getSelectedFile();
            try {
                desktop.open(speedFile);
            } catch (Exception IOex) {
                IOex.printStackTrace();
            }
        } // ** try
    } // ** readFile(String)

    public static void openSystemFile() {
        openSystemFile(SPEEDS_FILE);
    } // ** readFile()

} // ** class ExtendedOpenFile
