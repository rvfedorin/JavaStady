package MyWork.MenuAction;

import javax.swing.*;
import java.awt.*;

public class ManualDialog extends JDialog{
    public ManualDialog(JFrame owner) {
        super(owner, "Help", true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(owner.getLocation());

        JPanel text = new JPanel();
        text.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        text.add(new JLabel("<html>" +
                "<p>If you need specify the root switch:</p>" +
                "<p>'X.X.X.XrootX.X.X.X' where X.X.X.X after word 'root' is the root switch</p>" +
                "<p>Mobibox is added in DB as \"95.80.120.13X172.17.239.129X172.16.44.235X28\" where:</p>" +
                "IP of central mikrotik<b>X</b>" +
                "IP Gw for clients net<b>X</b>" +
                "Switch for unnumbered port<b>X</b>" +
                "unnubered port on switch" +
                "<p>If you specify 'free' instead the numbor of ports - app will find free port</p>" +
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
}
