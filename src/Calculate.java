import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.*;
import javax.swing.*;

public class Calculate {
    private JFrame frame;
    private JPanel panel;
    private JTextField textResult;
    private JTextField textInput;

    public static void main(String[] args) {
        Calculate gui = new Calculate();
        gui.setupGui();
    }

    private void setupGui(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Перевод из Bytes в Мбит/c");


        BorderLayout layout = new BorderLayout();
        panel = new JPanel(layout);

//        Box box = new Box(BoxLayout.Y_AXIS);
        JPanel topPanel = new JPanel();

        textInput = new JTextField(10);
        textInput.addMouseListener(new MyMouseListener());
        textResult = new JTextField(10);
        JLabel equalSign = new JLabel(" = ");
        JLabel TitleStr = new JLabel("Перевод из Bytes в Мбит/c");
        JPanel title = new JPanel();
        title.add(TitleStr);

        JButton calc = new JButton("Посчитать");
        calc.addActionListener(new CalcButton());

        topPanel.add(textInput);
        topPanel.add(equalSign);
        topPanel.add(textResult);

        panel.add(BorderLayout.NORTH, title);
        panel.add(BorderLayout.CENTER, topPanel);
        panel.add(BorderLayout.SOUTH, calc);

        frame.getContentPane().add(panel);

        frame.setSize(280, 115);
        frame.setVisible(true);

    }  // private void setupGu()

    class CalcButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ev) {
            try {
                float fromBytes = Integer.parseInt(textInput.getText().trim());
                float result = fromBytes * 8 / 1024 / 1024;
                String sResult = String.format("%.2f", result);
//                System.out.println(sResult);  // 9859870 = 75,2248382568359375
                textResult.setText(sResult);
            } catch (Exception ex) {
                textResult.setText("Ошибка вычисления.");
//                System.out.println("error");
            }
        }
    }  // class CalcButton implements ActionListener

    class MyMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent ev) {
            if (ev.getButton() == MouseEvent.BUTTON3) {
                Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                DataFlavor dataFlavor = DataFlavor.stringFlavor;
                try {
                    String temp = clpbrd.getData(dataFlavor).toString().trim();
                    textInput.setText(temp);
                } catch (Exception ex) {
                    textInput.setText("Error read buffer.");
                }

            }
        } // public void mouseClicked(MouseEvent ev)
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }  // class MyMouseListener implements MouseListener
}  // public class Calculate
