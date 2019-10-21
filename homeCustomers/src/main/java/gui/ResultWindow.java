/*
 * Created by Roman V. Fedorin
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import tools.ExtendedTextArea;

/**
 *
 * @author Wolf
 */
public class ResultWindow extends JFrame {

    private String message;
    private ExtendedTextArea textField;

    public ResultWindow(JFrame parent) {
        this.message = "";
        this.textField = new ExtendedTextArea(message, 14, 80);
        this.textField.setFont(new Font("Monospaced", Font.PLAIN, 12));

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setTitle("Результат выполнения.");
        System.out.println(parent.getX() + " " + parent.getWidth() + " " + parent.getY());
        setLocation(parent.getX() + parent.getWidth() + 10, parent.getY() - 5);

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(e -> {
            setVisible(false);
        });

        add(new JScrollPane(textField), BorderLayout.CENTER);
        add(okButton, BorderLayout.SOUTH);
        pack();
    }

    public void showResult(String toShow) {
        setVisible(true);
        textField.setText(toShow);
    }
}
