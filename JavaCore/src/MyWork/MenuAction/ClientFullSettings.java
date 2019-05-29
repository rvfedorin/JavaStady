package MyWork.MenuAction;

import MyWork.EventPrintFrame;
import MyWork.ExtendStandart.ExtendedTextField;
import MyWork.NodesClass.Region;

import javax.swing.*;

import java.awt.*;

import static MyWork.Config.CITIES;
import static MyWork.Config.LINE;

public class ClientFullSettings extends JFrame {

    public ClientFullSettings(EventPrintFrame toPrint) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (dimension.getWidth() - getWidth())/3;
        int y = (int) (dimension.getHeight() - getHeight())/3;
        this.setLocation(x, y);

        this.setTitle("Настройки клиента.");
        JLabel label = new JLabel("Клиент: ");
        label.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        ExtendedTextField textField = new ExtendedTextField(30);
        JButton okButton = new JButton("ok");
        okButton.addActionListener(e -> {
            String data = textField.getText();
            getFullSettings(toPrint, data);
            this.dispose();
        });

        this.add(label, BorderLayout.WEST);
        this.add(textField, BorderLayout.CENTER);
        this.add(okButton, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
    }

    private void getFullSettings(EventPrintFrame toPrint, String data) {
        // data = "Orel-IPSidK74           in      5120    95.80.109.176           -unnumbered";
        String[] cl = data.split("\\s+");

        if (cl.length > 3) {
            toPrint.printEvent(LINE);
            toPrint.pDate();
            if(data.contains("number")) {
                toPrint.printEvent("IP " + cl[3]);
                toPrint.printEvent("MASK 255.255.255.0");
                String[] ip = cl[3].split("\\.");
                if(ip.length == 4)
                    toPrint.printEvent("GW " + ip[0] + "." + ip[1] + "." + ip[2] + "." + "1");
            } else if (data.contains("/")) {
                String[] ip = cl[3].split("\\.");
                String[] ipAndCidr = ip[3].split("/");
                int ipCount = (int) Math.pow(2, (32 - Integer.valueOf(ipAndCidr[1])));
                int mask = 256 - ipCount;
                int okt4 = Integer.valueOf(ipAndCidr[0]);
                for(int i=2; i < (ipCount-1); i++)
                    toPrint.printEvent("IP " +ip[0] + "." + ip[1] + "." + ip[2] + "." + (okt4+i));
                toPrint.printEvent("MASK 255.255.255." + mask);
                toPrint.printEvent("GW " +ip[0] + "." + ip[1] + "." + ip[2] + "." + (okt4+1));
            }
            String prefix = cl[0].split("-")[0];
            Region city = CITIES.getOrDefault(prefix, null);
            if (city != null) {
                toPrint.printEvent("DNS1 " + city.getCoreUnix());
            }
            toPrint.printEvent("DNS2 213.170.64.33");
            toPrint.printEvent(LINE);
        } // ** if (cl.length > 3)
    } // ** getFullSettings()
} // ** class ClientFullSettings
