package MyWork;

import static MyWork.Config.IP_MB_S;
import static MyWork.Config.IP_SWITCH_S;

public class MainPanelMB extends MainPanelOptic {
    MainPanelMB(MainWindow mainWindow) {
        super(mainWindow);
        inputPanel.allLB.get(IP_SWITCH_S).setText(IP_MB_S);
    }
} // ** MainPanelMB
