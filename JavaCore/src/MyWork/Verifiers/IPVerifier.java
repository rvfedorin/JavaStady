package MyWork.Verifiers;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

import static MyWork.Config.IP_PATTERN;

public class IPVerifier extends InputVerifier {
    private Border defaultBorder;

    public IPVerifier(JComponent input) {
        defaultBorder = input.getBorder();
    }

    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        if(IP_PATTERN.matcher(text).matches()) {
            input.setBorder(defaultBorder);
            return true;
        } else {
            input.setBorder(BorderFactory.createLineBorder(Color.RED));
            return false;
        }
    }
}
