import javax.swing.*;
import java.awt.*;

class AuthDialog extends JDialog {
    private JTextField loginField;
    private JPasswordField passField;
    private boolean okClick;

    AuthDialog(JFrame owner){
        this(owner, "Authentication");

    } // ** const AuthDialog(JFrame owner, String title)

    AuthDialog(JFrame owner, String title){
        super(owner, title, true);
        okClick = false;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation(owner.getLocation());

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        panel.setLayout(new GridLayout(1,1));

        loginField = new JTextField();
        passField = new JPasswordField();

        panel.add(new Label("Password: "));
        panel.add(passField);

        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("Ok");
        okButton.addActionListener(e -> {
            this.dispose();
//            this.setVisible(false);
        });
        this.getRootPane().setDefaultButton(okButton);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            System.exit(0);
        });
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);

    } // ** const AuthDialog(JFrame owner, String title)

    boolean isSuccessAuth() {
        if(!okClick) {
            return true;
        }
        return false;
    }

    char[] getPass(){
        return passField.getPassword();
    }
} // ***** class AuthDialog
