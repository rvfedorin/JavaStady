package MyWork.MenuAction;

import javax.swing.*;
import java.awt.*;

public class AboutDialog extends JDialog {
    public AboutDialog(JFrame owner) {
        super(owner, "About", true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(owner.getLocation());

        JPanel text = new JPanel();
        text.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        text.add(new JLabel("<html>" +
                "<h3><i>Работа с клиентам v.1.0  </i></h3>" +
                "<hr>" +
                "By Roman V. Fedorin" +
                "</html>"));
        add(text, BorderLayout.CENTER);
        JPanel newPanel = new JPanel();
        JButton okButton = new JButton("Ok");
        okButton.addActionListener(e -> this.dispose());
        newPanel.add(okButton);
        add(newPanel, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }
} // class AboutDialog