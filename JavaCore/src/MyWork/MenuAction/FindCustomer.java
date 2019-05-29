package MyWork.MenuAction;

import MyWork.EventPrintFrame;
import MyWork.ExtendStandart.ExtendedTextField;
import MyWork.Intranet.ExcelIntranet;
import MyWork.NodesClass.CitiesComboBox;
import MyWork.Verifiers.IPVerifier;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

import static MyWork.Config.CITIES;
import static MyWork.Config.CITIES_BY_NAME;

public class FindCustomer extends JFrame{
    private char[] passKey;
    private EventPrintFrame toPrint;
    private String mnemokod;
    private String city;

    public FindCustomer(EventPrintFrame toPrint, char[] passKey) {
        this.toPrint = toPrint;
        this.passKey = passKey;

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (dimension.getWidth() - getWidth())/3;
        int y = (int) (dimension.getHeight() - getHeight())/3;
        this.setLocation(x, y);

        this.setTitle("Поиск клиента.");
        this.setResizable(false);

        CitiesComboBox citiesComboBox = new CitiesComboBox();
        citiesComboBox.setPreferredSize(new Dimension(100, 20));

        JLabel ipSwJL = new JLabel("Мнемокод: ");
        ipSwJL.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

        ExtendedTextField mnemoField = new ExtendedTextField(20);

        JButton okButton = new JButton("ok");
        okButton.addActionListener(e -> {
            mnemokod = mnemoField.getText();
            city = CITIES_BY_NAME.get(citiesComboBox.getItemAt(citiesComboBox.getSelectedIndex()));
            findCustomer();
            this.dispose();
        });

        JPanel inputPanel = new JPanel(new GridLayout(1,1));
        JPanel labelPanel = new JPanel(new GridLayout(1,1));

        labelPanel.add(ipSwJL);
        inputPanel.add(mnemoField);

        JPanel allInput = new JPanel();

        allInput.add(labelPanel);
        allInput.add(inputPanel);

        this.add(citiesComboBox, BorderLayout.NORTH);
        this.add(allInput, BorderLayout.CENTER);
        this.add(okButton, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
        mnemoField.requestFocusInWindow();
    } // ** constructor

    private void findCustomer() {
        try {
            ExcelIntranet intranet = new ExcelIntranet(passKey, CITIES.get(city));
            String customerFrom = intranet.findClient(mnemokod);
            toPrint.pDate();
            toPrint.printEvent(customerFrom);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
