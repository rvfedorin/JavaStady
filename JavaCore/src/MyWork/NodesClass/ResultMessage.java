package MyWork.NodesClass;

import MyWork.ExtendStandart.ExtendedTextArea;

import javax.swing.*;
import java.awt.*;

import static MyWork.Config.X_SCREEN_SIZE;
import static MyWork.Config.Y_SCREEN_SIZE;

public class ResultMessage extends JFrame {

    public ResultMessage (String message) {
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            int x = (int) (X_SCREEN_SIZE - getWidth())/3;
            int y = (int) (Y_SCREEN_SIZE - getHeight())/3;
            this.setLocation(x, y);

            this.setTitle("Результат выполнения операции.");
            ExtendedTextArea textArea = new ExtendedTextArea(10, 60);
            JButton okButton = new JButton("ok");
            okButton.addActionListener(e -> this.dispose());
            textArea.append(message + "\n");

            this.add(textArea, BorderLayout.CENTER);
            this.add(okButton, BorderLayout.SOUTH);

            this.pack();
            this.setVisible(true);
        }
}