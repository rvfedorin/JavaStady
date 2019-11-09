/*
 * Created by Roman V. Fedorin
 */
package frv.org.chapter2_11.composite;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

/**
 *
 * @author Wolf
 */
public class CompositeTestFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 400;

    private CompositeComponent canvas;
    private JComboBox<Rule> ruleCombo;
    private JSlider alphaSlider;
    private JTextField explanation;

    public CompositeTestFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        canvas = new CompositeComponent();
        add(canvas, BorderLayout.CENTER);

        ruleCombo = new JComboBox<>(new Rule[]{
            new Rule("CLEAR", " ", " "),
            new Rule("SRC", " S", " S"), new Rule("DST", " ", "DD"),
            new Rule("SRC_OVER", " S", "DS"), new Rule("DST_OVER", " S", "DD"),
            new Rule("SRC_IN", " ", " S"), new Rule("SRC_OUT", " S", " "),
            new Rule("DST_IN", " ", " D"), new Rule("DST_OUT", " ", "D "),
            new Rule("SRC_ATOP", " ", "DS"), new Rule("DST_ATOP", " S", " ")
        }
        );

        ruleCombo.addActionListener(e -> {
            Rule r = (Rule) ruleCombo.getSelectedItem();
            canvas.setRule(r.getValue());
            explanation.setText(r.getExplanation());
        });

        alphaSlider = new JSlider(0, 100, 75);
        alphaSlider.addChangeListener(e -> {
            canvas.setAlpha(alphaSlider.getValue());
        });

        JPanel panel = new JPanel();
        panel.add(ruleCombo);
        panel.add(new JLabel("Alpha"));
        panel.add(alphaSlider);
        add(panel, BorderLayout.NORTH);

        explanation = new JTextField();
        add(explanation, BorderLayout.SOUTH);

        canvas.setAlpha(alphaSlider.getValue());
        Rule r = (Rule) ruleCombo.getSelectedItem();
        canvas.setRule(r.getValue());
        explanation.setText(r.getExplanation());
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame f = new CompositeTestFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
        });
    }
}
