import gui.MainFrame;

public class StartPoint {
    public static void main(String[] args) {

        MainFrame mainFrame = new MainFrame();
        AuthDialog authDialog = new AuthDialog(mainFrame);
        if(authDialog.isSuccessAuth()) {
            mainFrame.setKey(authDialog.getPass());
            mainFrame.createGUI();
            mainFrame.setVisible(true);
        }
    }
}
