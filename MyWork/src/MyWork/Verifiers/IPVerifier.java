package MyWork.Verifiers;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.util.regex.Pattern;

import static MyWork.Config.IP_PATTERN;

public class IPVerifier extends InputVerifier {
    private Border defaultBorder;

    public IPVerifier(JComponent input) {
        defaultBorder = input.getBorder();
    }

    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        Pattern withNewRootP = Pattern.compile(IP_PATTERN + "root" + IP_PATTERN);

        if(IP_PATTERN.matcher(text).matches() || withNewRootP.matcher(text).matches()) {
            input.setBorder(defaultBorder);
            return true;
        } else {
            input.setBorder(BorderFactory.createLineBorder(Color.RED));
            return false;
        }
    }
}
