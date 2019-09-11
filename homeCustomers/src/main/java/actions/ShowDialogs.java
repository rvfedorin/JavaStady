package actions;

import javax.swing.*;

public class ShowDialogs {

    public static void info(String message) {
        JOptionPane.showMessageDialog(null, message, "Результат", JOptionPane.INFORMATION_MESSAGE);
    }
}
