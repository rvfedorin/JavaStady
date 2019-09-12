import gui.MainFrame;

import java.awt.*;

public class StartPoint {
    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            AuthDialog authDialog = new AuthDialog(mainFrame);
            if(authDialog.isSuccessAuth()) {
                mainFrame.setKey(authDialog.getPass());
                mainFrame.createGUI();
                mainFrame.setVisible(true);
            }
        });

    }
}
