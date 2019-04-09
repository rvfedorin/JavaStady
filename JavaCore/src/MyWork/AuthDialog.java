package MyWork;

import javax.swing.*;
import java.awt.*;

import static MyWork.Config.*;
import static MyWork.CryptDecrypt.correct;

class AuthDialog extends JDialog {
    private JTextField loginField;
    private JPasswordField passField;
    private boolean okAuth;
    private boolean okClick;

    AuthDialog(JFrame owner){
        this(owner, "Authentication");

    } // ** const AuthDialog(JFrame owner, String title)

    AuthDialog(JFrame owner, String title){
        super(owner, title, true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(owner.getLocation());

        okAuth = false;
        okClick = false;

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        panel.setLayout(new GridLayout(2,2));

        loginField = new JTextField();
        passField = new JPasswordField();

        panel.add(new Label("User name: "));
        panel.add(loginField);
        panel.add(new Label("Password: "));
        panel.add(passField);

        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("Ok");
        okButton.addActionListener(e -> {
            okClick = true;
            this.dispose();
//            this.setVisible(false);
        });
        this.getRootPane().setDefaultButton(okButton);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            okAuth = false;
            this.dispose();
        });
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);

    } // ** const AuthDialog(JFrame owner, String title)

    boolean isSuccessAuth() {
        if(!okClick) {
            okAuth = false;
        } else {
            String login = loginField.getText().trim();
            String pass = String.valueOf(passField.getPassword());

            if (login.equals(RUN_LOGIN) && correct(pass, RUN_PASS)) {
                okAuth = true;
            }
        }
        return okAuth;
    }
} // ***** class AuthDialog
