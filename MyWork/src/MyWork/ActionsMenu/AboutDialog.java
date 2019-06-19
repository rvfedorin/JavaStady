package MyWork.ActionsMenu;

import javax.swing.*;
import java.awt.*;

import static MyWork.Config.*;

public class AboutDialog extends JDialog {
    public AboutDialog(JFrame owner) {
        super(owner, "About", true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(owner.getLocation());

        JPanel text = new JPanel();
        text.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        text.add(new JLabel("<html>" +
                "<h4>Трудовые будни v."+ VERSION +"  </h4>" +
                "<hr>" +
                "Powered by Roman V. Fedorin" +
                "<ht>" +
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