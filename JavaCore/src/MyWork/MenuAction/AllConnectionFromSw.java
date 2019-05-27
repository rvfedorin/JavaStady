package MyWork.MenuAction;

import MyWork.EventPrintFrame;
import MyWork.ExtendStandart.ExtendedTextField;
import MyWork.Intranet.ExcelIntranet;
import MyWork.Intranet.Intranet;
import MyWork.Intranet.WebIntranet;
import MyWork.NodesClass.CitiesComboBox;
import MyWork.Verifiers.IPVerifier;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

import static MyWork.Config.*;
import static MyWork.Config.CITIES;
import static MyWork.Config.INTRANET_TYPE.EXCEL;
import static MyWork.Config.LINE;

public class AllConnectionFromSw extends JFrame{
    private char[] passKey;
    private EventPrintFrame toPrint;
    private String ipSw;
    private String city;
    private boolean onlySw;

    public AllConnectionFromSw(EventPrintFrame toPrint, char[] pass) {
        this.toPrint = toPrint;
        this.passKey = pass;

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (dimension.getWidth() - getWidth())/3;
        int y = (int) (dimension.getHeight() - getHeight())/3;
        this.setLocation(x, y);

        this.setTitle("Все подключения от свитча.");
        this.setResizable(false);

        CitiesComboBox citiesComboBox = new CitiesComboBox();
        citiesComboBox.setPreferredSize(new Dimension(100, 20));

        JLabel ipSwJL = new JLabel("IP свитча: ");
        ipSwJL.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

        ExtendedTextField ipSwField = new ExtendedTextField(20);
        ipSwField.setInputVerifier(new IPVerifier(ipSwField));

        JCheckBox withLinksField = new JCheckBox("Показать только свитчи");
        withLinksField.setSelected(true);

        JButton okButton = new JButton("ok");
        okButton.addActionListener(e -> {
            if(ipSwField.getInputVerifier().verify(ipSwField)) {
                ipSw = ipSwField.getText();
                onlySw = withLinksField.isSelected();
                city = CITIES_BY_NAME.get(citiesComboBox.getItemAt(citiesComboBox.getSelectedIndex()));
                getAllConnectionFromSwitch();
                this.dispose();
            }
        });

        JPanel inputPanel = new JPanel(new GridLayout(2,1));
        JPanel labelPanel = new JPanel(new GridLayout(2,1));

        labelPanel.add(ipSwJL);
        labelPanel.add(new JLabel());
        inputPanel.add(ipSwField);
        inputPanel.add(withLinksField);

        JPanel allInput = new JPanel();

        allInput.add(labelPanel);
        allInput.add(inputPanel);

        this.add(citiesComboBox, BorderLayout.NORTH);
        this.add(allInput, BorderLayout.CENTER);
        this.add(okButton, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
        ipSwField.requestFocusInWindow();
    }

    private void getAllConnectionFromSwitch(){
        Intranet intranet = null;
        if (CURRENT_INTRANET_TYPE == EXCEL) {
            try {
                intranet = new ExcelIntranet(passKey, CITIES.get(city));
            } catch (FileNotFoundException ex) {
                toPrint.printEvent(LINE);
                toPrint.printEvent(ex.toString());
                toPrint.printEvent(LINE);
            }
        } else {
            intranet = new WebIntranet(passKey, CITIES.get(city));
        }

        if(intranet != null && ipSw != null) {
            toPrint.printEvent(LINE);
            toPrint.printEvent(intranet.allConnectionFromSwitch(ipSw, onlySw));
            toPrint.printEvent(LINE);
        } else {
            toPrint.printEvent(LINE);
            toPrint.printEvent("[Error] class AllConnectionFromSw -> getAllConnectionFromSwitch -> if(intranet != null)");
            toPrint.printEvent(LINE);
        }
    }
}
