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
import static MyWork.Config.INTRANET_TYPE.EXCEL;

public class FullPathToSw extends JFrame {
    private char[] passForWeb;
    private EventPrintFrame toPrint;
    private String ipSw;
    private String city;
    private boolean withLinks;

    public FullPathToSw(EventPrintFrame toPrint, char[] passForWeb) {
        this.toPrint = toPrint;
        this.passForWeb = passForWeb;

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (dimension.getWidth() - getWidth())/3;
        int y = (int) (dimension.getHeight() - getHeight())/3;
        this.setLocation(x, y);

        this.setTitle("Маршрут до свитча.");
        this.setResizable(false);

        CitiesComboBox citiesComboBox = new CitiesComboBox();
        citiesComboBox.setPreferredSize(new Dimension(100, 20));

        JLabel ipSwJL = new JLabel("IP свитча: ");
        ipSwJL.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

        ExtendedTextField ipSwField = new ExtendedTextField(20);
        ipSwField.setInputVerifier(new IPVerifier(ipSwField));

        JCheckBox withLinksField = new JCheckBox("Показать с линками");
        withLinksField.setSelected(true);

        JButton okButton = new JButton("ok");
        okButton.addActionListener(e -> {
            if(ipSwField.getInputVerifier().verify(ipSwField)) {
                ipSw = ipSwField.getText();
                withLinks = withLinksField.isSelected();
                city = CITIES_BY_NAME.get(citiesComboBox.getItemAt(citiesComboBox.getSelectedIndex()));
                getFullPathToSw();
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

    private void getFullPathToSw(){
        if(withLinks){
            toPrint.printEvent(LINE);
            toPrint.printEvent("С линками");
            toPrint.printEvent("Версия с линками пока не готова.");
            toPrint.printEvent(LINE);
        } else {

            Intranet intranet = null;
            if (CURRENT_INTRANET_TYPE == EXCEL) {
                try {
                    intranet = new ExcelIntranet(CITIES.get(city));
                } catch (FileNotFoundException ex) {
                    toPrint.printEvent(LINE);
                    toPrint.printEvent(ex.toString());
                    toPrint.printEvent(LINE);
                }
            } else {
                intranet = new WebIntranet(passForWeb, CITIES.get(city));
            }

            if(intranet != null && ipSw != null) {
                toPrint.printEvent(LINE);
                toPrint.printEvent(intranet.getFullPath(ipSw));
                toPrint.printEvent(LINE);
            } else {
                toPrint.printEvent(LINE);
                toPrint.printEvent("[Error] class FullPathToSw -> getFullPathToSw -> if(intranet != null)");
                toPrint.printEvent(LINE);
            }
        }
    }
}
